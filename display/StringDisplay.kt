package display

import java.awt.Color
import java.awt.Font

/**
 * Class that represents a displayed String
 */
class StringDisplay {

    /**
     * The displayed text
     */
    public var text : String

    /**
     * Its colour
     */
    public var color : Color

    /**
     * Its font
     */
    public var font : Font

    constructor(text : String, color : Color = DEFAULT_COLOR, font : Font = DEFAULT_FONT){
        this.text = text
        this.color = color
        this.font = font
    }
    constructor(text : String, font : Font = DEFAULT_FONT, color : Color = DEFAULT_COLOR) : this(text, color, font)
    constructor(color : Color, font : Font, text : String) : this(text, color, font)
    constructor(color : Color, text : String, font : Font = DEFAULT_FONT) : this(text, color, font)
    constructor(font : Font, text : String, color : Color = DEFAULT_COLOR) : this(text, color, font)
    constructor(font : Font, color : Color, text : String) : this(text, color, font)

    public operator fun plus(other : StringDisplay) : String = this.text + other.text
    public operator fun plus(other : String) : String = this.text + other
    public operator fun String.plus(other : StringDisplay) : String = this + other.text

    public operator fun contains(other : String) : Boolean = text.contains(other)

}