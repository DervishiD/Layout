package usages.schinz

import java.awt.Color
import java.awt.Graphics
import java.util.*
import kotlin.math.abs
import kotlin.random.Random
import llayout3.DEFAULT_LARGE_FONT
import llayout3.DEFAULT_MEDIUM_FONT
import llayout3.displayers.CanvasDisplayer
import llayout3.displayers.RegularGrid
import llayout3.displayers.TextButton
import llayout3.frame.LScene
import llayout3.utilities.StringDisplay
import usages.schinz.GameScene.Algorithm.CurrentGoal.*
import usages.schinz.GameScene.Algorithm.MoveType.*

object GameScene : LScene() {

    private const val SHUFFLING_ITERATIONS : Int = 1000

    private const val HUMAN_PERIOD : Int = 200

    private const val COMPUTER_PERIOD : Int = 500

    private class Block(private val index : Int, var line : Int, var column : Int) : CanvasDisplayer(){

        companion object{
            private val DEFAULT_COLOR : Color = Color(255, 160, 20)
            private val FINISHED_COLOR : Color = Color(160, 235, 90)
        }

        private var color : Color = DEFAULT_COLOR

        init{
            addGraphicAction({ g : Graphics, w : Int, h : Int ->
                g.color = color()
                g.fillRect(0, 0, w, h)
            })
            writeCentered(StringDisplay(index, DEFAULT_LARGE_FONT))
            twoPixelFrame()
        }

        fun index() : Int = index

        fun setInteractable(){
            setOnMouseReleasedAction { move() }
        }

        fun setNotInteractable(){
            setOnMouseReleasedAction {  }
        }

        fun move(){
            if(neighbourOfHole(this)){
                swapWithHole()
                if(hasWon()) win()
            }
        }

        private fun swapWithHole(){
            var previous : Int = holeLine
            holeLine = line
            line = previous
            previous = holeColumn
            holeColumn = column
            column = previous
            grid.removeAt(holeLine, holeColumn)
            grid[line, column] = this
        }

        private fun color() : Color = color

        fun end(){
            color = FINISHED_COLOR
        }

    }

    private object Algorithm{

        private const val HOLE : Int = 0

        enum class CurrentGoal{ SOLVING_LINE, SOLVING_COLUMN, CYCLING }

        enum class MoveType{ MOVING_BLOCK, MOVING_HOLE, CYCLING_VERTICAL, CYCLING_HORIZONTAL, TWO_BY_THREE,
                             MOVING_FIRST, MOVING_SECOND, MOVING_FIRST_AGAIN }

        private val placedSet : MutableSet<Int> = mutableSetOf()

        private var firstLineIndex : Int = 0

        private var firstColumnIndex : Int = 0

        private val currentGoalRemainingBlocks : MutableList<Int> = mutableListOf()

        private var currentBlock : Int = HOLE

        private var currentBlockLine : Int = 0

        private var currentBlockColumn : Int = 0

        private var currentBlockTargetLine : Int = 0

        private var currentBlockTargetColumn : Int = 0

        private var currentPath : LinkedList<Pair<Int, Int>> = LinkedList()

        private var holePath : LinkedList<Pair<Int, Int>> = LinkedList()

        private var currentGoalType : CurrentGoal = SOLVING_LINE

        private var currentMoveType : MoveType = MOVING_BLOCK

        private var firstMove : Boolean = true

        private var targetSquare : MutableList<Pair<Int, Int>> = mutableListOf()

        private var indexOfCyclicMovement : Int = 0

        private var additionalFixedBlock : Int = 0

        private var firstBlock : Int = 0

        private var secondBlock : Int = 0

        private fun remainingLinesNumber() : Int = numberOfLines() - firstLineIndex

        private fun remainingColumnsNumber() : Int = numberOfColumns() - firstColumnIndex

        private fun nextBlock() : Int{
            var minimum = numberOfLines() * numberOfColumns()
            for(toMove : Int in currentGoalRemainingBlocks){
                if(toMove < minimum) minimum = toMove
            }
            return minimum
        }

        private fun goalIsAttained() : Boolean{
            return if(currentGoalRemainingBlocks.isEmpty()){
                true
            }else{
                allGoalBlocksWellPlaced()
            }
        }

