package usages.newton2D

class MaterialPoint {

    var position : Vector

    var mass : Double

    constructor(position : Vector, mass : Double){
        if(mass <= 0) throw IllegalArgumentException("Non-positive mass $mass in MaterialPoint constructor")
        this.position = position
        this.mass = mass
    }

    constructor(mass : Double, position : Vector) : this(position, mass)

}