package usages.doublependulum

import llayout6.displayers.Canvas
import usages.doublependulum.Scene.MAXIMAL_LENGTH
import usages.doublependulum.Scene.MAXIMAL_MASS
import usages.doublependulum.Scene.MINIMAL_MASS
import java.awt.Color
import java.awt.Graphics
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

internal object SimulationPane : Canvas() {

    private const val ITERATIONS_PER_TICK : Int = 10000

    private const val INTEGRATION_STEP : Double = TIMER_PERIOD * 1e-3 / ITERATIONS_PER_TICK

    private const val DRAWING_SPACE : Double = 0.9

    private const val MINIMAL_MASS_RADIUS : Int = 3

    private const val MAXIMAL_MASS_RADIUS : Int = 10

    private val PENDULUM_COLOUR : Color = Color.BLACK

    private var g : Double = 0.0

    private var mu : Double = 0.0

    private var lPhi : Double = 0.0

    private var lPsi : Double = 0.0

    private var mPhi : Double = 0.0

    private var mPsi : Double = 0.0

    private var phi : Double = 0.0

    private var psi : Double = 0.0

    private var phiDot : Double = 0.0

    private var psiDot : Double = 0.0

    private var running : Boolean = false

    init{
        addGraphicAction({ g : Graphics, w : Int, h : Int -> if(running) draw(g, w, h) })
    }

    internal fun reload(g : Double, mu : Double, lPhi : Double, lPsi : Double, mPhi : Double, mPsi : Double,
                        phi : Double, psi : Double, phiDot : Double, psiDot : Double){
        this.g = g
        this.mu = mu
        this.lPhi = lPhi
        this.lPsi = lPsi
        this.mPhi = mPhi
        this.mPsi = mPsi
        this.phi = phi
        this.psi = psi
        this.phiDot = phiDot
        this.psiDot = psiDot
        running = true
    }

    private fun drawnLength() : Double = 2 * MAXIMAL_LENGTH

    private fun massRadius(mass : Double) : Int{
        val t : Double = ( mass - MINIMAL_MASS ) / ( MAXIMAL_MASS - MINIMAL_MASS )
        return ( t * MAXIMAL_MASS_RADIUS + ( 1 - t ) * MINIMAL_MASS_RADIUS ).toInt()
    }

    private fun drawingRadius(w : Int, h : Int) : Int = ( DRAWING_SPACE * min(w, h) / 2 ).toInt()

    private fun mPhiX() : Double = lPhi * sin(phi)

    private fun mPhiY() : Double = - lPhi * cos(phi)

    private fun mPsiX(mPhiX : Double) : Double = mPhiX + lPsi * sin(psi)

    private fun mPsiY(mPhiY : Double) : Double = mPhiY - lPsi * cos(psi)

    private fun centerPixelX(w : Int) : Int = w / 2

    private fun centerPixelY(h : Int) : Int = h / 2

    private fun mPhiPixelX(w : Int, h : Int) : Int = centerPixelX(w) + ( drawingRadius(w, h) * mPhiX() / drawnLength() ).toInt()

    private fun mPhiPixelY(w : Int, h : Int) : Int = centerPixelY(h) - ( drawingRadius(w, h) * mPhiY() / drawnLength() ).toInt()

    private fun mPsiPixelX(w : Int, h : Int) : Int = centerPixelX(w) + ( drawingRadius(w, h) * mPsiX(mPhiX()) / drawnLength() ).toInt()

    private fun mPsiPixelY(w : Int, h : Int) : Int = centerPixelY(h) - ( drawingRadius(w, h) * mPsiY(mPhiY()) / drawnLength() ).toInt()

    private fun draw(g : Graphics, w : Int, h : Int){
        val mPhiPixelX : Int = mPhiPixelX(w, h)
        val mPhiPixelY : Int = mPhiPixelY(w, h)
        drawPendulum(g, centerPixelX(w), centerPixelY(h), mPhiPixelX, mPhiPixelY, massRadius(mPhi))
        drawPendulum(g, mPhiPixelX, mPhiPixelY, mPsiPixelX(w, h), mPsiPixelY(w, h), massRadius(mPsi))
    }

    private fun drawPendulum(g : Graphics, fromX : Int, fromY : Int, toX : Int, toY : Int, radius : Int){
        g.color = PENDULUM_COLOUR
        g.drawLine(fromX, fromY, toX, toY)
        g.fillOval(toX - radius, toY - radius, 2 * radius, 2 * radius)
    }

    private fun theta() : Double = phi - psi

    private fun sinTheta() : Double = sin(theta())

    private fun cosTheta() : Double = cos(theta())

    private fun phiDotDot(sinTheta : Double, cosTheta : Double) : Double = - ( mPhi * g * sin(phi) + mu * lPhi * phiDot + mPsi * sinTheta * ( g * cos(psi) + lPhi * phiDot * phiDot * cosTheta + lPsi * ( phiDot + psiDot ) * ( phiDot + psiDot ) ) ) / ( lPhi * ( mPhi + mPsi * sinTheta * sinTheta ) )

    private fun psiDotDot(sinTheta : Double, cosTheta : Double, phiDotDot : Double) : Double = ( - g * sin(psi) - mu * lPsi * psiDot / mPsi - ( lPsi + lPhi * cosTheta ) * phiDotDot + lPhi * phiDot * phiDot * sinTheta ) / lPsi

    private fun integrate(){
        val sinTheta : Double = sinTheta()
        val cosTheta : Double = cosTheta()
        val phiDotDot : Double = phiDotDot(sinTheta, cosTheta)
        val psiDotDot : Double = psiDotDot(sinTheta, cosTheta, phiDotDot)
        phi += phiDot * INTEGRATION_STEP + 0.5 * phiDotDot * INTEGRATION_STEP * INTEGRATION_STEP
        psi += psiDot * INTEGRATION_STEP + 0.5 * psiDotDot * INTEGRATION_STEP * INTEGRATION_STEP
        phiDot += phiDotDot * INTEGRATION_STEP
        psiDot += psiDotDot * INTEGRATION_STEP
    }

    private fun timerTick(){
        for(i : Int in 1..ITERATIONS_PER_TICK) integrate()
        updatePlots()
    }

    private fun updatePlots(){
        PhiPlot.plot(phi)
        PsiPlot.plot(psi)
    }

    override fun onTimerTick() {
        if(running) timerTick()
    }

}