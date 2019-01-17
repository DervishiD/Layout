package geometry

class Vector {

    private var x : Double
    private var y : Double

    constructor(x : Double, y : Double){
        this.x = x
        this.y = y
    }
    constructor(x : Int, y : Int) : this(x.toDouble(), y.toDouble())
    constructor() : this(0, 0)
    constructor(p : Point) : this(p.x(), p.y())
    constructor(v : Vector) : this(v.x, v.y)

    public fun x() : Double = x
    public fun y() : Double = y

    /*
    * Change the sign of x and y.
    * */
    public fun oppose(){
        x = -x
        y = -y
    }

    /*
    * Return the opposite of this Vector.
    * */
    public fun opposed() : Vector = Vector(-x, -y)

    /*
    * Vector addition.
    * */
    public operator fun plus(v : Vector) : Vector = Vector(x + v.x, y + v.y)

    /*
    * Vector subtraction.
    * */
    public operator fun minus(v : Vector) : Vector = Vector(x - v.x, y - v.y)

    /*
    * Dot product.
    * */
    public operator fun times(v : Vector) : Double = x * v.x + y * v.y

}