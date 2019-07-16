package usages.doublependulum

import llayout6.DEFAULT_MEDIUM_FONT
import llayout6.displayers.HorizontalDoubleSlider
import llayout6.displayers.Label
import llayout6.displayers.TextButton
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

    internal const val MINIMAL_MASS : Double = 0.1
    internal const val MAXIMAL_MASS : Double = 2.0
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
    internal const val MAXIMAL_LENGTH : Double = 1.0
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

    private val STARTING_PHI_LABEL : Label = Label("Starting First Angle")
    private val STARTING_PHI_SLIDER : HorizontalDoubleSlider = HorizontalDoubleSlider(SLIDER_WIDTH, SLIDER_HEIGHT)
    private val STARTING_PHI_INDICATOR : Label = Label()
    private const val STARTING_PHI_Y : Double = 0.4

    private val STARTING_PSI_LABEL : Label = Label("Starting Second Angle")
    private val STARTING_PSI_SLIDER : HorizontalDoubleSlider = HorizontalDoubleSlider(SLIDER_WIDTH, SLIDER_HEIGHT)
    private val STARTING_PSI_INDICATOR : Label = Label()
    private const val STARTING_PSI_Y : Double = 0.48

    private const val MINIMAL_ANGULAR_VELOCITY : Double = -3.0
    private const val MAXIMAL_ANGULAR_VELOCITY : Double = - MINIMAL_ANGULAR_VELOCITY
    private const val ANGULAR_VELOCITY_PRECISION : Double = 0.1
    private const val DEFAULT_ANGULAR_VELOCITY : Double = 0.0

    private val STARTING_PHI_DOT_LABEL : Label = Label("Starting First Angular Velocity")
    private val STARTING_PHI_DOT_SLIDER : HorizontalDoubleSlider = HorizontalDoubleSlider(SLIDER_WIDTH, SLIDER_HEIGHT)
    private val STARTING_PHI_DOT_INDICATOR : Label = Label()
    private const val STARTING_PHI_DOT_Y : Double = 0.56

    private val STARTING_PSI_DOT_LABEL : Label = Label("Starting Second Angular Velocity")
    private val STARTING_PSI_DOT_SLIDER : HorizontalDoubleSlider = HorizontalDoubleSlider(SLIDER_WIDTH, SLIDER_HEIGHT)
    private val STARTING_PSI_DOT_INDICATOR : Label = Label()
    private const val STARTING_PSI_DOT_Y : Double = 0.64

    private const val MINIMAL_GRAVITY : Double = 0.0
    private const val MAXIMAL_GRAVITY : Double = 12.0
    private const val GRAVITY_PRECISION : Double = 0.1
    private const val DEFAULT_GRAVITY : Double = 9.8

    private val GRAVITY_LABEL : Label = Label("Gravity")
    private val GRAVITY_SLIDER : HorizontalDoubleSlider = HorizontalDoubleSlider(SLIDER_WIDTH, SLIDER_HEIGHT)
    private val GRAVITY_INDICATOR : Label = Label()
    private const val GRAVITY_Y : Double = 0.72

    private const val MINIMAL_FRICTION : Double = 0.0
    private const val MAXIMAL_FRICTION : Double = 1.0
    private const val FRICTION_PRECISION : Double = 0.1
    private const val DEFAULT_FRICTION : Double = 0.0

    private val FRICTION_LABEL : Label = Label("Friction")
    private val FRICTION_SLIDER : HorizontalDoubleSlider = HorizontalDoubleSlider(SLIDER_WIDTH, SLIDER_HEIGHT)
    private val FRICTION_INDICATOR : Label = Label()
    private const val FRICTION_Y : Double = 0.8

    private val RELOAD_BUTTON : TextButton = TextButton("Reload") { reload() }
    private const val RELOAD_BUTTON_Y : Double = 0.92

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
        addStartingPhi()
        addStartingPsi()
        addStartingPhiDot()
        addStartingPsiDot()
        addGravity()
        addFriction()
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
        PHI_MASS_SLIDER.addValueListener { PHI_MASS_INDICATOR.setText("%.1f".format(PHI_MASS_SLIDER.value())) }
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
        PSI_MASS_SLIDER.addValueListener { PSI_MASS_INDICATOR.setText("%.1f".format(PSI_MASS_SLIDER.value())) }
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
        PHI_LENGTH_SLIDER.addValueListener { PHI_LENGTH_INDICATOR.setText("%.1f".format(PHI_LENGTH_SLIDER.value())) }
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
        PSI_LENGTH_SLIDER.addValueListener { PSI_LENGTH_INDICATOR.setText("%.1f".format(PSI_LENGTH_SLIDER.value())) }
        add(PSI_LENGTH_INDICATOR.setText(PSI_LENGTH_SLIDER.value()).setX(CENTRAL_X).alignTopToBottom(PSI_LENGTH_SLIDER, LABEL_SLIDER_GAP))
    }

    private fun addStartingPhi(){
        addStartingPhiLabel()
        addStartingPhiSlider()
        addStartingPhiIndicator()
    }

    private fun addStartingPhiLabel(){
        add(STARTING_PHI_LABEL.setX(CENTRAL_X).setY(STARTING_PHI_Y))
    }

    private fun addStartingPhiSlider(){
        STARTING_PHI_SLIDER
                .setRange(MINIMAL_STARTING_ANGLE, MAXIMAL_STARTING_ANGLE)
                .setPrecision(STARTING_ANGLE_PRECISION)
                .setStartingValue(DEFAULT_STARTING_ANGLE)
        add(STARTING_PHI_SLIDER.setX(CENTRAL_X).alignTopToBottom(STARTING_PHI_LABEL, LABEL_SLIDER_GAP))
    }

    private fun addStartingPhiIndicator(){
        STARTING_PHI_SLIDER.addValueListener { STARTING_PHI_INDICATOR.setText(STARTING_PHI_SLIDER.value()) }
        add(STARTING_PHI_INDICATOR.setText(STARTING_PHI_SLIDER.value()).setX(CENTRAL_X).alignTopToBottom(STARTING_PHI_SLIDER, LABEL_SLIDER_GAP))
    }

    private fun addStartingPsi(){
        addStartingPsiLabel()
        addStartingPsiSlider()
        addStartingPsiIndicator()
    }

    private fun addStartingPsiLabel(){
        add(STARTING_PSI_LABEL.setX(CENTRAL_X).setY(STARTING_PSI_Y))
    }

    private fun addStartingPsiSlider(){
        STARTING_PSI_SLIDER
                .setRange(MINIMAL_STARTING_ANGLE, MAXIMAL_STARTING_ANGLE)
                .setPrecision(STARTING_ANGLE_PRECISION)
                .setStartingValue(DEFAULT_STARTING_ANGLE)
        add(STARTING_PSI_SLIDER.setX(CENTRAL_X).alignTopToBottom(STARTING_PSI_LABEL, LABEL_SLIDER_GAP))
    }

    private fun addStartingPsiIndicator(){
        STARTING_PSI_SLIDER.addValueListener { STARTING_PSI_INDICATOR.setText(STARTING_PSI_SLIDER.value()) }
        add(STARTING_PSI_INDICATOR.setText(STARTING_PSI_SLIDER.value()).setX(CENTRAL_X).alignTopToBottom(STARTING_PSI_SLIDER, LABEL_SLIDER_GAP))
    }

    private fun addStartingPhiDot(){
        addStartingPhiDotLabel()
        addStartingPhiDotSlider()
        addStartingPhiDotIndicator()
    }

    private fun addStartingPhiDotLabel(){
        add(STARTING_PHI_DOT_LABEL.setX(CENTRAL_X).setY(STARTING_PHI_DOT_Y))
    }

    private fun addStartingPhiDotSlider(){
        STARTING_PHI_DOT_SLIDER
                .setRange(MINIMAL_ANGULAR_VELOCITY, MAXIMAL_ANGULAR_VELOCITY)
                .setPrecision(ANGULAR_VELOCITY_PRECISION)
                .setStartingValue(DEFAULT_ANGULAR_VELOCITY)
        add(STARTING_PHI_DOT_SLIDER.setX(CENTRAL_X).alignTopToBottom(STARTING_PHI_DOT_LABEL, LABEL_SLIDER_GAP))
    }

    private fun addStartingPhiDotIndicator(){
        STARTING_PHI_DOT_SLIDER.addValueListener { STARTING_PHI_DOT_INDICATOR.setText("%.1f".format(STARTING_PHI_DOT_SLIDER.value())) }
        add(STARTING_PHI_DOT_INDICATOR
                .setText(STARTING_PHI_DOT_SLIDER.value()).setX(CENTRAL_X).alignTopToBottom(STARTING_PHI_DOT_SLIDER, LABEL_SLIDER_GAP))
    }

    private fun addStartingPsiDot(){
        addStartingPsiDotLabel()
        addStartingPsiDotSlider()
        addStartingPsiDotIndicator()
    }

    private fun addStartingPsiDotLabel(){
        add(STARTING_PSI_DOT_LABEL.setX(CENTRAL_X).setY(STARTING_PSI_DOT_Y))
    }

    private fun addStartingPsiDotSlider(){
        STARTING_PSI_DOT_SLIDER
                .setRange(MINIMAL_ANGULAR_VELOCITY, MAXIMAL_ANGULAR_VELOCITY)
                .setPrecision(ANGULAR_VELOCITY_PRECISION)
                .setStartingValue(DEFAULT_ANGULAR_VELOCITY)
        add(STARTING_PSI_DOT_SLIDER.setX(CENTRAL_X).alignTopToBottom(STARTING_PSI_DOT_LABEL, LABEL_SLIDER_GAP))
    }

    private fun addStartingPsiDotIndicator(){
        STARTING_PSI_DOT_SLIDER.addValueListener { STARTING_PSI_DOT_INDICATOR.setText("%.1f".format(STARTING_PSI_DOT_SLIDER.value())) }
        add(STARTING_PSI_DOT_INDICATOR
                .setText(STARTING_PSI_DOT_SLIDER.value()).setX(CENTRAL_X).alignTopToBottom(STARTING_PSI_DOT_SLIDER, LABEL_SLIDER_GAP))
    }

    private fun addGravity(){
        addGravityLabel()
        addGravitySlider()
        addGravityIndicator()
    }

    private fun addGravityLabel(){
        add(GRAVITY_LABEL.setX(CENTRAL_X).setY(GRAVITY_Y))
    }

    private fun addGravitySlider(){
        GRAVITY_SLIDER
                .setRange(MINIMAL_GRAVITY, MAXIMAL_GRAVITY)
                .setPrecision(GRAVITY_PRECISION)
                .setStartingValue(DEFAULT_GRAVITY)
        add(GRAVITY_SLIDER.setX(CENTRAL_X).alignTopToBottom(GRAVITY_LABEL, LABEL_SLIDER_GAP))
    }

    private fun addGravityIndicator(){
        GRAVITY_SLIDER.addValueListener { GRAVITY_INDICATOR.setText("%.1f".format(GRAVITY_SLIDER.value())) }
        add(GRAVITY_INDICATOR
                .setText(GRAVITY_SLIDER.value()).setX(CENTRAL_X).alignTopToBottom(GRAVITY_SLIDER, LABEL_SLIDER_GAP))
    }

    private fun addFriction(){
        addFrictionLabel()
        addFrictionSlider()
        addFrictionIndicator()
    }

    private fun addFrictionLabel(){
        add(FRICTION_LABEL.setX(CENTRAL_X).setY(FRICTION_Y))
    }

    private fun addFrictionSlider(){
        FRICTION_SLIDER
                .setRange(MINIMAL_FRICTION, MAXIMAL_FRICTION)
                .setPrecision(FRICTION_PRECISION)
                .setStartingValue(DEFAULT_FRICTION)
        add(FRICTION_SLIDER.setX(CENTRAL_X).alignTopToBottom(FRICTION_LABEL, LABEL_SLIDER_GAP))
    }

    private fun addFrictionIndicator(){
        FRICTION_SLIDER.addValueListener { FRICTION_INDICATOR.setText("%.1f".format(FRICTION_SLIDER.value())) }
        add(FRICTION_INDICATOR
                .setText(FRICTION_SLIDER.value()).setX(CENTRAL_X).alignTopToBottom(FRICTION_SLIDER, LABEL_SLIDER_GAP))
    }

    private fun addReloadButton(){
        add(RELOAD_BUTTON.setX(CENTRAL_X).setY(RELOAD_BUTTON_Y))
    }

    private fun g() : Double = GRAVITY_SLIDER.value()

    private fun mu() : Double = FRICTION_SLIDER.value()

    private fun lPhi() : Double = PHI_LENGTH_SLIDER.value()

    private fun lPsi() : Double = PSI_LENGTH_SLIDER.value()

    private fun mPhi() : Double = PHI_MASS_SLIDER.value()

    private fun mPsi() : Double = PSI_MASS_SLIDER.value()

    private fun phi() : Double = STARTING_PHI_SLIDER.value()

    private fun psi() : Double = STARTING_PSI_SLIDER.value()

    private fun phiDot() : Double = STARTING_PHI_DOT_SLIDER.value()

    private fun psiDot() : Double = STARTING_PSI_DOT_SLIDER.value()

    private fun reload(){
        PhiPlot.reset()
        PsiPlot.reset()
        SimulationPane.reload(g(), mu(), lPhi(), lPsi(), mPhi(), mPsi(), phi(), psi(), phiDot(), psiDot())
    }

}