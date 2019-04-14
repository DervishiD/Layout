package usages.rpg.gamepackage.gamegeometry

import usages.rpg.DEFAULT_GRID_MESH_SIZE
import usages.rpg.gamepackage.game.gridobjects.BackgroundObject
import usages.rpg.gamepackage.game.gridobjects.BackgroundObject.EMPTY_BACKGROUND
import java.awt.image.BufferedImage

class Cell {
    //TODO
    var surface : BackgroundObject = EMPTY_BACKGROUND

    fun image(size : Int = DEFAULT_GRID_MESH_SIZE) : BufferedImage = surface.image(size)

}