package com.hdt.sleepsound.utils.extensions

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.net.toUri
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.google.android.gms.tasks.Task
import com.google.android.play.core.review.ReviewInfo
import com.google.android.play.core.review.ReviewManagerFactory
import com.hdt.sleepsound.R
import com.hdt.sleepsound.utils.PreferenceHelper

fun Fragment.navigate(
    destination: Int,
    extraData: Bundle? = null
) {
    activity?.let {
        try {
            Navigation.findNavController(it, R.id.nav_host_fragment)
                .navigate(destination, extraData, navOptions {})
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }
}

fun Fragment.onBackPressed(runnable: Runnable) {
    activity?.onBackPressedDispatcher?.addCallback(
        viewLifecycleOwner,
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                runnable.run()
            }
        })
}

fun Fragment.popBackStack(destination: Int? = null, inclusive: Boolean = false) {
    try {
        val navController = findNavController()
        if (destination != null) {
            if (navController.currentDestination?.id == destination || navController.graph.findNode(
                    destination
                ) != null
            ) {
                navController.popBackStack(destination, inclusive)
            } else {
                Log.w("popBackStack", "Destination $destination not found in graph")
            }
        } else {
            navController.popBackStack()
        }
    } catch (ex: Exception) {
        Log.e("popBackStack", "Failed to popBackStack", ex)
    }
}

fun DialogFragment.setWidthPercent(percentage: Int) {
    val percent = percentage.toFloat() / 100
    val dm = Resources.getSystem().displayMetrics
    val rect = dm.run { Rect(0, 0, widthPixels, heightPixels) }
    val percentWidth = rect.width() * percent
    dialog?.window?.setLayout(percentWidth.toInt(), ViewGroup.LayoutParams.WRAP_CONTENT)
}

fun Fragment.showDialog(dialogFragment: DialogFragment, tag: String? = null) {
    val fm = if (this is DialogFragment) parentFragmentManager else childFragmentManager
    if (isAdded && !isDetached && activity != null) {
        try {
            dialogFragment.show(fm, tag)
        } catch (ex: IllegalStateException) {
            ex.printStackTrace()
        }
    }
}

fun Fragment.hideSoftKeyboard() {
    view?.let { v ->
        val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(v.windowToken, 0)
    }
}

fun Fragment.showToast(message: String) {
    Toast.makeText(
        context ?: return,
        message,
        Toast.LENGTH_SHORT
    ).show()
}

fun Fragment.enableFullScreen() {
    val window = requireActivity().window
    ViewCompat.setOnApplyWindowInsetsListener(requireView()) { view, insets ->
        WindowInsetsCompat.CONSUMED
    }

    WindowCompat.setDecorFitsSystemWindows(window, false)
    WindowInsetsControllerCompat(window, window.decorView).let { controller ->
        controller.hide(WindowInsetsCompat.Type.systemBars())
        controller.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
    }
}

fun Fragment.openUrl(url: String) {
    runCatching {
        Intent(Intent.ACTION_VIEW, url.toUri()).also {
            this.startActivity(it)
        }
    }
}

fun Fragment.shareApp() {
    val applicationID = requireContext().packageName
    val appPlayStoreUrl = "https://play.google.com/store/apps/details?id=$applicationID"

    val shareIntent = Intent(Intent.ACTION_SEND)
    shareIntent.type = "text/plain"
    shareIntent.putExtra(Intent.EXTRA_SUBJECT, this.getString(R.string.app_name))
    shareIntent.putExtra(
        Intent.EXTRA_TEXT,
        getString(R.string.check_out_this_amazing_app) + appPlayStoreUrl
    )

    val chooserIntent = Intent.createChooser(shareIntent, getString(R.string.share_via))
    chooserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    startActivity(chooserIntent)
}

fun Activity.rateApp(appPreference: PreferenceHelper, onFinish: () -> Unit) {
    val manager = ReviewManagerFactory.create(this)
    val request = manager.requestReviewFlow()
    request.addOnCompleteListener { task: Task<ReviewInfo> ->
        if (task.isSuccessful) {
            val reviewInfo = task.result
            val flow =
                manager.launchReviewFlow(this, reviewInfo)
            flow.addOnCompleteListener {
                appPreference.isRated = true
                Log.d("RateApp", "Rate complete")
                onFinish()
            }
        } else {
            onFinish()
        }
    }
}
