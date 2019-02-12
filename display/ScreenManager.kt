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
            currentScreen.initialize()
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
        @JvmStatic fun mouseClick(source : Component) = currentScreen.mouseClick(source)

        /**
         * Performs the event in the current Screen
         */
        @JvmStatic fun mousePress(source : Component) = currentScreen.mousePress(source)

        /**
         * Performs the event in the current Screen
         */
        @JvmStatic fun mouseRelease(source : Component) = currentScreen.mouseRelease(source)

        /**
         * Performs the event in the current Screen
         */
        @JvmStatic fun mouseEnter(source : Component) = currentScreen.mouseEnter(source)

        /**
         * Performs the event in the current Screen
         */
        @JvmStatic fun mouseExit(source : Component) = currentScreen.mouseExit(source)

        /**
         * Performs the event in the current Screen
         */
        @JvmStatic fun mouseMoved(source : Component) = currentScreen.mouseMoved(source)

        /**
         * Performs the event in the current Screen
         */
        @JvmStatic fun mouseDrag(source : Component) = currentScreen.mouseDrag(source)

        /**
         * Performs the event in the current Screen
         */
        @JvmStatic fun mouseWheelMoved(source : Component, units : Int) = currentScreen.mouseWheelMoved(source, units)

    }
}







