package com.example.programmertyccon

import android.app.Activity
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import java.util.*

class PlayableArea(
    val activity: Activity,
    val view: View
) {
    private val player: Player = Player.getInstance()

    private var backgroundImageView: ImageView = view.findViewById(R.id.background_image_view)

    //The timer that updates the scrolling view and adds tokens
    private var backgroundTimer: Timer = Timer()
    //Scroll speed of the background (lines / second)
    private var scrollSpeed: Double = 5.0
    //The height that is needed to scroll one line (pixels / line)
    private var scrollHeight: Int = 100
    // Starts the background scrolling
    fun scrollStart() {
        backgroundTimer = Timer().apply {
            scheduleAtFixedRate(backgroundUpdate(), 0, (1000 / scrollSpeed).toLong())
        }
    }
    // Stops the background scrolling
    fun scrollStop() {
        backgroundTimer.cancel()
        backgroundTimer.purge()
    }
    private fun backgroundUpdate(): TimerTask = object: TimerTask(){
        override fun run() {
            backgroundImageView.scrollY += scrollHeight
            if(backgroundImageView.scrollY + container.height > backgroundImageView.drawable.intrinsicHeight)
                backgroundImageView.scrollY = 0
        }
    }


    //The relativeLayout that contains all the elements
    private var container: RelativeLayout = view.findViewById(R.id.playable_area_container)

    private val tokenSet: MutableSet<ClickableToken> = mutableSetOf()
    //Timer that created and adds the Tokens
    private var tokenTimer: Timer = Timer()
    // TimerTask that creates and adds the Tokens
    private fun createToken(): TimerTask = object : TimerTask() {
        override fun run() {
            activity.runOnUiThread {
                if(tokenCount >= player.maxTokenActive)
                    return@runOnUiThread
                val token = ClickableToken(container).apply {
                    setOnClickListener { tokenClicked(this) }
                }
                tokenSet.add(token)
                container.addView(token)
                tokenCount ++;
            }
        }
    }
    // Current number of tokens on screen
    private var tokenCount: Long = 0;

    //Start creating tokens
    fun tokenCreationStart() {
        tokenTimer = Timer()
        tokenTimer.scheduleAtFixedRate(createToken(), 1000, (1000 / player.tokenSpawnRate).toLong())
    }

    // Stops creating tokens
    private fun tokenCreationStop() {
        tokenTimer.cancel()
    }

    // Player has clicked a token
    fun tokenClicked(token: ClickableToken) {
        tokenSet.remove(token)
        activity.runOnUiThread {
            container.removeView(token) // The token is removes from the container
        }

        tokenCount --;
        player.tokenClicked() // Player data gets updated
    }

    fun stop() {
        tokenCreationStop()
        scrollStop()
    }

}