package com.example.programmertyccon

interface Subject{
    fun addObserver(observer: Observer)
    fun removeObserver(observer: Observer)
    fun notifyObservers()
}

interface Observer{
    fun update()
}

