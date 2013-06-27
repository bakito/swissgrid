package ch.bakito.utils.swissgrid;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;

// http://boulter.com/gps/#N%2046%B0%2056.859%20E%20007%B0%2026.541
public class WGS84Test extends Assert {
	@Test
	public void toWGS84Decimal() {
		WGS84 wgs84 = WGS84.toWGS84("46.947650 7.440850");
		assertEquals("46.947650", "7.440850", wgs84);
	}

	@Test
	public void toWGS84Dm() {
		WGS84 wgs84 = WGS84.toWGS84("N 46° 56.859 E 007° 26.451");
		assertEquals("46.947650", "7.440850", wgs84);
	}

	@Test
	public void toWGS84Dms() {
		WGS84 wgs84 = WGS84.toWGS84("N 46° 56' 51.540\" E 007° 26' 27.060\"");
		assertEquals("46.947650", "7.440850", wgs84);
	}

	private void assertEquals(String north, String east, WGS84 wgs84) {
		assertNotNull(wgs84);
		assertEquals(new BigDecimal(north).stripTrailingZeros(), wgs84.getNorthDegrees());
		assertEquals(new BigDecimal(east).stripTrailingZeros(), wgs84.getEastDegrees());
	}
}
