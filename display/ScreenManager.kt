package display

import display.screens.Screen
import main.mainFrame

/**
 * A class that manages the transition between screens
 */
class ScreenManager {
    companion object{

        /**
         * The current Screen
         */
        @JvmStatic private var currentScreen : Screen = mainMenuScreen

        /**
         * Displays the main Screen
         */
        @JvmStatic fun start(){
            mainMenuScreen.load()
            mainFrame.contentPane = mainMenuScreen
            currentScreen = mainMenuScreen
        }

        /**
         * Goes to the previous Screen
         */
        @JvmStatic fun toPreviousScreen(){
            setScreen(currentScreen.previousScreen())
        }

        /**
         * Changes the displayed Screen for the given one
         */
        @JvmStatic infix fun setScreen(screen : Screen){
            currentScreen.save()
            currentScreen = screen
            currentScreen.load()
            mainFrame.contentPane = currentScreen
            currentScreen.initialize()
        }

        /**
         * The current Screen
         */
        @JvmStatic fun currentScreen() : Screen{
            return currentScreen
        }

    }
}







