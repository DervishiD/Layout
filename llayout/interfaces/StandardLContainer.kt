package llayout.interfaces

import llayout.displayers.cores.DisplayerCore
import llayout.frame.LSceneCore
import java.awt.Component
import java.awt.Container

/**
 * A custom Container class.
 * Java awt containers are objects that can include Components inside them. For example,
 * JFrames, JPanels and JLabels are Containers. This StandardLContainer plays a similar
 * role for Displayers. A class implements the StandardLContainer interface if it is
 * able to contain Displayers. Examples are the LSceneCore class and the DisplayerScrollPane class.
 * Since this interface is seen as a type of extension to the awt Container class, it only makes
 * sense for a class to implement it if it is already an awt Container.
 * Note : Parts are not updated on a resize. This behaviour is implemented by implementing classes.
 * @see Container
 * @see DisplayerCore
 * @see LSceneCore
 * @since LLayout 1
 */
interface StandardLContainer : LContainerCore {

    /**
     * Adds a DisplayerCore to this StandardLContainer.
     * @param d The DisplayerCore that will be added to this StandardLContainer.
     * @see DisplayerCore
     * @see DisplayerCore.onAdd
     * @see parts
     * @since LLayout 2
     */
    fun add(d : Displayable) : StandardLContainer {
        d.onAdd(this)
        parts.add(d)
        if(this is Container && d is Component) (this as Container).add(d, 0)
        d.updateRelativeValues(width(), height())
        d.addRequestUpdateListener(this){d.updateRelativeValues(width(), height())}
        return this
    }

    /**
     * Removes a DisplayerCore from this StandardLContainer.
     * @param d The DisplayerCore that will be removed from this StandardLContainer.
     * @see DisplayerCore
     * @see DisplayerCore.onRemove
     * @see parts
     * @since LLayout 1
     */
    fun remove(d : Displayable) : StandardLContainer {
        d.onRemove(this)
        parts.remove(d)
        if(this is Container && d is Component) (this as Container).remove(d)
        d.removeRequestUpdateListener(this)
        return this
    }

    /**
     * Disambiguation for DisplayerCore.
     * @return this
     * @since LLayout 1
     */
    fun add(d : DisplayerCore) : StandardLContainer = add(d as Displayable)

    /**
     * Disambiguation for DisplayerCore.
     * @return this
     * @since LLayout 1
     */
    fun remove(d : DisplayerCore) : StandardLContainer = remove(d as Displayable)

}