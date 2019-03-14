package display

import display.screens.*
import display.selectors.TextArrowSelector
import display.texts.MenuText
import editor.GridDisplayer
import editor.selections.ColumnSelector
import editor.selections.LineSelector
import main.*
import java.awt.Color
import java.awt.Color.BLACK
import java.awt.Component
import java.awt.Font
import java.awt.Font.BOLD
import java.awt.Graphics
import java.awt.event.KeyEvent
import java.awt.event.WindowEvent

//FONTS------------------------------------------------------------------------

internal val DEFAULT_FONT : Font = Font("monospaced", BOLD, 24)
internal val QUESTION_FONT : Font = Font("monospaced", BOLD, 32)
internal val TITLE_FONT : Font = Font("monospaced", BOLD, 40)

//COLOURS----------------------------------------------------------------------

internal val DEFAULT_COLOR : Color = BLACK

//BUTTONS----------------------------------------------------------------------

private const val BACK_BUTTON_TEXT : String = "<-"
private val BACK_BUTTON_ACTION : Action = {ScreenManager.escape()}
val BACK_BUTTON : Button by lazy{
    val result = Button(0, 0, BACK_BUTTON_TEXT, BACK_BUTTON_ACTION)
    result alignLeftTo 0
    result alignUpTo 0
    result
}

//NUMBERS----------------------------------------------------------------------

const val DEFAULT_GRID_MESH_SIZE : Int = 120
const val GRID_IMAGE_SIZE_DELTA : Int = 10
const val SMALLEST_GRID_IMAGE_SIZE : Int = 20

//SCREENS----------------------------------------------------------------------

/**
 * The Main Menu Screen, extending the Screen class
 */
