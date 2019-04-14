package layout

import java.awt.Color
import java.awt.Font
import java.awt.Graphics

//TYPEALIAS------------------------------------------------------------------

typealias Action = () -> Unit
typealias GraphicAction = (Graphics, Int, Int) -> Unit
typealias MouseWheelAction = (Int) -> Unit
typealias RealFunction = (Double) -> Double

//FONTS-----------------------------------------------------------------------

/**
 * The default font used in the display system.
 * @see Font
 */
internal val DEFAULT_FONT : Font = Font("Courier New", Font.BOLD, 16)
/**
 * The default font used in the questions asked to the User.
 * @see Font
 */
internal val QUESTION_FONT : Font = Font("Courier New", Font.BOLD, 24)

/**
 * The default font used in title-type displays.
 * @see Font
 */
internal val TITLE_FONT : Font = Font("Courier New", Font.BOLD, 32)

//COLOURS----------------------------------------------------------------------

/**
 * The default color used in the display system.
 * @see Color
 */
internal val DEFAULT_COLOR : Color = Color.BLACK

//NUMBERS----------------------------------------------------------------------

const val DOUBLE_PRECISION : Double = 10e-2
