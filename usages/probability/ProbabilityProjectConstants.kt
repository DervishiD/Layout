package usages.probability

import layout.Action
import layout.displayers.Label
import layout.displayers.TextArrowSelector
import layout.displayers.TextButton
import layout.frame.LApplication
import layout.frame.LFrame
import layout.frame.LFrameBuilder
import layout.frame.Screen
import layout.utilities.Text
import layout.utilities.randomCosPlusX
import layout.utilities.randomHomogeneous
import kotlin.math.*

val probabilityApplication : LApplication = object : LApplication(){
    override fun run() {
        mainFrame.run()
        randomDistributionFrame.run()
    }
}

val mainFrame : LFrame by lazy {
    LFrameBuilder(mainScreen)
            .exitOnClose()
            .setCenterXCoordinate(0.2)
            .setCenterYCoordinate(0.2)
            .setWidth(0.4)
            .setHeight(0.5)
            .setDecorated(false)
            .setFullScreen(false)
            .isRunningIfHidden(false)
            .build()
}

val randomDistributionFrame : LFrame by lazy{
    LFrameBuilder(randomDistributionScreen)
            .hideOnClose()
            .setFullScreen(false)
            .setDecorated(false)
            .setCenterXCoordinate(0.7)
            .setCenterYCoordinate(0.5)
            .setWidth(0.6)
            .setHeight(1.0)
            .isRunningIfHidden(false)
            .build()
}

class RandomDistributionScreen : Screen(){

    private val exitButton = TextButton(0, 0, "X", { randomDistributionFrame.close()}).alignUpTo(0).alignLeftTo(0)

    override var previousScreen: Screen = this

    private var firstGenerator : () -> Double = {0.0}

    private var secondGenerator : () -> Double = {0.0}

    private val drawRectPoint : Action = {drawPoint3(firstGenerator.invoke(), secondGenerator.invoke())}

    private val drawRadPoint : Action = {
        val angle : Double = 2 * PI * secondGenerator.invoke()
        val distance = firstGenerator.invoke()
        val R = distance * min(width(), height()) / 2
        drawPoint3((width() / 2 + R * cos(angle)).toInt(), (height() / 2 + R * sin(angle)).toInt())
    }

    private var currentGeometryDrawPoint : Action = drawRectPoint

    infix fun setFirstGenerator(generator : () -> Double){
        firstGenerator = generator
    }

    infix fun setSecondGenerator(generator: () -> Double){
        secondGenerator = generator
    }

    fun clear() = clearBackground()

    fun setRectangular(){
        currentGeometryDrawPoint = drawRectPoint
    }

    fun setRadial(){
        currentGeometryDrawPoint = drawRadPoint
    }

    override fun onTimerTick(): Screen {
        currentGeometryDrawPoint.invoke()
        return super.onTimerTick()
    }

    override fun load() {
        add(exitButton)
    }

    override fun save() {
        remove(exitButton)
    }

}

val randomDistributionScreen : RandomDistributionScreen = RandomDistributionScreen()

val mainScreen : Screen = object : Screen(){

    val runButton : TextButton = TextButton(0.5, 0.85, "Run", {
        if(randomDistributionFrame.isHidden()) randomDistributionFrame.setVisible()
        randomDistributionScreen.setFirstGenerator(randomTypeSelector1.selectedOption())
        randomDistributionScreen.setSecondGenerator(randomTypeSelector2.selectedOption())
        if(radialRectSwitch.selectedOption() == RECTANGULAR){
            randomDistributionScreen.setRectangular()
        }else{
            randomDistributionScreen.setRadial()
        }
        randomDistributionScreen.clear()
    })

    val RECTANGULAR : Int = 0
    val RADIAL : Int = 1

    val rectFirst : String = "x : "
    val rectSecond : String = "y : "
    val radFirst : String = "ρ : "
    val radSecond : String = "φ : "

    val firstCoordinate : Label = Label(0.2, 0.5, rectFirst)
    val secondCoordinate : Label = Label(0.2, 0.6, rectSecond)

    val randomTypes : Map<Text, () -> Double> = mapOf(
            Text("Homogeneous") to {randomHomogeneous()},
            Text("cosx + x") to {randomCosPlusX()}
    )

    @Suppress("UNCHECKED_CAST")
    val randomTypeSelector1 = TextArrowSelector(0.33, 0.5, randomTypes).alignLeftToRight(firstCoordinate, 10) as TextArrowSelector<()->Double>

    @Suppress("UNCHECKED_CAST")
    val randomTypeSelector2 = TextArrowSelector(0.33, 0.6, randomTypes).alignLeftToRight(secondCoordinate, 10) as TextArrowSelector<()->Double>

    val radialRectMap : Map<Text, Int> = mapOf(Text("Rectangular") to RECTANGULAR, Text("Radial") to RADIAL)

    val radialRectSwitch : TextArrowSelector<Int> = TextArrowSelector(0.5, 0.3, radialRectMap).also{
        it.addSelectionListener{
            if(it.selectedOption() == RECTANGULAR){
                firstCoordinate.setDisplayedText(rectFirst)
                secondCoordinate.setDisplayedText(rectSecond)
            }else{
                firstCoordinate.setDisplayedText(radFirst)
                secondCoordinate.setDisplayedText(radSecond)
            }
        }
    }

    val title : Label = Label(0.5, 0.1, "Random number generator")

    val exitButton = TextButton(0, 0, "X", {mainFrame.close()}).alignUpTo(0).alignLeftTo(0)

    override var previousScreen: Screen = this

    override fun load() {
        add(title)
        add(runButton)
        add(firstCoordinate)
        add(secondCoordinate)
        add(radialRectSwitch)
        add(randomTypeSelector1)
        add(randomTypeSelector2)
        add(exitButton)
    }

    override fun save() {
        remove(title)
        remove(runButton)
        remove(firstCoordinate)
        remove(secondCoordinate)
        remove(radialRectSwitch)
        remove(randomTypeSelector1)
        remove(randomTypeSelector2)
        remove(exitButton)
    }

}
