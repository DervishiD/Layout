package usages.hanoi

import llayout2.Action
import llayout2.DEFAULT_COLOR
import llayout2.GraphicAction
import llayout2.displayers.CanvasDisplayer
import llayout2.displayers.TextButton
import llayout2.frame.LScene
import java.awt.Color
import java.awt.Graphics
import java.awt.event.KeyEvent
import java.awt.event.KeyEvent.*

internal object GameScene : LScene() {

    private val TOWER_COLOR : Color = Color(220, 220, 220)

    private val CONTOUR_COLOR : Color = DEFAULT_COLOR

    private val DISC_COLOR : Color = Color(180, 180, 180)

    private val SELECTED_COLOR : Color = Color(30, 190, 40)

    private val HOVERED_COLOR : Color = Color(255, 140, 20)

    private val WINNING_COLOR : Color = Color(0, 255, 0)

    private const val NUMBER_OF_TOWERS : Int = 3

    private const val BACKGROUND_KEY : String = "Key of the discs' backgrounds"

    private const val TOWER_WIDTH : Int = 30

    private const val HUMAN_TIMER_PERIOD : Int = 100

    private const val COMPUTER_TIMER_PERIOD : Int = 500

    private val BLOCKED_KEY_PRESS : (KeyEvent) -> Unit = {}

    private val HUMAN_KEY_PRESS : (KeyEvent) -> Unit = { e -> handleInput(e.keyCode) }

    private const val TOWER_HEIGHT : Double = 0.8

    private val discs : Array<MutableList<CanvasDisplayer>> = Array(NUMBER_OF_TOWERS){ mutableListOf<CanvasDisplayer>() }

    private val towers : Array<CanvasDisplayer> = Array(NUMBER_OF_TOWERS){ CanvasDisplayer(TOWER_WIDTH, TOWER_HEIGHT) }

    private val backButton : TextButton = TextButton("Back"){ back() }

    private var currentIndex : Int = 0

    private var isSelecting : Boolean = false

    private var isHuman : Boolean = true

    private var numberOfDiscs : Int = 0

    private val DISC_CONTOUR : GraphicAction = { g : Graphics, w : Int, h : Int ->
        g.color = CONTOUR_COLOR
        g.drawRect(0, 0, w - 1, h - 1)
    }

    private val DEFAULT_DISC_BACKGROUND : GraphicAction = { g : Graphics, w : Int, h : Int ->
        g.color = DISC_COLOR
        g.fillRect(0, 0, w, h)
        DISC_CONTOUR(g, w, h)
    }

    private val HOVERED_DISC_BACKGROUND : GraphicAction = { g : Graphics, w : Int, h : Int ->
        g.color = HOVERED_COLOR
        g.fillRect(0, 0, w, h)
        DISC_CONTOUR(g, w, h)
    }

    private val SELECTED_DISC_BACKGROUND : GraphicAction = { g : Graphics, w : Int, h : Int ->
        g.color = SELECTED_COLOR
        g.fillRect(0, 0, w, h)
        DISC_CONTOUR(g, w, h)
    }

    private val WINNING_DISC_BACKGROUND : GraphicAction = { g : Graphics, w : Int, h : Int ->
        g.color = WINNING_COLOR
        g.fillRect(0, 0, w, h)
        DISC_CONTOUR(g, w, h)
    }

    private var algorithmIterationIndex : Int = 0

    private val ON_TIMER_TICK_ALGORITHM : Action = {
        val middleIndex : Int = (NUMBER_OF_TOWERS - 1) / 2
        if(evenNumberOfDiscs()){
            when(algorithmIterationIndex % 3){
                0 -> legalMove(0, middleIndex)
                1 -> legalMove(0, NUMBER_OF_TOWERS - 1)
                2 -> legalMove(middleIndex, NUMBER_OF_TOWERS - 1)
            }
        }else{
            when(algorithmIterationIndex % 3){
                0 -> legalMove(0, NUMBER_OF_TOWERS - 1)
                1 -> legalMove(0, middleIndex)
                2 -> legalMove(middleIndex, NUMBER_OF_TOWERS - 1)
            }
        }
        algorithmIterationIndex++
        if(hasWon()) win()
    }

    init{
        addTowers()
        addBackButton()
        setOnKeyPressedAction { e -> handleInput(e.keyCode) }
        addWidthListener { setDiscsX() }
    }

