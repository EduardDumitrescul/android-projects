package com.example.programmertyccon

import android.app.Activity
import android.content.Context
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import java.util.*
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit
import kotlin.random.Random

private const val TAG = "GameFragment"

class GameFragment : Fragment() {

    companion object {
        fun newInstance() = GameFragment()
    }

    private lateinit var viewModel: GameViewModel
    private lateinit var playableArea: PlayableArea
    private lateinit var bottomNavigationMenuView: BottomNavigationMenuView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, "onCreateView()")
        val view = inflater.inflate(R.layout.game_fragment, container, false)

        playableArea = PlayableArea(requireActivity(), view)
        bottomNavigationMenuView = view.findViewById(R.id.bottomNavigationView)


        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[GameViewModel::class.java]
        // TODO: Use the ViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        playableArea.scrollStart()
        playableArea.tokenCreationStart()
        Log.d(TAG, "onViewCreated() " + view?.width.toString())
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onViewCreated() " + view?.measuredWidth .toString())
    }
}

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
    private var containerWidth: Int = 0 // in pixels
    private var containerHeight: Int = 0 // in pixels

    //Timer that created and adds the Tokens
    private var tokenTimer: Timer = Timer()
    // The speed at which are added the tokens (token / second)
    private var tokenCreateSpeed: Double = 1.0
    // TimerTask that creates and adds the Tokens
    private fun createToken(): TimerTask = object : TimerTask() {
        override fun run() {
            activity.runOnUiThread {
                if(tokenCount >= 5)
                    return@runOnUiThread
                val token = ClickableToken(view).apply {
                    setOnClickListener { tokenClicked(this) }
                }

                container.addView(token)
                tokenCount ++;
            }
        }
    }
    // Current number of tokens on screen
    private var tokenCount: Long = 0;

    //Start creating tokens
    fun tokenCreationStart() {
        tokenTimer.scheduleAtFixedRate(createToken(), 1000, (1000 / tokenCreateSpeed).toLong())
    }

    // Stops creating tokens
    fun tokenCreationStop() {
        tokenTimer.cancel()
    }

    // Player has clicked a token
    fun tokenClicked(token: ClickableToken) {

        container.removeView(token) // The token is removes from the container
        tokenCount --;
        player.tokenClicked() // Player data gets updated
    }

}

class ClickableToken(view: View): ImageView(view.context) {
    init {
        setImageBitmap(BitmapFactory.decodeResource(context.resources, R.mipmap.test_clickable))
        val layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        val marginParams = ViewGroup.MarginLayoutParams(layoutParams)


        marginParams.leftMargin = Random.nextInt(0, view.width - drawable.intrinsicWidth)
        marginParams.topMargin = Random.nextInt(0, view.height - drawable.intrinsicHeight)
        this.layoutParams = marginParams
    }
}