package usages.rpg.gamepackage.game.gridobjects

import usages.rpg.DEFAULT_GRID_MESH_SIZE
import usages.rpg.GRID_IMAGE_SIZE_DELTA
import usages.rpg.SMALLEST_GRID_IMAGE_SIZE
import usages.rpg.img.programimages.RED101
import usages.rpg.img.programimages.resizeGridImage
import java.awt.image.BufferedImage

enum class BackgroundObject(private val image: BufferedImage) {
    EMPTY_BACKGROUND(RED101)
    ;

    private val imageMap : HashMap<Int, BufferedImage> by lazy{
        val result : HashMap<Int, BufferedImage> = HashMap()
        var currentSize : Int = DEFAULT_GRID_MESH_SIZE
        while(currentSize >= SMALLEST_GRID_IMAGE_SIZE){
            result[currentSize] = resizeGridImage(image, currentSize)
            currentSize -= GRID_IMAGE_SIZE_DELTA
        }
        result
    }

    fun image(size : Int = DEFAULT_GRID_MESH_SIZE) : BufferedImage = if(imageMap.containsKey(size)) imageMap[size]!! else resizeGridImage(image, size)

}