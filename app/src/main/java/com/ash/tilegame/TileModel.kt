package com.ash.tilegame

const val ACTION_NONE = 0
const val ACTION_UPDATE_UI = 101
const val ACTION_SHOW_SUCCESS_DIALOG = 102
const val ACTION_SHOW_FAILED_DIALOG = 103

data class TileModel(
    var action : Int,
    var tapCount: Int,
    var selectedNo: Int = -1,
    var list: List<Tile>
) {

    fun incrementTapCount() {
        tapCount += 1
    }

    fun isTapRemaining(): Boolean = tapCount < MAX_TRY

    fun isTileMatched(tileNumber: Int): Boolean = tileNumber == selectedNo

}
