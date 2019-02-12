package display.screens

import display.*
import display.texts.MenuText
import main.Action
import main.FRAMEX
import main.FRAMEY
import main.mainFrame
import java.awt.event.WindowEvent
import java.awt.event.WindowEvent.WINDOW_CLOSING

class ExitScreen : Screen() {

    companion object {

        private val EXIT_PROGRAM_BUTTON_X : Int = FRAMEX / 3
        private val EXIT_PROGRAM_BUTTON_Y : Int = FRAMEY * 2 / 3
        private const val EXIT_PROGRAM_BUTTON_TEXT : String = "Exit"
        private val EXIT_PROGRAM_BUTTON_ACTION : Action = {
                mainFrame.isVisible = false
                mainFrame.dispatchEvent(WindowEvent(mainFrame, WINDOW_CLOSING))
            }
        private val EXIT_PROGRAM_BUTTON : Button by lazy{Button(EXIT_PROGRAM_BUTTON_X, EXIT_PROGRAM_BUTTON_Y, EXIT_PROGRAM_BUTTON_TEXT, EXIT_PROGRAM_BUTTON_ACTION)}

        private val CANCEL_EXIT_BUTTON_X : Int = FRAMEX * 2 / 3
        private val CANCEL_EXIT_BUTTON_Y : Int = EXIT_PROGRAM_BUTTON_Y
        private const val CANCEL_EXIT_BUTTON_TEXT : String = "Cancel"
        private val CANCEL_EXIT_BUTTON_ACTION : Action = {ScreenManager.escape()}
        private val CANCEL_EXIT_BUTTON : Button by lazy{Button(CANCEL_EXIT_BUTTON_X, CANCEL_EXIT_BUTTON_Y, CANCEL_EXIT_BUTTON_TEXT, CANCEL_EXIT_BUTTON_ACTION)}

        private val EXIT_PROGRAM_QUESTION_X : Int = FRAMEX / 2
        private val EXIT_PROGRAM_QUESTION_Y : Int = FRAMEY / 4
        private const val EXIT_PROGRAM_QUESTION_TEXT : String = "Are you sure you want to quit?"
        private val EXIT_PROGRAM_QUESTION : MenuText by lazy{ MenuText(EXIT_PROGRAM_QUESTION_X, EXIT_PROGRAM_QUESTION_Y, StringDisplay(EXIT_PROGRAM_QUESTION_TEXT, QUESTION_FONT)) }

    }

    init{
        previousScreen = mainMenuScreen
    }


    override fun load() {
        this add EXIT_PROGRAM_QUESTION
        this add EXIT_PROGRAM_BUTTON
        this add CANCEL_EXIT_BUTTON
    }

    override fun save() {
        this remove EXIT_PROGRAM_QUESTION
        this remove EXIT_PROGRAM_BUTTON
        this remove CANCEL_EXIT_BUTTON
    }

}