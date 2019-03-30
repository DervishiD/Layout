package gamepackage.gamegeometry

import display.DEFAULT_GRID_MESH_SIZE
import gamepackage.game.gridobjects.BackgroundObject
import gamepackage.game.gridobjects.BackgroundObject.EMPTY_BACKGROUND
import java.awt.image.BufferedImage

class Cell {
    //TODO
    var surface : BackgroundObject = EMPTY_BACKGROUND

    fun image(size : Int = DEFAULT_GRID_MESH_SIZE) : BufferedImage = surface.image(size)

}