        private fun allGoalBlocksWellPlaced() : Boolean{
            for(block : Int in currentGoalRemainingBlocks){
                if(!isWellPlaced(block)){
                    return false
                }
            }
            return true
        }

        private fun setNextBlock(){
            currentBlock = nextBlock()
        }

        private fun isWellPlaced(block : Int) : Boolean{
            var result = false
            grid.forEach { b ->
                if(!result && (b as Block).index() == block && correctBlockAt(b.line, b.column) == b.index()){
                    result = true
                }
            }
            return result
        }

        private fun correctBlockAt(line : Int, column : Int) : Int = line * numberOfColumns() + column + 1

        private fun blockAt(line : Int, column : Int) : Int{
            val block = grid[line, column]
            return if(block == null) 0 else (block as Block).index()
        }

        private fun isFixed(line : Int, column : Int) : Boolean = blockAt(line, column) in placedSet || blockAt(line, column) == additionalFixedBlock

        private fun isFixed(index : Int) : Boolean{
            val position : Pair<Int, Int> = find(index)
            return isFixed(position.first, position.second)
        }

        private fun find(index : Int) : Pair<Int, Int>{
            var line = 0
            var column = 0
            grid.forEach { block ->
                if((block as Block).index() == index){
                    line = block.line
                    column = block.column
                }
            }
            return Pair(line, column)
        }

        private fun movingTwoBlocks() : Boolean{
            return currentMoveType == MOVING_FIRST ||
                   currentMoveType == MOVING_SECOND ||
                   currentMoveType == MOVING_FIRST_AGAIN
        }

        private fun loadNextGoal(){
            when{
                twoByTwoGrid() -> {}
                moreColumns() -> loadNextColumn()
                else -> loadNextLine()
            }
            if(moreColumns()) loadNextColumn() else loadNextLine()
            setNextBlock()
            findNextBlock()
            setTarget()
            findPath()
            prepareNextMove()
        }

        private fun prepareNextMove(){
            /*
             * Set hole path if needed, set move type, find special cases (2x2), etc.
             */
            when{
                twoByTwoGrid() -> setCycling()
                twoBlocksToMove() -> prepareForTwoBlocks()
                cantMove() -> prepareHolePath()
                else -> prepareToMoveBlock()
            }
        }

