package com.example.programmertyccon

import java.io.*

private val TAG = "FileReaderUtil"

class FileReaderUtil {
    companion object {
        fun readFileAsString(filename: String): String {
            val sb = StringBuilder()
            try {
                val fis: FileInputStream = MainActivity.context!!.openFileInput(filename)
                val isr = InputStreamReader(fis, "UTF-8")
                val bufferedReader = BufferedReader(isr)

                var line: String?
                while (bufferedReader.readLine().also { line = it } != null) {
                    sb.append(line).append("\n")
                }

            } catch (e: FileNotFoundException) {
                "$TAG readFileAsString"
            } catch (e: UnsupportedEncodingException) {
                "$TAG readFileAsString"
            } catch (e: IOException) {
                "$TAG readFileAsString"
            }
            return sb.toString()
        }
    }
}