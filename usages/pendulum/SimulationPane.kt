package usages.pendulum

import llayout5.displayers.CanvasDisplayer
import usages.pendulum.Scene.MAXIMAL_LENGTH
import java.awt.Color
import java.awt.Graphics
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

internal object SimulationPane : CanvasDisplayer() {

    private const val INTEGRATION_STEP : Double = 0.0001

    private const val ITERATIONS_PER_TICK : Int = 100

    private val SIMULATION_COLOR : Color = Color.BLACK

    private const val BORDER_GAP : Double = 0.1

    private const val MASS_RADIUS : Int = 6

    private var theta : Double = 0.0

    private var thetaDot : Double = 0.0

    private var g : Double = 0.0

    private var l : Double = 0.0

    private var mu : Double = 0.0

    private var running : Boolean = false

    internal fun restart(theta : Double, thetaDot : Double, g : Double, l : Double, mu : Double){
        this.theta = theta
        this.thetaDot = thetaDot
        this.g = g
        this.l = l
        this.mu = mu
        if(!running) startAnimation()
        running = true
    }

    private fun startAnimation(){
        addGraphicAction({ g : Graphics, w : Int, h : Int ->
            g.color = SIMULATION_COLOR
            drawRod(g, w, h)
            drawMass(g, w, h)
        })
    }

    private fun drawnL(w : Int, h : Int) : Double = min(w, h) * (0.5 - BORDER_GAP) * l / MAXIMAL_LENGTH

    private fun drawingX(w : Int, h : Int) : Int = ( w / 2 + drawnL(w, h) * sin(theta) ).toInt()

    private fun drawingY(w : Int, h : Int) : Int = ( h / 2 + drawnL(w, h) * cos(theta) ).toInt()

    private fun drawRod(g : Graphics, w : Int, h : Int){
        g.drawLine(w / 2, h / 2, drawingX(w, h), drawingY(w, h))
    }

    private fun drawMass(g : Graphics, w : Int, h : Int){
        g.fillOval(drawingX(w, h) - MASS_RADIUS, drawingY(w, h) - MASS_RADIUS, 2 * MASS_RADIUS, 2 * MASS_RADIUS)
    }

    private fun thetaDotDot() : Double = -g/l * sin(theta) - mu * thetaDot

    private fun updateAngle(){
        val thetaDotDot : Double = thetaDotDot()
        theta += thetaDot * INTEGRATION_STEP + 0.5 * INTEGRATION_STEP * INTEGRATION_STEP * thetaDotDot
        thetaDot += thetaDotDot * INTEGRATION_STEP
    }

    private fun timerUpdate(){
        for(i : Int in 1..ITERATIONS_PER_TICK) updateAngle()
    }

    override fun onTimerTick() {
        if(running) timerUpdate()
    }

}