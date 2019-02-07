package display

import display.screens.EditorScreen
import display.screens.MainMenuScreen
import display.texts.MenuText
import main.Action
import main.FRAMEX
import main.FRAMEY
import java.awt.Color
import java.awt.Color.BLACK
import java.awt.Font
import java.awt.Font.BOLD

//FONTS------------------------------------------------------------------------

internal val DEFAULT_FONT : Font = Font("monospaced", BOLD, 24)
internal val TITLE_FONT : Font = Font("monospaced", BOLD, 40)

//COLOURS----------------------------------------------------------------------

internal val DEFAULT_COLOR : Color = BLACK

//STRINGS AND STRINGDISPLAYS---------------------------------------------------

private const val TITLE_TEXT : String = "Title"
private const val EDITOR_BUTTON_TEXT : String = "Editor"

//COORDINATES------------------------------------------------------------------

private val TITLE_X : Int = FRAMEX / 2
private val TITLE_Y : Int = FRAMEY / 5

private val EDITOR_BUTTON_X : Int = FRAMEX / 3
private val EDITOR_BUTTON_Y : Int = FRAMEY / 2

//ACTIONS----------------------------------------------------------------------

private val EDITOR_BUTTON_ACTION : Action = {ScreenManager setScreen editorScreen}

//SCREENS----------------------------------------------------------------------

val mainMenuScreen : MainMenuScreen by lazy{MainMenuScreen()}
val editorScreen : EditorScreen by lazy{EditorScreen()}

//MENUTEXTS--------------------------------------------------------------------

val TITLE : MenuText by lazy{MenuText(TITLE_X, TITLE_Y, StringDisplay(TITLE_TEXT, TITLE_FONT))}

//BUTTONS----------------------------------------------------------------------

val EDITOR_BUTTON : Button by lazy{Button(EDITOR_BUTTON_X, EDITOR_BUTTON_Y, EDITOR_BUTTON_TEXT, EDITOR_BUTTON_ACTION)}


