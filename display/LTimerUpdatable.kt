package display

interface LTimerUpdatable {
    fun onTimerTick() : LTimerUpdatable{
        return this
    }
}