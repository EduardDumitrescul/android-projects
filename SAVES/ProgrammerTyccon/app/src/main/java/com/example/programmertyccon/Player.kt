package com.example.programmertyccon

import android.util.Log

private val TAG = "Player"

//Singleton Class that holds the information about the current player

class Player private constructor(){
    companion object {
        private var INSTANCE: Player? = null
        fun initialize() {
            if(INSTANCE == null)
                INSTANCE = Player()
        }
        fun getInstance(): Player {
            return INSTANCE ?:
            throw IllegalStateException("Player() has not been initialized")
        }
    }

    private var currentMoney: Int = 0;
    private var tokenValue: Int = 1;

    fun tokenClicked() {
        currentMoney += tokenValue
        Log.d(TAG, "tokenClicked() " + currentMoney.toString())
    }
}