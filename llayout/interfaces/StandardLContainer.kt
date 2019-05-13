package llayout.interfaces

import llayout.displayers.Displayer
import llayout.frame.LScene
import java.awt.Component
import java.awt.Container

/**
 * A custom Container class.
 * Java awt containers are objects that can include Components inside them. For example,
 * JFrames, JPanels and JLabels are Containers. This StandardLContainer plays a similar
 * role for Displayers. A class implements the StandardLContainer interface if it is
 * able to contain Displayers. Examples are the LScene class and the DisplayerScrollPane class.
 * Since this interface is seen as a type of extension to the awt Container class, it only makes
 * sense for a class to implement it if it is already an awt Container.
 * @see Container
 * @see Displayer
 * @see LScene
 */
interface StandardLContainer : LContainerCore {

    /**
     * Adds a Displayer to this StandardLContainer.
     * @param d The Displayer that will be added to this StandardLContainer.
     * @see Displayer
     * @see Displayer.onAdd
     * @see parts
     */
    fun add(d : Displayable) : StandardLContainer {
        d.onAdd(this)
        parts.add(d)
        if(this is Container && d is Component) (this as Container).add(d)
        d.updateRelativeValues(width(), height())
        w.addListener(d){d.updateRelativeValues(width(), height())}
        h.addListener(d){d.updateRelativeValues(width(), height())}
        d.addRequestUpdateListener(this){d.updateRelativeValues(width(), height())}
        return this
    }

    /**
     * Removes a Displayer from this StandardLContainer.
     * @param d The Displayer that will be removed from this StandardLContainer.
     * @see Displayer
     * @see Displayer.onRemove
     * @see parts
     */
    fun remove(d : Displayable) : StandardLContainer {
        d.onRemove(this)
        parts.remove(d)
        if(this is Container && d is Component) (this as Container).remove(d)
        w.removeListener(d)
        h.removeListener(d)
        d.removeRequestUpdateListener(this)
        return this
    }

    fun add(d : Displayer) : StandardLContainer = add(d as Displayable)
    fun remove(d : Displayer) : StandardLContainer = remove(d as Displayable)

}