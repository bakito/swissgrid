package ch.bakito.utils.swissgrid;

import java.math.BigDecimal;
import java.util.regex.Matcher;

public class WGS84 {

	private static final BigDecimal MINUTE = new BigDecimal(60);
	private static final BigDecimal DEGREE = MINUTE.multiply(MINUTE);

	private final BigDecimal east;
	private final BigDecimal north;

	public WGS84(BigDecimal north, BigDecimal east) {
		this.north = north.stripTrailingZeros();
		this.east = east.stripTrailingZeros();
	}

	public BigDecimal getNorthDegrees() {
		return north;
	}

	public BigDecimal getEastDegrees() {
		return east;
	}

	public String getNorthString(WGS84Format format) {
		return "N " + format(north, format);
	}

	public String getEastString(WGS84Format format) {
		return "E " + format(east, format);
	}

	private String format(BigDecimal decimal, WGS84Format format) {
		StringBuilder sb = new StringBuilder();

		switch (format) {
		case Decimal:
			sb.append(decimal);
			break;

		case DM:
			int degree = decimal.intValue();
			sb.append(degree).append("° ");

			BigDecimal minutes = decimal.subtract(new BigDecimal(degree)).multiply(MINUTE)
					.multiply(new BigDecimal(1000));
			sb.append(minutes.intValue() / 1000.).append("'");

			break;
		case DMS:
			degree = decimal.intValue();
			sb.append(degree).append("° ");

			int min = decimal.subtract(new BigDecimal(degree)).multiply(MINUTE).intValue();
			sb.append(min).append("' ");

			BigDecimal seconds = decimal.subtract(new BigDecimal(degree)).multiply(MINUTE)
					.subtract(new BigDecimal(min)).multiply(MINUTE).multiply(new BigDecimal(1000));
			sb.append(seconds.intValue() / 1000.).append("'");
			break;
		}

		return sb.toString();
	}

	@Override
	public String toString() {
		return toString(WGS84Format.DM);
	}

	public String toString(WGS84Format format) {
		return "WGS84T [" + getNorthString(format) + " " + getEastString(format) + "]";
	}

	public static WGS84 toWGS84(String coordiates) {
		Matcher matcher = WGS84Format.Decimal.getPattern().matcher(coordiates);
		if (matcher.matches()) {
			return new WGS84(new BigDecimal(matcher.group(1)), new BigDecimal(matcher.group(2)));
		}

		matcher = WGS84Format.DM.getPattern().matcher(coordiates);
		if (matcher.matches()) {
			return new WGS84(new BigDecimal(matcher.group(1)).add(new BigDecimal(matcher.group(2)).divide(MINUTE, 10,
					BigDecimal.ROUND_HALF_UP)), new BigDecimal(matcher.group(3)).add(new BigDecimal(matcher.group(4))
					.divide(MINUTE, 10, BigDecimal.ROUND_HALF_UP)));
		}

		matcher = WGS84Format.DMS.getPattern().matcher(coordiates);
		if (matcher.matches()) {
			return new WGS84(new BigDecimal(matcher.group(1)).add(
					new BigDecimal(matcher.group(2)).divide(MINUTE, 10, BigDecimal.ROUND_HALF_UP)).add(
					new BigDecimal(matcher.group(3)).divide(DEGREE, 10, BigDecimal.ROUND_HALF_UP)), new BigDecimal(
					matcher.group(5))
					.add(new BigDecimal(matcher.group(6)).divide(MINUTE, 10, BigDecimal.ROUND_HALF_UP)).add(
							new BigDecimal(matcher.group(7)).divide(DEGREE, 10, BigDecimal.ROUND_HALF_UP)));
		}
		throw new IllegalArgumentException("Unsopported pattern.");
	}

}
