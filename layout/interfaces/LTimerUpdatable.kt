package layout.interfaces

interface LTimerUpdatable {
    fun onTimerTick() : LTimerUpdatable {
        return this
    }
}