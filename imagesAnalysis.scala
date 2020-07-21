 import javax.imageio.ImageIO
 import java.io.File
 import java.awt.image.BufferedImage


object main {
	def getAverageLuminosity(image: BufferedImage) : Float = {
		val numOfPixels: Int = image.getWidth()*image.getHeight()
		var darknessRatio: Float = 0;
		var color, red, green, blue: Int = 0;
		for (i <- 0 to image.getHeight() - 1) {
			for (j <- 0 to image.getWidth() - 1) {
				color = image.getRGB(j, i)
				red = (color & 0xff0000) / 65536
				green = (color & 0xff00) / 256
				blue = (color & 0xff)
				darknessRatio += (100 - ((0.2126*red + 0.7152*green + 0.0722*blue)/2.55)).toFloat
			}
		}
		return darknessRatio/numOfPixels
	}

	def getListOfFiles(dir: String) : List[File] = {
		val file = new File(dir)
		if (file.exists && file.isDirectory) file.listFiles.filter(_.isFile).toList
		else List[File]()
	}
	
	def buildOutputFile(file: File, averageLuminosity: Float, extension: String, breakingPoint: Int) : File = {
		val name = file.getName().replaceFirst("[.][^.]+$", "")
		val darkOrBright = if (averageLuminosity > breakingPoint) "dark" else "bright"
		val newFile = new File("output/" + name + "_" + darkOrBright + "_" + averageLuminosity.toInt + "." + extension)
		return newFile
	}

	def main(args: Array[String]) {
		val files = getListOfFiles("input/")
		val breakingPoint = args(0).toInt
		for (file <- files) {
			var image: BufferedImage = ImageIO.read(file)
			var averageLuminosity: Float = getAverageLuminosity(image)
			var extension: String = file.getName().toString.split("\\.").last
			var outputFile = buildOutputFile(file, averageLuminosity, extension, breakingPoint)
			ImageIO.write(image, extension, outputFile)
		}
	}
}