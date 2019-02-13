package display.screens

import display.BACK_BUTTON
import display.TextField
import display.mainMenuScreen
import editor.GridDisplayer
import main.FRAMEX
import main.FRAMEY
import main.shiftPressed
import java.awt.Color.BLACK
import java.awt.Color.WHITE
import java.awt.Component
import java.awt.Graphics
import java.awt.event.KeyEvent

class EditorScreen : Screen(), TextFieldUser{

    companion object {
        private val ALLOWED_GRID_WIDTH : Int = FRAMEX * 3/4
        private val ALLOWED_GRID_HEIGHT : Int = FRAMEY * 3/4
        private val ALLOWED_LEFT_WIDTH : Int = FRAMEX - ALLOWED_GRID_WIDTH
        private val ALLOWED_LEFT_HEIGHT : Int = FRAMEY - ALLOWED_GRID_HEIGHT

        private val GRID_DISPLAYER_X : Int = ALLOWED_LEFT_WIDTH + ALLOWED_GRID_WIDTH / 2
        private val GRID_DISPLAYER_Y : Int = ALLOWED_GRID_HEIGHT / 2
        private val GRID_DISPLAYER : GridDisplayer = GridDisplayer(GRID_DISPLAYER_X, GRID_DISPLAYER_Y, ALLOWED_GRID_WIDTH, ALLOWED_GRID_HEIGHT)

    }

    override var currentTextField: TextField? = null

    init{
        previousScreen = mainMenuScreen

    }

    override fun pressKey(key: Int) {
        var text : String = KeyEvent.getKeyText(key).toLowerCase()
        if(shiftPressed()){
            text = text.toUpperCase()
        }
        if(currentTextField != null){
            if(text.length == 1){
                currentTextField!!.type(text)
            }else when(text){
                "space", "SPACE" -> currentTextField!!.type(" ")
                "backspace", "BACKSPACE" -> currentTextField!!.backspace()
                "period", "PERIOD" -> currentTextField!!.type(".")
                "comma", "COMMA" -> currentTextField!!.type(",")
                "minus", "MINUS" -> currentTextField!!.type("-")
            }
        }
    }

    override fun mouseClick(source: Component) {
        super.mouseClick(source)
        currentTextField?.unfocus()
        if(source is TextField){
            currentTextField = source
            currentTextField!!.focus()
        }else if(currentTextField != null){
            currentTextField = null
        }
    }

    override fun load() {
        this add BACK_BUTTON
        this add GRID_DISPLAYER
    }

    override fun save() {
        currentTextField = null
        this remove BACK_BUTTON
        this remove GRID_DISPLAYER
    }

}