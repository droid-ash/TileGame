package com.ash.tilegame

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TileViewModel : ViewModel() {

    private val uiData: TileModel by lazy {
        TileModel(
            action = ACTION_NONE,
            tapCount = 0,
            selectedNo = -1,
            list = getShuffledList()
        )
    }

    val uiLiveData: LiveData<TileModel>
        get() = _uiLiveData

    private val _uiLiveData: MutableLiveData<TileModel> by lazy {
        MutableLiveData()
    }

    fun onUIStarted() = notifyActionOnUI(ACTION_UPDATE_UI)

    fun setSelectedNumber(number: Int) = with(uiData) {
        selectedNo = number
    }

    fun onTileClicked(tile: Tile, position: Int) = with(uiData) {

        list[position].isSelected = true

        notifyActionOnUI(ACTION_UPDATE_UI)

        incrementTapCount()

        if (isTapRemaining() && !isTileMatched(tile.tileNumber)) return

        if (isTileMatched(tile.tileNumber)) {
            notifyActionOnUI(ACTION_SHOW_SUCCESS_DIALOG)
        } else {
            notifyActionOnUI(ACTION_SHOW_FAILED_DIALOG)
        }

    }

    private fun getShuffledList(): List<Tile> = ArrayList<Tile>().apply {
        for (i in START_INT..END_INT) {
            add(
                Tile(
                    tileNumber = i,
                    isSelected = false
                )
            )
        }
        shuffle()
    }

    private fun notifyActionOnUI(action: Int) {
        uiData.action = action
        notifyChangeInUI()
    }

    private fun notifyChangeInUI() {
        _uiLiveData.value = uiData
    }

}
