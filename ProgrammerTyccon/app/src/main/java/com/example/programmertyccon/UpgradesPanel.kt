package com.example.programmertyccon

import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.programmertyccon.Upgrades.*

private val TAG = "UpgradesPanel"

class UpgradesPanel(
    private val parent: Fragment,
    private val view: View
): Observer{

    private val player = Player.getInstance()
    private var container: ConstraintLayout = view.findViewById(R.id.upgrades_panel)

    private var callbacks: Callbacks = parent as Callbacks

    private var topContainer: LinearLayout = view.findViewById(R.id.top_container)
    private var recyclerView: RecyclerView = view.findViewById(R.id.recycler_view) as RecyclerView

    private var skillButton: TextView = view.findViewById(R.id.skill_button)
    private var equipmentButton: TextView = view.findViewById(R.id.equip_button)
    private var assistantButton: TextView = view.findViewById(R.id.assist_button)
    private var settingsButton: TextView = view.findViewById(R.id.settings_button)

    private var skillUpgradesSelected: Boolean = false
    private var equipmentUpgradesSelected: Boolean = false
    private var assistantUpgradesSelected: Boolean = false
    private var settingsSelected: Boolean = false

    private var isExtended: Boolean = false;
    private var fraction = 0.8f

    init {
        player.addObserver(this)
        recyclerView.layoutManager = LinearLayoutManager(view.context)
        onClick()
    }

    // When the toolbar it is clicked, the panel should hide / show
    private fun onClick() {
        skillButton.setOnClickListener {
            if(skillUpgradesSelected) {
                callbacks.collapseUpgradesPanel()
                skillUpgradesSelected = false
            }
            else {
                callbacks.expandUpgradesPanel()
                skillUpgradesSelected = true
                equipmentUpgradesSelected = false
                assistantUpgradesSelected = false
                settingsSelected = false
            }
            updateUI()
        }
        equipmentButton.setOnClickListener {
            if(equipmentUpgradesSelected) {
                callbacks.collapseUpgradesPanel()
                equipmentUpgradesSelected = false;
            }
            else {
                callbacks.expandUpgradesPanel()
                skillUpgradesSelected = false
                equipmentUpgradesSelected = true
                assistantUpgradesSelected = false
                settingsSelected = false
            }
            updateUI()
        }
        assistantButton.setOnClickListener {
            if(assistantUpgradesSelected) {
                callbacks.collapseUpgradesPanel()
                assistantUpgradesSelected = false
            }
            else {
                callbacks.expandUpgradesPanel()
                skillUpgradesSelected = false
                equipmentUpgradesSelected = false
                assistantUpgradesSelected = true
                settingsSelected = false
            }
            updateUI()
        }
        settingsButton.setOnClickListener {
            if(settingsSelected) {
                callbacks.collapseUpgradesPanel()
                settingsSelected = false
            }
            else {
                callbacks.expandUpgradesPanel()
                skillUpgradesSelected = false
                equipmentUpgradesSelected = false
                assistantUpgradesSelected = false
                settingsSelected = true
            }
            updateUI()
        }
    }

    fun updateUI() {
        Log.d(TAG, "updateUI()")
        if(skillUpgradesSelected) {
            recyclerView.adapter = SkillListAdapter(player.skillUpgradesList)
        }
        else if(assistantUpgradesSelected) {
            recyclerView.adapter = AssistantListAdapter(player.assistantUpgradeList)
        }
        else if(equipmentUpgradesSelected) {
            recyclerView.adapter = EquipmentListAdapter(player.equipmentUpgradeList)
        }
        else {
            recyclerView.adapter = null
        }
    }

    fun extend() {
        isExtended = true
    }

    fun collapse() {
        isExtended = false
        skillUpgradesSelected = false
        equipmentUpgradesSelected = false
        assistantUpgradesSelected = false
        settingsSelected = false
    }

    fun isExtended(): Boolean = isExtended


    interface Callbacks {
        fun expandUpgradesPanel()
        fun collapseUpgradesPanel()
    }

    override fun update() {
        updateUI()
    }

}