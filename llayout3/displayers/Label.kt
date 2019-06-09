package llayout3.displayers

import llayout3.utilities.StringDisplay
import llayout3.utilities.Text

/**
 * A label that displays text.
 * @see TextDisplayer
 */
class Label : TextDisplayer {

    private companion object{
        private const val LABEL_LATERAL_DISTANCE : Int = 0
    }

    override var lateralAdditionalDistance: Int = LABEL_LATERAL_DISTANCE

    constructor(text : Collection<StringDisplay>) : super(text)

    constructor(text : Text) : super(text)

    constructor(text : StringDisplay) : super(text)

    constructor(text : CharSequence) : super(text)

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