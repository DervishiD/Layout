package llayout.displayers

/**
 * A simple vertical scroll pane that only contains [ResizableDisplayer]s.
 * It scales their width to fit the pane.
 * @since LLayout 7
 */
class VerticalScrollPane : ResizableDisplayer {

    private companion object{

        /**
         * The slider's width.
         * @since LLayout 7
         */
        private const val SLIDER_WIDTH : Int = 25

        /**
         * The default height of a component, that is, the height automatically given if its height is not defined.
         * @since LLayout 7
         */
        private const val DEFAULT_COMPONENT_HEIGHT : Int = 100

        /**
         * A component is said to have no height if its height has not been set and thus has this value.
         * @see ResizableDisplayer
         * @since LLayout 7
         */
        private const val NO_HEIGHT : Int = 1

        /**
         * The default gap between two components.
         * @since LLayout 7
         */
        private const val DEFAULT_GAP : Int = 0

        /**
         * A class that represents a component and a gap between it and the component that comes before it.
         * @since LLayout 7
         */
        private class PaneComponent(private val component : ResizableDisplayer, private val beforeGap : Int){

            init{
                if(beforeGap < 0) throw IllegalArgumentException("Negative gap between two scroll pane components.")
            }

            /**
             * The [ResizableDisplayer] represented by this object.
             * @since LLayout 7
             */
            fun component() : ResizableDisplayer = component

            /**
             * The gap before the last component.
             * @since LLayout 7
             */
            fun gap() : Int = beforeGap

        }

    }

    /**
     * The container that holds the components.
     * @since LLayout 7
     */
    private val pane : DisplayerContainer = DisplayerContainer()

    /**
     * The components contained in the pane, as [PaneComponent].
     * @since LLayout 7
     */
    private val components : MutableList<PaneComponent> = mutableListOf()

    /**
     * The pane's slider.
     * @since LLayout 7
     */
    private val slider : VerticalDoubleSlider = VerticalDoubleSlider(SLIDER_WIDTH, 1.0)

    init{
        addWidthListener { resizePane() }
        resizePane()
        pane.alignLeftTo(0)
        pane.alignTopTo(0)
        core.add(pane)
        slider.setRange(0, 1).addValueListener { updatePanePosition(1 - slider.value()) }.alignTopTo(0).alignRightTo(1.0)
        slider.setStartingValue(1.0)
        core.add(slider)
    }

    constructor(width : Int, height : Int) : super(width, height)

    constructor(width : Double, height : Int) : super(width, height)

    constructor(width : Int, height : Double) : super(width, height)

    constructor(width : Double, height : Double) : super(width, height)

    constructor() : super()

    /**
     * Adds the given component at the end of the list.
     * @return this
     * @since LLayout 7
     */
    fun add(component : ResizableDisplayer, beforeGap : Int = DEFAULT_GAP) : VerticalScrollPane = addAtEnd(component, beforeGap)

    /**
     * Adds the given component at the given index in the list.
     * @return this
     * @throws IndexOutOfBoundsException If the index is out of bounds.
     * @since LLayout 7
     */
    fun addAt(component : ResizableDisplayer, index : Int, beforeGap : Int = DEFAULT_GAP) : VerticalScrollPane{
        formatComponent(component)
        if(noComponents()){
            components.add(PaneComponent(component, 0))
        }else{
            checkIndex(index)
            if(beforeGap < 0) throw IndexOutOfBoundsException("The gap $beforeGap must be positive.")
            components.add(index, PaneComponent(component, beforeGap))
        }
        reloadPane()
        return this
    }

    /**
     * Adds the given component at the end of the list.
     * @return this
     * @since LLayout 7
     */
    fun addAtEnd(component : ResizableDisplayer, beforeGap : Int = DEFAULT_GAP) : VerticalScrollPane = addAt(component, lastIndex(), beforeGap)

    /**
     * Adds the given component at the start of the list.
     * @return this
     * @since LLayout 7
     */
    fun addAtTop(component : ResizableDisplayer) : VerticalScrollPane = addAt(component, 0, 0)

    /**
     * Replaces the [oldComponent] by the [newComponent].
     * @return this
     * @since LLayout 7
     */
    fun replace(newComponent : ResizableDisplayer, oldComponent : ResizableDisplayer, beforeGap : Int = DEFAULT_GAP) : VerticalScrollPane{
        return if(contains(oldComponent)){
            replaceAt(newComponent, indexOf(oldComponent), beforeGap)
        }else this
    }

    /**
     * Replaces the component at the given [index] by the given [component].
     * @return this
     * @throws IndexOutOfBoundsException If the index is out of bounds.
     * @since LLayout 7
     */
    fun replaceAt(component : ResizableDisplayer, index : Int, beforeGap: Int = DEFAULT_GAP) : VerticalScrollPane{
        formatComponent(component)
        if(noComponents()){
            add(component, beforeGap)
        }else{
            checkIndex(index)
            if(beforeGap < 0) throw IndexOutOfBoundsException("The gap $beforeGap must be positive.")
            pane.remove(components[index].component())
            components[index] = PaneComponent(component, beforeGap)
        }
        reloadPane()
        return this
    }

    /**
     * Replaces the first component by the given [component].
     * @return this
     * @since LLayout 7
     */
    fun replaceFirst(component : ResizableDisplayer) : VerticalScrollPane = replaceAt(component, 0, 0)

    /**
     * Replaces the last component by the given [component].
     * @return this
     * @since LLayout 7
     */
    fun replaceLast(component : ResizableDisplayer, beforeGap: Int = DEFAULT_GAP) : VerticalScrollPane = replaceAt(component, lastIndex(), beforeGap)

