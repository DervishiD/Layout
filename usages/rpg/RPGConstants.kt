package usages.rpg

import llayout.*
import llayout.displayers.*
import llayout.frame.LApplication
import llayout.frame.LFrame
import llayout.frame.LFrameBuilder
import llayout.frame.LScene
import llayout.utilities.StringDisplay
import llayout.utilities.Text
import usages.rpg.editor.GridDisplayer
import usages.rpg.editor.selections.AbstractGridSelector
import usages.rpg.editor.selections.ColumnSelector
import usages.rpg.editor.selections.LineSelector
import java.awt.Component
import java.awt.Graphics
import java.awt.event.KeyEvent.VK_ENTER
import java.awt.event.KeyEvent.VK_ESCAPE

//APPLICATION-----------------------------------------------------------------

val rpgApplication : LApplication = object : LApplication(){
    override fun run() {
        rpgFrame.run()
    }
}

//LFRAME----------------------------------------------------------------------

val rpgFrame : LFrame by lazy{ LFrameBuilder(mainMenuScreen).exitOnClose().setDecorated(false).setFullScreen(true).build()}

//NUMBERS----------------------------------------------------------------------

const val DEFAULT_GRID_MESH_SIZE : Int = 120
const val GRID_IMAGE_SIZE_DELTA : Int = 10
const val SMALLEST_GRID_IMAGE_SIZE : Int = 20

//SCREENS----------------------------------------------------------------------

/**
 * The Main Menu LScene.
 * @see LScene
 */
val mainMenuScreen : LScene = object : LScene() {

    private val TITLE_X : Double = 0.5
    private val TITLE_Y : Double = 0.2
    private val TITLE_TEXT : String = "Title"
    private val TITLE : Label by lazy{
        Label(
                TITLE_X,
                TITLE_Y,
                StringDisplay(TITLE_TEXT, TITLE_FONT)
        )
    }

    private val EDITOR_BUTTON_X : Double = 1.0/3
    private val EDITOR_BUTTON_Y : Double = 0.5
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

    private val TEST_BUTTON_X : Double = 0.5
    private val TEST_BUTTON_Y : Double = 0.8
    private val TEST_BUTTON_TEXT : String = "Test LScene"
    private val TEST_BUTTON_ACTION : Action = {setNextScreen(testScreen)}
    private val TEST_BUTTON : TextButton = TextButton(TEST_BUTTON_X, TEST_BUTTON_Y, TEST_BUTTON_TEXT, TEST_BUTTON_ACTION)

    override var onKeyReleased: LKeyEvent = {key -> if(key == VK_ESCAPE) setNextScreen(exitProgramScreen)}

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

}

/**
 * The Editor LScene.
 * @see LScene
 */
