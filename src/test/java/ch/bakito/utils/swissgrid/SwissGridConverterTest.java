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
		CH1903To ch1903 = SGC.toCH1903(new WGS84TTo(new BigDecimal(46 * 60 * 60 + 2 * 60 + 38.87), new BigDecimal(8 * 60 * 60 + 43 * 60
				+ 49.79)));
		
		assertEquals(100000, ch1903.getX());
		assertEquals(700000, ch1903.getY());
	}

	@Test
	public void toWGS84() {
		WGS84TTo wgs84 = SGC.toWGS84(new CH1903To(100000, 700000));
		assertEquals("N 46° 2.647", wgs84.getNorthString());
		assertEquals("E 8° 43.829", wgs84.getEastString());
	}

}
