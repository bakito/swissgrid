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

	public String getNorthString() {
		return "N " + format(north);
	}

	public String getEastString() {
		return "E " + format(east);
	}

	private String format(BigDecimal seconds) {
		StringBuilder sb = new StringBuilder();
		int degree = seconds.divide(DEGREE, BigDecimal.ROUND_FLOOR).intValue();
		sb.append(degree).append("° ");

		BigDecimal minutes = seconds.subtract(new BigDecimal(degree).multiply(DEGREE))
				.divide(MINUTE, BigDecimal.ROUND_FLOOR).multiply(new BigDecimal(1000));
		sb.append(minutes.intValue() / 1000.);
		return sb.toString();
	}

	@Override
	public String toString() {
		return "WGS84T [" + getNorthString() + " " + getEastString() + "]";
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
