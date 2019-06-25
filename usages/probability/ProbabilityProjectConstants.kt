package usages.probability

import llayout4.Action
import llayout4.displayers.*
import llayout4.frame.*
import llayout4.utilities.*
import kotlin.math.*

val probabilityApplication : LApplication = LApplication {
    mainFrame.run()
    randomDistributionFrame.run()
}

object RandomDistributionScreen : LScene(){

    private val exitButton = TextButton("X", { randomDistributionFrame.close()}).alignTopTo(0).alignLeftTo(0)

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

    init{
        add(exitButton)
    }

    fun setFirstGenerator(generator : () -> Double){
        firstGenerator = generator
    }

    fun setSecondGenerator(generator: () -> Double){
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

}

object MainScreen : LScene(){

    val runButton : TextButton = TextButton("Run") {
        if(randomDistributionFrame.isHidden()) randomDistributionFrame.setVisible()
        RandomDistributionScreen.setFirstGenerator(randomTypeSelector1.selectedOption())
        RandomDistributionScreen.setSecondGenerator(randomTypeSelector2.selectedOption())
        if(radialRectSwitch.selectedOption() == RECTANGULAR){
            RandomDistributionScreen.setRectangular()
        }else{
            RandomDistributionScreen.setRadial()
        }
        RandomDistributionScreen.clear()
    }.setX(0.5).setY(0.85) as TextButton

    val RECTANGULAR : Int = 0
    val RADIAL : Int = 1

    val rectFirst : String = "x :"
    val rectSecond : String = "y :"
    val radFirst : String = "ρ :"
    val radSecond : String = "φ :"

    val firstCoordinate : Label = Label(rectFirst).setX(0.2).setY(0.5) as Label
    val secondCoordinate : Label = Label(rectSecond).setX(0.2).setY(0.6) as Label

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
    val randomTypeSelector1 = TextArrowSelector(randomTypes)
            .setY(0.5).alignLeftToRight(firstCoordinate, 10) as TextArrowSelector<()->Double>

    @Suppress("UNCHECKED_CAST")
    val randomTypeSelector2 = TextArrowSelector(randomTypes)
            .setY(0.6).alignLeftToRight(secondCoordinate, 10) as TextArrowSelector<()->Double>

    val radialRectMap : Map<Text, Int> = mapOf(Text("Rectangular") to RECTANGULAR, Text("Radial") to RADIAL)

    val radialRectSwitch : TextArrowSelector<Int> = TextArrowSelector(radialRectMap).also{
        it.addSelectionListener{
            if(it.selectedOption() == RECTANGULAR){
                firstCoordinate.setText(rectFirst)
                secondCoordinate.setText(rectSecond)
            }else{
                firstCoordinate.setText(radFirst)
                secondCoordinate.setText(radSecond)
            }
        }
        it.setX(0.5)
        it.setY(0.3)
    }

    val title : Label = Label("Random number generator").setX(0.5).setY(0.1) as Label

    val exitButton = TextButton("X", {mainFrame.close()}).alignTopTo(0).alignLeftTo(0)

    init{
        add(title)
        add(runButton)
        add(firstCoordinate)
        add(secondCoordinate)
        add(radialRectSwitch)
        add(randomTypeSelector1)
        add(randomTypeSelector2)
        add(exitButton)
    }

}

val mainFrame : LFrame = LFrame(MainScreen).setX(0.2).setY(0.2).setWidth(0.4).setHeight(0.5).setUndecorated()

val randomDistributionFrame : LFrame = LFrame(RandomDistributionScreen).setUndecorated().setX(0.7).setY(0.5).setWidth(0.6).setHeight(1.0)