        private fun prepareHolePath(){
            currentMoveType = MOVING_HOLE
            /*
             * The hole's target is the next position in the block's path.
             * This method needs to build the hole's path to this position.
             */
            val targetLine : Int = nextStep().first
            val targetColumn : Int = nextStep().second
            val traps : MutableSet<Pair<Int, Int>> = mutableSetOf()

            fun isFirstMove() : Boolean = holePath.isEmpty()
            fun lastHoleLine() : Int = if(isFirstMove()) holeLine else holePath.last.first
            fun lastHoleColumn() : Int = if(isFirstMove()) holeColumn else holePath.last.second
            fun lineDirection() : Int = targetLine - holeLine
            fun columnDirection() : Int = targetColumn - holeColumn
            fun goingLeft() : Boolean = columnDirection() < 0
            fun goingRight() : Boolean = columnDirection() > 0
            fun goingUp() : Boolean = lineDirection() < 0
            fun goingDown() : Boolean = lineDirection() > 0
            fun verticalMagnitude() : Int = abs(lineDirection())
            fun horizontalMagnitude() : Int = abs(columnDirection())
            fun shouldGoHorizontal() : Boolean = horizontalMagnitude() >= verticalMagnitude()
            fun blockLeft() : Int = blockAt(lastHoleLine(), lastHoleColumn() - 1)
            fun blockRight() : Int = blockAt(lastHoleLine(), lastHoleColumn() + 1)
            fun blockUp() : Int = blockAt(lastHoleLine() - 1, lastHoleColumn())
            fun blockDown() : Int = blockAt(lastHoleLine() + 1, lastHoleColumn())
            fun positionLeft() : Pair<Int, Int> = Pair(lastHoleLine(), lastHoleColumn() - 1)
            fun positionRight() : Pair<Int, Int> = Pair(lastHoleLine(), lastHoleColumn() + 1)
            fun positionUp() : Pair<Int, Int> = Pair(lastHoleLine() - 1, lastHoleColumn())
            fun positionDown() : Pair<Int, Int> = Pair(lastHoleLine() + 1, lastHoleColumn())
            fun canGoLeft() : Boolean{
                return when {
                    lastHoleColumn() <= firstColumnIndex -> false
                    movingTwoBlocks() -> !isFixed(blockLeft()) && blockLeft() !in currentGoalRemainingBlocks
                            && positionLeft() !in traps
                    else -> blockLeft() !in placedSet && blockLeft() != currentBlock
                            && positionLeft() !in traps
                }
            }
            fun canGoRight() : Boolean{
                return when{
                    lastHoleColumn() == numberOfColumns() - 1 -> false
                    movingTwoBlocks() -> !isFixed(blockRight()) && blockRight() !in currentGoalRemainingBlocks
                            && positionRight() !in traps
                    else -> blockRight() !in placedSet && blockRight() != currentBlock
                            && positionRight() !in traps
                }
            }
            fun canGoUp() : Boolean{
                return when{
                    lastHoleLine() <= firstLineIndex -> false
                    movingTwoBlocks() -> !isFixed(blockUp()) && blockUp() !in currentGoalRemainingBlocks
                            && positionUp() !in traps
                    else -> blockUp() !in placedSet && blockUp() != currentBlock
                            && positionUp() !in traps
                }
            }
            fun canGoDown() : Boolean{
                return when{
                    lastHoleLine() == numberOfLines() - 1 -> false
                    movingTwoBlocks() -> !isFixed(blockDown()) && blockDown() !in currentGoalRemainingBlocks
                            && positionDown() !in traps
                    else -> blockDown() !in placedSet && blockDown() != currentBlock
                            && positionDown() !in traps
                }
            }
            fun goLeft(){
                holePath.offer(Pair(lastHoleLine(), lastHoleColumn() - 1))
            }
            fun goRight(){
                holePath.offer(Pair(lastHoleLine(), lastHoleColumn() + 1))
            }
            fun goUp(){
                holePath.offer(Pair(lastHoleLine() - 1, lastHoleColumn()))
            }
            fun goDown(){
                holePath.offer(Pair(lastHoleLine() + 1, lastHoleColumn()))
            }
            fun targetNotFound() : Boolean{
                return if(holePath.isNotEmpty()){
                    holePath.last.first != targetLine || holePath.last.second != targetColumn
                }else holeLine == targetLine && holeColumn == targetColumn
            }
            fun goBackTwice(){
                traps.add(holePath.removeLast())
                traps.add(holePath.removeLast())
            }
            fun tryToGoLeft(){
                when{
                    canGoLeft() -> goLeft()
                    canGoDown() -> goDown()
                    canGoUp() -> goUp()
                    else -> goBackTwice()
                }
            }
            fun tryToGoRight(){
                when{
                    canGoRight() -> goRight()
                    canGoDown() -> goDown()
                    canGoUp() -> goUp()
                    else -> goBackTwice()
                }
            }
            fun tryToGoUp(){
                when{
                    canGoUp() -> goUp()
                    canGoRight() -> goRight()
                    canGoLeft() -> goLeft()
                    else -> goBackTwice()
                }
            }
            fun tryToGoDown(){
                when{
                    canGoDown() -> goDown()
                    canGoRight() -> goRight()
                    canGoLeft() -> goLeft()
                    else -> goBackTwice()
                }
            }

            while(targetNotFound()){
                if(shouldGoHorizontal()){
                    when{
                        goingLeft() -> tryToGoLeft()
                        goingRight() -> tryToGoRight()
                        else -> traps.add(holePath.removeLast())
                    }
                }else{
                    when{
                        goingUp() -> tryToGoUp()
                        goingDown() -> tryToGoDown()
                        else -> traps.add(holePath.removeLast())
                    }
                }
            }

        }

        private fun setCycling(){
            if(currentGoalType != CYCLING){
                currentGoalType = CYCLING
                currentMoveType = CYCLING_HORIZONTAL
            }
        }

