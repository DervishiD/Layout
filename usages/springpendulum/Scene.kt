package usages.springpendulum

import llayout6.DEFAULT_LARGE_FONT
import llayout6.displayers.HorizontalDoubleSlider
import llayout6.displayers.Label
import llayout6.displayers.TextButton
import llayout6.frame.LScene
import llayout6.utilities.StringDisplay
import kotlin.math.PI

internal object Scene : LScene() {

    private const val SIMULATION_ZONE_WIDTH : Double = 0.75

    private const val SIMULATION_PANE_WIDTH : Double = SIMULATION_ZONE_WIDTH * 0.4

    private const val RHO_GRAPH_WIDTH : Double = SIMULATION_ZONE_WIDTH - SIMULATION_PANE_WIDTH

    private const val SIMULATION_PANE_HEIGHT : Double = 0.75

    private const val RHO_GRAPH_HEIGHT : Double = SIMULATION_PANE_HEIGHT

    private const val THETA_GRAPH_HEIGHT : Double = 1 - SIMULATION_PANE_HEIGHT

    private const val THETA_GRAPH_WIDTH : Double = SIMULATION_ZONE_WIDTH

    private const val SELECTION_PANE_WIDTH : Double = 1 - SIMULATION_ZONE_WIDTH

    private const val SLIDER_WIDTH : Double = SELECTION_PANE_WIDTH * 0.8

    private const val SLIDER_HEIGHT : Double = 0.04

    private const val MIDDLE_X : Double = SELECTION_PANE_WIDTH / 2

    private val TITLE : Label = Label(StringDisplay("Spring Pendulum", DEFAULT_LARGE_FONT))

    private val MASS_LABEL : Label = Label("Mass")

    private val MASS_SLIDER : HorizontalDoubleSlider = HorizontalDoubleSlider(SLIDER_WIDTH, SLIDER_HEIGHT)

    private const val MINIMAL_MASS : Double = 0.1

    private const val MAXIMAL_MASS : Double = 5.0

    private const val DEFAULT_MASS : Double = 1.0

    private const val MASS_PRECISION : Double = 0.1

    private val MASS_INDICATOR : Label = Label()

    private val SPRING_CONSTANT_LABEL : Label = Label("Spring constant")

    private val SPRING_CONSTANT_SLIDER : HorizontalDoubleSlider = HorizontalDoubleSlider(SLIDER_WIDTH, SLIDER_HEIGHT)

    private const val MINIMAL_SPRING_CONSTANT : Double = 0.1

    private const val MAXIMAL_SPRING_CONSTANT : Double = 5.0

    private const val DEFAULT_SPRING_CONSTANT : Double = 1.0

    private const val SPRING_CONSTANT_PRECISION : Double = 0.1

    private val SPRING_CONSTANT_INDICATOR : Label = Label()

    private val RELAXED_LENGTH_LABEL : Label = Label("Relaxed length")

    private val RELAXED_LENGTH_SLIDER : HorizontalDoubleSlider = HorizontalDoubleSlider(SLIDER_WIDTH, SLIDER_HEIGHT)

    private const val MINIMAL_RELAXED_LENGTH : Double = 0.1

    private const val MAXIMAL_RELAXED_LENGTH : Double = 5.0

    private const val DEFAULT_RELAXED_LENGTH : Double = 1.0

    private const val RELAXED_LENGTH_PRECISION : Double = 0.1

    private val RELAXED_LENGTH_INDICATOR : Label = Label()

    private val STARTING_LENGTH_LABEL : Label = Label("Starting length")

    private val STARTING_LENGTH_SLIDER : HorizontalDoubleSlider = HorizontalDoubleSlider(SLIDER_WIDTH, SLIDER_HEIGHT)

    private const val MINIMAL_STARTING_LENGTH : Double = 0.1

    private const val MAXIMAL_STARTING_LENGTH : Double = 5.0

    private const val DEFAULT_STARTING_LENGTH : Double = 1.0

