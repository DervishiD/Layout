package llayout5.utilities

/**
 * @since LLayout 1
 */
typealias Action = () -> Unit

/**
 * Invokes both actions.
 * @see Action
 * @since LLayout 3
 */
operator fun Action.plus(other : Action) : Action{
    return {
        invoke()
        other()
    }
}

/**
 * Repeats this Action [n] times.
 * @see Action
 * @since LLayout 5
 */
operator fun Action.times(n : Int) : Action{
    if(n < 0) throw IllegalArgumentException("An Action can't be executed a negative number of times.")
    return if(n == 0){
        {}
    }else{
        { for(k : Int in 1..n) invoke() }
    }
}

/**
 * Repeats the given Action [this] number of times.
 * @see Action
 * @since LLayout 5
 */
operator fun Int.times(action : Action) : Action = action * this
