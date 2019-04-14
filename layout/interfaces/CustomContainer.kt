package layout.interfaces

import layout.displayers.Displayer
import java.awt.Container

/**
 * A custom Container class.
 * Java awt containers are objects that can include Components inside them. For example,
 * JFrames, JPanels and JLabels are Containers. This CustomContainer plays a similar
 * role for Displayers. A class implements the CustomContainer interface if it is
 * able to contain Displayers. Examples are the Screen class and the DisplayerScrollPane class.
 * Since this interface is seen as a type of extension to the awt Container class, it only makes
 * sense for a class to implement it if it is already an awt Container.
 * @see Container
 * @see Displayer
 * @see Screen
 * @see DisplayerScrollPane
 */
interface CustomContainer : HavingDimension {

    /**
     * The Displayers contained in this Container
     * @see Displayer
     */
    val parts : MutableCollection<Displayer>

    /**
     * Adds a Displayer to this CustomContainer.
     * @param d The Displayer that will be added to this CustomContainer.
     * @see Displayer
     * @see Displayer.onAdd
     * @see parts
     */
    infix fun add(d : Displayer) : CustomContainer {
        parts.add(d)
        (this as Container).add(d)
        d.updateRelativeValues(width(), height())
        w.addListener(d){d.updateRelativeValues(width(), height())}
        h.addListener(d){d.updateRelativeValues(width(), height())}
        d.addRequestUpdateListener(this){d.updateRelativeValues(width(), height())}
        d.onAdd(this)
        return this
    }

    /**
     * Removes a Displayer from this CustomContainer.
     * @param d The Displayer that will be removed from this CustomContainer.
     * @see Displayer
     * @see Displayer.onRemove
     * @see parts
     */
    infix fun remove(d : Displayer) : CustomContainer {
        parts.remove(d)
        (this as Container).remove(d)
        w.removeListener(d)
        h.removeListener(d)
        d.removeRequestUpdateListener(this)
        d.onRemove(this)
        return this
    }

    /**
     * Forces the initialization of its Displayer components.
     * This should normally not be called by the User.
     * @see Displayer
     * @see Displayer.initialize
     * @see Displayer.initphase
     * @see parts
     */
    fun initialization(){
        for(part : Displayer in parts){
            part.initialize()
        }
    }

    /**
     * Reacts to a key pressed event.
     * The default reaction is to do nothing, but the method can be overriden in subclasses.
     * @param key The Java key code of the pressed key.
     */
    fun pressKey(key : Int){}

    /**
     * Reacts to a key released event.
     * The default reaction is to do nothing, but the method can be overriden in subclasses.
     * @param key The Java key code of the released key.
     */
    fun releaseKey(key : Int){}

    /**
     * Reacts to a mouse click event on this CustomContainer.
     * @param x The x coordinate of the mouse event.
     * @param y The y coordinate of the mouse event.
     */
    fun mouseClick(x : Int, y : Int)

    /**
     * Reacts to a mouse press event on this CustomContainer.
     * @param x The x coordinate of the mouse event.
     * @param y The y coordinate of the mouse event.
     */
    fun mousePress(x : Int, y : Int)

    /**
     * Reacts to a mouse release event on this CustomContainer.
     * @param x The x coordinate of the mouse event.
     * @param y The y coordinate of the mouse event.
     */
    fun mouseRelease(x : Int, y : Int)

    /**
     * Reacts to a mouse enter event on this CustomContainer.
     * @param x The x coordinate of the mouse event.
     * @param y The y coordinate of the mouse event.
     */
    fun mouseEnter(x : Int, y : Int)

    /**
     * Reacts to a mouse exit event on this CustomContainer.
     * @param x The x coordinate of the mouse event.
     * @param y The y coordinate of the mouse event.
     */
    fun mouseExit(x : Int, y : Int)

    /**
     * Reacts to a mouse drag event on this CustomContainer.
     * @param x The x coordinate of the mouse event.
     * @param y The y coordinate of the mouse event.
     */
    fun mouseDrag(x : Int, y : Int)

    /**
     * Reacts to a mouse moved event on this CustomContainer.
     * @param x The x coordinate of the mouse event.
     * @param y The y coordinate of the mouse event.
     */
    fun mouseMoved(x : Int, y : Int)

    /**
     * Reacts to a mouse wheel moved event on this CustomContainer.
     * @param x The x coordinate of the mouse event.
     * @param y The y coordinate of the mouse event.
     * @param units The number of units scrolled by the mouse wheel.
     */
    fun mouseWheelMoved(x : Int, y : Int, units : Int)

}