package img.programimages

import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

private const val IMAGES_FOLDER : String = "src\\img\\images"

private const val RED101_PATH : String = "$IMAGES_FOLDER\\red101.png"

val RED101 : BufferedImage by lazy{loadImage(RED101_PATH)}

private fun loadImage(path : String) : BufferedImage = ImageIO.read(File(path))

