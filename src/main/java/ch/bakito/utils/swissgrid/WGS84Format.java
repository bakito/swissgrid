/**
 * 
 */
package ch.bakito.utils.swissgrid;

import java.util.regex.Pattern;

/**
 * @author marc
 * 
 */
public enum WGS84Format {
	Decimal("^(\\d{1,2}\\.\\d*)[ ]+(\\d{1,3}\\.\\d*)$"),

	DM("^[NS][ ]+(\\d{1,2})°[ ]+(\\d{1,2}\\.\\d{1,3})[ ]+[EW][ ]+(\\d{1,3})°[ ]+(\\d{1,2}\\.\\d{1,3})$"),

	DMS("^[NS][ ]+(\\d{1,2})°[ ]+(\\d{1,2})'[ ]+(\\d{1,2}\\.\\d{1,3})(\"|'')[ ]"
			+ "+[EW][ ]+(\\d{1,3})°[ ]+(\\d{1,2})'[ ]+(\\d{1,2}\\.\\d{1,3})(\"|'')$");

	private final Pattern pattern;

	private WGS84Format(String pattern) {
		this.pattern = Pattern.compile(pattern);
	}

	/**
	 * @return the pattern
	 */
	public Pattern getPattern() {
		return pattern;
	}
}
