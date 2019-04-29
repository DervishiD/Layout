package debug

import llayout.displayers.Label
import llayout.displayers.TextButton
import llayout.displayers.TextField
import llayout.frame.LApplication
import llayout.frame.LFrame
import llayout.frame.LFrameBuilder
import llayout.frame.LScene
import java.awt.event.KeyEvent

val testScreen : LScene = object : LScene(){

    val b : TextButton = TextButton(0.5, 0.5, "Button", {}).also{ it.onMouseRelease = {it.moveAlong(-5, 5)} }
    val l : Label = Label(0, 0, "Label").also{it.alignUpToDown(b).alignRightToRight(b)}
    val l2 = Label(0, 0, "Label 2").alignLeftTo(0).alignUpTo(0)
    val f : TextField = TextField(0, 0.5).alignLeftTo(0) as TextField

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
    }

    override fun save() {
        remove(b)
        remove(l)
        remove(l2)
        remove(f)
    }
}

val frame : LFrame = LFrameBuilder(testScreen).exitOnClose().setFullScreen(true).setDecorated(false).build()

val testApplication : LApplication = object : LApplication(){
    override fun run() {
        frame.run()
    }
}
