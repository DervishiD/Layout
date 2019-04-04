package main

import display.frame.LFrame
import display.frame.LFrameBuilder
import display.mainMenuScreen
import java.awt.Graphics
import java.awt.Toolkit
import javax.swing.JFrame

typealias Action = () -> Unit
typealias GraphicAction = (Graphics, Int, Int) -> Unit
typealias MouseWheelAction = (Int) -> Unit

val FRAMEX : Int = (Toolkit.getDefaultToolkit().screenSize.getWidth()).toInt()
val FRAMEY : Int = (Toolkit.getDefaultToolkit().screenSize.getHeight()).toInt()

val mainFrame : LFrame by lazy{ LFrameBuilder(mainMenuScreen).exitOnClose().setDecorated(false).setFullScreen(true).build()}

const val DOUBLE_PRECISION : Double = 10e-2