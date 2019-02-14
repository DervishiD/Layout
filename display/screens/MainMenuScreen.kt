package display.screens

import display.*
import display.texts.MenuText
import geometry.Vector
import main.Action
import main.FRAMEX
import main.FRAMEY
import main.Mouse

/**
 * The Main Menu Screen
 */
class MainMenuScreen : Screen() {

    companion object {
        private val TITLE_X : Int = FRAMEX / 2
        private val TITLE_Y : Int = FRAMEY / 5
        private const val TITLE_TEXT : String = "Title"
        private val TITLE : MenuText by lazy{MenuText(TITLE_X, TITLE_Y, StringDisplay(TITLE_TEXT, TITLE_FONT))}

        private val EDITOR_BUTTON_X : Int = FRAMEX / 3
        private val EDITOR_BUTTON_Y : Int = FRAMEY / 2
        private const val EDITOR_BUTTON_TEXT : String = "Editor"
        private val EDITOR_BUTTON_ACTION : Action = { ScreenManager setScreen editorScreen }
        private val EDITOR_BUTTON : Button by lazy{Button(EDITOR_BUTTON_X, EDITOR_BUTTON_Y, EDITOR_BUTTON_TEXT, EDITOR_BUTTON_ACTION)}

        private const val EXIT_BUTTON_TEXT : String = "X"
        private val EXIT_BUTTON_ACTION : Action = { ScreenManager setScreen exitProgramScreen }
        private val EXIT_BUTTON : Button by lazy{
            val result : Button = Button(0, 0, EXIT_BUTTON_TEXT, EXIT_BUTTON_ACTION)
            result alignUpTo 0
            result alignLeftTo 0
            result
        }

    }

    init{
        previousScreen = this

        var a : Button = Button(600, 600, "Click", {println("Hi")})
        val b : MenuText = MenuText(0, 0, "Hi")
        val c : DisplayerScrollPane = DisplayerScrollPane(1300, 600, 400, 500)
        c.add(a, 200)
        c.add(b)
        this add a
        this add b
        this add c

    }

    override fun load() {
        this add EDITOR_BUTTON
        this add TITLE
        this add EXIT_BUTTON
    }

    override fun save() {
        this remove EDITOR_BUTTON
        this remove TITLE
        this remove EXIT_BUTTON
    }

    override fun escape() {
        ScreenManager setScreen exitProgramScreen
    }

}