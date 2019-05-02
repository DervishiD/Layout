package llayout.displayers

import llayout.geometry.Point
import llayout.utilities.StringDisplay
import java.awt.Graphics

class TextScrollPane : ResizableDisplayer{

    private var totalHeight : Int = 0

    private var lines : MutableList<MutableList<StringDisplay>> = mutableListOf()

    constructor(x : Int, y : Int, width : Int, height : Int) : super(x, y, width, height)

    constructor(x : Int, y : Double, width : Int, height : Int) : super(x, y, width, height)

    constructor(x : Double, y : Int, width : Int, height : Int) : super(x, y, width, height)

    constructor(x : Double, y : Double, width : Int, height : Int) : super(x, y, width, height)

    constructor(p : Point, width : Int, height : Int) : super(p, width, height)

    constructor(x : Int, y : Int, width : Double, height : Int) : super(x, y, width, height)

    constructor(x : Int, y : Double, width : Double, height : Int) : super(x, y, width, height)

    constructor(x : Double, y : Int, width : Double, height : Int) : super(x, y, width, height)

    constructor(x : Double, y : Double, width : Double, height : Int) : super(x, y, width, height)

    constructor(p : Point, width : Double, height : Int) : super(p, width, height)

    constructor(x : Int, y : Int, width : Double, height : Double) : super(x, y, width, height)

    constructor(x : Int, y : Double, width : Double, height : Double) : super(x, y, width, height)

    constructor(x : Double, y : Int, width : Double, height : Double) : super(x, y, width, height)

    constructor(x : Double, y : Double, width : Double, height : Double) : super(x, y, width, height)

    constructor(p : Point, width : Double, height : Double) : super(p, width, height)

    constructor(x : Int, y : Int, width : Int, height : Double) : super(x, y, width, height)

    constructor(x : Int, y : Double, width : Int, height : Double) : super(x, y, width, height)

    constructor(x : Double, y : Int, width : Int, height : Double) : super(x, y, width, height)

    constructor(x : Double, y : Double, width : Int, height : Double) : super(x, y, width, height)

    constructor(p : Point, width : Int, height : Double) : super(p, width, height)

    fun writeln() : TextScrollPane{
        lines.add(mutableListOf(StringDisplay()))
        return this
    }

    fun writeln(s : StringDisplay) : TextScrollPane{
        TODO("Not implemented.")
    }

    fun writeln(s : String) : TextScrollPane = this.writeln(StringDisplay(s))

    fun writeln(c : Char) : TextScrollPane = this.writeln(StringDisplay(c))

    fun writeln(s : StringBuilder) : TextScrollPane = this.writeln(StringDisplay(s))

    fun writeln(i : Int) : TextScrollPane = this.writeln(StringDisplay(i))

    fun writeln(d : Double) : TextScrollPane = this.writeln(StringDisplay(d))

    fun writeln(f : Float) : TextScrollPane = this.writeln(StringDisplay(f))

    fun writeln(l : Long) : TextScrollPane = this.writeln(StringDisplay(l))

    fun writeln(s : Short) : TextScrollPane = this.writeln(StringDisplay(s))

    fun writeln(b : Byte) : TextScrollPane = this.writeln(StringDisplay(b))

    fun writeln(b : Boolean) : TextScrollPane = this.writeln(StringDisplay(b))

    fun write(s : StringDisplay) : TextScrollPane{
        TODO("Not implemented.")
    }

    fun write(s : String) : TextScrollPane = this.writeln(StringDisplay(s))

    fun write(c : Char) : TextScrollPane = this.writeln(StringDisplay(c))

    fun write(s : StringBuilder) : TextScrollPane = this.writeln(StringDisplay(s))

    fun write(i : Int) : TextScrollPane = this.writeln(StringDisplay(i))

    fun write(d : Double) : TextScrollPane = this.writeln(StringDisplay(d))

    fun write(f : Float) : TextScrollPane = this.writeln(StringDisplay(f))

    fun write(l : Long) : TextScrollPane = this.writeln(StringDisplay(l))

    fun write(s : Short) : TextScrollPane = this.writeln(StringDisplay(s))

    fun write(b : Byte) : TextScrollPane = this.writeln(StringDisplay(b))

    fun write(b : Boolean) : TextScrollPane = this.writeln(StringDisplay(b))

    override fun loadParameters(g: Graphics) {
        TODO("Not implemented.")
    }

    override fun drawDisplayer(g: Graphics) {
        TODO("Not implemented.")
    }

}