package layout.utilities

/**
 * This class represents any type of text, be it a String, a StringDisplay
 * or a Collection of StringDisplays.
 * It is immutable.
 * @see StringDisplay
 */
class Text {

    /**
     * The text stores a version of itself as a list of lines of StringDisplays.
     * @see StringDisplay
     * @see toLinesList
     */
    private val asLines : List<List<StringDisplay>>

    /**
     * The text stores a raw String representation of itself.
     */
    private val asString : String

    /**
     * Constructs a Text from a String.
     * @param text a String that will be encoded as a Text object.
     */
    constructor(text : String){
        asString = text
        val lines = StringDisplay(text).toLines()
        val textAsLines : MutableList<List<StringDisplay>> = mutableListOf()
        for(line in lines){
            textAsLines.add(listOf(line))
        }
        asLines = textAsLines
    }

    /**
     * Constructs a Text from a StringDisplay.
     * @param text a StringDisplay that will be encoded as a Text object.
     * @see StringDisplay
     */
    constructor(text : StringDisplay){
        asString = text.text
        asLines = listOf(text).toLinesList()
    }

    /**
     * Constructs a Text from a Collection of StringDisplays.
     * @param text a Collection of StringDisplays that will be encoded as a Text object.
     * @see StringDisplay
     */
    constructor(text : Collection<StringDisplay>){
        asString = text.collapse()
        asLines = text.toLinesList()
    }

    /**
     * Constructs a Text from an Int, i.e. the string representation of the given Int.
     * @param text an Int that will be encoded as a Text object.
     */
    constructor(text : Int) : this(text.toString())

    /**
     * Constructs a Text from a Double, i.e. the string representation of the given Double.
     * @param text a Double that will be encoded as a Text object.
     */
    constructor(text : Double) : this(text.toString())

    /**
     * Returns the content of this Text object as a List of lines of StringDisplays.
     * @return The content of this Text object as a List of lines of StringDisplays.
     * @see StringDisplay
     */
    fun asLines() : List<List<StringDisplay>> = asLines

    /**
     * Returns the content of this Text object as a String.
     * @return The content of this Text object as a String.
     */
    fun asString() : String = asString

    /**
     * Returns true if the contained text is empty.
     * @return True if the contained text is empty.
     */
    fun isEmpty() : Boolean = asString.isEmpty()

    /**
     * Returns true if the contained text is not empty.
     * @return True if the contained text is not empty.
     */
    fun isNotEmpty() : Boolean = asString.isNotEmpty()

    override fun toString(): String = asString

}