package display

import editor.GridDisplayer
import editor.selections.AbstractGridSelector
import editor.selections.ColumnSelector
import editor.selections.LineSelector
import main.*
import utilities.StringDisplay
import utilities.Text
import java.awt.Color
import java.awt.Color.BLACK
import java.awt.Component
import java.awt.Font
import java.awt.Font.BOLD
import java.awt.Graphics
import java.awt.event.KeyEvent.VK_ENTER

//FONTS------------------------------------------------------------------------

/**
 * The default font used in the display system.
 * @see Font
 */
internal val DEFAULT_FONT : Font = Font("Courier New", BOLD, 16)
/**
 * The default font used in the questions asked to the User.
 * @see Font
 */
internal val QUESTION_FONT : Font = Font("Courier New", BOLD, 24)

/**
 * The default font used in title-type displays.
 * @see Font
 */
internal val TITLE_FONT : Font = Font("Courier New", BOLD, 32)

//COLOURS----------------------------------------------------------------------

/**
 * The default color used in the display system.
 * @see Color
 */
internal val DEFAULT_COLOR : Color = BLACK

//NUMBERS----------------------------------------------------------------------

const val DEFAULT_GRID_MESH_SIZE : Int = 120
const val GRID_IMAGE_SIZE_DELTA : Int = 10
const val SMALLEST_GRID_IMAGE_SIZE : Int = 20

//SCREENS----------------------------------------------------------------------

/**
 * The Main Menu Screen.
 * @see Screen
 */
val mainMenuScreen : Screen = object : Screen() {

    private val TITLE_X : Int = FRAMEX / 2
    private val TITLE_Y : Int = FRAMEY / 5
    private val TITLE_TEXT : String = "Title"
    private val TITLE : Label by lazy{
        Label(
            TITLE_X,
            TITLE_Y,
            StringDisplay(TITLE_TEXT, TITLE_FONT)
        )
    }

    private val EDITOR_BUTTON_X : Int = FRAMEX / 3
    private val EDITOR_BUTTON_Y : Int = FRAMEY / 2
    private val EDITOR_BUTTON_TEXT : String = "Editor"
    private val EDITOR_BUTTON_ACTION : Action = { setNextScreen(editorScreen) }
    private val EDITOR_BUTTON : TextButton by lazy{ TextButton(EDITOR_BUTTON_X, EDITOR_BUTTON_Y, EDITOR_BUTTON_TEXT, EDITOR_BUTTON_ACTION) }

    private val EXIT_BUTTON_TEXT : String = "X"
    private val EXIT_BUTTON_ACTION : Action = { setNextScreen(exitProgramScreen) }
    private val EXIT_BUTTON : TextButton by lazy{
        val result = TextButton(0, 0, EXIT_BUTTON_TEXT, EXIT_BUTTON_ACTION)
        result alignUpTo 0
        result alignLeftTo 0
        result
    }

    private val TEST_BUTTON_X : Int = FRAMEX / 2
    private val TEST_BUTTON_Y : Int = FRAMEY * 4 / 5
    private val TEST_BUTTON_TEXT : String = "Test Screen"
    private val TEST_BUTTON_ACTION : Action = {setNextScreen(testScreen)}
    private val TEST_BUTTON : TextButton = TextButton(TEST_BUTTON_X, TEST_BUTTON_Y, TEST_BUTTON_TEXT, TEST_BUTTON_ACTION)

    override var previousScreen: Screen = this

    override fun load() {
        this add EDITOR_BUTTON
        this add TITLE
        this add TEST_BUTTON
        this add EXIT_BUTTON
    }

    override fun save() {
        this remove EDITOR_BUTTON
        this remove TITLE
        this remove TEST_BUTTON
        this remove EXIT_BUTTON
    }

    override fun escape() = setNextScreen(exitProgramScreen)

}

/**
 * The Editor Screen.
 * @see Screen
 */
