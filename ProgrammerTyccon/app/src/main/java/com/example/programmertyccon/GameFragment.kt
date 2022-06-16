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
import androidx.fragment.app.findFragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController

private const val TAG = "GameFragment"

class GameFragment : Fragment(), UpgradesPanel.Callbacks, InfoPanel.Callbacks, Observer {

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

    init {
        Player.getInstance().addObserver(this)
    }

    companion object {
        fun newInstance() = GameFragment()
    }
    private val viewModel: GameViewModel by lazy {
        ViewModelProvider(this)[GameViewModel::class.java]
    }

    private var player = Player.getInstance()

    private lateinit var constraintLayout: ConstraintLayout
    private lateinit var upgradesPanel: UpgradesPanel
    private lateinit var infoPanel: InfoPanel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, "onCreateView()")
        val view = inflater.inflate(R.layout.game_fragment, container, false)
        constraintLayout = view.findViewById(R.id.constraintLayout)
        upgradesPanel = UpgradesPanel(this, view)
        infoPanel = InfoPanel(this, requireActivity(), view)
        infoPanel.addListeners()


        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        player.notifyObservers()
    }

    override fun expandUpgradesPanel() {
        Log.d(TAG, "expandUpgradesPanel()")
        val constraintSet = ConstraintSet()
        constraintSet.clone(constraintLayout)
        constraintSet.clear(R.id.upgrades_panel, ConstraintSet.TOP)
        constraintSet.connect(R.id.upgrades_panel, ConstraintSet.TOP, R.id.guideline_h45p, ConstraintSet.TOP)
        TransitionManager.beginDelayedTransition(view as ViewGroup?)
        constraintSet.applyTo(constraintLayout)
    }

    override fun collapseUpgradesPanel() {
        val constraintSet = ConstraintSet()
        constraintSet.clone(constraintLayout)
        constraintSet.clear(R.id.upgrades_panel, ConstraintSet.TOP)
        constraintSet.connect(R.id.upgrades_panel, ConstraintSet.TOP, R.id.guideline_h60bottom, ConstraintSet.TOP)
        TransitionManager.beginDelayedTransition(view as ViewGroup?)
        constraintSet.applyTo(constraintLayout)
    }

    override fun settingsPressed() {
        //SettingsDialog()
        findNavController().navigate(R.id.action_gameFragment_to_settingsDialog)
    }

    override fun update() {
        for(upg in Player.getInstance().assistantUpgradeList) {
            if(upg.level == 0)
                (view?.findViewById<ConstraintLayout>(upg.entityViewId))?.visibility  = View.INVISIBLE
            else
                (view?.findViewById<ConstraintLayout>(upg.entityViewId))?.visibility  = View.VISIBLE
        }
    }
}