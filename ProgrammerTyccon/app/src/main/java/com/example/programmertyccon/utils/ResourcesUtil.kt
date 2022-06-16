package com.example.programmertyccon.utils

import com.example.programmertyccon.MainActivity

class ResourcesUtil {
    companion object {
        fun getString(id: Int): String {
            return MainActivity.context!!.getString(id)
        }

    }
}