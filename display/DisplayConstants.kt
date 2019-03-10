package display

import display.screens.EditorScreen
import display.screens.ExitScreen
import display.screens.MainMenuScreen
import main.Action
import java.awt.Color
import java.awt.Color.BLACK
import java.awt.Font
import java.awt.Font.BOLD

//FONTS------------------------------------------------------------------------

internal val DEFAULT_FONT : Font = Font("monospaced", BOLD, 24)
internal val QUESTION_FONT : Font = Font("monospaced", BOLD, 32)
internal val TITLE_FONT : Font = Font("monospaced", BOLD, 40)

//COLOURS----------------------------------------------------------------------

internal val DEFAULT_COLOR : Color = BLACK

//SCREENS----------------------------------------------------------------------

val mainMenuScreen : MainMenuScreen by lazy{MainMenuScreen()}
val editorScreen : EditorScreen by lazy{EditorScreen()}
val exitProgramScreen : ExitScreen by lazy{ExitScreen()}

//BUTTONS----------------------------------------------------------------------

private const val BACK_BUTTON_TEXT : String = "<-"
private val BACK_BUTTON_ACTION : Action = {ScreenManager.escape()}
val BACK_BUTTON : Button by lazy{
    val result : Button = Button(0, 0, BACK_BUTTON_TEXT, BACK_BUTTON_ACTION)
    result alignLeftTo 0
    result alignUpTo 0
    result
}

//NUMBERS----------------------------------------------------------------------

const val DEFAULT_GRID_MESH_SIZE : Int = 120
const val GRID_IMAGE_SIZE_DELTA : Int = 10
const val SMALLEST_GRID_IMAGE_SIZE : Int = 20



