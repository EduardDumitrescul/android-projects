package com.example.programmertyccon

import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.programmertyccon.upgrades.AssistantListAdapter
import com.example.programmertyccon.upgrades.SkillListAdapter

private const val TAG = "UpgradesPanel"

private const val NO_TAB = 0
private const val WORK_TAB = 1
private const val SKILL_TAB = 2
private const val ASSISTANT_TAB = 3
private const val SETTINGS_TAB = 4

class UpgradesPanel(
    parent: Fragment,
    view: View
): Observer{


    private val player = Player.getInstance()

    private var callbacks: Callbacks = parent as Callbacks

    private var recyclerViewContainer: FrameLayout = view.findViewById(R.id.recycler_view_container)
    private var recyclerView: RecyclerView = (view.findViewById(R.id.recycler_view) as RecyclerView).apply {
        itemAnimator = null
    }
    private var workContainer: FrameLayout = view.findViewById(R.id.work_panel_container)
    private var playableArea: PlayableArea = PlayableArea(parent, view)

    private var skillButton: ImageView = view.findViewById(R.id.skill_button)
    private var workButton: ImageView = view.findViewById(R.id.work_button)
    private var assistantButton: ImageView = view.findViewById(R.id.assist_button)
    private var extraButton: ImageView = view.findViewById(R.id.extra_button)

    private var selectedTab = NO_TAB

    init {
        player.addObserver(this)
        recyclerView.layoutManager = LinearLayoutManager(view.context)
        onClick()
    }

    //Top bar is clicked
    private fun onClick() {
        skillButton.setOnClickListener {
            selectTab(SKILL_TAB)
        }
        workButton.setOnClickListener {
            selectTab(WORK_TAB)
        }
        assistantButton.setOnClickListener {
            selectTab(ASSISTANT_TAB)
        }
        extraButton.setOnClickListener {
            selectTab(SETTINGS_TAB)
        }
    }

    private fun selectTab(tab: Int) {
        if(selectedTab == tab) {
            callbacks.collapseUpgradesPanel()
            selectedTab = NO_TAB
            return
        }
        else if (selectedTab == NO_TAB)
            callbacks.expandUpgradesPanel()

        selectedTab = tab
        recyclerView.adapter = null
        recyclerViewContainer.visibility = View.INVISIBLE
        workContainer.visibility = View.INVISIBLE
        playableArea.disable()

        when(tab) {
            SKILL_TAB -> {
                recyclerView.adapter = SkillListAdapter(player.skillUpgradesList)
                recyclerViewContainer.visibility = View.VISIBLE
            }
            WORK_TAB -> {
                workContainer.visibility = View.VISIBLE
                playableArea.enable()
            }
            ASSISTANT_TAB -> {
                recyclerView.adapter = AssistantListAdapter(player.assistantUpgradeList)
                recyclerViewContainer.visibility = View.VISIBLE
            }
            SETTINGS_TAB -> {
                recyclerView.visibility = View.VISIBLE
            }
        }
    }

    fun isExtended(): Boolean = (selectedTab != 0)


    interface Callbacks {
        fun expandUpgradesPanel()
        fun collapseUpgradesPanel()
    }

    override fun update() {
        when(selectedTab) {
            WORK_TAB -> {

            }
            else -> {
                recyclerView.adapter?.let { recyclerView.adapter!!.notifyItemRangeChanged(0, it.itemCount) }
            }
        }
    }
}