val editorScreen : Screen = object : Screen() {

    private val ALLOWED_GRID_WIDTH : Int = FRAMEX * 4/5
    private val ALLOWED_GRID_HEIGHT : Int = FRAMEY * 3/4
    private val ALLOWED_LEFT_WIDTH : Int = FRAMEX - ALLOWED_GRID_WIDTH
    private val ALLOWED_LEFT_HEIGHT : Int = ALLOWED_GRID_HEIGHT

    private val GRID_DISPLAYER_X : Int = ALLOWED_LEFT_WIDTH + ALLOWED_GRID_WIDTH / 2
    private val GRID_DISPLAYER_Y : Int = ALLOWED_GRID_HEIGHT / 2
    private val GRID_DISPLAYER : GridDisplayer = GridDisplayer(GRID_DISPLAYER_X, GRID_DISPLAYER_Y, ALLOWED_GRID_WIDTH, ALLOWED_GRID_HEIGHT)

    private val BACK_BUTTON_TEXT : String = "<-"
    private val BACK_BUTTON_ACTION : Action = {escape()}
    val BACK_BUTTON : TextButton by lazy{
        val result = TextButton(0, 0, BACK_BUTTON_TEXT, BACK_BUTTON_ACTION)
        result alignLeftTo 0
        result alignUpTo 0
        result
    }

    private val WIDTH_TEXT_STRING : String = "Grid width"
    private val HEIGHT_TEXT_STRING : String = "Grid height"
    private val WIDTH_TEXT : Label = Label(0, 0, WIDTH_TEXT_STRING)
    private val HEIGHT_TEXT : Label = Label(0, 0, HEIGHT_TEXT_STRING)

    private val TEXTFIELD_WIDTH : Int = ALLOWED_LEFT_WIDTH / 2
    private val DEFAULT_GRID_WIDTH : Int = 10
    private val DEFAULT_GRID_HEIGHT : Int = 10
    private val WIDTH_TEXTFIELD : TextField = TextField(0, 0, TEXTFIELD_WIDTH, "$DEFAULT_GRID_WIDTH", "\\d")
    private val HEIGHT_TEXTFIELD : TextField = TextField(0, 0, TEXTFIELD_WIDTH, "$DEFAULT_GRID_HEIGHT", "\\d")

    private val RECENTER_BUTTON_TEXT : String = "Recenter grid"
    private val RECENTER_BUTTON_ACTION : Action = { GRID_DISPLAYER.resetOrigin()}
    private val RECENTER_BUTTON : TextButton = TextButton(0, 0, RECENTER_BUTTON_TEXT, RECENTER_BUTTON_ACTION)

    private val BRUSH_SELECTOR_TEXT_STRING : String = "Brush size"
    private val BRUSH_SELECTOR_TEXT : Label = Label(0, 0, BRUSH_SELECTOR_TEXT_STRING)

    private val BRUSH_SIZE_SELECTOR_OPTIONS : Map<Text, Int> = mapOf(
        Text(1) to 1, Text(3) to 3, Text(5) to 5, Text(7) to 7, Text(9) to 9, Text(11) to 11
    )
    private val BRUSH_SIZE_SELECTOR : TextArrowSelector<Int> =
        TextArrowSelector(0, 0, BRUSH_SIZE_SELECTOR_OPTIONS)

    private val SELECTOR_SELECTOR_TEXT_STRING : String = "Selection type"
    private val SELECTOR_SELECTOR_TEXT : Label = Label(0, 0, SELECTOR_SELECTOR_TEXT_STRING)

    private val CELL_SELECTORS : Map<Text, AbstractGridSelector?> =
        mapOf(Text("None") to null, Text("Lines") to LineSelector(GRID_DISPLAYER), Text("Columns") to ColumnSelector(GRID_DISPLAYER))
    private val CELL_SELECTOR_SELECTOR : TextArrowSelector<AbstractGridSelector?> =
        TextArrowSelector(0, 0, CELL_SELECTORS)

    private val WIDTH_TEXT_DELTA : Int = 50
    private val WIDTH_FIELD_DELTA : Int = 10
    private val HEIGHT_TEXT_DELTA : Int = 50
    private val HEIGHT_FIELD_DELTA : Int = 10
    private val RECENTER_BUTTON_DELTA : Int = 50
    private val BRUSH_SELECTOR_TEXT_DELTA : Int = 50
    private val BRUSH_SIZE_SELECTOR_DELTA : Int = 10
    private val SELECTOR_SELECTOR_TEXT_DELTA : Int = 50
    private val CELL_SELECTOR_SELECTOR_DELTA : Int = 10
    private val LEFT_SCROLL_PANE : DisplayerScrollPane =
        DisplayerScrollPane(ALLOWED_LEFT_WIDTH/2, ALLOWED_LEFT_HEIGHT/2, ALLOWED_LEFT_WIDTH, ALLOWED_LEFT_HEIGHT).also {
            it.alignLeftTo(0)
            it.alignUpTo(0)
            it.addToScrollPane(WIDTH_TEXT, WIDTH_TEXT_DELTA)
            it.addToScrollPane(WIDTH_TEXTFIELD, WIDTH_FIELD_DELTA)
            it.addToScrollPane(HEIGHT_TEXT, HEIGHT_TEXT_DELTA)
            it.addToScrollPane(HEIGHT_TEXTFIELD, HEIGHT_FIELD_DELTA)
            it.addToScrollPane(RECENTER_BUTTON, RECENTER_BUTTON_DELTA)
            it.addToScrollPane(BRUSH_SELECTOR_TEXT, BRUSH_SELECTOR_TEXT_DELTA)
            it.addToScrollPane(BRUSH_SIZE_SELECTOR, BRUSH_SIZE_SELECTOR_DELTA)
            it.addToScrollPane(SELECTOR_SELECTOR_TEXT, SELECTOR_SELECTOR_TEXT_DELTA)
            it.addToScrollPane(CELL_SELECTOR_SELECTOR, CELL_SELECTOR_SELECTOR_DELTA)
        }

    override var previousScreen: Screen = mainMenuScreen

    var currentTextField: TextField? = null

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
        if(currentTextField != null){
            if(key == VK_ENTER){
                unfocusTextField()
                updateGrid()
            }else currentTextField!!.type(key)
        }
    }

    infix fun focusTextField(toFocus : TextField){
        currentTextField = toFocus
        currentTextField!!.focus()
    }

    fun unfocusTextField(){
        currentTextField?.unfocus()
        currentTextField = null
    }

    override fun mouseRelease(x : Int, y : Int) {
        unfocusTextField()
        val component : Component = getComponentAt(x, y)
        when(component){
            //is Screen -> mouseRelease() -- useless here
            is AbstractDisplayerContainer ->{
                val bottom : Displayer = component.displayerAt(x - component.lowestX(), y - component.lowestY())
                if(bottom is TextField){
                    focusTextField(bottom)
                    bottom.mouseRelease()
                }else bottom.mouseRelease()
            }
            is Displayer -> {
                if(component is TextField){
                    focusTextField(component)
                    component.mouseRelease()
                }else component.mouseRelease()
            }
        }
        updateGrid()
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
 * The Screen that exits the program.
 * @see Screen
 */
val exitProgramScreen : Screen = object : Screen(){

    private val EXIT_PROGRAM_BUTTON_X : Int = FRAMEX / 3
    private val EXIT_PROGRAM_BUTTON_Y : Int = FRAMEY * 2 / 3
    private val EXIT_PROGRAM_BUTTON_TEXT : String = "Exit"
    private val EXIT_PROGRAM_BUTTON_ACTION : Action = {mainFrame.close()}
    private val EXIT_PROGRAM_BUTTON : TextButton by lazy{TextButton(EXIT_PROGRAM_BUTTON_X, EXIT_PROGRAM_BUTTON_Y, EXIT_PROGRAM_BUTTON_TEXT, EXIT_PROGRAM_BUTTON_ACTION)}

    private val CANCEL_EXIT_BUTTON_X : Int = FRAMEX * 2 / 3
    private val CANCEL_EXIT_BUTTON_Y : Int = EXIT_PROGRAM_BUTTON_Y
    private val CANCEL_EXIT_BUTTON_TEXT : String = "Cancel"
    private val CANCEL_EXIT_BUTTON_ACTION : Action = {escape()}
    private val CANCEL_EXIT_BUTTON : TextButton by lazy{TextButton(CANCEL_EXIT_BUTTON_X, CANCEL_EXIT_BUTTON_Y, CANCEL_EXIT_BUTTON_TEXT, CANCEL_EXIT_BUTTON_ACTION)}

    private val EXIT_PROGRAM_QUESTION_X : Int = FRAMEX / 2
    private val EXIT_PROGRAM_QUESTION_Y : Int = FRAMEY / 4
    private val EXIT_PROGRAM_QUESTION_TEXT : String = "Are you sure you want to quit?"
    private val EXIT_PROGRAM_QUESTION : Label by lazy{
        Label(
            EXIT_PROGRAM_QUESTION_X,
            EXIT_PROGRAM_QUESTION_Y,
            StringDisplay(EXIT_PROGRAM_QUESTION_TEXT, QUESTION_FONT)
        )
    }

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

/**
 * A Test Screen, to test stuff.
 * @see Screen
 */
val testScreen : Screen = object : Screen(){

    private val BACK_BUTTON_TEXT : String = "<-"
    private val BACK_BUTTON_ACTION : Action = {escape()}
    val BACK_BUTTON : TextButton by lazy{
        val result = TextButton(0, 0, BACK_BUTTON_TEXT, BACK_BUTTON_ACTION)
        result alignLeftTo 0
        result alignUpTo 0
        result
    }

    override var previousScreen: Screen = mainMenuScreen

    init{
        val textField = TextField(210,500, 420, "Hello there!")
        textField.alignLeftToRight(BACK_BUTTON,5)
        textField.alignDownToDown(BACK_BUTTON)
        add(textField)
    }

    override fun load() {
        this add BACK_BUTTON
    }

    override fun save() {
        this remove BACK_BUTTON
    }

}
