package usages.coloursquare

import llayout.displayers.CanvasDisplayer
import llayout.displayers.TextButton
import llayout.frame.*
import java.awt.Color
import java.awt.Graphics
import kotlin.random.Random

val colourSquareApplication : LApplication = LApplication { frame.run() }

object Screen : LScene(){

    val exitButton = TextButton("X") {frame.close()}.alignLeftTo(0).alignUpTo(0)

    val colourStep : Int = 5

    var colour : Color = Color(125, 125, 125)

    val variableSquare : CanvasDisplayer = (CanvasDisplayer(0.25, 0.25)
            .setX(0.5).setY(0.33) as CanvasDisplayer).addGraphicAction({
        g : Graphics, w : Int, h : Int -> run{
            g.color = colour
            g.fillRect(0, 0, w, h)
        }
    })

    val constantSquare : CanvasDisplayer = (CanvasDisplayer(0.25, 0.25)
            .setX(0.5).setY(0.66) as CanvasDisplayer).addGraphicAction({
        g : Graphics, w : Int, h : Int -> run{
            g.color = if(following) colour else fixed
            g.fillRect(0, 0, w, h)
        }
    })

    var following : Boolean = true
    var fixed : Color = colour

    val toggleButton : TextButton = TextButton("Toggle") {
        following = !following
        fixed = colour
    }.setX(0.75).setY(0.5) as TextButton

    init{
        add(exitButton)
        add(variableSquare)
        add(constantSquare)
        add(toggleButton)
    }

    override fun onTimerTick() = addRandomColourStep()

    private fun addRandomColourStep(){
        when(Random.nextInt(0, 6)){
            0 -> addRed()
            1 -> subtractRed()
            2 -> addGreen()
            3 -> subtractGreen()
            4 -> addBlue()
            5 -> subtractBlue()
        }
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

val frame : LFrame = LFrame(Screen)
