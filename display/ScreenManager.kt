package display

import main.mainFrame

/**
 * A class that manages the transition between screens.
 * @see Screen
 */
class ScreenManager {

    companion object{

        /**
         * The Screen that is currently displayed.
         * @see Screen
         */
        @JvmStatic private var currentScreen : Screen = mainMenuScreen

        /**
         * Displays the starting Screen.
         * @see Screen
         */
        @JvmStatic fun start(){
            mainMenuScreen.load()
            mainFrame.contentPane = mainMenuScreen
            currentScreen = mainMenuScreen
        }

        /**
         * Goes to the previous Screen.
         * @see Screen
         * @see Screen.previousScreen
         * @see setScreen
         */
        @JvmStatic fun toPreviousScreen(){
            setScreen(currentScreen.previousScreen())
        }

        /**
         * Changes the current Screen.
         * The methods starts by saving the old one, then loads the new one, and
         * finally initializes it.
         * @param screen The new displayed Screen.
         * @see Screen
         * @see Screen.save
         * @see Screen.load
         * @see Screen.initialization
         * @see currentScreen
         */
        @JvmStatic infix fun setScreen(screen : Screen){
            currentScreen.save()
            currentScreen = screen
            currentScreen.load()
            mainFrame.contentPane = currentScreen
            currentScreen.initialization()
        }

        /**
         * Escapes the current screen.
         * @see currentScreen
         * @see Screen
         * @see Screen.escape
         */
        @JvmStatic fun escape() = currentScreen.escape()

        /**
         * Transmits the event to the current Screen.
         * @param key The code of the pressed key.
         * @see currentScreen
         * @see Screen
         * @see Screen.pressKey
         */
        @JvmStatic infix fun pressKey(key : Int) = currentScreen.pressKey(key)

        /**
         * Transmits the event to the current Screen.
         * @param key The code of the released key.
         * @see currentScreen
         * @see Screen
         * @see Screen.releaseKey
         */
        @JvmStatic infix fun releaseKey(key : Int) = currentScreen.releaseKey(key)

        /**
         * Transmits the event to the current Screen.
         * @param x The x coordinate of the event.
         * @param y The y coordinate of the event.
         * @see currentScreen
         * @see Screen
         * @see Screen.mouseClick
         */
        @JvmStatic fun mouseClick(x : Int, y : Int) = currentScreen.mouseClick(x, y)

        /**
         * Transmits the event to the current Screen.
         * @param x The x coordinate of the event.
         * @param y The y coordinate of the event.
         * @see currentScreen
         * @see Screen
         * @see Screen.mousePress
         */
        @JvmStatic fun mousePress(x : Int, y : Int) = currentScreen.mousePress(x, y)

        /**
         * Transmits the event to the current Screen.
         * @param x The x coordinate of the event.
         * @param y The y coordinate of the event.
         * @see currentScreen
         * @see Screen
         * @see Screen.mouseRelease
         */
        @JvmStatic fun mouseRelease(x : Int, y : Int) = currentScreen.mouseRelease(x, y)

        /**
         * Transmits the event to the current Screen.
         * @param x The x coordinate of the event.
         * @param y The y coordinate of the event.
         * @see currentScreen
         * @see Screen
         * @see Screen.mouseEnter
         */
        @JvmStatic fun mouseEnter(x : Int, y : Int) = currentScreen.mouseEnter(x, y)

        /**
         * Transmits the event to the current Screen.
         * @param x The x coordinate of the event.
         * @param y The y coordinate of the event.
         * @see currentScreen
         * @see Screen
         * @see Screen.mouseExit
         */
        @JvmStatic fun mouseExit(x : Int, y : Int) = currentScreen.mouseExit(x, y)

        /**
         * Transmits the event to the current Screen.
         * @param x The x coordinate of the event.
         * @param y The y coordinate of the event.
         * @see currentScreen
         * @see Screen
         * @see Screen.mouseMoved
         */
        @JvmStatic fun mouseMoved(x : Int, y : Int) = currentScreen.mouseMoved(x, y)

        /**
         * Transmits the event to the current Screen.
         * @param x The x coordinate of the event.
         * @param y The y coordinate of the event.
         * @see currentScreen
         * @see Screen
         * @see Screen.mouseDrag
         */
        @JvmStatic fun mouseDrag(x : Int, y : Int) = currentScreen.mouseDrag(x, y)

        /**
         * Transmits the event to the current Screen.
         * @param x The x coordinate of the event.
         * @param y The y coordinate of the event.
         * @param units The number of units scrolled.
         * @see currentScreen
         * @see Screen
         * @see Screen.mouseClick
         */
        @JvmStatic fun mouseWheelMoved(x : Int, y : Int, units : Int) = currentScreen.mouseWheelMoved(x, y, units)

    }

}
