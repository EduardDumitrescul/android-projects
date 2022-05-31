package com.example.recyclerviewdialog

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View

private val TAG = "MainActivity"

class MainActivity : AppCompatActivity(), RecyclerViewDialog.Callbacks {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button = findViewById<View>(R.id.pop_dialog_button)
        button.setOnClickListener {
            val recyclerViewDialog = RecyclerViewDialog()
            recyclerViewDialog.show(this.supportFragmentManager, "TAG")
        }
    }

    override fun itemClicked(string: String) {
        Log.d(TAG, string)
    }
}