package display

import utilities.LProperty

interface HavingDimension {
    var w : LProperty<Int>
    var h : LProperty<Int>
    fun width() : Int = w.value
    fun height() : Int = h.value
}