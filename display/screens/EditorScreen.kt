package display.screens

import display.TextField
import display.mainMenuScreen
import main.FRAMEX
import main.FRAMEY
import main.shiftPressed
import java.awt.Component
import java.awt.event.KeyEvent

class EditorScreen : Screen(), TextFieldUser{

    companion object {
        private val ALLOWED_GRID_WIDTH : Int = FRAMEX * 3/4
        private val ALLOWED_GRID_HEIGHT : Int = FRAMEY * 3/4
    }

    override var currentTextField: TextField? = null

    init{
        previousScreen = mainMenuScreen
        val a : TextField = TextField(500, 500, 100, "Hello there", true)
        a alignLeftTo 0
        this add a
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

    override fun save() {
        currentTextField = null
    }

    override fun load() {
        //TODO?
    }

}