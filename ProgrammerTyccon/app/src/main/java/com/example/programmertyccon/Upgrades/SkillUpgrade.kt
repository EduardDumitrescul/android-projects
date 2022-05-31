package com.example.programmertyccon.Upgrades

import android.widget.ImageView
import com.example.programmertyccon.R


data class SkillUpgrade(
    var title: String = "Skill Upgrade",
    var info: String = "skill info",
    var price: Int = 1000,
    var effect: String = "effect",
    var img: Int = R.mipmap.test_clickable,
    var level: Int = 0
) {

}