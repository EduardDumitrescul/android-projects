package com.example.programmertyccon

import android.animation.ObjectAnimator
import android.app.Activity
import android.content.Context
import android.graphics.BitmapFactory
import android.icu.text.IDNA
import android.os.Bundle
import android.transition.TransitionManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.activity.OnBackPressedCallback
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayout
import java.util.*
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit
import kotlin.random.Random

private const val TAG = "GameFragment"

class GameFragment() : Fragment(), UpgradesPanel.Callbacks {

    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            if(upgradesPanel.isExtended())
                collapseUpgradesPanel()
            else {
                isEnabled = false
                requireActivity().onBackPressed()
            }

        }

    }

    companion object {
        fun newInstance() = GameFragment()
    }

    private lateinit var constraintLayout: ConstraintLayout
    private lateinit var viewModel: GameViewModel
    private lateinit var playableArea: PlayableArea
    private lateinit var upgradesPanel: UpgradesPanel
    private lateinit var infoPanel: InfoPanel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(this, onBackPressedCallback);
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, "onCreateView()")
        val view = inflater.inflate(R.layout.game_fragment, container, false)
        constraintLayout = view.findViewById(R.id.constraintLayout)
        playableArea = PlayableArea(requireActivity(), view)
        upgradesPanel = UpgradesPanel(this, view)
        infoPanel = InfoPanel(requireActivity(), view)


        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        playableArea.stop()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[GameViewModel::class.java]
        // TODO: Use the ViewModel


    }

    override fun expandUpgradesPanel() {
        val constraintSet = ConstraintSet()
        constraintSet.clone(constraintLayout)
        constraintSet.clear(R.id.upgrades_panel, ConstraintSet.TOP)
        constraintSet.connect(R.id.upgrades_panel, ConstraintSet.TOP, R.id.guideline3, ConstraintSet.TOP)
        TransitionManager.beginDelayedTransition(view as ViewGroup?)
        constraintSet.applyTo(constraintLayout)
        upgradesPanel.extend()
    }
    override fun collapseUpgradesPanel() {
        val constraintSet = ConstraintSet()
        constraintSet.clone(constraintLayout)
        constraintSet.clear(R.id.upgrades_panel, ConstraintSet.TOP)
        constraintSet.connect(R.id.upgrades_panel, ConstraintSet.TOP, R.id.guideline, ConstraintSet.TOP)
        TransitionManager.beginDelayedTransition(view as ViewGroup?)
        constraintSet.applyTo(constraintLayout)
        upgradesPanel.collapse()
    }


    override fun onStart() {
        super.onStart()
        playableArea.scrollStart()
        playableArea.tokenCreationStart()
        Log.d(TAG, "onViewCreated() " + view?.width.toString())
    }

    override fun onPause() {
        super.onPause()
        playableArea.stop()
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onViewCreated() " + view?.measuredWidth .toString())
    }
}

