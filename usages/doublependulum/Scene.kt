package usages.doublependulum

import llayout6.DEFAULT_MEDIUM_FONT
import llayout6.displayers.HorizontalDoubleSlider
import llayout6.displayers.Label
import llayout6.frame.LScene
import llayout6.utilities.StringDisplay
import kotlin.math.PI

internal object Scene : LScene() {

    private const val SIMULATION_WIDTH : Double = 0.75

    private const val PARAMETERS_WIDTH : Double = 1 - SIMULATION_WIDTH

    private const val SLIDER_WIDTH : Double = 0.8 * PARAMETERS_WIDTH

    private const val SLIDER_HEIGHT : Double = 0.03

    private const val CENTRAL_X : Double = PARAMETERS_WIDTH / 2

    private const val GRAPH_HEIGHT : Double = 0.22

    private const val SIMULATION_PANE_HEIGHT : Double = 1 - 2 * GRAPH_HEIGHT

    private val PHI_PLOT_LABEL : Label = Label("Phi")
    private val PSI_PLOT_LABEL : Label = Label("Psi")
    private const val PLOT_LABEL_GAP : Int = 4

    private const val LABEL_SLIDER_GAP : Int = 3

    private val TITLE : Label = Label(StringDisplay("Double Pendulum", DEFAULT_MEDIUM_FONT))
    private const val TITLE_Y : Double = 0.04

    private const val MINIMAL_MASS : Double = 0.1
    private const val MAXIMAL_MASS : Double = 2.0
    private const val MASS_PRECISION : Double = 0.1
    private const val DEFAULT_MASS : Double = 1.0

    private val PHI_MASS_LABEL : Label = Label("First Mass")
    private val PHI_MASS_SLIDER : HorizontalDoubleSlider = HorizontalDoubleSlider(SLIDER_WIDTH, SLIDER_HEIGHT)
    private val PHI_MASS_INDICATOR : Label = Label()
    private const val PHI_MASS_Y : Double = 0.08

    private val PSI_MASS_LABEL : Label = Label("Second Mass")
    private val PSI_MASS_SLIDER : HorizontalDoubleSlider = HorizontalDoubleSlider(SLIDER_WIDTH, SLIDER_HEIGHT)
    private val PSI_MASS_INDICATOR : Label = Label()
    private const val PSI_MASS_Y : Double = 0.16

    private const val MINIMAL_LENGTH : Double = 0.1
    private const val MAXIMAL_LENGTH : Double = 1.0
    private const val LENGTH_PRECISION : Double = 0.1
    private const val DEFAULT_LENGTH : Double = 0.5

    private val PHI_LENGTH_LABEL : Label = Label("First Length")
    private val PHI_LENGTH_SLIDER : HorizontalDoubleSlider = HorizontalDoubleSlider(SLIDER_WIDTH, SLIDER_HEIGHT)
    private val PHI_LENGTH_INDICATOR : Label = Label()
    private const val PHI_LENGTH_Y : Double = 0.24

    private val PSI_LENGTH_LABEL : Label = Label("Second Length")
    private val PSI_LENGTH_SLIDER : HorizontalDoubleSlider = HorizontalDoubleSlider(SLIDER_WIDTH, SLIDER_HEIGHT)
    private val PSI_LENGTH_INDICATOR : Label = Label()
    private const val PSI_LENGTH_Y : Double = 0.32

    private const val MINIMAL_STARTING_ANGLE : Double = - PI
    private const val MAXIMAL_STARTING_ANGLE : Double = PI
    private const val STARTING_ANGLE_PRECISION : Double = 0.0
    private const val DEFAULT_STARTING_ANGLE : Double = 0.0

    /*
     * Angle stuff
     */

    init{
        addSimulationPane()
        addPhi()
        addPsi()
        addTitle()
        addParameters()
        addReloadButton()
    }

    private fun addSimulationPane(){
        add(SimulationPane.setWidth(SIMULATION_WIDTH).setHeight(SIMULATION_PANE_HEIGHT).alignRightTo(1.0).alignTopTo(0))
    }

    private fun addPhi(){
        addPhiPlot()
        addPhiLabel()
    }

    private fun addPhiPlot(){
        add(PhiPlot.setWidth(SIMULATION_WIDTH).setHeight(GRAPH_HEIGHT).alignTopToBottom(SimulationPane).alignRightTo(1.0))
    }

    private fun addPhiLabel(){
        add(PHI_PLOT_LABEL.alignLeftToLeft(PhiPlot, PLOT_LABEL_GAP).alignTopToTop(PhiPlot, PLOT_LABEL_GAP))
    }

    private fun addPsi(){
        addPsiPlot()
        addPsLabel()
    }

    private fun addPsiPlot(){
        add(PsiPlot.setWidth(SIMULATION_WIDTH).setHeight(GRAPH_HEIGHT).alignTopToBottom(PhiPlot).alignRightTo(1.0))
    }

    private fun addPsLabel(){
        add(PSI_PLOT_LABEL.alignTopToTop(PsiPlot, PLOT_LABEL_GAP).alignLeftToLeft(PsiPlot, PLOT_LABEL_GAP))
    }