val editorScreen : LScene = object : LScene() {

    private val ALLOWED_GRID_WIDTH : Double = 4.0/5
    private val ALLOWED_GRID_HEIGHT : Double = 3.0/4
    private val ALLOWED_LEFT_WIDTH : Double = 1 - ALLOWED_GRID_WIDTH
    private val ALLOWED_LEFT_HEIGHT : Double = ALLOWED_GRID_HEIGHT

    private val GRID_DISPLAYER : GridDisplayer = GridDisplayer(0, 0, ALLOWED_GRID_WIDTH, ALLOWED_GRID_HEIGHT)

    private val BACK_BUTTON_TEXT : String = "<-"
    private val BACK_BUTTON_ACTION : Action = {setNextScreen(mainMenuScreen)}
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

    private val TEXTFIELD_WIDTH : Double = 0.5
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
        DisplayerScrollPane(0, 0, ALLOWED_LEFT_WIDTH, ALLOWED_LEFT_HEIGHT)
            .addToScrollPane(WIDTH_TEXT, WIDTH_TEXT_DELTA)
            .addToScrollPane(WIDTH_TEXTFIELD, WIDTH_FIELD_DELTA)
            .addToScrollPane(HEIGHT_TEXT, HEIGHT_TEXT_DELTA)
            .addToScrollPane(HEIGHT_TEXTFIELD, HEIGHT_FIELD_DELTA)
            .addToScrollPane(RECENTER_BUTTON, RECENTER_BUTTON_DELTA)
            .addToScrollPane(BRUSH_SELECTOR_TEXT, BRUSH_SELECTOR_TEXT_DELTA)
            .addToScrollPane(BRUSH_SIZE_SELECTOR, BRUSH_SIZE_SELECTOR_DELTA)
            .addToScrollPane(SELECTOR_SELECTOR_TEXT, SELECTOR_SELECTOR_TEXT_DELTA)
            .addToScrollPane(CELL_SELECTOR_SELECTOR, CELL_SELECTOR_SELECTOR_DELTA).also{
                    it.alignUpTo(0).alignLeftTo(0)
                    GRID_DISPLAYER.alignUpTo(0).alignLeftToRight(it)
                }

    var currentTextField: TextField? = null

    /**
     * Updates the edited Grid
     */
    private fun updateGrid(){
        GRID_DISPLAYER.setGridWidth(WIDTH_TEXTFIELD.typedText().toInt())
        GRID_DISPLAYER.setGridHeight(HEIGHT_TEXTFIELD.typedText().toInt())
    }

    override var onKeyPressed : LKeyEvent = {key ->
        if(currentTextField != null){
            if(key == VK_ENTER){
                unfocusTextField()
                updateGrid()
            }else currentTextField!!.type(key)
        }
    }

    override var onKeyReleased: LKeyEvent = {key -> if(key == VK_ESCAPE) setNextScreen(mainMenuScreen)}

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
        when(val component : Component = getComponentAt(x, y)){
            //is LScene -> mouseRelease() -- useless here
            is AbstractDisplayerContainer ->{
                val bottom : Displayer = component.displayerAt(x - component.leftSideX(), y - component.upSideY())
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
        addGraphicAction({ g : Graphics, w : Int, h : Int -> run{
            val separatorLineThickness = 24
            g.color = DEFAULT_COLOR
            g.fillRect((ALLOWED_LEFT_WIDTH * w).toInt() - separatorLineThickness/2, 0, separatorLineThickness, (ALLOWED_GRID_HEIGHT * h).toInt())
            g.fillRect((ALLOWED_LEFT_WIDTH * w).toInt(), (ALLOWED_LEFT_HEIGHT * h - separatorLineThickness / 2).toInt(), (ALLOWED_GRID_WIDTH * w).toInt(), separatorLineThickness)
        }}
        , this)
    }

    override fun save() {
        unfocusTextField()
        this remove BACK_BUTTON
        this remove GRID_DISPLAYER
        this remove LEFT_SCROLL_PANE
        removeDrawing(this)
    }

}

/**
 * The LScene that exits the program.
 * @see LScene
 */
val exitProgramScreen : LScene = object : LScene(){

    private val EXIT_PROGRAM_BUTTON_X : Double = 1/3.0
    private val EXIT_PROGRAM_BUTTON_Y : Double = 2 / 3.0
    private val EXIT_PROGRAM_BUTTON_TEXT : String = "Exit"
    private val EXIT_PROGRAM_BUTTON_ACTION : Action = {rpgFrame.close()}
    private val EXIT_PROGRAM_BUTTON : TextButton by lazy{ TextButton(EXIT_PROGRAM_BUTTON_X, EXIT_PROGRAM_BUTTON_Y, EXIT_PROGRAM_BUTTON_TEXT, EXIT_PROGRAM_BUTTON_ACTION) }

    private val CANCEL_EXIT_BUTTON_X : Double = 2/3.0
    private val CANCEL_EXIT_BUTTON_Y : Double = EXIT_PROGRAM_BUTTON_Y
    private val CANCEL_EXIT_BUTTON_TEXT : String = "Cancel"
    private val CANCEL_EXIT_BUTTON_ACTION : Action = {setNextScreen(mainMenuScreen)}
    private val CANCEL_EXIT_BUTTON : TextButton by lazy{ TextButton(CANCEL_EXIT_BUTTON_X, CANCEL_EXIT_BUTTON_Y, CANCEL_EXIT_BUTTON_TEXT, CANCEL_EXIT_BUTTON_ACTION) }

    private val EXIT_PROGRAM_QUESTION_X : Double = 0.5
    private val EXIT_PROGRAM_QUESTION_Y : Double = 0.25
    private val EXIT_PROGRAM_QUESTION_TEXT : String = "Are you sure you want to quit?"
    private val EXIT_PROGRAM_QUESTION : Label by lazy{
        Label(
                EXIT_PROGRAM_QUESTION_X,
                EXIT_PROGRAM_QUESTION_Y,
                StringDisplay(EXIT_PROGRAM_QUESTION_TEXT, QUESTION_FONT)
        )
    }

    override var onKeyReleased: LKeyEvent = {key -> if(key == VK_ESCAPE) setNextScreen(mainMenuScreen)}

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
 * A Test LScene, to test stuff.
 * @see LScene
 */
val testScreen : LScene = object : LScene(){

    private val BACK_BUTTON_TEXT : String = "<-"
    private val BACK_BUTTON_ACTION : Action = {setNextScreen(mainMenuScreen)}
    val BACK_BUTTON : TextButton by lazy{
        val result = TextButton(0, 0, BACK_BUTTON_TEXT, BACK_BUTTON_ACTION)
        result alignLeftTo 0
        result alignUpTo 0
        result
    }

    init{
        val textField = TextField(210, 500, 0.5, "Hello there!")
        textField.alignLeftToRight(BACK_BUTTON,5)
        textField.alignDownToDown(BACK_BUTTON)
        add(textField)

        val canvas = CanvasDisplayer(0.5, 0.5, 0.5, 0.5)
        canvas.drawRectangle(0.0, 0.0, 0.999, 0.999)
        canvas.drawPoint(0.25, 0.25)
        canvas.drawPoint3(22, 22)
        canvas.drawLine(0.5, 0.5, 0.0, 1.0)
        canvas.drawOval(0.8, 0.8, 25, 25)
        add(canvas)
    }

    override var onKeyReleased: LKeyEvent = {key -> if(key == VK_ESCAPE) setNextScreen(mainMenuScreen)}

    override fun load() {
        this add BACK_BUTTON
    }

    override fun save() {
        this remove BACK_BUTTON
    }

}
