package usages.springpendulum

import llayout6.displayers.Canvas
import java.awt.Color
import java.awt.Graphics
import kotlin.math.*

internal object SimulationPane : Canvas() {

    private const val ITERATIONS_PER_TICK : Int = 100

    private const val INTEGRATION_STEP : Double = TIMER_PERIOD * 1e-3 / ITERATIONS_PER_TICK

    private const val MASS_RADIUS : Int = 10

    private const val SPRING_WIDTH : Int = 10

    private val DRAWING_COLOR : Color = Color.BLACK

    private var running : Boolean = false

    private var m : Double = 0.0

    private var k : Double = 0.0

    private var l : Double = 0.0

    private var rho : Double = 0.0

    private var rhoDot : Double = 0.0

    private var theta : Double = 0.0

    private var thetaDot : Double = 0.0

    private var g : Double = 0.0

    private var mu : Double = 0.0

    internal fun reload(m : Double, k : Double, l : Double, rho : Double, rhoDot : Double, theta : Double, thetaDot : Double, g : Double, mu : Double){
        this.m = m
        this.k = k
        this.l = l
        this.rho = rho
        this.rhoDot = rhoDot
        this.theta = theta
        this.thetaDot = thetaDot
        this.g = g
        this.mu = mu
        if(!running) startSimulation()
        running = true
    }

    private fun startSimulation(){
        addGraphicAction({ g : Graphics, w : Int, h : Int ->
            drawPendulum(g, w, h)
        })
    }

    private fun maxDrawnLength() : Double = 4 * ln(2 + l) * m * g / k

    private fun centerX(width : Int) = width / 2

    private fun centerY(height : Int) = height / 2

    private fun minWH(width : Int, height : Int) : Int = min(width, height)

    private fun massX() : Double = rho * sin(theta)

    private fun massY() : Double = - rho * cos(theta)

    private fun massPixelX(width : Int, height : Int) : Int = ( centerX(width) + massX() * minWH(width, height) / maxDrawnLength() ).toInt()

    private fun massPixelY(width : Int, height : Int) : Int = ( centerY(height) - massY() * minWH(width, height) / maxDrawnLength() ).toInt()

    private fun drawPendulum(g : Graphics, w : Int, h : Int){
        drawSpring(g, centerX(w), centerY(h), massPixelX(w, h), massPixelY(w, h))
        drawMass(g, w, h)
    }

    private fun drawSpring(g : Graphics, fromX : Int, fromY : Int, toX : Int, toY : Int){
        //Draws an ugly spring
//        val points : List<Pair<Int, Int>> = pointsForSpring(fromX, fromY, toX, toY)
//        g.color = DRAWING_COLOR
//        for(i : Int in 1 until points.size){
//            g.drawLine(points[i - 1].first, points[i - 1].second, points[i].first, points[i].second)
//        }
        //Draws a line
        g.color = DRAWING_COLOR
        g.drawLine(fromX, fromY, toX, toY)
    }

    private fun pointsForSpring(fromX : Int, fromY : Int, toX : Int, toY : Int) : List<Pair<Int, Int>>{
        val numberOfPoints = 15
        val result : MutableList<Pair<Int, Int>> = mutableListOf()
        val totalVector : Pair<Int, Int> = Pair(toX - fromX, toY - fromY)
        val directionVector : Pair<Double, Double> = Pair(totalVector.first / numberOfPoints.toDouble(), totalVector.second / numberOfPoints.toDouble())
        fun directionVectorLength() : Double = sqrt(directionVector.first * directionVector.first + directionVector.second * directionVector.second)
        val normalVector : Pair<Double, Double> = Pair(
                - directionVector.second / directionVectorLength() * SPRING_WIDTH,
                directionVector.first / directionVectorLength() * SPRING_WIDTH
        )

        result.add(Pair(fromX, fromY))
        result.add(Pair((result.last().first + directionVector.first).toInt(), (result.last().second + directionVector.second).toInt()))
        result.add(Pair((result.last().first + directionVector.first + normalVector.first).toInt(),
                (result.last().second + directionVector.second + normalVector.second).toInt()))
        result.add(Pair((result.last().first + directionVector.first - normalVector.first).toInt(),
                (result.last().second + directionVector.second - normalVector.second).toInt()))
        result.add(Pair((result.last().first + directionVector.first - normalVector.first).toInt(),
                (result.last().second + directionVector.second - normalVector.second).toInt()))
        result.add(Pair((result.last().first + directionVector.first + normalVector.first).toInt(),
                (result.last().second + directionVector.second + normalVector.second).toInt()))
        result.add(Pair((result.last().first + directionVector.first + normalVector.first).toInt(),
                (result.last().second + directionVector.second + normalVector.second).toInt()))
        result.add(Pair((result.last().first + directionVector.first - normalVector.first).toInt(),
                (result.last().second + directionVector.second - normalVector.second).toInt()))
        result.add(Pair((result.last().first + directionVector.first - normalVector.first).toInt(),
                (result.last().second + directionVector.second - normalVector.second).toInt()))
        result.add(Pair((result.last().first + directionVector.first + normalVector.first).toInt(),
                (result.last().second + directionVector.second + normalVector.second).toInt()))
        result.add(Pair((result.last().first + directionVector.first + normalVector.first).toInt(),
                (result.last().second + directionVector.second + normalVector.second).toInt()))
        result.add(Pair((result.last().first + directionVector.first - normalVector.first).toInt(),
                (result.last().second + directionVector.second - normalVector.second).toInt()))
        result.add(Pair((result.last().first + directionVector.first - normalVector.first).toInt(),
                (result.last().second + directionVector.second - normalVector.second).toInt()))
        result.add(Pair((result.last().first + directionVector.first + normalVector.first).toInt(),
                (result.last().second + directionVector.second + normalVector.second).toInt()))
        result.add(Pair((result.last().first + directionVector.first).toInt(), (result.last().second + directionVector.second).toInt()))

        return result
    }

    private fun drawMass(g : Graphics, width : Int, height : Int){
        g.color = DRAWING_COLOR
        g.fillOval(massPixelX(width, height) - MASS_RADIUS, massPixelY(width, height) - MASS_RADIUS, 2 * MASS_RADIUS, 2 * MASS_RADIUS)
    }

    private fun rhoDotDot() : Double = g * cos(theta) + rho * thetaDot * thetaDot - mu * rhoDot / m - k * ( rho - l ) / m

    private fun thetaDotDot() : Double{
        return if(rho >  0.0){
            - ( g * sin(theta) + 2 * rhoDot * thetaDot + mu * rho * thetaDot / m ) / rho
        }else{
            0.0
        }
    }

    private fun updateState(){
        val rhoDotDot : Double = rhoDotDot()
        val thetaDotDot : Double = thetaDotDot()
        rho += rhoDot * INTEGRATION_STEP + 0.5 * rhoDotDot * INTEGRATION_STEP * INTEGRATION_STEP
        theta += thetaDot * INTEGRATION_STEP + 0.5 * thetaDotDot * INTEGRATION_STEP * INTEGRATION_STEP
        rhoDot += rhoDotDot * INTEGRATION_STEP
        thetaDot += thetaDotDot * INTEGRATION_STEP
    }

    private fun timerUpdate(){
        for(i : Int in 1..ITERATIONS_PER_TICK) updateState()
        updatePlots()
    }

    private fun updatePlots(){
        RhoPlot.plot(rho)
        ThetaPlot.plot(theta)
    }

    override fun onTimerTick() {
        if(running) timerUpdate()
    }

}