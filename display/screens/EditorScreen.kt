package display.screens

import display.*
import display.selectors.ArrowSelector
import display.texts.MenuText
import editor.GridDisplayer
import main.Action
import main.FRAMEX
import main.FRAMEY
import main.shiftPressed
import java.awt.Component
import java.awt.Graphics
import java.awt.event.KeyEvent

class EditorScreen : Screen(), TextFieldUser{

    private companion object {
        private val ALLOWED_GRID_WIDTH : Int = FRAMEX * 4/5
        private val ALLOWED_GRID_HEIGHT : Int = FRAMEY * 3/4
        private val ALLOWED_LEFT_WIDTH : Int = FRAMEX - ALLOWED_GRID_WIDTH
        private val ALLOWED_LEFT_HEIGHT : Int = ALLOWED_GRID_HEIGHT

        private val GRID_DISPLAYER_X : Int = ALLOWED_LEFT_WIDTH + ALLOWED_GRID_WIDTH / 2
        private val GRID_DISPLAYER_Y : Int = ALLOWED_GRID_HEIGHT / 2
        private val GRID_DISPLAYER : GridDisplayer = GridDisplayer(GRID_DISPLAYER_X, GRID_DISPLAYER_Y, ALLOWED_GRID_WIDTH, ALLOWED_GRID_HEIGHT)

        private const val WIDTH_TEXT_STRING : String = "Grid width"
        private const val HEIGHT_TEXT_STRING : String = "Grid height"
        private val WIDTH_TEXT : MenuText = MenuText(0, 0, WIDTH_TEXT_STRING)
        private val HEIGHT_TEXT : MenuText = MenuText(0, 0, HEIGHT_TEXT_STRING)

        private val TEXTFIELD_WIDTH : Int = ALLOWED_LEFT_WIDTH / 2
        private const val DEFAULT_GRID_WIDTH : Int = 10
        private const val DEFAULT_GRID_HEIGHT : Int = 10
        private val WIDTH_TEXTFIELD : TextField = TextField(0, 0, TEXTFIELD_WIDTH, "$DEFAULT_GRID_WIDTH", false, "\\d")
        private val HEIGHT_TEXTFIELD : TextField = TextField(0, 0, TEXTFIELD_WIDTH, "$DEFAULT_GRID_HEIGHT", false, "\\d")

        private const val RECENTER_BUTTON_TEXT : String = "Recenter grid"
        private val RECENTER_BUTTON_ACTION : Action = { GRID_DISPLAYER.resetOrigin()}
        private val RECENTER_BUTTON : Button = Button(0, 0, RECENTER_BUTTON_TEXT, RECENTER_BUTTON_ACTION)

        private val BRUSH_SIZE_SELECTOR_OPTIONS : ArrayList<Int> = arrayListOf(1, 3, 5, 7, 9, 11, 13, 15, 17, 19)
        private val BRUSH_SIZE_SELECTOR : ArrowSelector<Int> = ArrowSelector(0, 0, BRUSH_SIZE_SELECTOR_OPTIONS)

        private const val WIDTH_TEXT_DELTA : Int = 50
        private const val WIDTH_FIELD_DELTA : Int = 10
        private const val HEIGHT_TEXT_DELTA : Int = 50
        private const val HEIGHT_FIELD_DELTA : Int = 10
        private const val RECENTER_BUTTON_DELTA : Int = 50
        private const val BRUSH_SIZE_SELECTOR_DELTA : Int = 50
        private val LEFT_SCROLL_PANE : DisplayerScrollPane =
            DisplayerScrollPane(ALLOWED_LEFT_WIDTH/2, ALLOWED_LEFT_HEIGHT/2, ALLOWED_LEFT_WIDTH, ALLOWED_LEFT_HEIGHT).also {
                it.addToScrollPane(WIDTH_TEXT, WIDTH_TEXT_DELTA)
                it.addToScrollPane(WIDTH_TEXTFIELD, WIDTH_FIELD_DELTA)
                it.addToScrollPane(HEIGHT_TEXT, HEIGHT_TEXT_DELTA)
                it.addToScrollPane(HEIGHT_TEXTFIELD, HEIGHT_FIELD_DELTA)
                it.addToScrollPane(RECENTER_BUTTON, RECENTER_BUTTON_DELTA)
                it.addToScrollPane(BRUSH_SIZE_SELECTOR, BRUSH_SIZE_SELECTOR_DELTA)
            }

    }

    override var currentTextField: TextField? = null

    init{
        previousScreen = mainMenuScreen

    }

    /**
     * Updates the edited Grid
     */
    private fun updateGrid(){
        GRID_DISPLAYER.setGridWidth(WIDTH_TEXTFIELD.typedText().toInt())
        GRID_DISPLAYER.setGridHeight(HEIGHT_TEXTFIELD.typedText().toInt())
    }

    override fun drawBackground(g: Graphics) {
        val separatorLineThickness : Int = 24
        g.color = DEFAULT_COLOR
        g.fillRect(ALLOWED_LEFT_WIDTH - separatorLineThickness/2, 0, separatorLineThickness, ALLOWED_GRID_HEIGHT)
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
                "enter", "ENTER" -> updateGrid()
            }
        }
    }

    override fun mouseRelease(source: Component) {
        updateGrid()
        unfocusTextField()
        super.mouseRelease(source)
    }

    override fun load() {
        this add BACK_BUTTON
        this add GRID_DISPLAYER
        this add LEFT_SCROLL_PANE
    }

    override fun save() {
        unfocusTextField()
        this remove BACK_BUTTON
        this remove GRID_DISPLAYER
        this remove LEFT_SCROLL_PANE
    }

}