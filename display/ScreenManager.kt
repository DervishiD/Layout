package display

import display.screens.Screen
import main.mainFrame

public class ScreenManager {
    public companion object{

        @JvmStatic private var currentScreen : Screen = mainMenuScreen

        @JvmStatic private var previousScreen : Screen = mainMenuScreen

        @JvmStatic public fun start(){
            mainMenuScreen.load()
            mainFrame.contentPane = mainMenuScreen
            currentScreen = mainMenuScreen
        }

        @JvmStatic public fun toPreviousScreen(){
            currentScreen.save()
            currentScreen = currentScreen.previousScreen()
            previousScreen = currentScreen.previousScreen()
            currentScreen.load()
            mainFrame.contentPane = currentScreen
        }

        @JvmStatic public fun setScreen(screen : Screen){
            currentScreen.save()
            previousScreen = currentScreen.previousScreen()
            currentScreen = screen
            currentScreen.load()
            mainFrame.contentPane = currentScreen
        }

        @JvmStatic public fun currentScreen() : Screen{
            return currentScreen
        }

    }
}







