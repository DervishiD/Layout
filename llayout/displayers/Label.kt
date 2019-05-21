package llayout.displayers

import llayout.GraphicAction
import llayout.utilities.StringDisplay
import llayout.utilities.Text

/**
 * A Label is a Displayer that displays only text.
 * @see TextDisplayer
 * @see Displayer
 * @see Text
 * @see StringDisplay
 */
class Label : TextDisplayer {

    override var upDelta: Int = 0
    override var downDelta: Int = 0
    override var leftDelta: Int = 0
    override var rightDelta: Int = 0

    constructor(text : CharSequence) : super(text)

    constructor(text : StringDisplay) : super(text)

    constructor(text : Collection<StringDisplay>) : super(text)

    constructor(text : Text) : super(text)

    constructor() : super()

    constructor(text : Int) : super(text)

    constructor(text : Double) : super(text)

    constructor(text : Long) : super(text)

    constructor(text : Float) : super(text)

    constructor(text : Short) : super(text)

    constructor(text : Byte) : super(text)

    constructor(text : Boolean) : super(text)

    constructor(text : Char) : super(text)

}