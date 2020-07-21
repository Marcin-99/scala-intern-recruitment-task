 import javax.imageio.ImageIO
 import java.io.File
 import java.awt.image.BufferedImage


object main {
	def main(args: Array[String]) {
		def getAverageLuminosity(image: BufferedImage) : Float = {
			var luminosity: Float = 0;
			val numOfPixels = image.getWidth()*image.getHeight()

			for (i <- 0 to image.getHeight() - 1) {
				for (j <- 0 to image.getWidth() - 1) {
					val color = image.getRGB(j, i)
					val red = (color & 0xff0000) / 65536
					val green = (color & 0xff00) / 256
					val blue = (color & 0xff)
					luminosity += (0.2126*red + 0.7152*green + 0.0722*blue).toFloat
				}
			}

			return luminosity/numOfPixels
		}

		val image = ImageIO.read(new File("a.jpg"))
		var averageLuminosity = getAverageLuminosity(image)
		println(averageLuminosity)
	}
}