package com.example.programmertyccon.utils

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.programmertyccon.R
import com.example.programmertyccon.upgrades.AssistantUpgrade
import com.example.programmertyccon.upgrades.SkillUpgrade
import com.google.gson.GsonBuilder
import java.io.IOException
import java.io.OutputStreamWriter

class DefaultDataUtil {
    companion object {
        fun saveSkillUpgradesDefaultData() {
            val listOfSkillUpgrade: MutableList<SkillUpgrade> = mutableListOf()
            listOfSkillUpgrade.add(SkillUpgrade(titleId = R.string.skill_upgrade_1, effect = 1.0 , index = 0, price = 10.0))
            listOfSkillUpgrade.add(SkillUpgrade(titleId = R.string.skill_upgrade_2, effect = 10.0 , index = 1, price = 100.0))
            listOfSkillUpgrade.add(SkillUpgrade(titleId = R.string.skill_upgrade_3, effect = 100.0 , index = 2, price = 1000.0))
            listOfSkillUpgrade.add(SkillUpgrade(titleId = R.string.skill_upgrade_4, effect = 1000.0 , index = 3, price = 10000.0))
            listOfSkillUpgrade.add(SkillUpgrade(titleId = R.string.skill_upgrade_5, effect = 10000.0 , index = 4, price = 100000.0))
            listOfSkillUpgrade.add(SkillUpgrade(titleId = R.string.skill_upgrade_6, effect = 100000.0 , index = 5, price = 1000000.0))
            listOfSkillUpgrade.add(SkillUpgrade(titleId = R.string.skill_upgrade_7, effect = 1000000.0 , index = 6, price = 100000000.0))
            listOfSkillUpgrade.add(SkillUpgrade(titleId = R.string.skill_upgrade_8, effect = 10000000.0 , index = 7, price = 1000000000.0))
            listOfSkillUpgrade.add(SkillUpgrade(titleId = R.string.skill_upgrade_9, effect = 100000000.0 , index = 8, price = 10000000000.0))
            listOfSkillUpgrade.add(SkillUpgrade(titleId = R.string.skill_upgrade_10, effect = 1000000000.0 , index = 8, price = 100000000000.0))

            val gson = GsonBuilder().setPrettyPrinting().create()

            val output = gson.toJson(listOfSkillUpgrade)

            try {
                FileWriterUtil.writeString(output, ResourcesUtil.getString(R.string.filename_skill_upgrades))
            } catch (e: IOException) {
                Log.e("Exception", "File write failed: $e")
            }
        }


        fun saveAssistantUpgradesDefaultData() {
            val list: MutableList<AssistantUpgrade> = mutableListOf()

            list.add(AssistantUpgrade(R.string.assistant_1, effect = 1.0, index = 0, coefficient = 1.06, basePrice = 10.0, entityViewId = R.id.character_1).apply {
                addMultiplier(25, 5)
                addMultiplier(50, 5)
                addMultiplier(100, 10)
                addMultiplier(150, 36)
            })
            list.add(AssistantUpgrade(R.string.assistant_2, effect = 20.0, index = 1, coefficient = 1.13, basePrice = 100.0, entityViewId = R.id.character_2).apply{
                addMultiplier(25, 10)
                addMultiplier(50, 12)
                addMultiplier(100, 400)
            })
            list.add(AssistantUpgrade(R.string.assistant_3, effect = 160.0, index = 2, coefficient = 1.12, basePrice = 2240.0, entityViewId = R.id.character_3).apply{
                addMultiplier(25, 12)
                addMultiplier(50, 16)
                addMultiplier(100, 50)
            })
            list.add(AssistantUpgrade(R.string.assistant_4, effect = 1920.0, index = 3, coefficient = 1.11, basePrice = 48000.0, entityViewId = R.id.character_4).apply {
                addMultiplier(25, 12)
                addMultiplier(50, 8)
                addMultiplier(100, 25)
            })
            list.add(AssistantUpgrade(R.string.assistant_5, effect = 30720.0, index = 4, coefficient = 1.1, basePrice = 2457600.0, entityViewId = R.id.character_5).apply {
                addMultiplier(25, 10)
                addMultiplier(50, 32)
                addMultiplier(100, 64)
            })
            list.add(AssistantUpgrade(R.string.assistant_6, effect = 737280.0, index = 5, coefficient = 1.09, basePrice = 147456000.0, entityViewId = R.id.character_6).apply {
                addMultiplier(25, 8)
                addMultiplier(50, 12)
                addMultiplier(100, 50)
            })
            list.add(AssistantUpgrade(R.string.assistant_7, effect = 23592900.0, index = 6, coefficient = 1.09, basePrice = 10616830000.0, entityViewId = R.id.character_7).apply {
                addMultiplier(25, 10)
                addMultiplier(50, 2)
                addMultiplier(100, 2)
            })
            list.add(AssistantUpgrade(R.string.assistant_8, effect = 1132460000.0, index = 7, coefficient = 1.08, basePrice = 1358950000000.0, entityViewId = R.id.character_8).apply {
                addMultiplier(25, 2)
            })

            val gson = GsonBuilder().setPrettyPrinting().create()

            val output = gson.toJson(list)

            try {
                FileWriterUtil.writeString(output, ResourcesUtil.getString(R.string.filename_assistant_upgrades))

            } catch (e: IOException) {
                Log.e("Exception", "File write failed: $e")
            }
        }
    }

}