package com.hdt.sleepsound.utils.view

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.withStyledAttributes
import com.hdt.sleepsound.R

class RatingBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private var userRating: Int = 1
    private var maximumRating: Int = 5
    private var emptyStarDraw: Drawable? = null
    private var filledStarDraw: Drawable? = null
    private var emptyLastStarDraw: Drawable? = null
    private var filledLastStarDraw: Drawable? = null
    private var starPadding: Int = 0

    private var onRatingChangeHandler: RatingChangeListener? = null

    interface RatingChangeListener {
        fun onRatingChanged(rating: Int)
    }

    init {
        orientation = HORIZONTAL

        context.withStyledAttributes(attrs, R.styleable.RatingBar, defStyleAttr, 0) {

            maximumRating = getInt(R.styleable.RatingBar_maxRating, 5)
            emptyStarDraw = getDrawable(R.styleable.RatingBar_emptyStarDrawable)
            filledStarDraw = getDrawable(R.styleable.RatingBar_filledStarDrawable)
            emptyLastStarDraw = getDrawable(R.styleable.RatingBar_emptyLastStarDrawable)
            filledLastStarDraw =
                getDrawable(R.styleable.RatingBar_filledLastStarDrawable)
            starPadding = getDimensionPixelSize(R.styleable.RatingBar_starSpacing, 0)
            userRating = getInt(R.styleable.RatingBar_defaultRate, 1)
        }

        setupRatingViews()
    }

    private fun setupRatingViews() {
        removeAllViews()

        for (i in 0 until maximumRating - 1) {
            val starRate = createStarView(i)
            starRate.setImageDrawable(emptyStarDraw)
            addView(starRate)

            // Apply spacing between stars
            if (starPadding > 0) {
                val layoutParams = starRate.layoutParams as LayoutParams
                layoutParams.marginEnd = starPadding
                starRate.layoutParams = layoutParams
            }
        }

        val lastStarRate = createStarView(maximumRating - 1)
        lastStarRate.setImageDrawable(emptyLastStarDraw)
        addView(lastStarRate)

        updateRating()
    }

    private fun createStarView(index: Int): ImageView {
        val starRateView = ImageView(context)
        val layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, 1.0f)
        starRateView.layoutParams = layoutParams
        starRateView.setOnClickListener {
            userRating = index + 1
            updateRating()
            onRatingChangeHandler?.onRatingChanged(userRating)
        }
        return starRateView
    }

    private fun updateRating() {
        for (i in 0 until childCount - 1) {
            val starRateView = getChildAt(i) as ImageView
            val drawable = if (i < userRating) filledStarDraw else emptyStarDraw
            starRateView.setImageDrawable(drawable)
        }

        val lastStarRateView = getChildAt(childCount - 1) as ImageView
        val drawable = if (userRating == maximumRating) filledLastStarDraw else emptyLastStarDraw
        lastStarRateView.setImageDrawable(drawable)
    }

    fun setRatingChangeListener(listener: RatingChangeListener) {
        this.onRatingChangeHandler = listener
    }

    fun setRating(rating: Int) {
        this.userRating = rating
        updateRating()
    }

    fun getRating(): Int {
        return userRating
    }

    fun setMaxRating(maxRating: Int) {
        this.maximumRating = maxRating
        setupRatingViews()
    }

    fun setEmptyStarDrawable(drawable: Drawable) {
        emptyStarDraw = drawable
        setupRatingViews()
    }

    fun setFilledStarDrawable(drawable: Drawable) {
        filledStarDraw = drawable
        setupRatingViews()
    }

    fun setEmptyLastStarDrawable(drawable: Drawable) {
        emptyLastStarDraw = drawable
        setupRatingViews()
    }

    fun setFilledLastStarDrawable(drawable: Drawable) {
        filledLastStarDraw = drawable
        setupRatingViews()
    }

    fun setStarSpacing(spacing: Int) {
        this.starPadding = spacing
        setupRatingViews()
    }
}
