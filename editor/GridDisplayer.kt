package editor

import display.Displayer
import gamepackage.gamegeometry.Grid
import geometry.Point
import java.awt.Graphics

/**
 * The Displayer that displays the Grid in the editor
 */
class GridDisplayer(p: Point, width : Int, height : Int) : Displayer(p) {

    override var w: Int = width
    override var h: Int = height

    private var grid : Grid = Grid(1, 1)

    override fun loadParameters(g: Graphics) {
        //TODO
    }

    override fun drawDisplayer(g: Graphics) {
        //TODO
    }

}