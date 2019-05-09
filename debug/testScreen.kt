package debug

import llayout.displayers.*
import llayout.frame.LApplication
import llayout.frame.LFrame
import llayout.frame.LFrameBuilder
import llayout.frame.LScene
import llayout.utilities.StringDisplay
import java.awt.Color.RED
import java.awt.event.KeyEvent

val testScreen : LScene = object : LScene(){

    val b : TextButton = TextButton(0.5, 0.6, "Button", {}).also{ it.onMouseRelease = {it.moveAlong(-5, 5)} }
    val l : Label = Label(0, 0, "Label").also{it.alignUpToDown(b).alignRightToRight(b)}
    val l2 = Label(0, 0, "Label 2").alignLeftTo(0).alignUpTo(0)
    val f : TextField = TextField(0, 0.5, 0.5).alignLeftTo(0) as TextField
    val tsp : TextScrollPane = TextScrollPane(0.8, 0.5, 0.4, 0.4)
    val csp : ConsoleScrollPane = ConsoleScrollPane(0, 0, 0.4, 0.4)

    override fun keyTyped(e: KeyEvent?) {
        f.type(e!!)
    }

    override fun keyPressed(e: KeyEvent?) {
        f.type(e!!)
    }

    override fun load() {
        add(l)
        add(b)
        add(l2)
        add(f)
        tsp.write(25)
        tsp.writeln(122)
        tsp.writeln(0.5)
        tsp.write(" apples")
        tsp.writeln(StringDisplay("Hello there", RED))
        tsp.writeln("override fun key typed e KeyEvent f type e override fun load add l add b add l2 add f " +
                "tsp write 25 tsp writeln 122 tsp writeln 0.5 tsp write apples tsp writeln stringdisplay hello there red" +
                "tsp writeln override fun keytyped e keyevent f type e override fun load add l "+
                "tsp write 25 tsp writeln 122 tsp writeln 0.5 tsp write apples tsp writeln stringdisplay hello there red" +
                "tsp writeln override fun keytyped e keyevent f type e override fun load add l "+
                "tsp write 25 tsp writeln 122 tsp writeln 0.5 tsp write apples tsp writeln stringdisplay hello there red" +
                "tsp writeln override fun keytyped e keyevent f type e override fun load add l "+
                "tsp write 25 tsp writeln 122 tsp writeln 0.5 tsp write apples tsp writeln stringdisplay hello there red" +
                "tsp writeln override fun keytyped e keyevent f type e override fun load add l "+
                "tsp write 25 tsp writeln 122 tsp writeln 0.5 tsp write apples tsp writeln stringdisplay hello there red" +
                "tsp writeln override fun keytyped e keyevent f type e override fun load add l ")
        add(tsp)
        csp.alignLeftTo(0)
        csp.alignDownTo(1.0)
        csp.write(1)
        csp.writeln("Now comes the time to finally know if, after about four minutes of a small edit, I can resize that scroll pane properly.")
        add(csp)
    }

    override fun save() {
        remove(b)
        remove(l)
        remove(l2)
        remove(f)
        remove(tsp)
    }

}

val frame : LFrame = LFrameBuilder(testScreen).build()

val testApplication : LApplication = object : LApplication(){
    override fun run() {
        frame.run()
    }
}