    private const val STARTING_LENGTH_PRECISION : Double = 0.1

    private val STARTING_LENGTH_INDICATOR : Label = Label()

    private val STARTING_RHO_DOT_LABEL : Label = Label("Starting radial velocity")

    private val STARTING_RHO_DOT_SLIDER : HorizontalDoubleSlider = HorizontalDoubleSlider(SLIDER_WIDTH, SLIDER_HEIGHT)

    private const val MINIMAL_STARTING_RHO_DOT : Double = - 2.0

    private const val MAXIMAL_STARTING_RHO_DOT : Double = 2.0

    private const val DEFAULT_STARTING_RHO_DOT : Double = 0.0

    private const val STARTING_RHO_DOT_PRECISION : Double = 0.1

    private val STARTING_RHO_DOT_INDICATOR : Label = Label()

    private val STARTING_ANGLE_LABEL : Label = Label("Starting angle")

    private val STARTING_ANGLE_SLIDER : HorizontalDoubleSlider = HorizontalDoubleSlider(SLIDER_WIDTH, SLIDER_HEIGHT)

    private const val MINIMAL_STARTING_ANGLE : Double = - PI

    private const val MAXIMAL_STARTING_ANGLE : Double = PI

    private const val DEFAULT_STARTING_ANGLE : Double = 0.0

    private const val STARTING_ANGLE_PRECISION : Double = 0.0

    private val STARTING_ANGLE_INDICATOR : Label = Label()

    private val STARTING_THETA_DOT_LABEL : Label = Label("Starting angular velocity")

    private val STARTING_THETA_DOT_SLIDER : HorizontalDoubleSlider = HorizontalDoubleSlider(SLIDER_WIDTH, SLIDER_HEIGHT)

    private const val MINIMAL_STARTING_THETA_DOT : Double = - 3.0

    private const val MAXIMAL_STARTING_THETA_DOT : Double = 3.0

    private const val DEFAULT_STARTING_THETA_DOT : Double = 0.0

    private const val STARTING_THETA_DOT_PRECISION : Double = 0.1

    private val STARTING_THETA_DOT_INDICATOR : Label = Label()

    private val GRAVITY_LABEL : Label = Label("Gravity")

    private val GRAVITY_SLIDER : HorizontalDoubleSlider = HorizontalDoubleSlider(SLIDER_WIDTH, SLIDER_HEIGHT)

    private const val MINIMAL_GRAVITY : Double = 0.0

    private const val MAXIMAL_GRAVITY : Double = 12.0

    private const val DEFAULT_GRAVITY : Double = 9.8

    private const val GRAVITY_PRECISION : Double = 0.1

    private val GRAVITY_INDICATOR : Label = Label()

    private val FRICTION_LABEL : Label = Label("Friction coefficient")

    private val FRICTION_SLIDER : HorizontalDoubleSlider = HorizontalDoubleSlider(SLIDER_WIDTH, SLIDER_HEIGHT)

    private const val MINIMAL_FRICTION : Double = 0.0

    private const val MAXIMAL_FRICTION : Double = 0.5

    private const val DEFAULT_FRICTION : Double = 0.0

    private const val FRICTION_PRECISION : Double = 0.05

    private val FRICTION_INDICATOR : Label = Label()

    private val RELOAD_BUTTON : TextButton = TextButton("Reload simulation") { reload() }

    private val RHO_PLOT_LABEL : Label = Label("Length")

    private val THETA_PLOT_LABEL : Label = Label("Angle")

    init{
        addSimulationPane()
        addRhoGraph()
        addRhoGraphLabel()
        addThetaGraph()
        addThetaGraphLabel()
        addTitle()
        addMass()
        addSpringConstant()
        addRelaxedLength()
        addStartingLength()
        addStartingRadialVelocity()
        addStartingAngle()
        addStartingAngularVelocity()
        addGravity()
        addFriction()
        addReloadButton()
    }

