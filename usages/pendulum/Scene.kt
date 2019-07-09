package usages.pendulum

import llayout6.DEFAULT_LARGE_FONT
import llayout6.displayers.HorizontalDoubleSlider
import llayout6.displayers.Label
import llayout6.displayers.TextButton
import llayout6.frame.LScene
import llayout6.utilities.StringDisplay
import kotlin.math.PI

internal object Scene : LScene() {

    private const val PANE_WIDTH : Double = 0.75
    
    private const val CENTRAL_X : Double = (1.0 - PANE_WIDTH) / 2

    private const val PARAMETERS_ZONE_WIDTH : Double = 1.0 - PANE_WIDTH

    private const val SLIDER_WIDTH : Double = PARAMETERS_ZONE_WIDTH - 0.05

    private const val SLIDER_HEIGHT : Double = 0.05

    private const val MINIMAL_ANGLE : Double = - PI

    private const val MAXIMAL_ANGLE : Double = PI

    private const val DEFAULT_STARTING_ANGLE : Double = 0.0

    private const val ANGLE_PRECISION : Double = 0.0

    private const val MINIMAL_SPEED : Double = -5.0

    private const val MAXIMAL_SPEED : Double = 5.0

    private const val DEFAULT_STARTING_SPEED : Double = 0.0

    private const val SPEED_PRECISION : Double = 0.1

    private const val MINIMAL_GRAVITY : Double = 0.0

    private const val MAXIMAL_GRAVITY : Double = 15.0

    private const val GRAVITY_PRECISION : Double = 0.1

    private const val DEFAULT_GRAVITY : Double = 9.8

    private const val MINIMAL_LENGTH : Double = 0.1

    internal const val MAXIMAL_LENGTH : Double = 1.0

    private const val LENGTH_PRECISION : Double = 0.05

    private const val DEFAULT_LENGTH : Double = 0.5

    private const val MINIMAL_FRICTION : Double = 0.0

    private const val MAXIMAL_FRICTION : Double = 1.0

    private const val FRICTION_PRECISION : Double = 0.05

    private const val DEFAULT_FRICTION : Double = 0.0

    private val TITLE : Label = Label(StringDisplay("Pendulum", DEFAULT_LARGE_FONT))

    private val RELOAD_BUTTON : TextButton = TextButton("Reload simulation") { reload() }

    private val STARTING_ANGLE_LABEL : Label = Label("Starting angle")

    private val STARTING_ANGLE_SLIDER : HorizontalDoubleSlider = HorizontalDoubleSlider(SLIDER_WIDTH, SLIDER_HEIGHT)

    private val STARTING_ANGLE_INDICATOR : Label = Label()

    private val GRAVITY_LABEL : Label = Label("Gravity")

    private val GRAVITY_SLIDER : HorizontalDoubleSlider = HorizontalDoubleSlider(SLIDER_WIDTH, SLIDER_HEIGHT)

    private val GRAVITY_INDICATOR : Label = Label()

    private val LENGTH_LABEL : Label = Label("Length")

    private val LENGTH_SLIDER : HorizontalDoubleSlider = HorizontalDoubleSlider(SLIDER_WIDTH, SLIDER_HEIGHT)

    private val LENGTH_INDICATOR : Label = Label()

    private val FRICTION_LABEL : Label = Label("Friction")

    private val FRICTION_SLIDER : HorizontalDoubleSlider = HorizontalDoubleSlider(SLIDER_WIDTH, SLIDER_HEIGHT)

    private val FRICTION_INDICATOR : Label = Label()

    private val STARTING_SPEED_LABEL : Label = Label("Starting speed")

    private val STARTING_SPEED_SLIDER : HorizontalDoubleSlider = HorizontalDoubleSlider(SLIDER_WIDTH, SLIDER_HEIGHT)

    private val STARTING_SPEED_INDICATOR : Label = Label()

    init{
        addComponents()
    }
    
    private fun addComponents(){
        addTitle()
        addPane()
        addParametersInterface()
        addReloadButton()
    }
    
    private fun addTitle(){
        add(TITLE.setX(CENTRAL_X).setY(0.10))
    }
    
    private fun addPane(){
        add(SimulationPane.setWidth(PANE_WIDTH).setHeight(1.0).alignRightTo(1.0).alignTopTo(0))
    }
    
    private fun addParametersInterface(){
        addStartingAngleParameters()
        addStartingSpeedParameters()
        addGravityParameters()
        addLengthParameters()
        addFrictionParameters()
    }
    
    private fun addStartingAngleParameters(){
        addStartingAngleLabel()
        addStartingAngleSlider()
        addStartingAngleIndicator()
    }

    private fun addStartingSpeedParameters(){
        addStartingSpeedLabel()
        addStartingSpeedSlider()
        addStartingSpeedIndicator()
    }

    private fun addGravityParameters(){
        addGravityLabel()
        addGravitySlider()
        addGravityIndicator()
    }

    private fun addLengthParameters(){
        addLengthLabel()
        addLengthSlider()
        addLengthIndicator()
    }

