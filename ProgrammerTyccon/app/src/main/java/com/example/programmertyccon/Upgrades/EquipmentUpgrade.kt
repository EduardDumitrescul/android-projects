package com.example.programmertyccon.Upgrades

import android.widget.ImageView
import com.example.programmertyccon.R


data class EquipmentUpgrade(
    var title: String = "Skill Upgrade",
    var info: String = "skill info",
    var price: Int = 1000,
    var effect: Double = 0.0,
    var img: Int = R.mipmap.test_clickable,
    var level: Int = 0,
    var index: Int = 0
) {

}