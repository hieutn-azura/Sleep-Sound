package com.hdt.sleepsound.ui.dialog

import android.content.ActivityNotFoundException
import android.content.DialogInterface
import android.content.Intent
import android.view.View
import android.widget.Toast
import androidx.core.net.toUri
import com.hdt.sleepsound.R
import com.hdt.sleepsound.base.BaseDialogFragment
import com.hdt.sleepsound.databinding.DialogRatingBinding
import com.hdt.sleepsound.utils.extensions.gone
import com.hdt.sleepsound.utils.extensions.rateApp
import com.hdt.sleepsound.utils.extensions.visible
import com.hdt.sleepsound.utils.view.RatingBar

class RatingDialog : BaseDialogFragment<DialogRatingBinding>() {

    private var onDismiss: (() ->  Unit)? = null

    override fun getViewBinding(): DialogRatingBinding {
        return DialogRatingBinding.inflate(layoutInflater)
    }

    override fun initView() {
        binding.tvRate.setOnClickListener {
            if (binding.ratingBar.getRating() == 0) {
                Toast.makeText(
                    getContextF(),
                    getContextF().getString(R.string.please_rate_us),
                    Toast.LENGTH_SHORT
                )
                    .show()
                return@setOnClickListener
            }
            if (binding.ratingBar.getRating() >= 4) {
                activity?.rateApp(appPreference) {
                    Toast.makeText(
                        getContextF(),
                        getContextF().getString(R.string.thanks_for_your_rating),
                        Toast.LENGTH_SHORT
                    )
                        .show()
                    runCatching { dismiss() }
                }
            } else {
                runCatching { dismiss() }
                composeEmail()
            }
        }

        binding.ratingBar.setRatingChangeListener(object : RatingBar.RatingChangeListener {
            override fun onRatingChanged(rating: Int) {
                when (rating) {
                    0 -> {
                        binding.llFocusItem.visible()
                        binding.imgRate.setImageResource(R.drawable.img_ic_rate_0)
                        binding.tvTitle.text = getString(R.string.rating_title_0)
                        binding.tvDescription.text =
                            getString(R.string.rating_description_0)
                        binding.tvRate.text = getString(R.string.rate)
                    }

                    1 -> {
                        binding.llFocusItem.visible()
                        binding.imgRate.setImageResource(R.drawable.img_ic_rate_1)
                        binding.tvTitle.text = getString(R.string.rating_title_1)
                        binding.tvDescription.text =
                            getString(R.string.rating_description_1)
                        binding.tvRate.text = getString(R.string.rate)
                    }

                    2 -> {
                        binding.llFocusItem.visible()
                        binding.imgRate.setImageResource(R.drawable.img_ic_rate_2)
                        binding.tvTitle.text = getString(R.string.rating_title_2)
                        binding.tvDescription.text =
                            getString(R.string.rating_description_2)
                        binding.tvRate.text = getString(R.string.rate)
                    }

                    3 -> {
                        binding.llFocusItem.visible()
                        binding.imgRate.setImageResource(R.drawable.img_ic_rate_3)
                        binding.tvTitle.text = getString(R.string.rating_title_3)
                        binding.tvDescription.text =
                            getString(R.string.rating_description_3)
                        binding.tvRate.text = getString(R.string.rate)
                    }

                    4 -> {
                        binding.llFocusItem.gone()
                        binding.imgRate.setImageResource(R.drawable.img_ic_rate_4)
                        binding.tvTitle.text = getString(R.string.rating_title_4)
                        binding.tvDescription.text =
                            getString(R.string.rating_description_4)
                        binding.tvRate.text = getString(R.string.rate_on_google_play)
                    }

                    else -> {
                        binding.llFocusItem.gone()
                        binding.imgRate.setImageResource(R.drawable.img_ic_rate_5)
                        binding.tvTitle.text = getString(R.string.rating_title_5)
                        binding.tvDescription.text =
                            getString(R.string.rating_description_5)
                        binding.tvRate.text = getString(R.string.rate_on_google_play)
                    }
                }
            }
        })

        binding.ivArrow.scaleX = if (isRTL()) -1f else 1f
    }

    private fun composeEmail() {
        val emailIntent = Intent(Intent.ACTION_SENDTO)
        emailIntent.data = "mailto:".toUri()
        emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf("support@azuraglobal.app"))
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Feedback")

        try {
            this.startActivity(Intent.createChooser(emailIntent, "Feedback"))
        } catch (e: ActivityNotFoundException) {
            e.printStackTrace()
        }
    }

    private fun isRTL(): Boolean {
        return this.resources.configuration.layoutDirection == View.LAYOUT_DIRECTION_RTL
    }

    override fun onDismiss(dialog: DialogInterface) {
        onDismiss?.invoke()
        super.onDismiss(dialog)

    }

    fun setListener(
        onDismiss: (() ->  Unit)? = null
    ) {
        this.onDismiss = onDismiss
    }

}