        private fun prepareForTwoBlocks(){
            findTargetSquare()
            findBothBlocks()
            if(secondBlockInPlace()){
                setFirstBlockForTwoAgain()
            }else if(firstBlockInPlace() && !secondBlockInPlace()){
                setSecondBlockForTwo()
            }else{
                setFirstBlockForTwo()
            }
        }

        private fun findBothBlocks(){
            if(currentGoalRemainingBlocks[0] < currentGoalRemainingBlocks[1]){
                firstBlock = currentGoalRemainingBlocks[0]
                secondBlock = currentGoalRemainingBlocks[1]
            }else{
                firstBlock = currentGoalRemainingBlocks[1]
                secondBlock = currentGoalRemainingBlocks[0]
            }
        }

        private fun firstBlockInPlace() : Boolean = find(firstBlock) == findTargetOf(firstBlock)

        private fun secondBlockInPlace() : Boolean = find(secondBlock) == findTargetOf(secondBlock)

        private fun twoBlocksAreHorizontal() : Boolean = find(firstBlock).first == find(secondBlock).first

        private fun findTargetSquare(){
            val smallTarget : Pair<Int, Int> = findTargetOf(firstBlock)
            val bigTarget : Pair<Int, Int> = findTargetOf(secondBlock)
            if(smallTarget.first == bigTarget.first){ // same line
                targetSquare.add(Pair(bigTarget.first + 1, bigTarget.second))
                targetSquare.add(Pair(bigTarget.first + 2, bigTarget.second))
                targetSquare.add(Pair(smallTarget.first + 1, smallTarget.second))
                targetSquare.add(Pair(smallTarget.first + 2, smallTarget.second))
            }else{ // same column
                targetSquare.add(Pair(bigTarget.first, bigTarget.second + 1))
                targetSquare.add(Pair(bigTarget.first, bigTarget.second + 2))
                targetSquare.add(Pair(smallTarget.first, smallTarget.second + 1))
                targetSquare.add(Pair(smallTarget.first, smallTarget.second + 2))
            }
        }

        private fun findTargetOf(index : Int) : Pair<Int, Int>{
            val line : Int = (index - 1) / numberOfColumns()
            return Pair(line, index - 1 - line * numberOfColumns())
        }

        private fun setFirstBlockForTwo(){
            currentMoveType = MOVING_FIRST
            val position : Pair<Int, Int> = find(firstBlock)
            val target : Pair<Int, Int> = targetSquare[0]
            currentBlock = firstBlock
            currentBlockLine = position.first
            currentBlockColumn = position.second
            currentBlockTargetLine = target.first
            currentBlockTargetColumn = target.second
        }

        private fun setSecondBlockForTwo(){
            currentMoveType = MOVING_SECOND
            val position : Pair<Int, Int> = find(secondBlock)
            val target : Pair<Int, Int> = targetSquare[1]
            currentBlock = secondBlock
            currentBlockLine = position.first
            currentBlockColumn = position.second
            currentBlockTargetLine = target.first
            currentBlockTargetColumn = target.second
        }

        private fun setFirstBlockForTwoAgain(){
            currentMoveType = MOVING_FIRST
            val position : Pair<Int, Int> = find(firstBlock)
            val target : Pair<Int, Int> = targetSquare[0]
            currentBlock = firstBlock
            currentBlockLine = position.first
            currentBlockColumn = position.second
            currentBlockTargetLine = target.first
            currentBlockTargetColumn = target.second
            additionalFixedBlock = secondBlock
        }

        private fun prepareToMoveBlock(){
            currentMoveType = MOVING_BLOCK
        }

        private fun twoBlocksToMove() : Boolean = currentGoalRemainingBlocks.size == 2

        private fun twoByTwoGrid() : Boolean = remainingLinesNumber() == 2 && remainingColumnsNumber() == 2

        private fun setTarget(){
            if(currentBlock != HOLE){
                currentBlockTargetLine = (currentBlock - 1) / numberOfColumns()
                currentBlockTargetColumn = currentBlock - 1 - currentBlockTargetLine * numberOfColumns()
            }
        }

        private fun findPath(){
            if(!lowerPath()) upperPath()
        }

