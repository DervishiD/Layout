package llayout.displayers

abstract class DisplayerContainer : ResizableDisplayer {

    protected constructor(width : Int, height : Int) : super(width, height)

    protected constructor(width : Double, height : Int) : super(width, height)

    protected constructor(width : Int, height : Double) : super(width, height)

    protected constructor(width : Double, height : Double) : super(width, height)

    protected constructor() : super()

    fun add(vararg components : Displayer) : Displayer{
        for(component : Displayer in components){
            core.add(core(component))
        }
        return this
    }

    fun add(components : Collection<Displayer>) : Displayer{
        for(component : Displayer in components){
            core.add(core(component))
        }
        return this
    }

    fun remove(vararg components : Displayer) : Displayer{
        for(component : Displayer in components){
            core.remove(core(component))
        }
        return this
    }

    fun remove(components : Collection<Displayer>) : Displayer{
        for(component : Displayer in components){
            core.remove(core(component))
        }
        return this
    }

}