package br.com.hackccr.features.tab

import com.ncapdevi.fragnav.FragNavController

interface TabView {
    fun goToTabWithId(tabId: Int)
    fun getFragmentController(): FragNavController?
}