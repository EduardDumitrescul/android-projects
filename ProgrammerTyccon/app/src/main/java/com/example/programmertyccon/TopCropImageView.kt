package com.example.programmertyccon

import android.content.Context
import android.graphics.Matrix
import android.util.AttributeSet
import android.util.Log
import androidx.appcompat.widget.AppCompatImageView
import java.util.*

private const val TAG = "TopCropImageView"

class TopCropImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : AppCompatImageView(context, attrs, defStyleAttr) {
    init {
        Log.d(TAG, "init")
        scaleType = ScaleType.MATRIX

    }

    private var steps: Int = 64 //number of steps to scroll the whole image
    private var duration: Long = 200

    private var scale: Float = 0F
    private var scrollFraction = 0F
    private var availableHeight = 0

    override fun setFrame(l: Int, t: Int, r: Int, b: Int): Boolean {
        val matrix = Matrix()

        val viewWidth = width - paddingLeft - paddingRight
        val viewHeight = height - paddingTop - paddingBottom
        val drawableWidth = drawable.intrinsicWidth
        val drawableHeight = drawable.intrinsicHeight

        scale = if (drawableWidth * viewHeight > drawableHeight * viewWidth) {
            viewHeight.toFloat() / drawableHeight.toFloat()
        } else {
            viewWidth.toFloat() / drawableWidth.toFloat()
        }

        matrix.setScale(scale, scale)

        imageMatrix = matrix
        return super.setFrame(l, t, r, b)
    }

    private var scrollingTimer = Timer()

    fun startScrolling() {
        Log.d(TAG, "startScrolling()")
        scrollY = 0
        scrollingTimer = Timer().apply {
            scheduleAtFixedRate(object: TimerTask() {
                override fun run() {
                    if(scale == 0F)
                        return

                    availableHeight = (scale * drawable.intrinsicHeight - (height - paddingBottom - paddingTop)).toInt()

                    if(scrollFraction + 1F / steps > 1)
                        scrollFraction = 0F
                    else
                        scrollFraction += 1F / steps

                    scrollY = (availableHeight * scrollFraction).toInt()
                    Log.d(TAG, "$scale $scrollY + $availableHeight + ${drawable.intrinsicHeight}")
                }

            },
            0,
            duration)
        }
    }
}