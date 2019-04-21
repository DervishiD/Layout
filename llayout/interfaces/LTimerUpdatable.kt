package llayout.interfaces

interface LTimerUpdatable {
    fun onTimerTick() : LTimerUpdatable {
        return this
    }
}