    fun reset(height : Int, isHuman : Boolean){
        numberOfDiscs = height
        clearTowers()
        refillFirstTower(height)
        loadAs(isHuman)
    }

    private fun loadAs(isHuman : Boolean){
        this.isHuman = isHuman
        if(isHuman) loadAsHuman() else loadAsComputer()
    }

    private fun loadAsHuman(){
        setOnKeyPressedAction(HUMAN_KEY_PRESS)
        frame.setTimerPeriod(HUMAN_TIMER_PERIOD)
    }

    private fun loadAsComputer(){
        setOnKeyPressedAction(BLOCKED_KEY_PRESS)
        frame.setTimerPeriod(COMPUTER_TIMER_PERIOD)
        startAlgorithm()
    }

    private fun setDiscsX(){
        for(index : Int in 0 until NUMBER_OF_TOWERS){
            for(disc : CanvasDisplayer in discs[index]){
                disc.setX(towers[index].x())
            }
        }
    }

    private fun relativeXAtTowerIndex(index : Int) : Double = (1 + 2 * index)/(2.0 * NUMBER_OF_TOWERS)

    private fun addTowers(){
        for(i : Int in 0 until NUMBER_OF_TOWERS){
            towers[i].addGraphicAction({ g : Graphics, w : Int, h : Int ->
                g.color = TOWER_COLOR
                g.fillRect(0, 0, w, h)
                g.color = CONTOUR_COLOR
                g.drawRect(0, 0, w - 1, h - 1)
            })
            towers[i].alignDownTo(1.0)
            towers[i].setX(relativeXAtTowerIndex(i))
            add(towers[i])
        }
    }

    private fun clearTowers(){
        for(tower : MutableList<CanvasDisplayer> in discs){
            for(disc : CanvasDisplayer in tower){
                remove(disc)
            }
            tower.clear()
        }
    }

    private fun refillFirstTower(height : Int){
        addDiscsOnFirstTower(height)
        setDiscsX()
    }

    private fun addDiscsOnFirstTower(height : Int){
        val discHeight : Int = towerHeight() / height
        for(i : Int in 0 until height){
            val newDisc = CanvasDisplayer()
            newDisc.setWidth(discWidth(i, height))
            newDisc.setHeight(discHeight)
            setDefaultBackground(newDisc)
            addToTower(newDisc, 0)
            add(newDisc)
        }
    }

    private fun discWidth(i : Int, height : Int) : Int = ((1 - i.toDouble() / height) * width() / NUMBER_OF_TOWERS + (i.toDouble() / height) * TOWER_WIDTH).toInt()

    private fun towerHeight() : Int = ( TOWER_HEIGHT * height() ).toInt()

    private fun back(){
        frame.setScene(SelectionScene)
    }

    private fun addBackButton(){
        add(backButton.alignUpTo(0).alignLeftTo(0))
    }

    private fun hover(index : Int){
        if(index !in 0 until NUMBER_OF_TOWERS) throw IllegalArgumentException("Invalid tower index $index")
        hover(discs[index].last())
    }

    private fun hover(disc : CanvasDisplayer){
        disc.addGraphicAction(HOVERED_DISC_BACKGROUND, BACKGROUND_KEY)
    }

    private fun unHover(index : Int){
        if(index !in 0 until NUMBER_OF_TOWERS) throw IllegalArgumentException("Invalid tower index $index")
        setDefaultBackground(discs[index].last())
    }

    private fun select(){
        if(discs[currentIndex].isNotEmpty()){
            isSelecting = true
            select(discs[currentIndex].last())
        }
    }

    private fun select(disc : CanvasDisplayer){
        disc.addGraphicAction(SELECTED_DISC_BACKGROUND, BACKGROUND_KEY)
    }

    private fun unSelect(){
        isSelecting = false
        hover(discs[currentIndex].last())
    }

    private fun setDefaultBackground(disc : CanvasDisplayer){
        disc.addGraphicAction(DEFAULT_DISC_BACKGROUND, BACKGROUND_KEY)
    }

    private fun handleInput(keyCode : Int){
        when(keyCode){
            VK_LEFT -> moveLeft()
            VK_RIGHT -> moveRight()
            VK_SPACE, VK_ENTER -> toggleSelection()
        }
    }

    private fun moveLeft(){
        if(isSelecting) moveLeftWhileSelecting() else moveLeftWhileHovering()
    }

