package com.example.programmertyccon

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.programmertyccon.Upgrades.AssistantUpgrade
import com.example.programmertyccon.Upgrades.EquipmentUpgrade
import com.example.programmertyccon.Upgrades.SkillUpgrade
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

private val TAG = "Player"

//Singleton Class that holds the information about the current player

class Player private constructor(): Subject{
    companion object {
        private var INSTANCE: Player? = null

        @RequiresApi(Build.VERSION_CODES.O)
        fun initialize() {
            if(INSTANCE == null) {
                INSTANCE = Player()
                INSTANCE!!.loadData()
            }

        }
        fun getInstance(): Player {
            return INSTANCE ?:
            throw IllegalStateException("Player() has not been initialized")
        }
    }


    var lastActiveTime: Long = 0

    var currentMoney: Double = 100000000.0
    var tokenValue: Double = 1.0
    // The speed at which are added the tokens (token / second)
    var tokenSpawnRate: Double = 10.0
    var multiplier: Double = 1.0
    // The rate of automatic token clicks (clicks / second)
    var assistantRate: Double = 0.0
    var lastTokenClick: Long = 0
    var maxTokenActive: Long = 5

    var skillUpgradesList: List<SkillUpgrade> = listOf()
    var equipmentUpgradeList: List<EquipmentUpgrade> = listOf()
    var assistantUpgradeList: List<AssistantUpgrade> = listOf()

    var incomeDeque: Deque<Pair<Long, Double>> = ArrayDeque()
    var incomeSpeed: Double = 0.0
    // The length of the time interval in which compute the amount of income gained in one second
    var intervalDuration: Long = 2000

    private var autoIncomeTimer: Timer = Timer().apply {
        scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                currentMoney += tokenValue * assistantRate
            }
        }, 0, 1000)
    }

    fun computeIncomeSpeed(): Double {
        while(!incomeDeque.isEmpty() && System.currentTimeMillis() - incomeDeque.first.first > intervalDuration) {

            incomeSpeed -= incomeDeque.first.second
            incomeDeque.removeFirst()
        }
        return incomeSpeed / (intervalDuration / 1000)
    }

    private fun loadData() {
        var string = FileReaderUtil.readFileAsString("skill-test.txt")
        skillUpgradesList = Gson().fromJson(string, object : TypeToken<List<SkillUpgrade>>() {}.type)
        string = FileReaderUtil.readFileAsString("equipment-test.txt")
        equipmentUpgradeList =
            Gson().fromJson(string, object : TypeToken<List<EquipmentUpgrade>>() {}.type)
        string = FileReaderUtil.readFileAsString("assistant-test.txt")
        assistantUpgradeList =
            Gson().fromJson(string, object : TypeToken<List<AssistantUpgrade>>() {}.type)

    }

    fun upgradeSkill(index: Int) {
        skillUpgradesList[index].apply {
            level ++
            price = (price * 1.2).toInt()
        }
        tokenValue += skillUpgradesList[index].effect

        notifyObservers()
    }

    fun tokenClicked() {
        val amount = tokenValue * multiplier
        incomeDeque.addLast(Pair(System.currentTimeMillis(), amount))
        lastTokenClick = System.currentTimeMillis()
        incomeSpeed += amount
        currentMoney += amount
        Log.d(TAG, "tokenClicked()")

        notifyObservers()
    }

    private val observers: MutableSet<Observer> = mutableSetOf()

    override fun addObserver(observer: Observer) {
        observers.add(observer)
    }

    override fun removeObserver(observer: Observer) {
        observers.remove(observer)
    }

    override fun notifyObservers() {
        observers.forEach {
            it.update()
        }
    }
}