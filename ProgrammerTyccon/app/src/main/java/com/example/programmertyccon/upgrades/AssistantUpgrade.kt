package com.example.programmertyccon.upgrades

import com.example.programmertyccon.Player
import com.example.programmertyccon.R
import com.example.programmertyccon.utils.ResourcesUtil
import java.util.*


data class AssistantUpgrade(
    var titleId: Int = R.string.assistant_default,
    var entityViewId: Int = R.id.character_player,
    var basePrice: Double = 1000.0,
    var effect: Double = 0.0,
    var imgId: Int = R.mipmap.test_clickable,
    var index: Int = 0,
    var coefficient: Double = 0.0,
    var duration: Long = 1000
) {
    var title: String = ResourcesUtil.getString(titleId)
    var level = 0
    var multiplierList: MutableMap<Int, Int> = mutableMapOf()
    var multiplier: Long = 1
    var price = basePrice
    var income: Double = 0.0

    fun addMultiplier(level: Int, multiplier: Int) {
        if(multiplierList.containsKey(level))
            throw IllegalArgumentException("already have a multiplier for this level")
        multiplierList[level] = multiplier
    }

    private var timer: Timer = Timer()

    fun updateTimer() {
        timer = Timer().apply {
            scheduleAtFixedRate(object: TimerTask() {
                override fun run() {
                    Player.getInstance().addAssistantIncome(index)
                }

            },0, duration / multiplier)
        }
    }

    fun upgrade() {
        if(level == 0) {
            updateTimer()
        }
        level ++
        income += effect
        price *= coefficient
        if(multiplierList.containsKey(level)) {
            multiplier *= multiplierList[level]?: 1
        }
    }


}