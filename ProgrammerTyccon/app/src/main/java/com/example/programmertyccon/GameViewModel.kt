package com.example.programmertyccon

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.programmertyccon.Upgrades.SkillUpgrade

class GameViewModel : ViewModel() {
    val player: Player = Player.getInstance()

}