    private fun moveLeftWhileHovering(){
        var nextIndex : Int
        for(i : Int in 1..NUMBER_OF_TOWERS){
            nextIndex = getIndexAtLeft(i)
            if(discs[nextIndex].isNotEmpty()){
                unHover(currentIndex)
                currentIndex = nextIndex
                hover(nextIndex)
                return
            }
        }
    }

    private fun moveLeftWhileSelecting(){
        var nextIndex : Int
        for(i : Int in 1..NUMBER_OF_TOWERS){
            nextIndex = getIndexAtLeft(i)
            if(isALegalMove(currentIndex, nextIndex)){
                move(currentIndex, nextIndex)
                currentIndex = nextIndex
                break
            }
        }
        if(hasWon()) win()
    }

    private fun getIndexAtLeft(i : Int) : Int{
        val theoreticalNext : Int = (currentIndex - i) % NUMBER_OF_TOWERS
        return if(theoreticalNext < 0) NUMBER_OF_TOWERS + theoreticalNext else theoreticalNext
    }

    private fun moveRight(){
        if(isSelecting) moveRightWhileSelecting() else moveRightWhileHovering()
    }

    private fun moveRightWhileHovering(){
        var nextIndex : Int
        for(i : Int in 1..NUMBER_OF_TOWERS){
            nextIndex = getIndexAtRight(i)
            if(discs[nextIndex].isNotEmpty()){
                unHover(currentIndex)
                currentIndex = nextIndex
                hover(nextIndex)
                return
            }
        }
    }

    private fun moveRightWhileSelecting(){
        var nextIndex : Int
        for(i : Int in 1..NUMBER_OF_TOWERS){
            nextIndex = getIndexAtRight(i)
            if(isALegalMove(currentIndex, nextIndex)){
                move(currentIndex, nextIndex)
                currentIndex = nextIndex
                break
            }
        }
        if(hasWon()) win()
    }

    private fun getIndexAtRight(i : Int) : Int = (currentIndex + i) % NUMBER_OF_TOWERS

    private fun addToTower(disc : CanvasDisplayer, index : Int){
        if(discs[index].isEmpty()){
            disc.alignDownTo(1.0)
        }else{
            disc.alignDownToUp(discs[index].last())
        }
        disc.setX(towers[index].x())
        discs[index].add(disc)
    }

    private fun toggleSelection() = if(isSelecting) unSelect() else select()

    private fun isALegalMove(from : Int, to : Int) : Boolean{
        return when{
            discs[from].isEmpty() -> false
            discs[to].isEmpty() -> true
            else -> discs[from].last().width() < discs[to].last().width()
        }
    }

    private fun block(){
        setOnKeyPressedAction(BLOCKED_KEY_PRESS)
    }

    private fun hasWon() : Boolean{
        if(discs[0].isEmpty()){
            var aTowerContainsDiscs = false
            for(i : Int in 1 until NUMBER_OF_TOWERS){
                if(discs[i].isNotEmpty()){
                    if(aTowerContainsDiscs){
                        return false
                    }else{
                        aTowerContainsDiscs = true
                    }
                }
            }
            return true
        }
        return false
    }

    private fun win(){
        block()
        colorForWin()
        if(!isHuman) stopAlgorithm()
    }

    private fun colorForWin(){
        for(tower : MutableList<CanvasDisplayer> in discs){
            for(disc : CanvasDisplayer in tower){
                setWinningColor(disc)
            }
        }
    }

    private fun setWinningColor(disc : CanvasDisplayer){
        disc.addGraphicAction(WINNING_DISC_BACKGROUND, BACKGROUND_KEY)
    }

    private fun startAlgorithm(){
        algorithmIterationIndex = 0
        setOnTimerTickAction(ON_TIMER_TICK_ALGORITHM)
    }

    private fun stopAlgorithm(){
        setOnTimerTickAction {  }
    }

    private fun evenNumberOfDiscs() : Boolean = numberOfDiscs % 2 == 0

    private fun legalMove(fromIndex : Int, toIndex : Int){
        if(isALegalMove(fromIndex, toIndex)){
            move(fromIndex, toIndex)
        }else if(isALegalMove(toIndex, fromIndex)){
            move(toIndex, fromIndex)
        }
    }

    private fun move(fromIndex : Int, toIndex : Int){
        val toMove : CanvasDisplayer = discs[fromIndex].last()
        discs[fromIndex].remove(toMove)
        addToTower(toMove, toIndex)
    }

}