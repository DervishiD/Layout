package llayout5

import java.awt.Color
import java.awt.Font
import java.awt.Graphics

//TYPEALIAS------------------------------------------------------------------

/**
 * @since LLayout 1
 */
typealias Action = () -> Unit

/**
 * @since LLayout 1
 */
typealias GraphicAction = (Graphics, Int, Int) -> Unit

/**
 * @since LLayout 1
 */
typealias RealFunction = (Double) -> Double

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

//FONTS-----------------------------------------------------------------------

/**
 * The default small font.
 * @see Font
 * @since LLayout 1
 */
internal val DEFAULT_SMALL_FONT : Font = Font("Monospaced", Font.BOLD, 16)
/**
 * The default medium font.
 * @see Font
 * @since LLayout 1
 */
internal val DEFAULT_MEDIUM_FONT : Font = Font("Monospaced", Font.BOLD, 24)

/**
 * The default large font.
 * @see Font
 * @since LLayout 1
 */
internal val DEFAULT_LARGE_FONT : Font = Font("Monospaced", Font.BOLD, 32)

//COLOURS----------------------------------------------------------------------

/**
 * The default color.
 * @see Color
 * @since LLayout 1
 */
internal val DEFAULT_COLOR : Color = Color.BLACK