    private fun addTitle(){
        add(TITLE.setX(CENTRAL_X).setY(TITLE_Y))
    }

    private fun addParameters(){
        addPhiMass()
        addPsiMass()
        addPhiLength()
        addPsiLength()
        //TODO("Not implemented.")
    }

    private fun addPhiMass(){
        addPhiMassLabel()
        addPhiMassSlider()
        addPhiMassIndicator()
    }

    private fun addPhiMassLabel(){
        add(PHI_MASS_LABEL.setX(CENTRAL_X).setY(PHI_MASS_Y))
    }

    private fun addPhiMassSlider(){
        PHI_MASS_SLIDER.setRange(MINIMAL_MASS, MAXIMAL_MASS).setPrecision(MASS_PRECISION).setStartingValue(DEFAULT_MASS)
        add(PHI_MASS_SLIDER.setX(CENTRAL_X).alignTopToBottom(PHI_MASS_LABEL, LABEL_SLIDER_GAP))
    }

    private fun addPhiMassIndicator(){
        PHI_MASS_SLIDER.addValueListener { PHI_MASS_INDICATOR.setText(PHI_MASS_SLIDER.value()) }
        add(PHI_MASS_INDICATOR.setText(PHI_MASS_SLIDER.value()).setX(CENTRAL_X).alignTopToBottom(PHI_MASS_SLIDER, LABEL_SLIDER_GAP))
    }

    private fun addPsiMass(){
        addPsiMassLabel()
        addPsiMassSlider()
        addPsiMassIndicator()
    }

    private fun addPsiMassLabel(){
        add(PSI_MASS_LABEL.setX(CENTRAL_X).setY(PSI_MASS_Y))
    }

    private fun addPsiMassSlider(){
        PSI_MASS_SLIDER.setRange(MINIMAL_MASS, MAXIMAL_MASS).setPrecision(MASS_PRECISION).setStartingValue(DEFAULT_MASS)
        add(PSI_MASS_SLIDER.setX(CENTRAL_X).alignTopToBottom(PSI_MASS_LABEL, LABEL_SLIDER_GAP))
    }

    private fun addPsiMassIndicator(){
        PSI_MASS_SLIDER.addValueListener { PSI_MASS_INDICATOR.setText(PSI_MASS_SLIDER.value()) }
        add(PSI_MASS_INDICATOR.setText(PSI_MASS_SLIDER.value()).setX(CENTRAL_X).alignTopToBottom(PSI_MASS_SLIDER, LABEL_SLIDER_GAP))
    }

    private fun addPhiLength(){
        addPhiLengthLabel()
        addPhiLengthSlider()
        addPhiLengthIndicator()
    }

    private fun addPhiLengthLabel(){
        add(PHI_LENGTH_LABEL.setX(CENTRAL_X).setY(PHI_LENGTH_Y))
    }

    private fun addPhiLengthSlider(){
        PHI_LENGTH_SLIDER.setRange(MINIMAL_LENGTH, MAXIMAL_LENGTH).setPrecision(LENGTH_PRECISION).setStartingValue(DEFAULT_LENGTH)
        add(PHI_LENGTH_SLIDER.setX(CENTRAL_X).alignTopToBottom(PHI_LENGTH_LABEL, LABEL_SLIDER_GAP))
    }

    private fun addPhiLengthIndicator(){
        PHI_LENGTH_SLIDER.addValueListener { PHI_LENGTH_INDICATOR.setText(PHI_LENGTH_SLIDER.value()) }
        add(PHI_LENGTH_INDICATOR.setText(PHI_LENGTH_SLIDER.value()).setX(CENTRAL_X).alignTopToBottom(PHI_LENGTH_SLIDER, LABEL_SLIDER_GAP))
    }

    private fun addPsiLength(){
        addPsiLengthLabel()
        addPsiLengthSlider()
        addPsiLengthIndicator()
    }

    private fun addPsiLengthLabel(){
        add(PSI_LENGTH_LABEL.setX(CENTRAL_X).setY(PSI_LENGTH_Y))
    }

    private fun addPsiLengthSlider(){
        PSI_LENGTH_SLIDER.setRange(MINIMAL_LENGTH, MAXIMAL_LENGTH).setPrecision(LENGTH_PRECISION).setStartingValue(DEFAULT_LENGTH)
        add(PSI_LENGTH_SLIDER.setX(CENTRAL_X).alignTopToBottom(PSI_LENGTH_LABEL, LABEL_SLIDER_GAP))
    }

    private fun addPsiLengthIndicator(){
        PSI_LENGTH_SLIDER.addValueListener { PSI_LENGTH_INDICATOR.setText(PSI_LENGTH_SLIDER.value()) }
        add(PSI_LENGTH_INDICATOR.setText(PSI_LENGTH_SLIDER.value()).setX(CENTRAL_X).alignTopToBottom(PSI_LENGTH_SLIDER, LABEL_SLIDER_GAP))
    }

    private fun addReloadButton(){
        //TODO("Not implemented.")
    }

}