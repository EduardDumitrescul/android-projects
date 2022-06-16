package com.example.programmertyccon.utils

import java.lang.Double.min

class NumberFormatUtil {
    companion object{
        fun toString(value: Double): String {
            var string = value.toString()
            when{
                value >= 1e30 -> {
                    string = String.format("%.3f", (value / 1e30)) + "D"
                }
                value >= 1e27 -> {
                    string = String.format("%.3f", (value / 1e27)) + "N"
                }
                value >= 1e24 -> {
                    string = String.format("%.3f", (value / 1e24)) + "O"
                }
                value >= 1e21 -> {
                    string = String.format("%.3f", (value / 1e21)) + "S"
                }
                value >= 1e18 -> {
                    string = String.format("%.3f", (value / 1e18)) + "s"
                }
                value >= 1e15 -> {
                    string = String.format("%.3f", (value / 1e15)) + "Q"
                }
                value >= 1e12 -> {
                    string = String.format("%.3f", (value / 1e12)) + "q"
                }
                value >= 1e9 -> {
                    string = String.format("%.3f", (value / 1e9)) + "T"
                }
                value >= 1e6 -> {
                    string = String.format("%.3f", (value / 1e6)) + "M"
                }
                else -> {
                    string = value.toInt().toString()
                }
            }
            return string
        }

        fun toLongString(value: Double): String {
            var string = value.toString()
            when{
                value >= 1e30 -> {
                    string = String.format("%.3f", (value / 1e30)) + "decillion"
                    if(string[0] != '1') string += "s"
                }
                value >= 1e27 -> {
                    string = String.format("%.3f", (value / 1e27)) + "nonillion"
                    if(string[0] != '1') string += "s"
                }
                value >= 1e24 -> {
                    string = String.format("%.3f", (value / 1e24)) + "octillion"
                    if(string[0] != '1') string += "s"
                }
                value >= 1e21 -> {
                    string = String.format("%.3f", (value / 1e21)) + "septillion"
                    if(string[0] != '1') string += "s"
                }
                value >= 1e18 -> {
                    string = String.format("%.3f", (value / 1e18)) + "sextillion"
                    if(string[0] != '1') string += "s"
                }
                value >= 1e15 -> {
                    string = String.format("%.3f", (value / 1e15)) + "quintillion"
                    if(string[0] != '1') string += "s"
                }
                value >= 1e12 -> {
                    string = String.format("%.3f", (value / 1e12)) + "quadrillion"
                    if(string[0] != '1') string += "s"
                }
                value >= 1e9 -> {
                    string = String.format("%.3f", (value / 1e9)) + "trillion"
                    if(string[0] != '1') string += "s"
                }
                value >= 1e6 -> {
                    string = String.format("%.3f", (value / 1e6)) + "million"
                    if(string[0] != '1') string += "s"
                }
                else -> {
                    string = value.toInt().toString()
                }
            }
            return string
        }
    }
    fun countDigits(value: Double): Int {
        var aux = value
        var ans = 1
        while(aux > 1) {
            ans ++
            aux /= 10
        }
        return ans
    }
}