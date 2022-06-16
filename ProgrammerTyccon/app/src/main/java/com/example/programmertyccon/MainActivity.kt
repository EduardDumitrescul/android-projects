package com.example.programmertyccon

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import com.example.programmertyccon.upgrades.AssistantUpgrade
import com.example.programmertyccon.upgrades.SkillUpgrade
import com.example.programmertyccon.utils.DefaultDataUtil
import com.example.programmertyccon.utils.PreferencesUtil
import com.example.programmertyccon.utils.ResourcesUtil
import com.google.gson.GsonBuilder
import java.io.IOException
import java.io.OutputStreamWriter

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    companion object {
        lateinit var context: Context
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context = this.baseContext

        PreferencesUtil.initialize(getPreferences(Context.MODE_PRIVATE))

        val mustLoadDefaultData = PreferencesUtil.getBoolean(ResourcesUtil.getString(R.string.data_exists))
        if(!mustLoadDefaultData) {
            DefaultDataUtil.saveSkillUpgradesDefaultData()
            DefaultDataUtil.saveAssistantUpgradesDefaultData()
        }

        Player.initialize(baseContext)

        setContentView(R.layout.activity_main)
    }

    override fun onStop() {
        super.onStop()
        Player.getInstance().saveData()
        Log.d(TAG, "onStop()")

        PreferencesUtil.save(ResourcesUtil.getString(R.string.data_exists), true)
    }

}