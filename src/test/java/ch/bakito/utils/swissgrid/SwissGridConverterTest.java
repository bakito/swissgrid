/**
 * 
 */
package ch.bakito.utils.swissgrid;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author marc
 * 
 */
public class SwissGridConverterTest extends Assert {

	private final SwissGridConverter SGC = new SwissGridConverter();

	@Test
	public void toCH1903() {
		CH1903 ch1903 = SGC.toCH1903(new WGS84(new BigDecimal(46 * 60 * 60 + 2 * 60 + 38.87), new BigDecimal(8 * 60
				* 60 + 43 * 60 + 49.79)));

		assertEquals(100000, ch1903.getX());
		assertEquals(700000, ch1903.getY());
	}

	@Test
	public void toWGS84DM() {
		WGS84 wgs84 = SGC.toWGS84(new CH1903(100000, 700000));
		assertEquals("N 46° 2.647'", wgs84.getNorthString(WGS84Format.DM));
		assertEquals("E 8° 43.829'", wgs84.getEastString(WGS84Format.DM));
	}

	@Test
	public void toWGS84DMS() {
		WGS84 wgs84 = SGC.toWGS84(new CH1903(100000, 700000));
		assertEquals("N 46° 2' 38.856'", wgs84.getNorthString(WGS84Format.DMS));
		assertEquals("E 8° 43' 49.797'", wgs84.getEastString(WGS84Format.DMS));
	}

	@Test
	public void toWGS84Decimal() {
		WGS84 wgs84 = SGC.toWGS84(new CH1903(100000, 700000));
		assertEquals("N 46.0441267778", wgs84.getNorthString(WGS84Format.Decimal));
		assertEquals("E 8.7304993333", wgs84.getEastString(WGS84Format.Decimal));
	}

}
