package usages.probability

import layout.Action
import layout.displayers.Label
import layout.displayers.TextArrowSelector
import layout.displayers.TextButton
import layout.frame.LApplication
import layout.frame.Screen
import layout.utilities.Text

val probabilityApplication : LApplication = object : LApplication(){
    override fun run() {
        TODO("Not implemented.")
    }
}

val mainScreen : Screen = object : Screen(){

    val runButton : TextButton = TextButton(2.0/3, 0.5, "Run", {TODO("Not implemented.")})

    val randomTypes : Map<Text, Action> = mapOf()

    val randomTypeSelector : TextArrowSelector<Action> = TextArrowSelector(1.0/3, 0.5, randomTypes)

    val title : Label = Label(0.5, 0.2, "Random number generator")

    override var previousScreen: Screen = this

    override fun load() {
        add(title)
        add(runButton)
        add(randomTypeSelector)
    }

    override fun save() {
        remove(title)
        remove(runButton)
        remove(randomTypeSelector)
    }

}