    private fun addSimulationPane(){
        add(SimulationPane.setWidth(SIMULATION_PANE_WIDTH).setHeight(SIMULATION_PANE_HEIGHT).alignRightToLeft(RhoPlot).alignTopTo(0))
    }

    private fun addRhoGraph(){
        add(RhoPlot.setWidth(RHO_GRAPH_WIDTH).setHeight(RHO_GRAPH_HEIGHT).alignRightTo(1.0).alignTopTo(0))
    }

    private fun addRhoGraphLabel(){
        add(RHO_PLOT_LABEL.alignTopTo(0).alignRightToLeft(RhoPlot))
    }

    private fun addThetaGraph(){
        add(ThetaPlot.setWidth(THETA_GRAPH_WIDTH).setHeight(THETA_GRAPH_HEIGHT).alignBottomTo(1.0).alignRightTo(1.0))
    }

    private fun addThetaGraphLabel(){
        add(THETA_PLOT_LABEL.alignLeftToLeft(ThetaPlot).alignBottomToTop(ThetaPlot))
    }

    private fun addTitle(){
        add(TITLE.setX(MIDDLE_X).setY(0.04))
    }

    private fun addMass(){
        addMassLabel()
        addMassSlider()
        addMassIndicator()
    }

    private fun addMassLabel(){
        add(MASS_LABEL.setX(MIDDLE_X).setY(0.1))
    }

    private fun addMassSlider(){
        MASS_SLIDER.setRange(MINIMAL_MASS, MAXIMAL_MASS).setPrecision(MASS_PRECISION).setValue(DEFAULT_MASS)
        add(MASS_SLIDER.setX(MIDDLE_X).setY(0.15))
    }

    private fun addMassIndicator(){
        MASS_SLIDER.addValueListener { MASS_INDICATOR.setText(MASS_SLIDER.value()) }
        add(MASS_INDICATOR.setText(MASS_SLIDER.value()).setX(MIDDLE_X).setY(0.18))
    }

    private fun addSpringConstant(){
        addSpringConstantLabel()
        addSpringConstantSlider()
        addSpringConstantIndicator()
    }

    private fun addSpringConstantLabel(){
        add(SPRING_CONSTANT_LABEL.setX(MIDDLE_X).setY(0.21))
    }

    private fun addSpringConstantSlider(){
        SPRING_CONSTANT_SLIDER
                .setRange(MINIMAL_SPRING_CONSTANT, MAXIMAL_SPRING_CONSTANT)
                .setPrecision(SPRING_CONSTANT_PRECISION)
                .setValue(DEFAULT_SPRING_CONSTANT)
        add(SPRING_CONSTANT_SLIDER.setX(MIDDLE_X).setY(0.24))
    }

    private fun addSpringConstantIndicator(){
        SPRING_CONSTANT_SLIDER.addValueListener { SPRING_CONSTANT_INDICATOR.setText(SPRING_CONSTANT_SLIDER.value()) }
        add(SPRING_CONSTANT_INDICATOR.setText(SPRING_CONSTANT_SLIDER.value()).setX(MIDDLE_X).setY(0.27))
    }

    private fun addRelaxedLength(){
        addRelaxedLengthLabel()
        addRelaxedLengthSlider()
        addRelaxedLengthIndicator()
    }

    private fun addRelaxedLengthLabel(){
        add(RELAXED_LENGTH_LABEL.setX(MIDDLE_X).setY(0.30))
    }

    private fun addRelaxedLengthSlider(){
        RELAXED_LENGTH_SLIDER
                .setRange(MINIMAL_RELAXED_LENGTH, MAXIMAL_RELAXED_LENGTH)
                .setPrecision(RELAXED_LENGTH_PRECISION)
                .setValue(DEFAULT_RELAXED_LENGTH)
        add(RELAXED_LENGTH_SLIDER.setX(MIDDLE_X).setY(0.33))
    }

