
public class FuzzyIntensifier {

	static int intensifyUsingFuzzyInt(double pixelRGBValue, double maxPixelValue, double averagePixelValue,
			double degree) {
		double fe = 2.0;
		double fd = (maxPixelValue - averagePixelValue) / ((Math.pow(0.5, -1.0 / fe) - 1.0));
		double pixelMembership = Math.pow((1.0 + (maxPixelValue - pixelRGBValue) / fd), -fe);
		// System.out.println(pixelMembership);
		pixelMembership = applyIntensificationOperator(pixelMembership, degree);
		// System.out.rintln(pixelMembership);
		pixelRGBValue = maxPixelValue - fd * (Math.pow(pixelMembership, -1.0 / fe)) + fd;
		// System.out.println(maxPixelValue);
		return (int) pixelRGBValue;
	}

	static double applyIntensificationOperator(double pixelMembership, double degree) {
		if (pixelMembership < 0.5) {
			pixelMembership = Math.pow(pixelMembership, degree) * 2.0;
		} else {
			pixelMembership = 1.0 - Math.pow(1.0 - pixelMembership, degree) * 2.0;
		}
		return pixelMembership;
	}
}
