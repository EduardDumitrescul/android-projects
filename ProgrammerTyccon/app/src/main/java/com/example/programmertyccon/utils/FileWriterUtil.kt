package com.example.programmertyccon.utils

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.programmertyccon.MainActivity
import com.example.programmertyccon.R
import java.io.FileWriter
import java.io.IOException
import java.io.OutputStreamWriter

class FileWriterUtil {
    companion object {
        fun writeString(string: String, filename: String) {
            try {
                val outputStreamWriter =
                    OutputStreamWriter(MainActivity.context!!.openFileOutput(filename, AppCompatActivity.MODE_PRIVATE))
                outputStreamWriter.write(string)
                outputStreamWriter.close()
            } catch (e: IOException) {
                Log.e("Exception", "File write failed: $e")
            }
        }
    }
}