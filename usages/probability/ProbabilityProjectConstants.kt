package usages.probability

import layout.displayers.Label
import layout.displayers.TextArrowSelector
import layout.displayers.TextButton
import layout.frame.LApplication
import layout.frame.LFrame
import layout.frame.LFrameBuilder
import layout.frame.Screen
import layout.utilities.Text
import layout.utilities.randomHomogeneous

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
            .setHeight(0.4)
            .setDecorated(false)
            .setFullScreen(false)
            .isRunningIfHidden(false)
            .build()
}

val randomDistributionFrame : LFrame by lazy{
    LFrameBuilder(randomDistributionTestScreen)
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

class RandomDistributionTestScreen : Screen(){

    val exitButton = TextButton(0, 0, "X", { randomDistributionFrame.close()}).alignUpTo(0).alignLeftTo(0)

    override var previousScreen: Screen = this

    private var generator : () -> Double = {0.0}

    infix fun setGenerator(generator : () -> Double){
        this.generator = generator
        clearBackground()
    }

    override fun onTimerTick(): Screen {
        drawPoint3(generator.invoke(), generator.invoke())
        return super.onTimerTick()
    }

    override fun load() {
        add(exitButton)
    }

    override fun save() {
        remove(exitButton)
    }

}

val randomDistributionTestScreen : RandomDistributionTestScreen = RandomDistributionTestScreen()

val mainScreen : Screen = object : Screen(){

    val runButton : TextButton = TextButton(2.0/3, 0.5, "Run", {
        if(randomDistributionFrame.isHidden()) randomDistributionFrame.setVisible()
        randomDistributionTestScreen.setGenerator(randomTypeSelector.selectedOption())
    })

    val randomTypes : Map<Text, () -> Double> = mapOf(Text("Homogeneous") to {randomHomogeneous(0, 1)})

    val randomTypeSelector : TextArrowSelector<() -> Double> = TextArrowSelector(1.0/3, 0.5, randomTypes)

    val title : Label = Label(0.5, 0.2, "Random number generator")

    val exitButton = TextButton(0, 0, "X", {mainFrame.close()}).alignUpTo(0).alignLeftTo(0)

    override var previousScreen: Screen = this

    override fun load() {
        add(title)
        add(runButton)
        add(randomTypeSelector)
        add(exitButton)
    }

    override fun save() {
        remove(title)
        remove(runButton)
        remove(randomTypeSelector)
        remove(exitButton)
    }

}