        private fun lowerPath() : Boolean{
            if(currentBlockColumn < currentBlockTargetColumn){
                for(column : Int in currentBlockColumn+1..currentBlockTargetColumn){
                    currentPath.add(Pair(currentBlockLine, column))
                }
            }else if(currentBlockColumn > currentBlockTargetColumn){
                for(column : Int in currentBlockColumn-1 downTo currentBlockTargetColumn){
                    currentPath.add(Pair(currentBlockLine, column))
                }
            }
            if(currentBlockLine < currentBlockTargetLine){
                for(line : Int in currentBlockLine+1..currentBlockTargetLine){
                    currentPath.add(Pair(line, currentBlockTargetColumn))
                }
            }else if(currentBlockLine > currentBlockTargetLine){
                for(line : Int in currentBlockLine-1 downTo currentBlockTargetLine){
                    currentPath.add(Pair(line, currentBlockTargetColumn))
                }
            }
            for(pair : Pair<Int, Int> in currentPath){
                if(isFixed(pair.first, pair.second)){
                    currentPath.clear()
                    return false
                }
            }
            return true
        }

        private fun upperPath() : Boolean{
            if(currentBlockLine < currentBlockTargetLine){
                for(line : Int in currentBlockLine+1..currentBlockTargetLine){
                    currentPath.add(Pair(line, currentBlockColumn))
                }
            }else if(currentBlockLine > currentBlockTargetLine){
                for(line : Int in currentBlockLine-1 downTo currentBlockTargetLine){
                    currentPath.add(Pair(line, currentBlockColumn))
                }
            }
            if(currentBlockColumn < currentBlockTargetColumn){
                for(column : Int in currentBlockColumn+1..currentBlockTargetColumn){
                    currentPath.add(Pair(currentBlockTargetLine, column))
                }
            }else if(currentBlockColumn > currentBlockTargetColumn){
                for(column : Int in currentBlockColumn-1 downTo currentBlockTargetColumn){
                    currentPath.add(Pair(currentBlockTargetLine, column))
                }
            }
            for(pair : Pair<Int, Int> in currentPath){
                if(isFixed(pair.first, pair.second)){
                    currentPath.clear()
                    return false
                }
            }
            return true
        }

        private fun cantMove() : Boolean = grid.cellIsNotEmpty(nextStep().first, nextStep().second)

        private fun nextStep() : Pair<Int, Int> = currentPath.first

        private fun nextHoleStep() : Pair<Int, Int> = holePath.first

        private fun findNextBlock(){
            grid.forEach { block ->
                if((block as Block).index() == currentBlock){
                    currentBlockLine = block.line
                    currentBlockColumn = block.column
                }
            }
        }

        private fun loadNextColumn(){
            fillGoalBlocksForNextColumn()
            currentGoalType = SOLVING_COLUMN
        }

        private fun loadNextLine(){
            fillGoalBlocksForNextLine()
            currentGoalType = SOLVING_LINE
        }

        private fun fillGoalBlocksForNextLine(){
            for(i : Int in firstColumnIndex until numberOfColumns()){
                currentGoalRemainingBlocks.add(correctBlockAt(firstLineIndex, i))
            }
        }

        private fun fillGoalBlocksForNextColumn(){
            for(i : Int in firstLineIndex until numberOfLines()){
                currentGoalRemainingBlocks.add(correctBlockAt(i, firstColumnIndex))
            }
        }

        private fun moreColumns() : Boolean = remainingColumnsNumber() > remainingLinesNumber()

        private fun performNextActionTowardsGoal(){
            when(currentMoveType){
                MOVING_BLOCK -> moveBlock()
                MOVING_HOLE -> moveHole()
                CYCLING_VERTICAL -> verticalCycle()
                CYCLING_HORIZONTAL -> horizontalCycle()
                TWO_BY_THREE -> moveInTwoByThree()
                MOVING_FIRST -> moveFirst()
                MOVING_SECOND -> moveSecond()
                MOVING_FIRST_AGAIN -> moveFirstAgain()
            }
            prepareNextMove()
        }

        private fun moveFirst(){
            if(cantMove()){
                prepareHolePath()
                (grid[nextHoleStep().first, nextHoleStep().second] as Block).move()
                holePath.removeFirst()
            }else{
                (grid[currentBlockLine, currentBlockColumn] as Block).move()
                currentBlockLine = nextStep().first
                currentBlockColumn = nextStep().second
                currentPath.removeFirst()
            }
            if(currentPath.size == 0){
                currentMoveType = MOVING_SECOND
            }
        }

