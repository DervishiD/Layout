package debug

import llayout.displayers.*
import llayout.frame.*
import llayout.utilities.StringDisplay
import java.awt.Color.RED
import java.awt.event.KeyEvent
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent

private val testScreen : LScene = object : LScene(){

    private val b : TextButton = TextButton(0.5, 0.6, "Button", {})
    private val l : Label = Label(0, 0, "Label").also{it.alignUpToDown(b).alignRightToRight(b)}
    private val l2 = Label(0, 0, "Label 2").alignLeftTo(0).alignUpTo(0)
    private val f : TextField = TextField(0, 0.5, 0.5).alignLeftTo(0) as TextField
    private val tsp : TextScrollPane = TextScrollPane(0.8, 0.5, 0.4, 0.4)
    private val csp : ConsoleScrollPane = ConsoleScrollPane(0, 0, 0.4, 0.4)
    private val hds : HorizontalDoubleSlider =
            HorizontalDoubleSlider(0.7, 0.8, 0.3, 30)
                    .setMinimum(0)
                    .setMaximum(5)
                    .setPrecision(0.5)
                    as HorizontalDoubleSlider
    private val vds : VerticalDoubleSlider =
            VerticalDoubleSlider(0.5, 0.25, 30, 250)
                    .setMinimum(0)
                    .setMaximum(5)
                    .setPrecision(0.5)
                    as VerticalDoubleSlider
    private val s : Switch = Switch(0, 0, 45, 45).alignUpTo(0).alignRightTo(1.0) as Switch
    private val dc : DoubleCursor = DoubleCursor(0, 0, 0.2, 0.3)
                    .setMinimalXValue(-2)
                    .setMaximalXValue(2)
                    .setMinimalYValue(0)
                    .setMaximalYValue(5)
                    .setXYPrecision(0.25)
                    .alignUpTo(0)
                    .alignLeftTo(0.1) as DoubleCursor

    init{
        b.addMouseListener(object : MouseAdapter(){
            override fun mouseReleased(e: MouseEvent?) {
                b.moveAlong(-5, 5)
            }
        })
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
        add(dc)
        setOnKeyPressedAction { e -> f.type(e.keyCode) }
        setOnKeyTypedAction { e -> f.type(e.keyChar) }
    }

}

private val frame : LFrame = LFrame(testScreen)

val testApplication : LApplication = LApplication{ frame.run() }
