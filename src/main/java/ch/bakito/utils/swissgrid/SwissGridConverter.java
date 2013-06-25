package ch.bakito.utils.swissgrid;

import java.math.BigDecimal;
import java.math.MathContext;

public class SwissGridConverter {

	private static BigDecimal bDiff = new BigDecimal("169028.66");
	private static BigDecimal lDiff = new BigDecimal("26782.5");
	private static BigDecimal divisor = new BigDecimal("10000");

	public CH1903To toCH1903(WGS84TTo wgs84tTo) {
		BigDecimal b = (wgs84tTo.getNorthSeconds().subtract(bDiff)).divide(divisor);
		BigDecimal l = (wgs84tTo.getEastSeconds().subtract(lDiff)).divide(divisor);

		BigDecimal y = new BigDecimal("600072.37").add(new BigDecimal("211455.93").multiply(l))
				.subtract(new BigDecimal("10938.51").multiply(l).multiply(b))
				.subtract(new BigDecimal("0.36").multiply(l).multiply(b.pow(2)))
				.subtract(new BigDecimal("44.54").multiply(l.pow(3)));

		BigDecimal x = new BigDecimal("200147.07").add(new BigDecimal("308807.95").multiply(b))
				.add(new BigDecimal("3745.25").multiply(l.pow(2))).add(new BigDecimal("76.63").multiply(b.pow(2)))
				.subtract(new BigDecimal("194.56").multiply(l.pow(2)).multiply(b))
				.add(new BigDecimal("119.79").multiply(b.pow(3)));

		return new CH1903To(x.round(new MathContext(1)).intValue(), y.round(new MathContext(1)).intValue());
	}

	public WGS84TTo toWGS84(CH1903To ch1903To) {
		BigDecimal x = new BigDecimal(ch1903To.getX()).subtract(new BigDecimal("200000")).divide(
				new BigDecimal("1000000"));
		BigDecimal y = new BigDecimal(ch1903To.getY()).subtract(new BigDecimal("600000")).divide(
				new BigDecimal("1000000"));

		BigDecimal east = new BigDecimal("2.6779094").add(new BigDecimal("4.728982").multiply(y))
				.add(new BigDecimal("0.791484").multiply(y).multiply(x))
				.add(new BigDecimal("0.1306").multiply(y).multiply(x.pow(2)))
				.subtract(new BigDecimal("0.0436").multiply(y.pow(3))).multiply(divisor);

		BigDecimal north = new BigDecimal("16.9023892").add(new BigDecimal("3.238272").multiply(x))
				.subtract(new BigDecimal("0.270978").multiply(y.pow(2)))
				.subtract(new BigDecimal("0.002528").multiply(x.pow(2)))
				.subtract(new BigDecimal("0.0447").multiply(y.pow(2)).multiply(x))
				.subtract(new BigDecimal("0.0140").multiply(x.pow(3))).multiply(divisor);
		return new WGS84TTo(north, east);
	}

}
