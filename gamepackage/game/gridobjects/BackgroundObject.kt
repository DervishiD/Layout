package gamepackage.game.gridobjects

import img.programimages.RED101
import java.awt.image.BufferedImage

enum class BackgroundObject(private val image: BufferedImage) {
    EMPTY_BACKGROUND(RED101)
    ;

    //TODO -- ADD STUFF

    fun image() : BufferedImage = image

}