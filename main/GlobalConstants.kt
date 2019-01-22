package main

import java.awt.Toolkit
import javax.swing.JFrame

public val FRAMEX : Int = (Toolkit.getDefaultToolkit().screenSize.getWidth()).toInt()
public val FRAMEY : Int = (Toolkit.getDefaultToolkit().screenSize.getHeight()).toInt()

public val mainFrame : JFrame = JFrame()

public const val DOUBLE_PRECISION : Double = 10e-2
