package main

import display.frame.LFrame
import display.frame.LFrameBuilder
import display.mainMenuScreen
import java.awt.Graphics

typealias Action = () -> Unit
typealias GraphicAction = (Graphics, Int, Int) -> Unit
typealias MouseWheelAction = (Int) -> Unit

val mainFrame : LFrame by lazy{ LFrameBuilder(mainMenuScreen).exitOnClose().setDecorated(false).setFullScreen(true).build()}

const val DOUBLE_PRECISION : Double = 10e-2