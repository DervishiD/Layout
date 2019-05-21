package usages.probability

import llayout.Action
import llayout.displayers.Label
import llayout.displayers.TextArrowSelector
import llayout.displayers.TextButton
import llayout.frame.*
import llayout.utilities.*
import kotlin.math.*

val probabilityApplication : LApplication = LApplication {
    mainFrame.run()
    randomDistributionFrame.run()
}

class RandomDistributionLScene : LScene(){

    private val exitButton = TextButton("X", { randomDistributionFrame.close()}).alignUpTo(0).alignLeftTo(0)

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

    override fun onTimerTick() {
        currentGeometryDrawPoint.invoke()
    }

    override fun load() {
        add(exitButton)
    }

    override fun save() {
        remove(exitButton)
    }

}

val randomDistributionScreen : RandomDistributionLScene = RandomDistributionLScene()

val MAIN_L_SCENE : LScene = object : LScene(){

    val runButton : TextButton = TextButton("Run") {
        if(randomDistributionFrame.isHidden()) randomDistributionFrame.setVisible()
        randomDistributionScreen.setFirstGenerator(randomTypeSelector1.selectedOption())
        randomDistributionScreen.setSecondGenerator(randomTypeSelector2.selectedOption())
        if(radialRectSwitch.selectedOption() == RECTANGULAR){
            randomDistributionScreen.setRectangular()
        }else{
            randomDistributionScreen.setRadial()
        }
        randomDistributionScreen.clear()
    }.setCenterX(0.5).setCenterY(0.85) as TextButton

    val RECTANGULAR : Int = 0
    val RADIAL : Int = 1

    val rectFirst : String = "x :"
    val rectSecond : String = "y :"
    val radFirst : String = "ρ :"
    val radSecond : String = "φ :"

    val firstCoordinate : Label = Label(rectFirst).setCenterX(0.2).setCenterY(0.5) as Label
    val secondCoordinate : Label = Label(rectSecond).setCenterX(0.2).setCenterY(0.6) as Label

    val randomTypes : Map<Text, () -> Double> = mapOf(
            Text("Homogeneous") to { randomHomogeneous() },
            Text("cosx + x") to { randomCosPlusX() },
            Text("exponential") to { randomExponential() },
            Text("arcsin") to { randomArcsin() },
            Text("root") to { randomRoot() },
            Text("ln") to { randomln() },
            Text("square") to { randomSquare() },
            Text("cube") to { randomCube() },
            Text("fourth") to { randomFourth() }
    )

    @Suppress("UNCHECKED_CAST")
    val randomTypeSelector1 = TextArrowSelector(randomTypes).setCenterY(0.5).alignLeftToRight(firstCoordinate, 10) as TextArrowSelector<()->Double>

    @Suppress("UNCHECKED_CAST")
    val randomTypeSelector2 = TextArrowSelector(randomTypes).setCenterY(0.6).alignLeftToRight(secondCoordinate, 10) as TextArrowSelector<()->Double>

    val radialRectMap : Map<Text, Int> = mapOf(Text("Rectangular") to RECTANGULAR, Text("Radial") to RADIAL)

    val radialRectSwitch : TextArrowSelector<Int> = TextArrowSelector(radialRectMap).also{
        it.addSelectionListener{
            if(it.selectedOption() == RECTANGULAR){
                firstCoordinate.setDisplayedText(rectFirst)
                secondCoordinate.setDisplayedText(rectSecond)
            }else{
                firstCoordinate.setDisplayedText(radFirst)
                secondCoordinate.setDisplayedText(radSecond)
            }
        }
        it.setCenterX(0.5)
        it.setCenterY(0.3)
    }

    val title : Label = Label("Random number generator").setCenterX(0.5).setCenterY(0.1) as Label

    val exitButton = TextButton("X", {mainFrame.close()}).alignUpTo(0).alignLeftTo(0)

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

val mainFrame : LFrame = LFrame(MAIN_L_SCENE).setX(0.2).setY(0.2).setWidth(0.4).setHeight(0.5).setUndecorated()

val randomDistributionFrame : LFrame = LFrame(randomDistributionScreen).setUndecorated().setX(0.7).setY(0.5).setWidth(0.6).setHeight(1.0)
