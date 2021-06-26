
import java.io.File;
import java.io.IOException;
import java.util.function.Function;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class ImageModifier {
	private BufferedImage image;
	private int width;
	private int height;
	private int[][][] imageAsArray;
	private double maxGreyLevel;
	private double averageGreyLevel;

	/*public static double redPerception = 0.299;
	public static double greenPerception = 0.587;
	public static double bluePerception = 0.144;
	*/
	public static double redPerception = 0.333;
	public static double greenPerception = 0.333;
	public static double bluePerception = 0.333;

	public ImageModifier(String imageName) throws IOException {
		BufferedImage img = null;
		try {
			File f = new File(imageName);
			img = ImageIO.read(f);
		} catch (IOException e) {
			System.out.println(e);
		}
		this.image = img;
		this.width = img.getWidth();
		this.height = img.getHeight();
		convertImageToArray();
		calculateLevels();
	}

	private void calculateLevels() {
		int maxLevel = 0;
		double averageLevel = 0;
		
		for (int i = 0; i < width; i += 1) {
			for (int j = 0; j < height; j += 1) {
				if (imageAsArray[i][j][3] > maxLevel) {
					maxLevel = imageAsArray[i][j][3];
				}
				averageLevel += imageAsArray[i][j][3];
			}
		}

		this.maxGreyLevel = (double)maxLevel;
		averageLevel = (double) averageLevel / (width * height);
		this.averageGreyLevel = averageLevel;
	}

	public double getMaxGreyLevel() {
		return this.maxGreyLevel;
	}

	public double getAverageGreyLevel() {
		return this.averageGreyLevel;
	}

	/*public double getAverageLevel(int color) {
		return averageLevel[color];
	}

	public double getMaxLevel(int color) {
		return maxLevel[color];
	}*/

	private void convertImageToArray() {
		imageAsArray = new int[width][height][4];
		for (int i = 0; i < width; i += 1) {
			for (int j = 0; j < height; j += 1) {
				int p = image.getRGB(i, j);

				// int a = (p >> 24) & 0xff;
				imageAsArray[i][j][0] = (p >> 16) & 0xff;
				imageAsArray[i][j][1] = (p >> 8) & 0xff;
				imageAsArray[i][j][2] = p & 0xff;
				imageAsArray[i][j][3] = (int) (redPerception * (double) imageAsArray[i][j][0]
						+ greenPerception * (double) imageAsArray[i][j][1]
						+ bluePerception * (double) imageAsArray[i][j][2]);
			}
		}
	}

	public void modifyPixels(String newImageName, Function<Integer, Integer> redModifier,
			Function<Integer, Integer> greenModifier, Function<Integer, Integer> blueModifier) {
		for (int i = 0; i < width; i += 1) {
			for (int j = 0; j < height; j += 1) {
				int a = (image.getRGB(i, j) >> 24) & 0xff;

				imageAsArray[i][j][0] = redModifier.apply(imageAsArray[i][j][0]);
				imageAsArray[i][j][1] = greenModifier.apply(imageAsArray[i][j][1]);
				imageAsArray[i][j][2] = blueModifier.apply(imageAsArray[i][j][2]);

				int p = (a << 24) | (imageAsArray[i][j][0] << 16) | (imageAsArray[i][j][1] << 8)
						| imageAsArray[i][j][2];
				image.setRGB(i, j, p);
				// System.out.print(r + " " + g + " " + b);
			}
			// System.out.println(" ");
		}

		try {
			File f = new File(newImageName);
			// ImageIO.write(image, newImageName.split(".")[1], f);
			ImageIO.write(image, "png", f);
		} catch (IOException e) {
			System.out.println(e);
		}

	}

	/*public int calculateMaxLevel(int color) {
		int maxLevel = 0;
		for (int i = 0; i < width; i += 1) {
			for (int j = 0; j < height; j += 1) {
				if (imageAsArray[i][j][color] > maxLevel) {
					maxLevel = imageAsArray[i][j][color];
				}
			}
		}
		return maxLevel;
	}*/

	public double calculateAverageLevel(int color) {
		double averageLevel = 0.0;
		for (int i = 0; i < width; i += 1) {
			for (int j = 0; j < height; j += 1) {
				averageLevel += (double) imageAsArray[i][j][color];
			}
		}
		/*
		 * if (width != 0 && height != 0) { return averageLevel / (width *
		 * height); } return 0;
		 */
		// System.out.println(averageLevel / (width * height));
		return averageLevel / (width * height);
	}

	public int getPixelIntensity(int i, int j, int color) {
		return imageAsArray[i][j][color];
	}
}