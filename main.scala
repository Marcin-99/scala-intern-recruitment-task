 import javax.imageio.ImageIO
 import java.io.File
 import java.awt.image.BufferedImage
 import java.io.File


object main {
	def main(args: Array[String]) {

		def getAverageLuminosity(image: BufferedImage) : Float = {
			var darknessRatio: Float = 0;
			val numOfPixels = image.getWidth()*image.getHeight()

			for (i <- 0 to image.getHeight() - 1) {
				for (j <- 0 to image.getWidth() - 1) {
					val color = image.getRGB(j, i)
					val red = (color & 0xff0000) / 65536
					val green = (color & 0xff00) / 256
					val blue = (color & 0xff)
					darknessRatio += (100 - ((0.2126*red + 0.7152*green + 0.0722*blue)/2.55)).toFloat
				}
			}

			return darknessRatio/numOfPixels
		}


		def getListOfFiles(dir: String):List[File] = {
		    val d = new File(dir)
		    if (d.exists && d.isDirectory) d.listFiles.filter(_.isFile).toList
		    else List[File]()
		}


		val files = getListOfFiles("input/")
		for (file <- files) {
			var image = ImageIO.read(file)
			var averageLuminosity = getAverageLuminosity(image)
			println(file + ": " + averageLuminosity)
		}

	}
}