    private fun addRelaxedLengthIndicator(){
        RELAXED_LENGTH_SLIDER.addValueListener { RELAXED_LENGTH_INDICATOR.setText(RELAXED_LENGTH_SLIDER.value()) }
        add(RELAXED_LENGTH_INDICATOR.setText(RELAXED_LENGTH_SLIDER.value()).setX(MIDDLE_X).setY(0.36))
    }

    private fun addStartingLength(){
        addStartingLengthLabel()
        addStartingLengthSlider()
        addStartingLengthIndicator()
    }

    private fun addStartingLengthLabel(){
        add(STARTING_LENGTH_LABEL.setX(MIDDLE_X).setY(0.39))
    }

    private fun addStartingLengthSlider(){
        STARTING_LENGTH_SLIDER
                .setRange(MINIMAL_STARTING_LENGTH, MAXIMAL_STARTING_LENGTH)
                .setPrecision(STARTING_LENGTH_PRECISION)
                .setValue(DEFAULT_STARTING_LENGTH)
        add(STARTING_LENGTH_SLIDER.setX(MIDDLE_X).setY(0.42))
    }

    private fun addStartingLengthIndicator(){
        STARTING_LENGTH_SLIDER.addValueListener { STARTING_LENGTH_INDICATOR.setText(STARTING_LENGTH_SLIDER.value()) }
        add(STARTING_LENGTH_INDICATOR.setText(STARTING_LENGTH_SLIDER.value()).setX(MIDDLE_X).setY(0.45))
    }

    private fun addStartingRadialVelocity(){
        addStartingRadialVelocityLabel()
        addStartingRadialVelocitySlider()
        addStartingRadialVelocityIndicator()
    }

    private fun addStartingRadialVelocityLabel(){
        add(STARTING_RHO_DOT_LABEL.setX(MIDDLE_X).setY(0.48))
    }

    private fun addStartingRadialVelocitySlider(){
        STARTING_RHO_DOT_SLIDER
                .setRange(MINIMAL_STARTING_RHO_DOT, MAXIMAL_STARTING_RHO_DOT)
                .setPrecision(STARTING_RHO_DOT_PRECISION)
                .setValue(DEFAULT_STARTING_RHO_DOT)
        add(STARTING_RHO_DOT_SLIDER.setX(MIDDLE_X).setY(0.51))
    }

    private fun addStartingRadialVelocityIndicator(){
        STARTING_RHO_DOT_SLIDER.addValueListener { STARTING_RHO_DOT_INDICATOR.setText(STARTING_RHO_DOT_SLIDER.value()) }
        add(STARTING_RHO_DOT_INDICATOR.setText(STARTING_RHO_DOT_SLIDER.value()).setX(MIDDLE_X).setY(0.54))
    }

    private fun addStartingAngle(){
        addStartingAngleLabel()
        addStartingAngleSlider()
        addStartingAngleIndicator()
    }

    private fun addStartingAngleLabel(){
        add(STARTING_ANGLE_LABEL.setX(MIDDLE_X).setY(0.57))
    }

    private fun addStartingAngleSlider(){
        STARTING_ANGLE_SLIDER
                .setRange(MINIMAL_STARTING_ANGLE, MAXIMAL_STARTING_ANGLE)
                .setPrecision(STARTING_ANGLE_PRECISION)
                .setValue(DEFAULT_STARTING_ANGLE)
        add(STARTING_ANGLE_SLIDER.setX(MIDDLE_X).setY(0.60))
    }

    private fun addStartingAngleIndicator(){
        STARTING_ANGLE_SLIDER.addValueListener { STARTING_ANGLE_INDICATOR.setText(STARTING_ANGLE_SLIDER.value()) }
        add(STARTING_ANGLE_INDICATOR.setText(STARTING_ANGLE_SLIDER.value()).setX(MIDDLE_X).setY(0.63))
    }

    private fun addStartingAngularVelocity(){
        addStartingAngularVelocityLabel()
        addStartingAngularVelocitySlider()
        addStartingAngularVelocityIndicator()
    }

