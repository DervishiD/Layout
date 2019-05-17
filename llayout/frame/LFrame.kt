package llayout.frame

import java.awt.Toolkit
import javax.swing.WindowConstants

class LFrame(scene : LScene) {

    companion object{

        /**
         * The computer's screen width.
         */
        private val SCREEN_WIDTH : Int = (Toolkit.getDefaultToolkit().screenSize.getWidth()).toInt()

        /**
         * The computer's screen height.
         */
        private val SCREEN_HEIGHT : Int = (Toolkit.getDefaultToolkit().screenSize.getHeight()).toInt()

    }

    private val core : LFrameCore = LFrameCore(scene)

    fun exitOnClose() : LFrame{
        core.defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
        return this
    }

    fun hideOnClose() : LFrame{
        core.defaultCloseOperation = WindowConstants.HIDE_ON_CLOSE
        return this
    }

    fun disposeOnClose() : LFrame{
        core.defaultCloseOperation = WindowConstants.DISPOSE_ON_CLOSE
        return this
    }

    fun nothingOnClose() : LFrame{
        core.defaultCloseOperation = WindowConstants.DO_NOTHING_ON_CLOSE
        return this
    }

    fun setTimerPeriod(period : Int) : LFrame{
        core.changeTimerPeriod(period)
        return this
    }

    fun setVisible() : LFrame{
        core.setVisible()
        return this
    }

    fun setInvisible() : LFrame{
        core.setInvisible()
        return this
    }

    fun close() : LFrame{
        core.close()
        return this
    }

    fun isVisible() : Boolean = core.isVisible

    fun isHidden() : Boolean = core.isHidden()

    fun pause() : LFrame{
        core.pause()
        return this
    }

    fun resume() : LFrame{
        core.resume()
        return this
    }

    fun run() : LFrame{
        core.run()
        return this
    }

    fun setFullscreen() : LFrame{
        core.setFullscreen()
        return this
    }

    fun setNotFullscreen() : LFrame{
        core.setNotFullScreen()
        return this
    }

    fun setX(x : Int) : LFrame{
        core.setCenterX(x)
        return this
    }

    fun setY(y : Int) : LFrame{
        core.setCenterY(y)
        return this
    }

    fun setWidth(width : Int) : LFrame{
        core.setW(width)
        return this
    }

    fun setHeight(height : Int) : LFrame{
        core.setH(height)
        return this
    }

    fun setX(x : Double) : LFrame = setX((x * SCREEN_WIDTH).toInt())

    fun setY(y : Double) : LFrame = setY((y * SCREEN_HEIGHT).toInt())

    fun setWidth(width : Double) : LFrame = setWidth((width * SCREEN_WIDTH).toInt())

    fun setHeight(height : Double) : LFrame = setHeight((height * SCREEN_HEIGHT).toInt())

    fun setDecorated() : LFrame{
        core.isUndecorated = false
        return this
    }

    fun setUndecorated() : LFrame{
        core.isUndecorated = true
        return this
    }

    fun setRunningIfHidden() : LFrame{
        core.setRunningIfHidden()
        return this
    }

    fun setNotRunningIfHidden() : LFrame{
        core.setNotRunningIfHidden()
        return this
    }

    fun setTitle(title : CharSequence) : LFrame{
        core.title = title.toString()
        return this
    }

    fun setResizable() : LFrame{
        core.isResizable = true
        return this
    }

    fun setUnResizable() : LFrame{
        core.isResizable = false
        return this
    }

    fun setMinimalWidth(width : Int) : LFrame{
        if(width <= 0) throw IllegalArgumentException("Negative width $width in LFrame.setMinimalWidth")
        core.minimumSize.width = width
        return this
    }

    fun setMinimalHeight(height : Int) : LFrame{
        if(height <= 0) throw IllegalArgumentException("Negative height $height in LFrame.setMinimalHeight")
        core.minimumSize.height = height
        return this
    }

    fun setMinimalWidth(width : Double) : LFrame = setMinimalWidth((width * SCREEN_WIDTH).toInt())

    fun setMinimalHeight(height : Double) : LFrame = setMinimalHeight((height * SCREEN_HEIGHT).toInt())

    fun setMinimalDimensions(width : Int, height : Int) : LFrame{
        setMinimalWidth(width)
        setMinimalHeight(height)
        return this
    }

    fun setMinimalDimensions(width : Int, height : Double) : LFrame{
        setMinimalWidth(width)
        setMinimalHeight(height)
        return this
    }

    fun setMinimalDimensions(width : Double, height : Int) : LFrame{
        setMinimalWidth(width)
        setMinimalHeight(height)
        return this
    }

    fun setMinimalDimensions(width : Double, height : Double) : LFrame{
        setMinimalWidth(width)
        setMinimalHeight(height)
        return this
    }

    fun setMaximalWidth(width : Int) : LFrame{
        if(width <= 0) throw IllegalArgumentException("Negative width $width in LFrame.setMaximalWidth")
        core.maximumSize.width = width
        return this
    }

    fun setMaximalHeight(height : Int) : LFrame{
        if(height <= 0) throw IllegalArgumentException("Negative height $height in LFrame.setMaximalHeight")
        core.maximumSize.height = height
        return this
    }

    fun setMaximalWidth(width : Double) : LFrame = setMaximalWidth((width * SCREEN_WIDTH).toInt())

    fun setMaximalHeight(height : Double) : LFrame = setMaximalHeight((height * SCREEN_HEIGHT).toInt())

    fun setMaximalDimensions(width : Int, height : Int) : LFrame{
        setMaximalWidth(width)
        setMaximalHeight(height)
        return this
    }

    fun setMaximalDimensions(width : Int, height : Double) : LFrame{
        setMaximalWidth(width)
        setMaximalHeight(height)
        return this
    }

    fun setMaximalDimensions(width : Double, height : Int) : LFrame{
        setMaximalWidth(width)
        setMaximalHeight(height)
        return this
    }

    fun setMaximalDimensions(width : Double, height : Double) : LFrame{
        setMaximalWidth(width)
        setMaximalHeight(height)
        return this
    }

    fun setPreferredWidth(width : Int) : LFrame{
        if(width <= 0) throw IllegalArgumentException("Negative width $width in LFrame.setPreferredWidth")
        core.preferredSize.width = width
        core.setW(width)
        return this
    }

    fun setPreferredHeight(height : Int) : LFrame{
        if(height <= 0) throw IllegalArgumentException("Negative height $height in LFrame.setPreferredHeight")
        core.preferredSize.height = height
        core.setH(height)
        return this
    }

    fun setPreferredWidth(width : Double) : LFrame = setPreferredWidth((width * SCREEN_WIDTH).toInt())

    fun setPreferredHeight(height : Double) : LFrame = setPreferredHeight((height * SCREEN_HEIGHT).toInt())

    fun setPreferredDimensions(width : Int, height : Int) : LFrame{
        setPreferredWidth(width)
        setPreferredHeight(height)
        return this
    }

    fun setPreferredDimensions(width : Int, height : Double) : LFrame{
        setPreferredWidth(width)
        setPreferredHeight(height)
        return this
    }

    fun setPreferredDimensions(width : Double, height : Int) : LFrame{
        setPreferredWidth(width)
        setPreferredHeight(height)
        return this
    }

    fun setPreferredDimensions(width : Double, height : Double) : LFrame{
        setPreferredWidth(width)
        setPreferredHeight(height)
        return this
    }

}