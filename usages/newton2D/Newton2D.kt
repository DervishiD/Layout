package usages.newton2D

import llayout.frame.LApplication
import llayout.frame.LFrame
import llayout.frame.LFrameBuilder

val newton2DApplication : LApplication = LApplication{ mainFrame.run() }

private val mainFrame : LFrame = LFrameBuilder(mainScene).build()
