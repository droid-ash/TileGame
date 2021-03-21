package com.ash.tilegame

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TileViewModel : ViewModel() {

    val tileListLiveData = getShuffledList()

    private fun getShuffledList(): LiveData<MutableList<Int>> {
        val list = ArrayList<Int>()
        for (i in START_INT..END_INT) {
            list.add(i)
        }
        list.shuffle()
        return MutableLiveData(list)
    }
}