package ch.bakito.utils.swissgrid;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;
// http://boulter.com/gps/#N%2046%B0%2056.859%20E%20007%B0%2026.541
public class WGS84Test extends Assert {
	@Test
	public void toWGS84Decimal() {
		WGS84 wgs84 = WGS84.toWGS84("46.947650 7.440850");
		assertNotNull(wgs84);
		assertEquals(wgs84.getNorthDegrees(), new BigDecimal("46.947650"));
		assertEquals(wgs84.getEastDegrees(), new BigDecimal("7.440850"));
	}
	
	@Test
	public void toWGS84Dm() {
		WGS84 wgs84 = WGS84.toWGS84("N 46° 56.859 E 007° 26.451");
		assertNotNull(wgs84);
		assertEquals(new BigDecimal("46.947650").stripTrailingZeros(), wgs84.getNorthDegrees());
		assertEquals(new BigDecimal("7.440850").stripTrailingZeros(), wgs84.getEastDegrees());
	}
}
