package llayout2.utilities

/**
 * This class represents any type of text, be it a String, a StringDisplay
 * or a Collection of StringDisplays.
 * It is immutable.
 * @see StringDisplay
 * @since LLayout 1
 */
class Text {

    /**
     * The text stores a version of itself as a list of lines of StringDisplays.
     * @see StringDisplay
     * @see toLinesList
     * @since LLayout 1
     */
    private val asLines : MutableList<MutableList<StringDisplay>>

    /**
     * The text stores a raw String representation of itself.
     * @since LLayout 1
     */
    private val asString : String

    /**
     * The text stores a version of itself as a collection of StringDisplays.
     * @since LLayout 1
     */
    private val asCollection : MutableCollection<StringDisplay>

    /**
     * Constructs a Text from a StringDisplay.
     * @param text a StringDisplay that will be encoded as a Text object.
     * @see StringDisplay
     * @since LLayout 1
     */
    constructor(text : StringDisplay) : this(setOf(text))

    constructor(text : CharSequence) : this(StringDisplay(text))

    /**
     * Constructs a Text from a Collection of StringDisplays.
     * @param text a Collection of StringDisplays that will be encoded as a Text object.
     * @see StringDisplay
     * @since LLayout 1
     */
    constructor(text : Collection<StringDisplay>){
        asString = text.collapse()
        asLines = text.toLinesList()
        asCollection = text.toMutableCollection()
    }

    /**
     * Constructs a Text from an Int, i.e. the string representation of the given Int.
     * @param text an Int that will be encoded as a Text object.
     * @since LLayout 1
     */
    constructor(text : Int) : this(StringDisplay(text))

    /**
     * Constructs a Text from a Double, i.e. the string representation of the given Double.
     * @param text a Double that will be encoded as a Text object.
     * @since LLayout 1
     */
    constructor(text : Double) : this(StringDisplay(text))

    constructor(text : Float) : this(StringDisplay(text))

    constructor(text : Char) : this(StringDisplay(text))

    constructor(text : Short) : this(StringDisplay(text))

    constructor(text : Long) : this(StringDisplay(text))

    constructor(text : Byte) : this(StringDisplay(text))

    constructor(text : Boolean) : this(StringDisplay(text))

    constructor() : this(StringDisplay())

    constructor(text : Text){
        asString = text.asString()
        asLines = text.asLines()
        asCollection = text.asCollection
    }

    /**
     * Returns the content of this Text object as a List of lines of StringDisplays.
     * @return The content of this Text object as a List of lines of StringDisplays.
     * @see StringDisplay
     * @since LLayout 1
     */
    fun asLines() : MutableList<MutableList<StringDisplay>> = asLines

    /**
     * Returns the content of this Text object as a String.
     * @return The content of this Text object as a String.
     * @since LLayout 1
     */
    fun asString() : String = asString

    /**
     * Returns the content of this Text as a MutableCollection of StringDisplays.
     * @since LLayout 1
     */
    fun asCollection() : MutableCollection<StringDisplay> = asCollection

    /**
     * Returns true if the contained text is empty.
     * @return True if the contained text is empty.
     * @since LLayout 1
     */
    fun isEmpty() : Boolean = asString.isEmpty()

    /**
     * Returns true if the contained text is not empty.
     * @return True if the contained text is not empty.
     * @since LLayout 1
     */
    fun isNotEmpty() : Boolean = asString.isNotEmpty()

    override fun toString(): String = asString

}