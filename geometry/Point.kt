package geometry

class Point {

    private var x : Double
    private var y : Double

    constructor(x : Double, y : Double){
        this.x = x
        this.y = y
    }
    constructor(x : Int, y : Int) : this(x.toDouble(), y.toDouble())
    constructor() : this(0, 0)
    constructor(p : Point) : this(p.x, p.y)
    constructor(v : Vector) : this(v.x(), v.y())

    public fun x() : Double = x
    public fun y() : Double = y

    public fun intx() : Int = x.toInt()
    public fun inty() : Int = y.toInt()

    /*
    * Addition of a Vector to a Point, corresponds to moving the Point along the Vector.
    * */
    public operator fun plus(v : Vector) : Point = Point(x + v.x(), y + v.y())

}