        private fun moveSecond(){
            if(cantMove()){
                prepareHolePath()
                (grid[nextHoleStep().first, nextHoleStep().second] as Block).move()
                holePath.removeFirst()
            }else{
                (grid[currentBlockLine, currentBlockColumn] as Block).move()
                currentBlockLine = nextStep().first
                currentBlockColumn = nextStep().second
                currentPath.removeFirst()
            }
            if(currentPath.size == 0){
                currentMoveType = MOVING_FIRST_AGAIN
            }
        }

        private fun moveFirstAgain(){
            if(cantMove()){
                prepareHolePath()
                (grid[nextHoleStep().first, nextHoleStep().second] as Block).move()
                holePath.removeFirst()
            }else{
                (grid[currentBlockLine, currentBlockColumn] as Block).move()
                currentBlockLine = nextStep().first
                currentBlockColumn = nextStep().second
                currentPath.removeFirst()
            }
            if(currentPath.size == 0){
                currentMoveType = TWO_BY_THREE
            }
        }

        private fun moveBlock(){
            (grid[currentBlockLine, currentBlockColumn] as Block).move()
            currentBlockLine = nextStep().first
            currentBlockColumn = nextStep().second
            currentPath.removeFirst()
        }

        private fun moveHole(){
            (grid[nextHoleStep().first, nextHoleStep().second] as Block).move()
            holePath.removeFirst()
        }

        private fun verticalCycle(){
            if(underHoleCanMove()){
                (grid[holeLine + 1, holeColumn] as Block).move()
            }else{
                (grid[holeLine - 1, holeColumn] as Block).move()
            }
            currentMoveType = CYCLING_HORIZONTAL
        }

        private fun horizontalCycle(){
            if(leftOfHoleCanMove()){
                (grid[holeLine, holeColumn - 1] as Block).move()
            }else{
                (grid[holeLine, holeColumn + 1] as Block).move()
            }
            currentMoveType = CYCLING_VERTICAL
        }

        private fun leftOfHoleCanMove() : Boolean{
            return if(holeColumn <= firstColumnIndex){
                false
            }else{
                blockAt(holeLine, holeColumn - 1) !in placedSet
            }
        }

        private fun underHoleCanMove() : Boolean{
            return if(holeLine == numberOfLines() - 1){
                false
            }else{
                blockAt(holeLine + 1, holeColumn) !in placedSet
            }
        }

        private fun moveInTwoByThree(){
            /*
             * Put hole in post-A == first without moving AB i.e. B is additionalFixed
             * if horizontal i.e. twoBlocksAreHorizontal, sequence of step
             * if vertical, other sequence of steps
             */
            TODO("Not implemented.")
        }

        private fun endCurrentGoal(){
            for(i : Int in currentGoalRemainingBlocks){
                placedSet.add(i)
            }
            currentGoalRemainingBlocks.clear()
            currentPath.clear()
            holePath.clear()
            targetSquare.clear()
            indexOfCyclicMovement = 0
            additionalFixedBlock = 0
            firstBlock = 0
            secondBlock = 0
            when(currentGoalType){
                SOLVING_LINE -> firstLineIndex++
                SOLVING_COLUMN -> firstColumnIndex++
                else -> {}
            }
        }

        tailrec fun performNextStep(){
            if(goalIsAttained()){
                if(firstMove) firstMove = false else endCurrentGoal()
                loadNextGoal()
                performNextStep()
            }else{
                performNextActionTowardsGoal()
            }
        }

    }

    private var grid : RegularGrid = RegularGrid(1, 1, 1, 1)

    private val backButton : TextButton = TextButton(StringDisplay("Back", DEFAULT_MEDIUM_FONT)){ back() }

    private var isHuman : Boolean = true

    private var holeLine : Int = 0

    private var holeColumn : Int = 0

    private var inGame : Boolean = false

    init{
        backButton.alignLeftTo(0).alignTopTo(0)
        setOnSaveAction {
            remove(grid)
            blockGame()
            inGame = false
        }
    }

