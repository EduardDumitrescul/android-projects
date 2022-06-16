package com.example.programmertyccon

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.programmertyccon.upgrades.AssistantUpgrade
import com.example.programmertyccon.upgrades.SkillUpgrade
import com.example.programmertyccon.utils.FileReaderUtil
import com.example.programmertyccon.utils.FileWriterUtil
import com.example.programmertyccon.utils.PreferencesUtil
import com.example.programmertyccon.utils.ResourcesUtil
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.io.IOException
import java.io.OutputStreamWriter
import java.util.*
import kotlin.math.max
import kotlin.math.sqrt

private const val TAG = "Player"
private const val LAST_ACTIVE_TIME_KEY = "LAST_ACTIVE_TIME"
private const val CURRENT_MONEY_KEY = "CURRENT_MONEY"

//Singleton Class that holds the information about the current player

class Player private constructor(): Subject{
    companion object {
        private var INSTANCE: Player? = null

        @RequiresApi(Build.VERSION_CODES.O)
        fun initialize(context: Context) {
            if(INSTANCE == null) {
                INSTANCE = Player()
                INSTANCE!!.loadData(context)
            }

        }
        fun getInstance(): Player {
            return INSTANCE ?:
            throw IllegalStateException("Player() has not been initialized")
        }
    }

    private var lastActiveTime: Long = 0

    var currentMoney: Double = 100000000.0
    var tokenValue: Double = 1.0
    var tokenSpawnRate: Double = 10.0
    var multiplier: Double = 1.0
    var maxMultiplier: Double = 4.0
    private var lastTokenClick: Long = 0
    var maxTokenActive: Long = 5

    var skillUpgradesList: List<SkillUpgrade> = listOf()
    var assistantUpgradeList: List<AssistantUpgrade> = listOf()

    private var incomeDeque: Deque<Pair<Long, Double>> = ArrayDeque()
    var incomeSpeed: Double = 0.0
    private var tapDeque: Deque<Long> = ArrayDeque()

    private var incomeSpeedTimer = Timer().apply {
        scheduleAtFixedRate(object: TimerTask() {
            override fun run() {
                computeIncomeSpeed()
                if(incomeDeque.size + 3.6 > multiplier * 3.6) {
                    multiplier += 0.1
                }
                else {
                    multiplier = max(1.0, multiplier - 0.2)
                }
                multiplier.coerceAtMost(maxMultiplier)
                multiplier.coerceAtLeast(1.0)
                multiplier = (multiplier * 10).toInt() / 10.0

                notifyObservers()
            }

        }, 0, 400)
    }

    private var autoSaveTimer = Timer().apply {
        scheduleAtFixedRate(object: TimerTask() {
            override fun run() {
                saveData()
            }

        }, 1000, 1000)
    }

    fun computeIncomeSpeed() {
        while(!incomeDeque.isEmpty() && System.currentTimeMillis() - incomeDeque.first.first > 1000) {
            incomeSpeed -= incomeDeque.first.second
            incomeDeque.removeFirst()
        }
        while(!tapDeque.isEmpty() && System.currentTimeMillis() - tapDeque.first > 60000) {
            tapDeque.removeFirst()
        }
    }

    private fun loadData(context: Context) {
        var string = FileReaderUtil.readFileAsString(context.resources.getString(R.string.filename_skill_upgrades))
        skillUpgradesList = Gson().fromJson(string, object : TypeToken<List<SkillUpgrade>>() {}.type)
        string = FileReaderUtil.readFileAsString(context.resources.getString(R.string.filename_assistant_upgrades))
        assistantUpgradeList = Gson().fromJson(string, object : TypeToken<List<AssistantUpgrade>>() {}.type)

        currentMoney = PreferencesUtil.getDouble(CURRENT_MONEY_KEY)
        lastActiveTime = PreferencesUtil.getLong(LAST_ACTIVE_TIME_KEY)

        skillUpgradesList.forEach {
            tokenValue += it.effect * it.level
        }
    }

    fun saveData() {
        val gson = GsonBuilder().setPrettyPrinting().create()
        Log.d(TAG, "saveData()")


        FileWriterUtil.writeString(gson.toJson(skillUpgradesList), ResourcesUtil.getString(R.string.filename_skill_upgrades))
        FileWriterUtil.writeString(gson.toJson(assistantUpgradeList), ResourcesUtil.getString(R.string.filename_assistant_upgrades))

        PreferencesUtil.save(CURRENT_MONEY_KEY, currentMoney)
        PreferencesUtil.save(LAST_ACTIVE_TIME_KEY, System.currentTimeMillis())
    }

    fun upgradeSkill(index: Int) {
        currentMoney -= skillUpgradesList[index].price
        skillUpgradesList[index].apply {
            level ++
            price = (price * 1.2).toLong().toDouble()
        }
        tokenValue += skillUpgradesList[index].effect

        notifyObservers()
    }

    fun upgradeAssistant(index: Int) {
        currentMoney -= assistantUpgradeList[index].price
        assistantUpgradeList[index].apply {
            level ++;
            price *= coefficient
        }
        notifyObservers()
    }

    fun tokenClicked() {
        val amount = tokenValue * multiplier
        incomeDeque.addLast(Pair(System.currentTimeMillis(), amount))
        tapDeque.addLast(System.currentTimeMillis())
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