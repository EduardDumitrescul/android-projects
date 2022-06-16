package com.example.programmertyccon

import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {
    val player: Player = Player.getInstance()

}