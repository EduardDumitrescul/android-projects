package com.example.programmertyccon

import android.app.Activity
import android.view.View
import android.widget.TextView
import org.w3c.dom.Text
import java.util.*

class InfoPanel(
    private val activity: Activity,
    private val view: View
) {
    private val player = Player.getInstance()

    private val totalAmountField: TextView = view.findViewById(R.id.money_textview)
    private val incomeSpeedField: TextView = view.findViewById(R.id.money_speed_textview)
    private val multiplierField: TextView = view.findViewById(R.id.multiplier)
    private val spawnRateField: TextView = view.findViewById(R.id.spawn_rate_textview)
    private val autoRateField: TextView = view.findViewById(R.id.auto_rate_textview)

//    private val updateTimer: Timer = Timer().apply {
//        scheduleAtFixedRate(object: TimerTask() {
//            override fun run() {
//                activity.runOnUiThread {
//                    incomeSpeedField.text = "${player.computeIncomeSpeed()} money/second"
//                    totalAmountField.text = "${player.currentMoney} money"
//                    spawnRateField.text = "${player.tokenSpawnRate} tokens / second"
//                    autoRateField.text = "${player.assistantRate * player.tokenValue} money / second"
//                    multiplierField.text = "x${player.multiplier}"
//                }
//            }
//        },
//        0,
//        100)
//    }

    fun updateUI() {
        incomeSpeedField.text = "${player.computeIncomeSpeed()} money/second"
        totalAmountField.text = "${player.currentMoney} money"
        spawnRateField.text = "${player.tokenSpawnRate} tokens / second"
        autoRateField.text = "${player.assistantRate * player.tokenValue} money / second"
        multiplierField.text = "x${player.multiplier}"
    }

}