    private fun addStartingAngularVelocityLabel(){
        add(STARTING_THETA_DOT_LABEL.setX(MIDDLE_X).setY(0.66))
    }

    private fun addStartingAngularVelocitySlider(){
        STARTING_THETA_DOT_SLIDER
                .setRange(MINIMAL_STARTING_THETA_DOT, MAXIMAL_STARTING_THETA_DOT)
                .setPrecision(STARTING_THETA_DOT_PRECISION)
                .setValue(DEFAULT_STARTING_THETA_DOT)
        add(STARTING_THETA_DOT_SLIDER.setX(MIDDLE_X).setY(0.69))
    }

    private fun addStartingAngularVelocityIndicator(){
        STARTING_THETA_DOT_SLIDER.addValueListener { STARTING_THETA_DOT_INDICATOR.setText(STARTING_THETA_DOT_SLIDER.value()) }
        add(STARTING_THETA_DOT_INDICATOR.setText(STARTING_THETA_DOT_SLIDER.value()).setX(MIDDLE_X).setY(0.72))
    }

    private fun addGravity(){
        addGravityLabel()
        addGravitySlider()
        addGravityIndicator()
    }

    private fun addGravityLabel(){
        add(GRAVITY_LABEL.setX(MIDDLE_X).setY(0.75))
    }

    private fun addGravitySlider(){
        GRAVITY_SLIDER
                .setRange(MINIMAL_GRAVITY, MAXIMAL_GRAVITY)
                .setPrecision(GRAVITY_PRECISION)
                .setValue(DEFAULT_GRAVITY)
        add(GRAVITY_SLIDER.setX(MIDDLE_X).setY(0.78))
    }

    private fun addGravityIndicator(){
        GRAVITY_SLIDER.addValueListener { GRAVITY_INDICATOR.setText(GRAVITY_SLIDER.value()) }
        add(GRAVITY_INDICATOR.setText(GRAVITY_SLIDER.value()).setX(MIDDLE_X).setY(0.81))
    }

    private fun addFriction(){
        addFrictionLabel()
        addFrictionSlider()
        addFrictionIndicator()
    }

    private fun addFrictionLabel(){
        add(FRICTION_LABEL.setX(MIDDLE_X).setY(0.84))
    }

    private fun addFrictionSlider(){
        FRICTION_SLIDER
                .setRange(MINIMAL_FRICTION, MAXIMAL_FRICTION)
                .setPrecision(FRICTION_PRECISION)
                .setValue(DEFAULT_FRICTION)
        add(FRICTION_SLIDER.setX(MIDDLE_X).setY(0.87))
    }

    private fun addFrictionIndicator(){
        FRICTION_SLIDER.addValueListener { FRICTION_INDICATOR.setText(FRICTION_SLIDER.value()) }
        add(FRICTION_INDICATOR.setText(FRICTION_SLIDER.value()).setX(MIDDLE_X).setY(0.90))
    }

    private fun addReloadButton(){
        add(RELOAD_BUTTON.setX(MIDDLE_X).setY(0.95))
    }

    private fun m() : Double = MASS_SLIDER.value()

    private fun k() : Double = SPRING_CONSTANT_SLIDER.value()

    private fun l() : Double = RELAXED_LENGTH_SLIDER.value()

    private fun rho() : Double = STARTING_LENGTH_SLIDER.value()

    private fun rhoDot() : Double = STARTING_RHO_DOT_SLIDER.value()

    private fun theta() : Double = STARTING_ANGLE_SLIDER.value()

    private fun thetaDot() : Double = STARTING_THETA_DOT_SLIDER.value()

    private fun g() : Double = GRAVITY_SLIDER.value()

    private fun mu() : Double = FRICTION_SLIDER.value()

    private fun reload(){
        SimulationPane.reload(m(), k(), l(), rho(), rhoDot(), theta(), thetaDot(), g(), mu())
        RhoPlot.reset()
        ThetaPlot.reset()
    }

}