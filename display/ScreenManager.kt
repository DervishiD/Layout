package display

import display.screens.Screen
import main.mainFrame
import java.awt.Component

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
            currentScreen.initialization()
        }

        /**
         * Escapes the current screen
         */
        @JvmStatic fun escape() = currentScreen.escape()

        /**
         * Gives the given key to the current Screen
         */
        @JvmStatic fun pressKey(key : Int) = currentScreen.pressKey(key)

        /**
         * Gives the given key to the current Screen
         */
        @JvmStatic fun releaseKey(key : Int) = currentScreen.releaseKey(key)

        /**
         * Performs the event in the current Screen
         */
        @JvmStatic fun mouseClick(x : Int, y : Int) = currentScreen.mouseClick(x, y)

        /**
         * Performs the event in the current Screen
         */
        @JvmStatic fun mousePress(x : Int, y : Int) = currentScreen.mousePress(x, y)

        /**
         * Performs the event in the current Screen
         */
        @JvmStatic fun mouseRelease(x : Int, y : Int) = currentScreen.mouseRelease(x, y)

        /**
         * Performs the event in the current Screen
         */
        @JvmStatic fun mouseEnter(x : Int, y : Int) = currentScreen.mouseEnter(x, y)

        /**
         * Performs the event in the current Screen
         */
        @JvmStatic fun mouseExit(x : Int, y : Int) = currentScreen.mouseExit(x, y)

        /**
         * Performs the event in the current Screen
         */
        @JvmStatic fun mouseMoved(x : Int, y : Int) = currentScreen.mouseMoved(x, y)

        /**
         * Performs the event in the current Screen
         */
        @JvmStatic fun mouseDrag(x : Int, y : Int) = currentScreen.mouseDrag(x, y)

        /**
         * Performs the event in the current Screen
         */
        @JvmStatic fun mouseWheelMoved(x : Int, y : Int, units : Int) = currentScreen.mouseWheelMoved(x, y, units)

    }
}







