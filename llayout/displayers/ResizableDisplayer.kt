package llayout.displayers

open class ResizableDisplayer : Displayer {

    protected constructor(width : Int, height : Int) : super(){
        setWidth(width)
        setHeight(height)
    }

    protected constructor(width : Double, height : Int) : super(){
        setWidth(width)
        setHeight(height)
    }

    protected constructor(width : Int, height : Double) : super(){
        setWidth(width)
        setHeight(height)
    }

    protected constructor(width : Double, height : Double) : super(){
        setWidth(width)
        setHeight(height)
    }

    protected constructor() : this(1, 1)

    fun setWidth(width : Int) : ResizableDisplayer{
        //DON'T USE PROPERTY ACCESS SYNTAX
        core.setWidth(width)
        return this
    }

    fun setWidth(width : Double) : ResizableDisplayer{
        //DON'T USE PROPERTY ACCESS SYNTAX
        core.setWidth(width)
        return this
    }

    fun setHeight(height : Int) : ResizableDisplayer{
        //DON'T USE PROPERTY ACCESS SYNTAX
        core.setHeight(height)
        return this
    }

    fun setHeight(height : Double) : ResizableDisplayer{
        //DON'T USE PROPERTY ACCESS SYNTAX
        core.setHeight(height)
        return this
    }

}