package com.example.programmertyccon

import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.programmertyccon.Upgrades.AssistantUpgrade
import com.example.programmertyccon.Upgrades.EquipmentUpgrade
import com.example.programmertyccon.Upgrades.SkillUpgrade
import com.google.gson.GsonBuilder
import java.io.IOException
import java.io.OutputStreamWriter
import kotlin.math.pow

private val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        context = applicationContext
        super.onCreate(savedInstanceState)
        saveSkillUpgradesTestData()
        saveEquipmentUpgradesTestData()
        saveAssistantUpgradesTestData()
        Player.initialize()

        setContentView(R.layout.activity_main)
    }

    // generates and saves inside the assets folder test data
    private fun saveSkillUpgradesTestData() {
        val listOfSkillUpgrade: MutableList<SkillUpgrade> = mutableListOf()
        val count = 20
        for(i in 0 until count)
            listOfSkillUpgrade.add(SkillUpgrade("skill $i", effect = 10.0.pow(i.toDouble()), index = i))

        val gson = GsonBuilder().setPrettyPrinting().create()

        val output = gson.toJson(listOfSkillUpgrade)

        try {
            val outputStreamWriter =
                OutputStreamWriter(baseContext.openFileOutput("skill-test.txt", MODE_PRIVATE))
            outputStreamWriter.write(output)
            outputStreamWriter.close()
        } catch (e: IOException) {
            Log.e("Exception", "File write failed: " + e.toString())
        }
    }

    // generates and saves inside the assets folder test data
    private fun saveEquipmentUpgradesTestData() {
        val list: MutableList<EquipmentUpgrade> = mutableListOf()
        val count = 20
        for(i in 0 until count)
            list.add(EquipmentUpgrade("equipment $i", effect = 10.0.pow(i.toDouble()), index = i))

        val gson = GsonBuilder().setPrettyPrinting().create()

        val output = gson.toJson(list)

        try {
            val outputStreamWriter =
                OutputStreamWriter(baseContext.openFileOutput("equipment-test.txt", MODE_PRIVATE))
            outputStreamWriter.write(output)
            outputStreamWriter.close()
        } catch (e: IOException) {
            Log.e("Exception", "File write failed: " + e.toString())
        }
    }

    private fun saveAssistantUpgradesTestData() {
        val list: MutableList<AssistantUpgrade> = mutableListOf()
        val count = 20
        for(i in 0 until count)
            list.add(AssistantUpgrade("assistant $i", effect = 10.0.pow(i.toDouble()), index = i))

        val gson = GsonBuilder().setPrettyPrinting().create()

        val output = gson.toJson(list)

        try {
            val outputStreamWriter =
                OutputStreamWriter(baseContext.openFileOutput("assistant-test.txt", MODE_PRIVATE))
            outputStreamWriter.write(output)
            outputStreamWriter.close()
        } catch (e: IOException) {
            Log.e("Exception", "File write failed: " + e.toString())
        }
    }

    companion object{
        var context: Context? = null
    }

}