    private fun addFrictionParameters(){
        addFrictionLabel()
        addFrictionSlider()
        addFrictionIndicator()
    }

    private fun addStartingAngleLabel(){
        add(STARTING_ANGLE_LABEL.setX(CENTRAL_X).setY(0.15))
    }

    private fun addStartingAngleSlider(){
        add(STARTING_ANGLE_SLIDER.setMinimum(MINIMAL_ANGLE).setMaximum(MAXIMAL_ANGLE).setPrecision(ANGLE_PRECISION).setX(CENTRAL_X).setY(0.20))
        STARTING_ANGLE_SLIDER.setStartingValue(DEFAULT_STARTING_ANGLE)
    }

    private fun addStartingAngleIndicator(){
        STARTING_ANGLE_SLIDER.addValueListener { STARTING_ANGLE_INDICATOR.setText(STARTING_ANGLE_SLIDER.value()) }
        add(STARTING_ANGLE_INDICATOR.setText(STARTING_ANGLE_SLIDER.value()).setX(CENTRAL_X).setY(0.25))
    }

    private fun addStartingSpeedLabel(){
        add(STARTING_SPEED_LABEL.setX(CENTRAL_X).setY(0.30))
    }

    private fun addStartingSpeedSlider(){
        add(STARTING_SPEED_SLIDER.setMinimum(MINIMAL_SPEED).setMaximum(MAXIMAL_SPEED).setPrecision(SPEED_PRECISION).setX(CENTRAL_X).setY(0.35))
        STARTING_SPEED_SLIDER.setStartingValue(DEFAULT_STARTING_SPEED)
    }

    private fun addStartingSpeedIndicator(){
        STARTING_SPEED_SLIDER.addValueListener { STARTING_SPEED_INDICATOR.setText(STARTING_SPEED_SLIDER.value()) }
        add(STARTING_SPEED_INDICATOR.setText(STARTING_SPEED_SLIDER.value()).setX(CENTRAL_X).setY(0.40))
    }

    private fun addGravityLabel(){
        add(GRAVITY_LABEL.setX(CENTRAL_X).setY(0.45))
    }

    private fun addGravitySlider(){
        add(GRAVITY_SLIDER.setMinimum(MINIMAL_GRAVITY).setMaximum(MAXIMAL_GRAVITY).setPrecision(GRAVITY_PRECISION).setX(CENTRAL_X).setY(0.50))
        GRAVITY_SLIDER.setStartingValue(DEFAULT_GRAVITY)
    }

    private fun addGravityIndicator(){
        GRAVITY_SLIDER.addValueListener { GRAVITY_INDICATOR.setText(GRAVITY_SLIDER.value()) }
        add(GRAVITY_INDICATOR.setText(GRAVITY_SLIDER.value()).setX(CENTRAL_X).setY(0.55))
    }

    private fun addLengthLabel(){
        add(LENGTH_LABEL.setX(CENTRAL_X).setY(0.60))
    }

    private fun addLengthSlider(){
        add(LENGTH_SLIDER.setMinimum(MINIMAL_LENGTH).setMaximum(MAXIMAL_LENGTH).setPrecision(LENGTH_PRECISION).setX(CENTRAL_X).setY(0.65))
        LENGTH_SLIDER.setStartingValue(DEFAULT_LENGTH)
    }

    private fun addLengthIndicator(){
        LENGTH_SLIDER.addValueListener { LENGTH_INDICATOR.setText(LENGTH_SLIDER.value()) }
        add(LENGTH_INDICATOR.setText(LENGTH_SLIDER.value()).setX(CENTRAL_X).setY(0.70))
    }

    private fun addFrictionLabel(){
        add(FRICTION_LABEL.setX(CENTRAL_X).setY(0.75))
    }

    private fun addFrictionSlider(){
        add(FRICTION_SLIDER.setMinimum(MINIMAL_FRICTION).setMaximum(MAXIMAL_FRICTION).setPrecision(FRICTION_PRECISION).setX(CENTRAL_X).setY(0.80))
        FRICTION_SLIDER.setStartingValue(DEFAULT_FRICTION)
    }

    private fun addFrictionIndicator(){
        FRICTION_SLIDER.addValueListener { FRICTION_INDICATOR.setText(FRICTION_SLIDER.value()) }
        add(FRICTION_INDICATOR.setText(FRICTION_SLIDER.value()).setX(CENTRAL_X).setY(0.85))
    }

    private fun addReloadButton(){
        add(RELOAD_BUTTON.setX(CENTRAL_X).setY(0.90))
    }

    private fun theta() : Double = STARTING_ANGLE_SLIDER.value()

    private fun g() : Double = GRAVITY_SLIDER.value()

    private fun l() : Double = LENGTH_SLIDER.value()

    private fun mu() : Double = FRICTION_SLIDER.value()

    private fun thetaDot() : Double = STARTING_SPEED_SLIDER.value()

    private fun reload(){
        SimulationPane.restart(theta(), thetaDot(), g(), l(), mu())
    }
    
}