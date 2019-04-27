package debug

import llayout.displayers.Label
import llayout.displayers.TextButton
import llayout.frame.LApplication
import llayout.frame.LFrame
import llayout.frame.LFrameBuilder
import llayout.frame.LScene

val testScreen : LScene = object : LScene(){

    val b : TextButton = TextButton(0.5, 0.5, "Button", {}).also{ it.onMouseRelease = {it.moveAlong(-5, 5)} }
    val l : Label = Label(0, 0, "Label").also{it.alignUpToDown(b).alignRightToRight(b)}
    val l2 = Label(0, 0, "Label 2").alignLeftTo(0).alignUpTo(0)

    override fun load() {
        add(l)
        add(b)
        add(l2)
    }

    override fun save() {
        remove(b)
        remove(l)
        remove(l2)
    }
}

val frame : LFrame = LFrameBuilder(testScreen).exitOnClose().setFullScreen(true).setDecorated(false).build()

val testApplication : LApplication = object : LApplication(){
    override fun run() {
        frame.run()
    }
}