val mainMenuScreen : Screen = object : Screen() {

    private val TITLE_X : Int = FRAMEX / 2
    private val TITLE_Y : Int = FRAMEY / 5
    private val TITLE_TEXT : String = "Title"
    private val TITLE : MenuText by lazy{ MenuText(TITLE_X, TITLE_Y, StringDisplay(TITLE_TEXT, TITLE_FONT)) }

    private val EDITOR_BUTTON_X : Int = FRAMEX / 3
    private val EDITOR_BUTTON_Y : Int = FRAMEY / 2
    private val EDITOR_BUTTON_TEXT : String = "Editor"
    private val EDITOR_BUTTON_ACTION : Action = { ScreenManager setScreen editorScreen }
    private val EDITOR_BUTTON : Button by lazy{ Button(EDITOR_BUTTON_X, EDITOR_BUTTON_Y, EDITOR_BUTTON_TEXT, EDITOR_BUTTON_ACTION) }

    private val EXIT_BUTTON_TEXT : String = "X"
    private val EXIT_BUTTON_ACTION : Action = { ScreenManager setScreen exitProgramScreen }
    private val EXIT_BUTTON : Button by lazy{
        val result = Button(0, 0, EXIT_BUTTON_TEXT, EXIT_BUTTON_ACTION)
        result alignUpTo 0
        result alignLeftTo 0
        result
    }

    override var previousScreen: Screen = this

    init{
        val a = Button(0, 0, "Click", {println("Hi")})
        val b  = MenuText(0, 0, "Hi")
        val c = DisplayerScrollPane(1300, 600, 400, 500) { g : Graphics, w : Int, h : Int -> run{
            g.color = Color.BLACK
            g.drawRect(0, 0, w-1, h-1)
        }}
        c.addToScrollPane(a, 50)
        c.addToScrollPane(b)
        this add c

        val d = TextArrowSelector(300, 600, "Hi" to "Hi", "hi hi hi hi hi hi hi hi hi" to "hi hi hi", isHorizontal = true)
        d setNextArrowColor Color.RED
        d setMaxLineLength 200
        d.alignUpToDown(c)
        d.alignRightToRight(c)
        this add d

        val e = TextArrowSelector(200, 200,
            "Hello there" to 1, "General Kenobi" to 2,
            StringDisplay("Hello There", Color.RED) to 3,
            StringDisplay("General Kenobi", Font("Arial", BOLD, 30)) to 4,
            listOf(StringDisplay("Hello there\n", Color.RED), StringDisplay("General Kenobi", Color.GREEN)) to 5)

        e.setMaxLineLength(250)
        e.setPreferredWidth(250)
        e.alignLeftTo(0)
        e.setNextArrowColor(Color.RED)
        this add e

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

/**
 * The Editor Screen, extending the Screen class
 */
val editorScreen : Screen = object : Screen(), TextFieldUser {

    private val ALLOWED_GRID_WIDTH : Int = FRAMEX * 4/5
    private val ALLOWED_GRID_HEIGHT : Int = FRAMEY * 3/4
    private val ALLOWED_LEFT_WIDTH : Int = FRAMEX - ALLOWED_GRID_WIDTH
    private val ALLOWED_LEFT_HEIGHT : Int = ALLOWED_GRID_HEIGHT

    private val GRID_DISPLAYER_X : Int = ALLOWED_LEFT_WIDTH + ALLOWED_GRID_WIDTH / 2
    private val GRID_DISPLAYER_Y : Int = ALLOWED_GRID_HEIGHT / 2
    private val GRID_DISPLAYER : GridDisplayer = GridDisplayer(GRID_DISPLAYER_X, GRID_DISPLAYER_Y, ALLOWED_GRID_WIDTH, ALLOWED_GRID_HEIGHT)

    private val WIDTH_TEXT_STRING : String = "Grid width"
    private val HEIGHT_TEXT_STRING : String = "Grid height"
    private val WIDTH_TEXT : MenuText = MenuText(0, 0, WIDTH_TEXT_STRING)
    private val HEIGHT_TEXT : MenuText = MenuText(0, 0, HEIGHT_TEXT_STRING)

    private val TEXTFIELD_WIDTH : Int = ALLOWED_LEFT_WIDTH / 2
    private val DEFAULT_GRID_WIDTH : Int = 10
    private val DEFAULT_GRID_HEIGHT : Int = 10
    private val WIDTH_TEXTFIELD : TextField = TextField(0, 0, TEXTFIELD_WIDTH, "$DEFAULT_GRID_WIDTH", false, "\\d")
    private val HEIGHT_TEXTFIELD : TextField = TextField(0, 0, TEXTFIELD_WIDTH, "$DEFAULT_GRID_HEIGHT", false, "\\d")

    private val RECENTER_BUTTON_TEXT : String = "Recenter grid"
    private val RECENTER_BUTTON_ACTION : Action = { GRID_DISPLAYER.resetOrigin()}
    private val RECENTER_BUTTON : Button = Button(0, 0, RECENTER_BUTTON_TEXT, RECENTER_BUTTON_ACTION)

    private val BRUSH_SIZE_SELECTOR_OPTIONS : Map<Any?, Any?> = mapOf(
        "1" to 1, "3" to 3, "5" to 5, "7" to 7, "9" to 9, "11" to 11, "13" to 13, "15" to 15, "17" to 17, "19" to 19
    )
    private val BRUSH_SIZE_SELECTOR : TextArrowSelector = TextArrowSelector(0, 0, BRUSH_SIZE_SELECTOR_OPTIONS)

    private val CELL_SELECTORS : Map<Any?, Any?> =
        mapOf("Lines" to LineSelector(GRID_DISPLAYER), "Columns" to ColumnSelector(GRID_DISPLAYER))
    private val CELL_SELECTOR_SELECTOR : TextArrowSelector = TextArrowSelector(0, 0, CELL_SELECTORS)

    private val WIDTH_TEXT_DELTA : Int = 50
    private val WIDTH_FIELD_DELTA : Int = 10
    private val HEIGHT_TEXT_DELTA : Int = 50
    private val HEIGHT_FIELD_DELTA : Int = 10
    private val RECENTER_BUTTON_DELTA : Int = 50
    private val BRUSH_SIZE_SELECTOR_DELTA : Int = 50
    private val CELL_SELECTOR_SELECTOR_DELTA : Int = 50
    private val LEFT_SCROLL_PANE : DisplayerScrollPane =
        DisplayerScrollPane(ALLOWED_LEFT_WIDTH/2, ALLOWED_LEFT_HEIGHT/2, ALLOWED_LEFT_WIDTH, ALLOWED_LEFT_HEIGHT).also {
            it.alignLeftTo(0)
            it.alignUpTo(0)
            it.addToScrollPane(WIDTH_TEXT, WIDTH_TEXT_DELTA)
            it.addToScrollPane(WIDTH_TEXTFIELD, WIDTH_FIELD_DELTA)
            it.addToScrollPane(HEIGHT_TEXT, HEIGHT_TEXT_DELTA)
            it.addToScrollPane(HEIGHT_TEXTFIELD, HEIGHT_FIELD_DELTA)
            it.addToScrollPane(RECENTER_BUTTON, RECENTER_BUTTON_DELTA)
            it.addToScrollPane(BRUSH_SIZE_SELECTOR, BRUSH_SIZE_SELECTOR_DELTA)
            it.addToScrollPane(CELL_SELECTOR_SELECTOR, CELL_SELECTOR_SELECTOR_DELTA)
        }


    override var currentTextField: TextField? = null
    override var previousScreen: Screen = mainMenuScreen

    /**
     * Updates the edited Grid
     */
    private fun updateGrid(){
        GRID_DISPLAYER.setGridWidth(WIDTH_TEXTFIELD.typedText().toInt())
        GRID_DISPLAYER.setGridHeight(HEIGHT_TEXTFIELD.typedText().toInt())
    }

    override fun drawBackground(g: Graphics) {
        val separatorLineThickness = 24
        g.color = DEFAULT_COLOR
        g.fillRect(ALLOWED_LEFT_WIDTH - separatorLineThickness/2, 0, separatorLineThickness, ALLOWED_GRID_HEIGHT)
        g.fillRect(ALLOWED_LEFT_WIDTH, ALLOWED_LEFT_HEIGHT - separatorLineThickness / 2, ALLOWED_GRID_WIDTH, separatorLineThickness)
    }

    override fun pressKey(key: Int) {
        var text : String = KeyEvent.getKeyText(key).toLowerCase()
        if(shiftPressed()){
            text = text.toUpperCase()
        }
        if(currentTextField != null){
            typeInField(text)
        }
    }

    /**
     * Types the given text in the focused TextField
     */
    private infix fun typeInField(text : String){
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

/**
 * The Screen that exits the program, extending the Screen class
 */
val exitProgramScreen : Screen = object : Screen(){

    private val EXIT_PROGRAM_BUTTON_X : Int = FRAMEX / 3
    private val EXIT_PROGRAM_BUTTON_Y : Int = FRAMEY * 2 / 3
    private val EXIT_PROGRAM_BUTTON_TEXT : String = "Exit"
    private val EXIT_PROGRAM_BUTTON_ACTION : Action = {
        mainFrame.isVisible = false
        mainFrame.dispatchEvent(WindowEvent(mainFrame, WindowEvent.WINDOW_CLOSING))
    }
    private val EXIT_PROGRAM_BUTTON : Button by lazy{Button(EXIT_PROGRAM_BUTTON_X, EXIT_PROGRAM_BUTTON_Y, EXIT_PROGRAM_BUTTON_TEXT, EXIT_PROGRAM_BUTTON_ACTION)}

    private val CANCEL_EXIT_BUTTON_X : Int = FRAMEX * 2 / 3
    private val CANCEL_EXIT_BUTTON_Y : Int = EXIT_PROGRAM_BUTTON_Y
    private val CANCEL_EXIT_BUTTON_TEXT : String = "Cancel"
    private val CANCEL_EXIT_BUTTON_ACTION : Action = {ScreenManager.escape()}
    private val CANCEL_EXIT_BUTTON : Button by lazy{Button(CANCEL_EXIT_BUTTON_X, CANCEL_EXIT_BUTTON_Y, CANCEL_EXIT_BUTTON_TEXT, CANCEL_EXIT_BUTTON_ACTION)}

    private val EXIT_PROGRAM_QUESTION_X : Int = FRAMEX / 2
    private val EXIT_PROGRAM_QUESTION_Y : Int = FRAMEY / 4
    private val EXIT_PROGRAM_QUESTION_TEXT : String = "Are you sure you want to quit?"
    private val EXIT_PROGRAM_QUESTION : MenuText by lazy{ MenuText(EXIT_PROGRAM_QUESTION_X, EXIT_PROGRAM_QUESTION_Y, StringDisplay(EXIT_PROGRAM_QUESTION_TEXT, QUESTION_FONT)) }

    override var previousScreen: Screen = mainMenuScreen

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
