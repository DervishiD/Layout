package main

import java.awt.Toolkit
import javax.swing.JFrame

public val FRAMEX : Int = (Toolkit.getDefaultToolkit().screenSize.getWidth()).toInt()
public val FRAMEY : Int = (Toolkit.getDefaultToolkit().screenSize.getHeight()).toInt()

public val mainFrame : JFrame = JFrame()


