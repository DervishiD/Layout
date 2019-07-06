package usages.pendulum

import llayout6.displayers.CanvasDisplayer
import usages.pendulum.Scene.MAXIMAL_LENGTH
import java.awt.Color
import java.awt.Graphics
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

internal object SimulationPane : CanvasDisplayer() {

    private const val ITERATIONS_PER_TICK : Int = 50

    private const val INTEGRATION_STEP : Double = TIMER_PERIOD * 1e-3 / ITERATIONS_PER_TICK

    private val SIMULATION_COLOR : Color = Color.BLACK

    private const val BORDER_GAP : Double = 0.1

    private const val MASS_RADIUS : Int = 6

    private const val ARROW_TIP_LENGTH : Int = 20

    private const val ARROW_TIP_HALF_WIDTH : Int = 8

    private const val ARROW_BODY_LENGTH : Int = 60

    private const val ARROW_LENGTH : Int = ARROW_BODY_LENGTH + ARROW_TIP_LENGTH

    private const val SPEED_NORM_FOR_RED : Double = 5.0

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
        PlotScene.clear()
        if(plotFrame.isHidden()) plotFrame.setVisible()
    }

    private fun startAnimation(){
        addGraphicAction({ g : Graphics, w : Int, h : Int ->
            g.color = SIMULATION_COLOR
            drawRod(g, w, h)
            drawMass(g, w, h)
            drawArrow(g, w, h)
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

    private fun arrowDirection() : Int = if(thetaDot >= 0.0) 1 else -1

    private fun drawArrow(g : Graphics, w : Int, h : Int){
        if(abs(thetaDot) > 0.01){
            val tipX : Int = (drawingX(w, h) + cos(theta) * ARROW_LENGTH * arrowDirection()).toInt()
            val tipY : Int = (drawingY(w, h) - sin(theta) * ARROW_LENGTH * arrowDirection()).toInt()
            val middleX : Int = (drawingX(w, h) + cos(theta) * ARROW_BODY_LENGTH * arrowDirection()).toInt()
            val middleY : Int = (drawingY(w, h) - sin(theta) * ARROW_BODY_LENGTH * arrowDirection()).toInt()
            val leftX : Int = (middleX - ARROW_TIP_HALF_WIDTH * sin(theta)).toInt()
            val leftY : Int = (middleY - ARROW_TIP_HALF_WIDTH * cos(theta)).toInt()
            val rightX : Int = (middleX + ARROW_TIP_HALF_WIDTH * sin(theta)).toInt()
            val rightY : Int = (middleY + ARROW_TIP_HALF_WIDTH * cos(theta)).toInt()
            g.color = colorForSpeed()
            g.drawLine(drawingX(w, h), drawingY(w, h), middleX, middleY)
            g.fillPolygon(intArrayOf(tipX, leftX, rightX), intArrayOf(tipY, leftY, rightY), 3)
        }
    }

    private fun speedNorm() : Double = abs(l * thetaDot)

    private fun colorForSpeed() : Color{
        val speed : Double = speedNorm()
        return if(speed > SPEED_NORM_FOR_RED){
            Color.RED
        }else{
            val t : Double = speedNorm() / SPEED_NORM_FOR_RED
            Color((t * t * 255).toInt(), ((1 - t * t) * 255).toInt(), 0)
        }
    }

    private fun thetaDotDot() : Double = -g/l * sin(theta) - mu * thetaDot

    private fun updateAngle(){
        val thetaDotDot : Double = thetaDotDot()
        theta += thetaDot * INTEGRATION_STEP + 0.5 * INTEGRATION_STEP * INTEGRATION_STEP * thetaDotDot
        thetaDot += thetaDotDot * INTEGRATION_STEP
    }

    private fun timerUpdate(){
        for(i : Int in 1..ITERATIONS_PER_TICK) updateAngle()
        PlotScene.plot(theta)
    }

    override fun onTimerTick() {
        if(running) timerUpdate()
    }

}