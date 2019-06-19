package usages.schinz

import java.awt.Color
import java.awt.Graphics
import kotlin.math.abs
import kotlin.random.Random
import llayout3.DEFAULT_LARGE_FONT
import llayout3.DEFAULT_MEDIUM_FONT
import llayout3.displayers.CanvasDisplayer
import llayout3.displayers.RegularGrid
import llayout3.displayers.TextButton
import llayout3.frame.LScene
import llayout3.utilities.StringDisplay
import usages.schinz.GameScene.Algorithm.Direction.*
import usages.schinz.GameScene.Algorithm.CycleType.*

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

        private enum class Direction{ UP, DOWN, LEFT, RIGHT }

        private enum class CycleType { VERTICAL, HORIZONTAL }

        private class Position(val line : Int, val column : Int){
            fun up() : Position = Position(line - 1, column)
            fun down() : Position = Position(line + 1, column)
            fun left() : Position = Position(line, column - 1)
            fun right() : Position = Position(line, column + 1)
            override fun equals(other: Any?): Boolean = if(other is Position) line == other.line && column == other.column else false
            override fun hashCode(): Int = 10000 * line + column
        }

        private val holePath : MutableList<Direction> = mutableListOf()

        private var virtualGrid : Array<Array<Int>> = Array(1) { Array(1) { 0 }  }

        private var fixedGrid : Array<Array<Boolean>> = Array(1) { Array(1) { false }  }

        private var firstLineIndex : Int = 0

        private var firstColumnIndex : Int = 0

        private var virtualHoleLine : Int = 0

        private var virtualHoleColumn : Int = 0

        private var cycleType : CycleType = VERTICAL

        private var firstBlockIndex : Int = 1

        private var firstBlockLine : Int = 0

        private var firstBlockColumn : Int = 0

        private var secondBlockIndex : Int = 1

        private var secondBlockLine : Int = 0

        private var secondBlockColumn : Int = 0

        private var buffer : MutableList<Position> = mutableListOf()

        private fun remainingLines() : Int = numberOfLines() - firstLineIndex

        private fun remainingColumns() : Int = numberOfColumns() - firstColumnIndex

        private fun firstBlockPosition() : Position = Position(firstBlockLine, firstBlockColumn)

        private fun secondBlockPosition() : Position = Position (secondBlockLine, secondBlockColumn)

        private fun virtualHolePosition() : Position = Position(virtualHoleLine, virtualHoleColumn)

        private fun reloadGrids(){
            virtualGrid = Array(numberOfLines()) { Array(numberOfColumns()) { 0 } }
            grid.forEachCell{ line : Int, column : Int ->
                if(grid.cellIsNotEmpty(line, column)){
                    virtualGrid[line][column] = (grid[line, column] as Block).index()
                }else{
                    virtualHoleLine = line
                    virtualHoleColumn = column
                    virtualGrid[line][column] = 0
                }
            }
            fixedGrid = Array(numberOfLines()) { Array(numberOfColumns()) { false } }
        }

        private fun done() : Boolean{
            for(i : Int in 0 until numberOfLines()){
                for(j : Int in 0 until numberOfColumns()){
                    if(blockAt(i, j) != 0 && blockAt(i, j) != correctBlockAt(i, j)) return false
                }
            }
            return true
        }

        private fun notDone() : Boolean = !done()

        private fun twoByTwoGrid() : Boolean = remainingColumns() <= 2 && remainingLines() <= 2

        private fun movingSingleBlock() : Boolean{
            return if(remainingLines() >= remainingColumns()){
                currentLineGap() > 2
            }else{
                currentColumnGap() > 2
            }
        }

        private fun movingTwoBlocks() : Boolean = !twoByTwoGrid() && !movingSingleBlock()

        private fun currentLineGap() : Int{
            var count = 0
            for(i : Int in numberOfColumns() - 1 downTo firstColumnIndex){
                if(isNotFixed(firstLineIndex, i)){
                    count++
                }else{
                    return count
                }
            }
            return count
        }

        private fun currentColumnGap() : Int{
            var count = 0
            for(i : Int in numberOfLines() - 1 downTo firstLineIndex){
                if(!fixedGrid[i][firstColumnIndex]){
                    count++
                }else return count
            }
            return count
        }

        private fun isFixed(line : Int, column : Int) : Boolean = fixedGrid[line][column]

        private fun isNotFixed(line : Int, column : Int) : Boolean = !isFixed(line, column)

        private fun isFixed(position : Position) : Boolean = isFixed(position.line, position.column)

        private fun isNotFixed(position: Position) : Boolean = !isFixed(position)

        private fun blockAt(line : Int, column : Int) : Int = virtualGrid[line][column]

        private fun blockAt(position : Position) : Int = blockAt(position.line, position.column)

        private fun correctBlockAt(line : Int, column : Int) : Int = numberOfColumns() * line + column + 1

        private fun findBlock(index : Int) : Position{
            for(i : Int in 0 until numberOfLines()){
                for(j : Int in 0 until numberOfColumns()){
                    if(virtualGrid[i][j] == index) return Position(i, j)
                }
            }
            throw UnsupportedOperationException("findBlock")
        }

        private fun targetOf(index : Int) : Position{
            val line : Int = (index - 1) / numberOfColumns()
            return Position(line, index - 1 - line * numberOfColumns())
        }

        private fun blockIsCorrectAt(line : Int, column : Int) : Boolean = blockAt(line, column) == correctBlockAt(line, column)

        private fun blockIsIncorrectAt(line : Int, column : Int) : Boolean = !blockIsCorrectAt(line, column)

        private fun canGoUp(position : Position) : Boolean = position.line > firstLineIndex && isNotFixed(position.up())

        private fun canGoDown(position : Position) : Boolean = position.line < numberOfLines() - 1

        private fun canGoLeft(position : Position) : Boolean = position.column > firstColumnIndex && isNotFixed(position.left())

        private fun canGoRight(position : Position) : Boolean = position.column < numberOfColumns() - 1

        private fun canGoDown() : Boolean = virtualHoleLine < numberOfLines() - 1

        private fun canGoRight() : Boolean = virtualHoleColumn < numberOfColumns() - 1

        private fun upOfHole() : Position = virtualHolePosition().up()

        private fun downOfHole() : Position = virtualHolePosition().down()

        private fun leftOfHole() : Position = virtualHolePosition().left()

        private fun rightOfHole() : Position = virtualHolePosition().right()

        private fun isLeftOfHole(line : Int, column : Int) : Boolean{
            return virtualHoleLine == line && virtualHoleColumn - 1 == column
        }

        private fun isRightOfHole(line : Int, column : Int) : Boolean{
            return virtualHoleLine == line && virtualHoleColumn + 1 == column
        }

        private fun isUpOfHole(line : Int, column : Int) : Boolean{
            return virtualHoleLine - 1 == line && virtualHoleColumn == column
        }

        private fun isDownOfHole(line : Int, column : Int) : Boolean{
            return virtualHoleLine + 1 == line && virtualHoleColumn == column
        }

        private fun isLeftOfHole(position : Position) : Boolean = isLeftOfHole(position.line, position.column)

        private fun isRightOfHole(position : Position) : Boolean = isRightOfHole(position.line, position.column)

        private fun isUpOfHole(position : Position) : Boolean = isUpOfHole(position.line, position.column)

        private fun isDownOfHole(position : Position) : Boolean = isDownOfHole(position.line, position.column)

        private fun swapWithHole(position : Position){
            val block : Int = blockAt(position)
            virtualGrid[position.line][position.column] = 0
            virtualGrid[virtualHoleLine][virtualHoleColumn] = block
            virtualHoleLine = position.line
            virtualHoleColumn = position.column
        }

        private fun goUp(){
            val up : Position = upOfHole()
            swapWithHole(up)
            holePath.add(UP)
        }

        private fun goDown(){
            val down : Position = downOfHole()
            swapWithHole(down)
            holePath.add(DOWN)
        }

        private fun goLeft(){
            val left : Position = leftOfHole()
            swapWithHole(left)
            holePath.add(LEFT)
        }

        private fun goRight(){
            val right : Position = rightOfHole()
            swapWithHole(right)
            holePath.add(RIGHT)
        }

        private fun pathIsValid(path : Collection<Position>, additionalFixed : Collection<Position>) : Boolean{
            for(position : Position in path){
                var isAlsoFixed = false
                for(pos in additionalFixed){
                    if(position == pos){
                        isAlsoFixed = true
                        break
                    }
                }
                if(isFixed(position) || isAlsoFixed) return false
            }
            return true
        }

        private fun pathIsInvalid(path : Collection<Position>, additionalFixed : Collection<Position>) : Boolean = !pathIsValid(path, additionalFixed)

        private fun pathIsValid(path : Collection<Position>) : Boolean{
            for(position : Position in path){
                if(isFixed(position)) return false
            }
            return true
        }

        private fun pathIsInvalid(path : Collection<Position>) : Boolean = !pathIsValid(path)

        private fun upperPath(start : Position, end : Position) : MutableList<Position>{
            val path : MutableList<Position> = mutableListOf()

            fun add(line : Int, column : Int) = path.add(Position(line, column))

            if(start.line < end.line){
                for(i : Int in start.line + 1 .. end.line){
                    add(i, start.column)
                }
            }else if(start.line > end.line){
                for(i : Int in start.line - 1 downTo end.line){
                    add(i, start.column)
                }
            }

            if(start.column < end.column){
                for(i : Int in start.column + 1 .. end.column){
                    add(end.line, i)
                }
            }else if(start.column > end.column){
                for(i : Int in start.column - 1 downTo end.column){
                    add(end.line, i)
                }
            }
            return path
        }

        private fun lowerPath(start : Position, end : Position) : MutableList<Position>{
            val path : MutableList<Position> = mutableListOf()

            fun add(line : Int, column : Int) = path.add(Position(line, column))

            if(start.column < end.column){
                for(i : Int in start.column + 1 .. end.column){
                    add(start.line, i)
                }
            }else if(start.column > end.column){
                for(i : Int in start.column - 1 downTo end.column){
                    add(start.line, i)
                }
            }

            if(start.line > end.line){
                for(i : Int in start.line - 1 downTo end.line){
                    add(i, end.column)
                }
            }else if(start.line < end.line){
                for(i : Int in start.line + 1 .. end.line){
                    add(i, end.column)
                }
            }

            return path
        }

        private fun checkGridState(){
            if(remainingLines() > 2){
                checkLinesState()
            }
            if(remainingColumns() > 2){
                checkColumnsState()
            }
        }

        private fun checkLinesState(){
            if(!movingTwoBlocks()){
                val columns : MutableList<Int> = mutableListOf()
                for(i : Int in 0 until numberOfColumns()){
                    if(blockIsCorrectAt(firstLineIndex, i)) columns.add(i)
                }
                if(columns.size <= numberOfColumns() - 2){ // not a case where one block only is missing
                    for(i : Int in columns){
                        fixedGrid[firstLineIndex][i] = true
                    }
                }else if(columns.size == numberOfColumns()){ //full line
                    for(i : Int in columns){
                        fixedGrid[firstLineIndex][i] = true
                    }
                    firstLineIndex++
                    checkGridState()
                } //else do nothing
            }else{
                val twoAreIn = blockIsCorrectAt(firstLineIndex, numberOfColumns() - 1) &&
                        blockIsCorrectAt(firstLineIndex, numberOfColumns() - 2)
                if(twoAreIn){
                    fixedGrid[firstLineIndex][numberOfColumns() - 1] = true
                    fixedGrid[firstLineIndex][numberOfColumns() - 2] = true
                    firstLineIndex++
                    checkGridState()
                }
            }
        }

        private fun checkColumnsState(){
            if(!movingTwoBlocks()){
                val lines : MutableList<Int> = mutableListOf()
                for(i : Int in 0 until numberOfLines()){
                    if(blockIsCorrectAt(i, firstColumnIndex)) lines.add(i)
                }
                if(lines.size == numberOfLines()){ // full line
                    for(i : Int in lines){
                        fixedGrid[i][firstColumnIndex] = true
                    }
                    firstColumnIndex++
                    checkGridState()
                }else if(lines.size <= numberOfLines() - 2){ // At least two missing
                    for(i : Int in lines){
                        fixedGrid[i][firstColumnIndex] = true
                    }
                } //else do nothing
            }else{
                val twoAreIn = blockIsCorrectAt(numberOfLines() - 1, firstColumnIndex) &&
                        blockIsCorrectAt(numberOfLines() - 2, firstColumnIndex)
                if(twoAreIn){
                    fixedGrid[numberOfLines() - 1][firstColumnIndex] = true
                    fixedGrid[numberOfLines() - 2][firstColumnIndex] = true
                    firstColumnIndex++
                    checkGridState()
                }
            }
        }

        private fun resetValues(){
            firstLineIndex = 0
            firstColumnIndex = 0
        }

        fun reload(){
            reloadGrids()
            resetValues()
            holePath.clear()
            checkGridState()
            while(notDone()) addNextStep()
        }

        private fun addNextStep(){
            println("FBI $firstBlockIndex " +
                    "SBI $secondBlockIndex " +
                    "FBP (${firstBlockPosition().line}, ${firstBlockPosition().column}) " +
                    "SBP (${secondBlockPosition().line}, ${secondBlockPosition().column}) " +
                    "VHP (${virtualHolePosition().line}, ${virtualHolePosition().column}) " +
                    "FLI $firstLineIndex FCI $firstColumnIndex")
            if(movingTwoBlocks()){
                locateTwoBlocks()
                locateBuffer()
            }else if(movingSingleBlock()){
                locateNextSingleBlock()
            }
            when{
                twoByTwoGrid() -> addInCycle()
                movingSingleBlock() -> addStepForSingleBlock()
                else -> addStepForTwoBlocks()
            }
            checkGridState()
        }

        private fun addInCycle() = if(cycleType == VERTICAL) cycleVertical() else cycleHorizontal()

        private fun cycleVertical(){
            if(canGoDown()) goDown() else goUp()
            cycleType = HORIZONTAL
        }

        private fun cycleHorizontal(){
            if(canGoRight()) goRight() else goLeft()
            cycleType = VERTICAL
        }

        private fun addStepForSingleBlock(){
            locateNextSingleBlock()
            addHolePathToBlockNextStep()
        }

        private fun locateNextSingleBlock(){
            findNextSingleBlockIndex()
            findNextSingleBlockPosition()
        }

        private fun findNextSingleBlockIndex(){
            firstBlockIndex = if(remainingLines() >= remainingColumns()){
                nextBlockInLine()
            }else{
                nextBlockInColumn()
            }
        }

        private fun nextBlockInLine() : Int{
            for(i : Int in firstColumnIndex until numberOfColumns()){
                if(blockIsIncorrectAt(firstLineIndex, i)) return correctBlockAt(firstLineIndex, i)
            }
            throw UnsupportedOperationException("nextBlockInLine")
        }

        private fun nextBlockInColumn() : Int{
            for(i : Int in firstLineIndex until numberOfLines()){
                if(blockIsIncorrectAt(i, firstColumnIndex)) return correctBlockAt(i, firstColumnIndex)
            }
            throw UnsupportedOperationException("nextBlockInColumn")
        }

        private fun findNextSingleBlockPosition(){
            val position : Position = findBlock(firstBlockIndex)
            firstBlockLine = position.line
            firstBlockColumn = position.column
        }

        private fun addHolePathToBlockNextStep(){
            println("bns")
            locateNextSingleBlock()
            val blockNextStep : Position = firstBlockNextStep() // must exist at that point
            val path : MutableList<Position> = holePathTo(blockNextStep, mutableListOf(firstBlockPosition()))
            path.add(firstBlockPosition())
            for(pos : Position in path){
                when{
                    isLeftOfHole(pos) -> goLeft()
                    isRightOfHole(pos) -> goRight()
                    isUpOfHole(pos) -> goUp()
                    isDownOfHole(pos) -> goDown()
                }
            }
        }

        private fun firstBlockNextStep() : Position {
            var path : MutableList<Position> = firstBlockUpperPath()
            if(pathIsInvalid(path)){
                path = firstBlockLowerPath()
            }
            return path[0] // Must exist by construction
        }

        private fun firstBlockUpperPath() : MutableList<Position> = upperPath(firstBlockPosition(), targetOf(firstBlockIndex))

        private fun firstBlockLowerPath() : MutableList<Position> = lowerPath(firstBlockPosition(), targetOf(firstBlockIndex))

        private fun holePathTo(target : Position, additionalFixed : Collection<Position>) : MutableList<Position>{
            println("hpt")
            val path : MutableList<Position> = mutableListOf()
            val traps : MutableList<Position> = mutableListOf()

            traps.addAll(additionalFixed)

            fun isATrap(pos : Position) : Boolean{
                for(p in traps){
                    if(pos == p) return true
                }
                return false
            }

            fun isNotATrap(pos : Position) : Boolean = !isATrap(pos)

            fun currentHoleLine() : Int = if(path.isEmpty()) virtualHoleLine else path.last().line

            fun currentHoleColumn() : Int = if(path.isEmpty()) virtualHoleColumn else path.last().column

            fun currentHolePosition() : Position = Position(currentHoleLine(), currentHoleColumn())

            fun lineDelta() : Int = target.line - currentHoleLine()

            fun columnDelta() : Int = target.column - currentHoleColumn()

            fun lineMagnitude() : Int = abs(lineDelta())

            fun columnMagnitude() : Int = abs(columnDelta())

            fun targetNotAttained() : Boolean = currentHoleLine() != target.line || currentHoleColumn() != target.column

            fun strongerHorizontal() : Boolean = columnMagnitude() >= lineMagnitude()

            fun cancel() : Boolean{
                println("cancel")
                return if(path.isNotEmpty()){
                    traps.add(path.last())
                    path.removeAt(path.size - 1)
                    true
                }else false
            }

            fun strongerLeft() : Boolean = columnDelta() < 0

            fun strongerRight() : Boolean = columnDelta() > 0

            fun strongerUp() : Boolean = lineDelta() < 0

            fun strongerDown() : Boolean = lineDelta() > 0

            fun upIsValid() : Boolean = canGoUp(currentHolePosition()) && isNotATrap(currentHolePosition().up())

            fun downIsValid() : Boolean = canGoDown(currentHolePosition()) && isNotATrap(currentHolePosition().down())

            fun leftIsValid() : Boolean = canGoLeft(currentHolePosition()) && isNotATrap(currentHolePosition().left())

            fun rightIsValid() : Boolean = canGoRight(currentHolePosition()) && isNotATrap(currentHolePosition().right())

            fun findNext() : Position{
                var next : Position = currentHolePosition()
                if(strongerHorizontal()){
                    if(strongerLeft()){
                        next = if(leftIsValid()){
                            currentHolePosition().left()
                        }else if(strongerUp()){
                            if(upIsValid()){
                                currentHolePosition().up()
                            }else if(downIsValid()){
                                currentHolePosition().down()
                            }else{
                                if(cancel()) currentHolePosition() else currentHolePosition().right()
                            }
                        }else{
                            if(downIsValid()){
                                currentHolePosition().down()
                            }else if(upIsValid()){
                                currentHolePosition().up()
                            }else{
                                if(cancel()) currentHolePosition() else currentHolePosition().right()
                            }
                        }
                    }else if(strongerRight()){
                        next = if(rightIsValid()){
                            currentHolePosition().right()
                        }else if(strongerUp()){
                            if(upIsValid()){
                                currentHolePosition().up()
                            }else if(downIsValid()){
                                currentHolePosition().down()
                            }else{
                                if(cancel()) currentHolePosition() else currentHolePosition().left()
                            }
                        }else{
                            if(downIsValid()){
                                currentHolePosition().down()
                            }else if(upIsValid()){
                                currentHolePosition().up()
                            }else{
                                if(cancel()) currentHolePosition() else currentHolePosition().left()
                            }
                        }
                    }
                }else{
                    if(strongerUp()){
                        next = if(upIsValid()){
                            currentHolePosition().up()
                        }else if(strongerLeft()){
                            if(leftIsValid()){
                                currentHolePosition().left()
                            }else if(rightIsValid()){
                                currentHolePosition().right()
                            }else{
                                if(cancel()) currentHolePosition() else currentHolePosition().down()
                            }
                        }else{
                            if(rightIsValid()){
                                currentHolePosition().right()
                            }else if(leftIsValid()){
                                currentHolePosition().left()
                            }else{
                                if(cancel()) currentHolePosition() else currentHolePosition().down()
                            }
                        }
                    }else if(strongerDown()){
                        next = if(downIsValid()){
                            currentHolePosition().down()
                        }else if(strongerLeft()){
                            if(leftIsValid()){
                                currentHolePosition().left()
                            }else if(rightIsValid()){
                                currentHolePosition().right()
                            }else{
                                if(cancel()) currentHolePosition() else currentHolePosition().up()
                            }
                        }else{
                            if(rightIsValid()){
                                currentHolePosition().right()
                            }else if(leftIsValid()){
                                currentHolePosition().left()
                            }else{
                                if(cancel()) currentHolePosition() else currentHolePosition().down()
                            }
                        }
                    }
                }
                return next
            }

            fun info(){
                println("------------------------")
                println("INFO HN")
                println("target (${target.line}, ${target.column})")
                println("FBI $firstBlockIndex " +
                        "SBI $secondBlockIndex " +
                        "FBP (${firstBlockPosition().line}, ${firstBlockPosition().column}) " +
                        "SBP (${secondBlockPosition().line}, ${secondBlockPosition().column}) " +
                        "VHP (${virtualHolePosition().line}, ${virtualHolePosition().column}) " +
                        "FLI $firstLineIndex FCI $firstColumnIndex")
                println()
                print("additionalFixed : ")
                for(pos in additionalFixed) print(" (${pos.line}, ${pos.column}) ")
                println()
                print("traps : ")
                for(pos in traps) print(" (${pos.line}, ${pos.column}) ")
                println()
                println("------------------------")
            }

            fun handleNext(position: Position){
                //info()
                path.add(position)
            }

            while(targetNotAttained()){
                handleNext(findNext())
            }

            return path

        }

        private fun addStepForTwoBlocks(){
            locateTwoBlocks()
            locateBuffer()
            when{
                blocksAndHoleInBuffer() -> addStepInBuffer()
                blocksInBuffer() -> moveHoleToBuffer()
                oneBlockInBuffer() -> addStepToSecondBlockInBuffer()
                else -> addStepToFirstBlockInBuffer()
            }
        }

        private fun locateTwoBlocks(){
            if(currentLineGap() <= currentColumnGap()){
                locateTwoBlocksInLine()
            }else{
                locateTwoBlocksInColumn()
            }
        }

        private fun locateTwoBlocksInLine(){
            secondBlockIndex = correctBlockAt(firstLineIndex, numberOfColumns() - 1)
            var position : Position = findBlock(secondBlockIndex)
            secondBlockLine = position.line
            secondBlockColumn = position.column
            firstBlockIndex = secondBlockIndex - 1
            position = findBlock(firstBlockIndex)
            firstBlockLine = position.line
            firstBlockColumn = position.column
        }

        private fun locateTwoBlocksInColumn(){
            secondBlockIndex = correctBlockAt(numberOfLines() - 1, firstColumnIndex)
            var position : Position = findBlock(secondBlockIndex)
            secondBlockLine = position.line
            secondBlockColumn = position.column
            firstBlockIndex = secondBlockIndex - numberOfColumns()
            position = findBlock(firstBlockIndex)
            firstBlockLine = position.line
            firstBlockColumn = position.column
        }

        private fun locateBuffer(){
            if(currentLineGap() <= currentColumnGap()){
                locateBufferInLine()
            }else{
                locateBufferInColumn()
            }
        }

        private fun locateBufferInLine(){
            buffer.clear()
            buffer.add(targetOf(firstBlockIndex))
            buffer.add(targetOf(secondBlockIndex))
            buffer.add(buffer[0].down())
            buffer.add(buffer[2].down())
            buffer.add(buffer[1].down())
            buffer.add(buffer[4].down())
        }

        private fun locateBufferInColumn(){
            buffer.clear()
            buffer.add(targetOf(firstBlockIndex))
            buffer.add(targetOf(secondBlockIndex))
            buffer.add(buffer[0].right())
            buffer.add(buffer[2].right())
            buffer.add(buffer[1].right())
            buffer.add(buffer[4].right())
        }

        private fun isInBuffer(pos : Position) : Boolean{
            for(p in buffer){
                if(pos == p) return true
            }
            return false
        }

        private fun blocksAndHoleInBuffer() : Boolean{
            return isInBuffer(firstBlockPosition()) && isInBuffer(secondBlockPosition()) && isInBuffer(virtualHolePosition())
        }

        private fun blocksInBuffer() : Boolean{
            return isInBuffer(firstBlockPosition()) && secondBlockPosition() == buffer[4]
        }

        private fun oneBlockInBuffer() : Boolean{
            return secondBlockPosition() == buffer[4]
        }

        private fun bufferIsVertical() : Boolean = buffer[0].line == buffer[1].line

        private fun addStepInBuffer(){
            when{
                firstIsPlaced() -> crawlBlocksInBuffer()
                secondIsPlaced() -> stepForFirstInBuffer()
                else -> stepForSecondInBuffer()
            }
        }

        private fun stepForSecondInBuffer(){
            println("buffer[5]")
            val blockPath : MutableList<Position> = lowerPath(secondBlockPosition(), buffer[5])
            val holePath : MutableList<Position> = holePathTo(blockPath[0], setOf(secondBlockPosition()))
            holePath.add(secondBlockPosition())
            for(pos : Position in holePath){
                when{
                    isLeftOfHole(pos) -> goLeft()
                    isRightOfHole(pos) -> goRight()
                    isUpOfHole(pos) -> goUp()
                    isDownOfHole(pos) -> goDown()
                }
            }
        }

        private fun stepForFirstInBuffer(){
            if(firstBlockPosition() == buffer[3]){
                invertedCase()
            }else if(firstBlockPosition() == buffer[2] && virtualHolePosition() == buffer[3]){
                if(bufferIsVertical()) goUp() else goLeft()
                invertedCase()
            }else{
                correctCase()
            }
        }

        private fun invertedCase(){
            println("ic")
            if(bufferIsVertical()){
                when(virtualHolePosition()){
                    buffer[0] -> {
                        goRight()
                        goDown()
                    }
                    buffer[1] -> goDown()
                    buffer[2] -> goRight()
                }
                goLeft()
                goDown()
                goRight()
                goUp()
                goLeft()
                goUp()
                goRight()
                goDown()
                goLeft()
                goDown()
                goRight()
                goUp()
                goLeft()
                goUp()
                goRight()
                goDown()
            }else{
                when(virtualHolePosition()){
                    buffer[0] -> {
                        goDown()
                        goRight()
                    }
                    buffer[1] -> goRight()
                    buffer[2] -> goDown()
                }
                goUp()
                goRight()
                goDown()
                goLeft()
                goUp()
                goLeft()
                goDown()
                goRight()
                goUp()
                goRight()
                goDown()
                goLeft()
                goUp()
                goLeft()
                goDown()
                goRight()
            }
        }

        private fun correctCase(){
            println("cc")
            if(bufferIsVertical()){
                when(firstBlockPosition()){
                    buffer[0] -> {
                        when(virtualHolePosition()){
                            buffer[1] -> goLeft()
                            buffer[2] -> goUp()
                            buffer[3] -> goUp()
                            buffer[4] -> goUp()
                        }
                    }
                    buffer[1] -> {
                        when(virtualHolePosition()){
                            buffer[0] -> goDown()
                            buffer[2] -> goRight()
                            buffer[3] -> goUp()
                            buffer[4] -> goUp()
                        }
                    }
                    buffer[2] -> {
                        when(virtualHolePosition()){
                            buffer[0] -> goRight()
                            buffer[1] -> goDown()
                            buffer[4] -> goLeft()
                        }
                    }
                }
            }else{
                when(firstBlockPosition()){
                    buffer[0] -> {
                        when(virtualHolePosition()){
                            buffer[3] -> goLeft()
                            buffer[2] -> goLeft()
                            buffer[4] -> goUp()
                            buffer[1] -> goUp()
                        }
                    }
                    buffer[1] -> {
                        when(virtualHolePosition()){
                            buffer[3] -> goLeft()
                            buffer[2] -> goDown()
                            buffer[0] -> goRight()
                            buffer[4] -> goLeft()
                        }
                    }
                    buffer[2] -> {
                        when(virtualHolePosition()){
                            buffer[0] -> goDown()
                            buffer[1] -> goRight()
                            buffer[4] -> goUp()
                        }
                    }
                }
            }
        }

        private fun crawlBlocksInBuffer(){
            println("crawl")
            if(bufferIsVertical()){
                when(virtualHolePosition()){
                    buffer[3] -> {
                        goUp()
                        goUp()
                        goRight()
                    }
                    buffer[2] -> {
                        goUp()
                        goRight()
                    }
                    buffer[0] -> goRight()
                }
                goDown()
                goDown()
                goLeft()
                goUp()
                goUp()
                goRight()
                goDown()
            }else{
                when(virtualHolePosition()){
                    buffer[3] -> {
                        goLeft()
                        goLeft()
                        goDown()
                    }
                    buffer[2] -> {
                        goLeft()
                        goDown()
                    }
                    buffer[0] -> goDown()
                }
                goRight()
                goRight()
                goUp()
                goLeft()
                goLeft()
                goDown()
                goRight()
            }
        }

        private fun firstIsPlaced() : Boolean = firstBlockPosition() == buffer[4] && secondBlockPosition() == buffer[5]

        private fun secondIsPlaced() : Boolean = secondBlockPosition() == buffer[5]

        private fun moveHoleToBuffer(){
            println("htb")
            fun isNotBlocked(pos : Position) : Boolean = pos != firstBlockPosition() && pos != secondBlockPosition()

            val path : MutableList<Position> = if(isNotBlocked(buffer[3])){
                holePathTo(buffer[3], setOf(firstBlockPosition(), secondBlockPosition()))
            }else if(bufferIsVertical()){
                if(virtualHoleLine > buffer[3].line){
                    holePathTo(buffer[5], setOf(firstBlockPosition(), secondBlockPosition()))
                }else{
                    holePathTo(buffer[2], setOf(firstBlockPosition(), secondBlockPosition()))
                }
            }else{
                if(virtualHoleLine < buffer[3].line){
                    holePathTo(buffer[2], setOf(firstBlockPosition(), secondBlockPosition()))
                }else{
                    holePathTo(buffer[5], setOf(firstBlockPosition(), secondBlockPosition()))
                }
            }

            for(pos : Position in path){
                when{
                    isLeftOfHole(pos) -> goLeft()
                    isRightOfHole(pos) -> goRight()
                    isUpOfHole(pos) -> goUp()
                    isDownOfHole(pos) -> goDown()
                }
            }

        }

        private fun addStepToSecondBlockInBuffer(){
            println("Sbib")
            val block : Position = firstBlockPosition()
            val other : Position = secondBlockPosition()
            var path : MutableList<Position> = upperPath(block, buffer[5])
            if(pathIsInvalid(path, setOf(other))){
                path = lowerPath(block, buffer[5])
            }
            val nextStep : Position = path[0] // Must exist by construction
            val holePath : MutableList<Position> = holePathTo(nextStep, mutableListOf(block, other))
            if(pathIsValid(holePath, mutableListOf(block, other))){
                holePath.add(block)
                for(pos : Position in holePath){
                    when{
                        isLeftOfHole(pos) -> goLeft()
                        isRightOfHole(pos) -> goRight()
                        isUpOfHole(pos) -> goUp()
                        isDownOfHole(pos) -> goDown()
                    }
                }
            }
        }

        private fun addStepToFirstBlockInBuffer(){
            println("Fbib")
            var path : MutableList<Position> = upperPath(secondBlockPosition(), buffer[4])
            if(pathIsInvalid(path)){
                path = lowerPath(secondBlockPosition(), buffer[4])
            }
            val nextStep : Position = path[0] // Must exist by construction
            val holePath : MutableList<Position> = holePathTo(nextStep, mutableListOf(secondBlockPosition()))
            if(pathIsInvalid(holePath, mutableListOf(secondBlockPosition()))) throw UnsupportedOperationException("addStepToFirstBlockInBuffer")
            holePath.add(secondBlockPosition())
            for(pos : Position in holePath){
                when{
                    isLeftOfHole(pos) -> goLeft()
                    isRightOfHole(pos) -> goRight()
                    isUpOfHole(pos) -> goUp()
                    isDownOfHole(pos) -> goDown()
                }
            }
        }

        fun performNextStep(){
            if(holePath.isNotEmpty()){
                when(holePath[0]){
                    UP -> moveRealHoleUp()
                    DOWN -> moveRealHoleDown()
                    LEFT -> moveRealHoleLeft()
                    RIGHT -> moveRealHoleRight()
                }
                holePath.removeAt(0)
            }
        }

        private fun moveRealHoleLeft() = realBlockLeftOfHole().move()

        private fun moveRealHoleRight() = realBlockRightOfHole().move()

        private fun moveRealHoleUp() = realBlockUpOfHole().move()

        private fun moveRealHoleDown() = realBlockDownOfHole().move()

        private fun realBlockLeftOfHole() : Block = grid[holeLine, holeColumn - 1] as Block //Note, not virtual

        private fun realBlockRightOfHole() : Block = grid[holeLine, holeColumn + 1] as Block //Note, not virtual

        private fun realBlockUpOfHole() : Block = grid[holeLine - 1, holeColumn] as Block //Note, not virtual

        private fun realBlockDownOfHole() : Block = grid[holeLine + 1, holeColumn] as Block //Note, not virtual

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
        addNewGrid(height, width)
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
        Algorithm.reload()
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