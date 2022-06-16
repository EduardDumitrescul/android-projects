package com.example.programmertyccon.upgrades

import com.example.programmertyccon.R
import com.example.programmertyccon.utils.ResourcesUtil


data class AssistantUpgrade(
    var titleId: Int = R.string.assistant_default,
    var entityViewId: Int = R.id.character_player,
    var basePrice: Double = 1000.0,
    var effect: Double = 0.0,
    var imgId: Int = R.mipmap.test_clickable,
    var index: Int = 0,
    var coefficient: Double = 0.0
) {
    var title: String = ResourcesUtil.getString(titleId)
    var level = 0
    var multiplierList: MutableMap<Int, Int> = mutableMapOf()
    var price = basePrice

    fun addMultiplier(level: Int, multiplier: Int) {
        if(multiplierList.containsKey(level))
            throw IllegalArgumentException("already have a multiplier for this level")
        multiplierList[level] = multiplier
    }
}