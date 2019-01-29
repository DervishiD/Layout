package display

import display.screens.staticscreens.MainMenuScreen
import display.texts.MenuText
import main.FRAMEX
import main.FRAMEY
import java.awt.Color
import java.awt.Color.BLACK
import java.awt.Font
import java.awt.Font.BOLD

//CONSTANTS--------------------------------------------------------------------

public const val TITLE_TEXT : String = "Title"
public val DEFAULT_FONT : Font = Font("monospaced", BOLD, 24)
public val TITLE_FONT : Font = Font("monospaced", BOLD, 40)
public val DEFAULT_COLOR : Color = BLACK

//SCREENS----------------------------------------------------------------------

public val mainMenuScreen : MainMenuScreen by lazy{MainMenuScreen()}

//MENUTEXTS--------------------------------------------------------------------

public val TITLE : MenuText by lazy{MenuText(FRAMEX / 2, FRAMEY / 5, StringDisplay(TITLE_TEXT, TITLE_FONT))}

//BUTTONS----------------------------------------------------------------------



