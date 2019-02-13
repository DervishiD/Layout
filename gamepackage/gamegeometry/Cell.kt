package gamepackage.gamegeometry

import gamepackage.game.gridobjects.BackgroundObject
import gamepackage.game.gridobjects.BackgroundObject.EMPTY_BACKGROUND
import java.awt.image.BufferedImage

class Cell {
    //TODO
    var surface : BackgroundObject = EMPTY_BACKGROUND

    fun image() : BufferedImage = surface.image()

}