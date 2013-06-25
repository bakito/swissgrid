package ch.bakito.utils.swissgrid;

import java.math.BigDecimal;

public class WGS84TTo {
	private static final BigDecimal MINUTE = new BigDecimal(60);
	private static final BigDecimal DEGREE = MINUTE.multiply(MINUTE);

	private BigDecimal east;
	private BigDecimal north;

	public WGS84TTo(BigDecimal north, BigDecimal east) {
		this.north = north;
		this.east = east;
	}

	public BigDecimal getNorthSeconds() {
		return north;
	}

	public BigDecimal getEastSeconds() {
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
		return "WGS84TTo [" + getNorthString() + " " + getEastString() + "]";
	}
	
	
	
	
}
