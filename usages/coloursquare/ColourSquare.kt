package usages.coloursquare

import llayout.displayers.CanvasDisplayer
import llayout.displayers.TextButton
import llayout.frame.*
import java.awt.Color
import java.awt.Graphics
import kotlin.random.Random

val colourSquareApplication : LApplication = LApplication { frame.run() }

val screen : LScene = object : LScene(){

    val exitButton = TextButton(0, 0, "X", {frame.close()}).alignLeftTo(0).alignUpTo(0)

    val colourStep : Int = 5

    var colour : Color = Color(125, 125, 125)

    val variableSquare : CanvasDisplayer = CanvasDisplayer(0.5, 0.33, 0.25, 0.25).addGraphicAction{
        g : Graphics, w : Int, h : Int -> run{
            g.color = colour
            g.fillRect(0, 0, w, h)
        }
    } as CanvasDisplayer

    val constantSquare : CanvasDisplayer = CanvasDisplayer(0.5, 0.66, 0.25, 0.25).addGraphicAction{
        g : Graphics, w : Int, h : Int -> run{
            g.color = if(following) colour else fixed
            g.fillRect(0, 0, w, h)
        }
    } as CanvasDisplayer

    var following : Boolean = true
    var fixed : Color = colour

    val toggleButton : TextButton = TextButton(0.75, 0.5, "Toggle", {
        following = !following
        fixed = colour
    })

    override fun load(){
        add(exitButton)
        add(variableSquare)
        add(constantSquare)
        add(toggleButton)
    }

    override fun save(){
        remove(exitButton)
        remove(variableSquare)
        remove(constantSquare)
        remove(toggleButton)
    }

    override fun onTimerTick(): LScene = addRandomColourStep()

    private fun addRandomColourStep() : LScene{
        when(Random.nextInt(0, 6)){
            0 -> addRed()
            1 -> subtractRed()
            2 -> addGreen()
            3 -> subtractGreen()
            4 -> addBlue()
            5 -> subtractBlue()
        }
        return this
    }

    private fun addRed(){
        if(colour.red <= 255 - colourStep) colour = Color(colour.red + colourStep, colour.green, colour.blue)
    }

    private fun subtractRed(){
        if(colour.red >= colourStep) colour = Color(colour.red - colourStep, colour.green, colour.blue)
    }

    private fun addGreen(){
        if(colour.green <= 255 - colourStep) colour = Color(colour.red, colour.green + colourStep, colour.blue)
    }

    private fun subtractGreen(){
        if(colour.green >= colourStep) colour = Color(colour.red, colour.green - colourStep, colour.blue)
    }

    private fun addBlue(){
        if(colour.blue <= 255 - colourStep) colour = Color(colour.red, colour.green, colour.blue + colourStep)
    }

    private fun subtractBlue(){
        if(colour.blue >= colourStep) colour = Color(colour.red, colour.green, colour.blue - colourStep)
    }

}

val frame : LFrame = LFrame(screen)
