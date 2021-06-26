import java.io.IOException;
import java.util.function.Function;

public class Main {

	public static void main(String[] args) throws IOException {
		String imageName = "1.jpg";
		FuzzyIntensifier intensifier;
		ImageModifier modifier = new ImageModifier(imageName);
		
		double maxPixelValue = modifier.getMaxGreyLevel();
		double averagePixelValue = modifier.getAverageGreyLevel();
		double degree = 2.0;
		
		Function<Integer, Integer> redModifier = (Integer pixelRGBValue) -> {
			return FuzzyIntensifier.intensifyUsingFuzzyInt(pixelRGBValue, maxPixelValue, averagePixelValue,degree);
		};
		
		Function<Integer, Integer> greenModifier = (Integer pixelRGBValue) -> {
			return FuzzyIntensifier.intensifyUsingFuzzyInt(pixelRGBValue, maxPixelValue, averagePixelValue,degree);
		};
		
		Function<Integer, Integer> blueModifier = (Integer pixelRGBValue) -> {
			return FuzzyIntensifier.intensifyUsingFuzzyInt(pixelRGBValue, maxPixelValue, averagePixelValue,degree);
		};
		modifier.modifyPixels("1-NEW.tif", redModifier, greenModifier, blueModifier);
		
		/*Function<Integer, Integer> identity = (Integer x) -> {
			return x;};
			
		modifier.modifyPixels("4-NEW degree-2.4.tif", identity , greenModifier, identity);
		*/
	}

}
