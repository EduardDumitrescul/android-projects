package com.example.programmertyccon.upgrades

import com.example.programmertyccon.R
import com.example.programmertyccon.utils.ResourcesUtil


data class SkillUpgrade(
    var titleId: Int = R.string.skill_upgrade_default,
    var price: Double = 0.0,
    var effect: Double = 0.0,
    var imgId: Int = R.mipmap.test_clickable,
    var index: Int = 0
) {
    var title: String = ResourcesUtil.getString(titleId)
    var level = 0

}