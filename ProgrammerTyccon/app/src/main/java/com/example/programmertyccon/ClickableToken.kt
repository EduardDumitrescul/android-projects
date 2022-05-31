package com.example.programmertyccon

import android.graphics.BitmapFactory
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.programmertyccon.R
import kotlin.random.Random

private val TAG = "ClickableToken"

class ClickableToken(view: View): ImageView(view.context) {
    init {
        setImageBitmap(BitmapFactory.decodeResource(context.resources, R.mipmap.test_clickable))
        val layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        val marginParams = ViewGroup.MarginLayoutParams(layoutParams)

        Log.d(TAG, "${view.width} ${view.height}")
        marginParams.leftMargin = Random.nextInt(0, view.width - drawable.intrinsicWidth)
        marginParams.topMargin = Random.nextInt(0, view.height - drawable.intrinsicHeight)
        this.layoutParams = marginParams
    }
}
