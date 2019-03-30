package gamepackage.game.gridobjects

import display.DEFAULT_GRID_MESH_SIZE
import display.GRID_IMAGE_SIZE_DELTA
import display.SMALLEST_GRID_IMAGE_SIZE
import img.programimages.RED101
import img.programimages.resizeGridImage
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