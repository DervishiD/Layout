package debug

import llayout.displayers.*
import llayout.frame.*
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
    val hds : HorizontalDoubleSlider =
            HorizontalDoubleSlider(0.7, 0.8, 0.3, 30)
                    .setMinimum(0)
                    .setMaximum(5)
                    .setPrecision(0.5)
                    as HorizontalDoubleSlider
    val vds : VerticalDoubleSlider =
            VerticalDoubleSlider(0.5, 0.25, 30, 300)
                    .setMinimum(0)
                    .setMaximum(5)
                    .setPrecision(0.5)
                    as VerticalDoubleSlider
    val s : Switch = Switch(0, 0, 45, 45).alignUpTo(0).alignRightTo(1.0) as Switch

    init{
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
        add(hds)
        add(vds)
        add(s)
    }

    override fun keyTyped(e: KeyEvent?) {
        f.type(e!!)
    }

    override fun keyPressed(e: KeyEvent?) {
        f.type(e!!)
    }

}

val frame : LFrame = LFrame(testScreen)

val testApplication : LApplication = object : LApplication(){
    override fun run() {
        frame.run()
    }
}
