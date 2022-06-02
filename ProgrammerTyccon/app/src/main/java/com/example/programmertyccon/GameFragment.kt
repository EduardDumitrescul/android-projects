package com.example.programmertyccon

import android.os.Bundle
import android.transition.TransitionManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider

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
    private val viewModel: GameViewModel by lazy {
        ViewModelProvider(this)[GameViewModel::class.java]
    }
    private var player = Player.getInstance()

    private lateinit var constraintLayout: ConstraintLayout

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
        playableArea = PlayableArea(this, requireActivity(), view)
        upgradesPanel = UpgradesPanel(this, view)
        infoPanel = InfoPanel(requireActivity(), view)


        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        playableArea.stop()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.player.observe(viewLifecycleOwner) { player ->
            player.let {
                Log.d(TAG, "onViewCreated() viewMode.player.observe")
                this.player = player
                updateUI()
            }
        }
    }

    private fun updateUI() {
        upgradesPanel.updateUI()
        infoPanel.updateUI()
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