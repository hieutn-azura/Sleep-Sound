package com.hdt.sleepsound.ui.record

import android.Manifest
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaRecorder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.hdt.sleepsound.R
import com.hdt.sleepsound.base.BaseFragment
import com.hdt.sleepsound.data.model.RecordModel
import com.hdt.sleepsound.databinding.FragmentRecordBinding
import com.hdt.sleepsound.ui.dialog.SaveRecordBottomSheetFragment
import com.hdt.sleepsound.utils.extensions.gone
import com.hdt.sleepsound.utils.extensions.invisible
import com.hdt.sleepsound.utils.extensions.navigate
import com.hdt.sleepsound.utils.extensions.openSettingApplication
import com.hdt.sleepsound.utils.extensions.popBackStack
import com.hdt.sleepsound.utils.extensions.showDialog
import com.hdt.sleepsound.utils.extensions.visible
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.ext.android.inject
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class RecordFragment : BaseFragment<FragmentRecordBinding>() {

    private var cacheFile: File? = null
    private var fileName: String = ""
    private val viewModel: RecordViewModel by inject()

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val granted = permissions.entries.all { it.value }
        if (!granted) {
            val showRationale = shouldShowRequestPermissionRationale(isPermission)
            if (!showRationale) {
                openSettingApplication(getContextF())
            }
        }
    }

    private var isPermission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        Manifest.permission.READ_MEDIA_AUDIO
    } else Manifest.permission.WRITE_EXTERNAL_STORAGE

    private var recorder: MediaRecorder? = null
    private var timerJob: Job? = null


    override fun isDisplayCutout(): Boolean = true

    override fun inflateBinding(inflater: LayoutInflater): FragmentRecordBinding {
        return FragmentRecordBinding.inflate(inflater)
    }

    override fun updateUI(view: View, savedInstanceState: Bundle?) {

    }

    override fun initListener() {
        super.initListener()

        binding.btnPlay.setOnClickListener {
            if (binding.btnPlay.isActivated) {
                stopRecording()
                stopTimer()
                binding.btnPlay.gone()
                binding.btnReplay.visible()
                binding.btnSave.visible()
                binding.lottieWaveRecord.invisible()
                return@setOnClickListener
            }

            if (!checkPermissions()) {
                requestPermissions()
            } else {
                binding.btnPlay.isActivated = !binding.btnPlay.isActivated
                binding.lottieWaveRecord.visible()
                binding.tvDuration.visible()
                countTimeRecord()
                startRecording()
            }
        }

        binding.btnReplay.setOnClickListener {
            binding.btnPlay.isActivated = true
            binding.btnPlay.visible()
            binding.btnReplay.gone()
            binding.btnSave.gone()
            binding.lottieWaveRecord.visible()
            countTimeRecord()
            startRecording()
        }

        binding.btnSave.setOnClickListener {
            showDialog(SaveRecordBottomSheetFragment().apply {
                setListener {
                    lifecycleScope.launch {
                        saveToMySound(
                            saveRecordingToDownloads(it)!!
                        )
                    }
                }
            })
        }

        binding.btnBack.setOnClickListener {
            popBackStack()
        }
    }

    private fun saveToMySound(uri: Uri) {
        viewModel.insertRecord(
            RecordModel(
                id = System.currentTimeMillis().toInt(),
                name = fileName,
                uri = uri.toString()
            )
        )
        navigate(R.id.mySoundFragment)
    }

    private fun requestPermissions() {
        requestPermissionLauncher.launch(
            arrayOf(
                Manifest.permission.RECORD_AUDIO,
                isPermission
            )
        )
    }

    private fun checkPermissions(): Boolean {
        val recordAudioPermission =
            ContextCompat.checkSelfPermission(getContextF(), Manifest.permission.RECORD_AUDIO)
        val readMediaPermission = ContextCompat.checkSelfPermission(
            getContextF(),
            isPermission
        )
        return recordAudioPermission == PackageManager.PERMISSION_GRANTED &&
                readMediaPermission == PackageManager.PERMISSION_GRANTED
    }

    private fun countTimeRecord() {
        timerJob = viewLifecycleOwner.lifecycleScope.launch {
            var seconds = 0
            while (true) {
                val formatted = getContextF().getString(
                    R.string.format_timer, seconds / 3600,
                    (seconds % 3600) / 60,
                    seconds % 60
                )
                binding.tvDuration.text = formatted
                delay(1000)
                seconds++
            }
        }
    }

    private fun stopTimer() {
        timerJob?.cancel()
        timerJob = null
    }

    private fun startRecording() {
        // File cache tạm
        cacheFile = File.createTempFile(
            "recording_${System.currentTimeMillis()}", ".m4a",
            requireContext().cacheDir
        )

        recorder = MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            setAudioEncodingBitRate(128000)
            setAudioSamplingRate(44100)
            setOutputFile(cacheFile?.absolutePath)

            try {
                prepare()
                start()
            } catch (e: IOException) {
                Log.e("TAG", "startRecording error: ${e.message}")
            }
        }
    }

    private fun stopRecording() {
        try {
            recorder?.apply {
                stop()
                release()
            }
        } catch (e: Exception) {
            Log.e("TAG", "stopRecording error: ${e.message}")
        }
        recorder = null
    }

    override fun onDestroyView() {
        if (recorder != null) {
            runCatching {
                recorder?.stop()
                recorder?.reset()
                recorder?.release()
                recorder = null
            }
        }
        super.onDestroyView()
    }

    suspend fun saveRecordingToDownloads(
        finalFileName: String
    ): Uri? {
        fileName =
            if (finalFileName.endsWith(".m4a")) finalFileName else "$finalFileName.m4a"

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            // Android 10+ dùng MediaStore
            val contentValues = ContentValues().apply {
                put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
                put(MediaStore.MediaColumns.MIME_TYPE, "audio/mp4")
                put(
                    MediaStore.MediaColumns.RELATIVE_PATH,
                    Environment.DIRECTORY_DOWNLOADS
                )
            }

            val resolver = getContextF().contentResolver
            val uri = resolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues)

            uri?.let {
                withContext(Dispatchers.IO) {
                    resolver.openOutputStream(it)?.use { output ->
                        cacheFile?.inputStream().use { input ->
                            input?.copyTo(output)
                        }
                    }
                }
            }

            cacheFile?.delete()
            uri

        } else {
            // Android 9 trở xuống
            val downloadDir =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
            if (!downloadDir.exists()) downloadDir.mkdirs()

            val outputFile = File(downloadDir, fileName)

            withContext(Dispatchers.IO) {
                cacheFile?.inputStream().use { input ->
                    FileOutputStream(outputFile).use { output ->
                        input?.copyTo(output)
                    }
                }
            }

            // quét media để file hiện trong app Download
            val uri = Uri.fromFile(outputFile)
            getContextF().sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri))

            cacheFile?.delete()
            uri
        }
    }
}