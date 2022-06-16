package com.example.programmertyccon

import android.view.View
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import java.util.*

private const val TAG = "PlayableArea"

class PlayableArea(
    val parent: Fragment,
    view: View
) {
    private val player: Player = Player.getInstance()

    private var backgroundImageView: TopCropImageView = view.findViewById(R.id.work_imageview)

    //The relativeLayout that contains all the elements
    private var container: FrameLayout = view.findViewById(R.id.work_panel_container)

    private val tokenSet: MutableSet<ClickableToken> = mutableSetOf()
    //Timer that created and adds the Tokens
    private var tokenTimer: Timer = Timer()
    // TimerTask that creates and adds the Tokens

    private fun createToken(): TimerTask = object : TimerTask() {
        override fun run() {
            parent.requireActivity().runOnUiThread {
                if(tokenCount >= player.maxTokenActive)
                    return@runOnUiThread
                val token = ClickableToken(container).apply {
                    setOnClickListener { tokenClicked(this) }
                }
                tokenSet.add(token)
                container.addView(token)
                tokenCount ++
            }
        }
    }
    // Current number of tokens on screen
    private var tokenCount: Long = 0

    //Start creating tokens
    private fun tokenCreationStart() {
        tokenTimer = Timer()
        tokenTimer.scheduleAtFixedRate(createToken(), 1000, (1000 / player.tokenSpawnRate).toLong())
    }

    fun enable() {
        tokenCreationStart()
        //backgroundImageView.startScrolling()
    }

    fun disable() {
        tokenCreationStop()
    }

    // Stops creating tokens
    private fun tokenCreationStop() {
        tokenTimer.cancel()
    }

    // Player has clicked a token
    fun tokenClicked(token: ClickableToken) {
        tokenSet.remove(token)
        parent.requireActivity().runOnUiThread {
            container.removeView(token) // The token is removes from the container
        }

        tokenCount --
        player.tokenClicked() // Player data gets updated
    }

}