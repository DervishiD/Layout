package usages.cellularautomaton

import llayout6.displayers.*
import llayout6.frame.LScene

internal object NeighbourhoodSelectionScene : LScene() {

    private const val GRID_WIDTH : Double = 0.8

    private const val GRID_HEIGHT : Double = 0.3

    private const val LABEL_GRID_GAP : Int = 20

    private const val BUTTON_Y : Double = 0.9

    private val SURVIVING_LABEL : Label = Label("An active cell survives if its neighbourhood contains those numbers of living cells")

    private val SURVIVING_GRID : RegularGrid = RegularGrid(2, 9, GRID_WIDTH, GRID_HEIGHT)

    private val BIRTH_LABEL : Label = Label("An inactive cell becomes active if its neighbourhood contains those numbers of living cells")

    private val BIRTH_GRID : RegularGrid = RegularGrid(2, 9, GRID_WIDTH, GRID_HEIGHT)

    private val BACK_BUTTON : TextButton = TextButton("Back") { back() }

    private val NEXT_BUTTON : TextButton = TextButton("Next") { next() }

    init{
        addSurviving()
        addBirth()
        addBackButton()
        addNextButton()
    }

    private fun addSurviving(){
        addSurvivingLabel()
        addSurvivingGrid()
    }

    private fun addSurvivingLabel(){
        add(SURVIVING_LABEL.setX(0.5).setY(0.1))
    }

    private fun addSurvivingGrid(){
        prepareSurvivingGrid()
        add(SURVIVING_GRID.setX(0.5).alignTopToBottom(SURVIVING_LABEL, LABEL_GRID_GAP))
    }

    private fun prepareSurvivingGrid(){
        SURVIVING_GRID.forEachCell { i, j ->
            if(i == 0){
                //Labels
                SURVIVING_GRID[i, j] = Canvas().writeCentered(j) as ResizableDisplayer
            }else{
                //Switches
                SURVIVING_GRID[i, j] = Switch()
            }
        }
    }

    private fun addBirth(){
        addBirthLabel()
        addBirthGrid()
    }

    private fun addBirthLabel(){
        add(BIRTH_LABEL.setX(0.5).setY(0.5))
    }

    private fun addBirthGrid(){
        prepareBirthGrid()
        add(BIRTH_GRID.setX(0.5).alignTopToBottom(BIRTH_LABEL, LABEL_GRID_GAP))
    }

    private fun prepareBirthGrid(){
        BIRTH_GRID.forEachCell { i, j ->
            if(i == 0){
                //Labels
                BIRTH_GRID[i, j] = Canvas().writeCentered(j) as ResizableDisplayer
            }else{
                //Switches
                BIRTH_GRID[i, j] = Switch()
            }
        }
    }

    private fun addBackButton(){
        add(BACK_BUTTON.setX(0.33).setY(BUTTON_Y))
    }

    private fun addNextButton(){
        add(NEXT_BUTTON.setX(0.66).setY(BUTTON_Y))
    }

    private fun back(){
        frame.setScene(ColourScene)
    }

    private fun survivingIndices() : Collection<Int>{
        val result : MutableCollection<Int> = mutableSetOf()
        SURVIVING_GRID.forEachCell { i, j ->
            if(i == 1){
                if((SURVIVING_GRID[i, j] as Switch).isOn()) result.add(j)
            }
        }
        return result
    }

    private fun birthIndices() : Collection<Int>{
        val result : MutableCollection<Int> = mutableSetOf()
        BIRTH_GRID.forEachCell { i, j ->
            if(i == 1){
                if((BIRTH_GRID[i, j] as Switch).isOn()) result.add(j)
            }
        }
        return result
    }

    private fun next(){
        updateParameters()
        frame.setScene(GridScene)
    }

    private fun updateParameters(){
        Parameters.setSurvivingParameters(survivingIndices())
        Parameters.setBirthParameters(birthIndices())
    }

}