    fun reload(width : Int, height : Int, isHuman : Boolean){
        this.isHuman = isHuman
        addNewGrid(width, height)
        add(backButton)
        frame.setScene(this)
        loadAs(isHuman)
        inGame = true
    }

    private fun addNewGrid(width : Int, height : Int){
        grid = RegularGrid(width, height, 1.0, 1.0)
        grid.alignTopTo(0).alignLeftTo(0)
        add(grid)
        refillGrid()
        setHolePosition()
        shuffle()
    }

    private fun loadAs(isHuman : Boolean){
        if(isHuman) loadAsHuman() else loadAsComputer()
    }

    private fun loadAsHuman(){
        makeBlocksInteract()
        frame.setTimerPeriod(HUMAN_PERIOD)
    }

    private fun makeBlocksInteract(){
        grid.forEach { block -> (block as Block).setInteractable() }
    }

    private fun makeBlocksNotInteract(){
        grid.forEach { block -> (block as Block).setNotInteractable() }
    }

    private fun loadAsComputer(){
        setOnTimerTickAction { Algorithm.performNextStep() }
        frame.setTimerPeriod(COMPUTER_PERIOD)
    }

    private fun numberOfLines() : Int = grid.numberOfLines()

    private fun numberOfColumns() : Int = grid.numberOfColumns()

    private fun refillGrid(){
        var index = 1
        for(i : Int in 0 until numberOfLines()){
            for(j : Int in 0 until numberOfColumns()){
                if(!(i == numberOfLines() - 1 && j == numberOfColumns() - 1)){
                    grid[i, j] = Block(index, i, j)
                    index++
                }
            }
        }
    }

    private fun setHolePosition(){
        holeLine = numberOfLines() - 1
        holeColumn = numberOfColumns() - 1
    }

    private fun neighbourOfHole(block : Block) : Boolean{
        return (lineDistanceToHole(block) == 0 && columnDistanceToHole(block) == 1)
                || (lineDistanceToHole(block) == 1 && columnDistanceToHole(block) == 0)
    }

    private fun lineDistanceToHole(block : Block) : Int{
        return abs(block.line - holeLine)
    }

    private fun columnDistanceToHole(block : Block) : Int{
        return abs(block.column - holeColumn)
    }

    private fun shuffle(){
        for(i : Int in 1..SHUFFLING_ITERATIONS){
            randomMove()
        }
    }

    private fun randomMove(){
        randomHoleNeighbour().move()
    }

    private fun randomHoleNeighbour() : Block{
        val result = when(Random.nextInt(4)){
            0 -> if(isValidColumn(holeColumn - 1)) grid[holeLine, holeColumn - 1] else grid[holeLine, holeColumn + 1]
            1 -> if(isValidColumn(holeColumn + 1)) grid[holeLine, holeColumn + 1] else grid[holeLine, holeColumn - 1]
            2 -> if(isValidLine(holeLine - 1)) grid[holeLine - 1, holeColumn] else grid[holeLine + 1, holeColumn]
            else -> if(isValidLine(holeLine + 1)) grid[holeLine + 1, holeColumn] else grid[holeLine - 1, holeColumn]
        }
        return result as Block
    }

    private fun isValidLine(lineIndex : Int) : Boolean = lineIndex >= 0 && lineIndex < numberOfLines()

    private fun isValidColumn(columnIndex : Int) : Boolean = columnIndex >= 0 && columnIndex < numberOfColumns()

    private fun hasWon() : Boolean{
        return if(inGame){
            var result = true
            grid.forEachComponent{ line : Int, column : Int ->
                result = result && (grid[line, column] as Block).index() == numberOfColumns() * line + column + 1
            }
            result
        }else false
    }

    private fun win(){
        blockGame()
        paintBlocksForEndgame()
    }

    private fun blockGame(){
        if(isHuman) blockAsHuman() else blockAsComputer()
    }

    private fun blockAsHuman() = makeBlocksNotInteract()

    private fun blockAsComputer() = setOnTimerTickAction {  }

    private fun paintBlocksForEndgame(){
        grid.forEach { c -> (c as Block).end() }
    }

    private fun back(){
        frame.setScene(MenuScene)
    }

}