    /**
     * Removes the given [component].
     * @return this
     * @since LLayout 7
     */
    fun remove(component : ResizableDisplayer) : VerticalScrollPane = if(contains(component)) removeAt(indexOf(component)) else this

    /**
     * Removes the component at the given [index].
     * @return this
     * @throws IndexOutOfBoundsException If the index is out of bounds
     * @since LLayout 7
     */
    fun removeAt(index : Int) : VerticalScrollPane{
        checkIndex(index)
        pane.remove(components[index].component())
        components.removeAt(index)
        reloadPane()
        return this
    }

    /**
     * Removes the last component in the list.
     * @since LLayout 7
     */
    fun removeLast() : VerticalScrollPane = removeAt(lastIndex())

    /**
     * Removes the first component in the list.
     * @since LLayout 7
     */
    fun removeFirst() : VerticalScrollPane = removeAt(0)

    /**
     * The number of components in the list.
     * @since LLayout 7
     */
    fun numberOfComponents() : Int = components.size

    /**
     * The last index in the list.
     * @since LLayout 7
     */
    private fun lastIndex() : Int = numberOfComponents() - 1

    /**
     * Returns the index of the given [component] in the list, or -1 if it is absent.
     * @since LLayout 7
     */
    private fun indexOf(component : ResizableDisplayer) : Int{
        for(i : Int in 0 until components.size){
            if(components[i].component() == component) return i
        }
        return -1
    }

    /**
     * Returns true if the given [component] is contained in the list.
     * @since LLayout 7
     */
    private fun contains(component: ResizableDisplayer) : Boolean = indexOf(component) != -1

    /**
     * If the index is out of bounds, throws an [IndexOutOfBoundsException].
     * @since LLayout 7
     */
    private fun checkIndex(index : Int){
        if(index < 0 || index > lastIndex()) throw IndexOutOfBoundsException("Invalid index $index, outside of the range [0, ${lastIndex()}].")
    }

    /**
     * True if the list is empty.
     * @since LLayout 7
     */
    private fun noComponents() : Boolean = numberOfComponents() == 0

    /**
     * Resize the pane when necessary.
     * @since LLayout 7
     */
    private fun resizePane(){
        val newWidth : Int = width() - SLIDER_WIDTH
        pane.setWidth(if(newWidth <= 0) 1 else newWidth)
        for(c : PaneComponent in components){
            c.component().setWidth(pane.width())
        }
    }

    /**
     * Formats the height of the given component.
     * @since LLayout 7
     */
    private fun formatComponent(component : ResizableDisplayer){
        if(hasNoHeight(component)){
            component.setHeight(DEFAULT_COMPONENT_HEIGHT)
        }
        component.resetAlignment()
        component.setX(0.5)
    }

    /**
     * Returns true if the given component's height has not been set.
     * @since LLayout 7
     */
    private fun hasNoHeight(component : ResizableDisplayer) : Boolean = component.height() <= NO_HEIGHT

    /**
     * Reloads the content of the pane.
     * @since LLayout 7
     */
    private fun reloadPane(){
        clearPane()
        addComponentsToPane()
    }

    /**
     * Clears the pane.
     * @since LLayout 7
     */
    private fun clearPane(){
        for(c : PaneComponent in components){
            pane.remove(c.component())
        }
    }

    /**
     * Adds the components to the pane.
     * @since LLayout 7
     */
    private fun addComponentsToPane(){
        if(noComponents()){
            pane.setHeight(1)
            paneToZero()
        }else{
            setPaneHeight()
            if(paneTooSmall() || paneTooLow()){
                paneToZero()
            }else if(paneTooHigh()){
                paneToBottom()
            }
            //Add first component
            components[0].component().alignTopTo(0)
            pane.add(components[0].component())
            //Add other components
            for(i : Int in 1..lastIndex()){
                components[i].component().alignTopToBottom(components[i - 1].component(), components[i].gap())
                pane.add(components[i].component())
            }
        }
    }

    /**
     * Sets the height of the pane.
     * @since LLayout 7
     */
    private fun setPaneHeight(){
        pane.setHeight(totalHeight())
    }

    /**
     * True if the [pane] is smaller than the scrollPane.
     * @since LLayout 7
     */
    private fun paneTooSmall() : Boolean = pane.height() <= height()

    /**
     * True if the pane is too high.
     * @since LLayout 7
     */
    private fun paneTooHigh() : Boolean = pane.downSideY() < height()

    /**
     * True if the pane is too low.
     * @since LLayout 7
     */
    private fun paneTooLow() : Boolean = pane.upSideY() > 0

    /**
     * Computes the height of the pane.
     * @since LLayout 7
     */
    private fun totalHeight() : Int{
        var result = 0
        for(c : PaneComponent in components){
            result += c.component().height() + c.gap()
        }
        return result
    }

    /**
     * Sets the pane position to zero.
     * @since LLayout 7
     */
    private fun paneToZero(){
        slider.setStartingValue(0)
    }

    /**
     * Sets the pane position to the bottom.
     * @since LLayout 7
     */
    private fun paneToBottom(){
        slider.setStartingValue(1.0)
    }

    /**
     * Returns the excess height of the pane.
     * @since LLayout 7
     */
    private fun heightExcess() : Int = pane.height() - height()

    /**
     * Updates the position of the pane.
     * @since LLayout 7
     */
    private fun updatePanePosition(sliderValue : Double){
        if(!paneTooSmall()){
            pane.setY(pane.height() / 2 - (heightExcess() * sliderValue).toInt())
        }
    }

    override fun updateRelativeValues(frameWidth: Int, frameHeight: Int) {
        super.updateRelativeValues(frameWidth, frameHeight)
        pane.updateRelativeValues(width(), height())
    }

}