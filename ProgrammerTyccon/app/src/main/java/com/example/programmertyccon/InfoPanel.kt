package com.example.programmertyccon

import android.app.Activity
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.findFragment
import com.example.programmertyccon.utils.NumberFormatUtil

class InfoPanel(
    parentFragment: Fragment,
    private val activity: Activity,
    view: View
): Observer {
    init {
        Player.getInstance().addObserver(this)
    }

    private val callbacks =  parentFragment as Callbacks?

    private val player = Player.getInstance()

    private val totalAmountField: TextView = view.findViewById(R.id.textview_money)
    private val incomeSpeedField: TextView = view.findViewById(R.id.textview_income)
    private val multiplierField: TextView = view.findViewById(R.id.textview_multiplier)
    private val settingsButton: FrameLayout = view.findViewById(R.id.frame_settings)

    fun addListeners() {
        settingsButton.setOnClickListener {
            callbacks?.settingsPressed()
        }
    }

    private fun updateUI() {
        incomeSpeedField.text = NumberFormatUtil.toString(player.incomeSpeed) + " /second"
        totalAmountField.text = NumberFormatUtil.toLongString(player.currentMoney)
        multiplierField.text = "x${player.multiplier}"
    }

    override fun update() {
        updateUI()
    }

    interface Callbacks {
        fun settingsPressed()
    }

}