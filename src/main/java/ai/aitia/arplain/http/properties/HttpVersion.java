package ai.aitia.arplain.http.properties;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum HttpVersion {

	HTTP1_1("HTTP/1.1", 1, 1); //have to be in order
	
	public final String literal;
	public final int major;
	public final int minor;
	
	private HttpVersion(final String literal, final int major, final int minor) {
		this.literal = literal;
		this.major = major;
		this.minor = minor;
	}
	
	private static final Pattern versionRegexPattern = Pattern.compile("HTTP/(?<major>\\d+).(?<minor>\\d+)");
	
	public static boolean checkPattern(final String literal) {
		Matcher matcher = versionRegexPattern.matcher(literal);
		if (!matcher.find() || matcher.groupCount() != 2) {
			return false;
		}
		return true;
	}
	
	public static HttpVersion getBestCompatibleVersion(final String literal) {
		Matcher matcher = versionRegexPattern.matcher(literal);
		if (!matcher.find() || matcher.groupCount() != 2) {
			return null;
		}
		int major = Integer.parseInt(matcher.group("major"));
		int minor = Integer.parseInt(matcher.group("minor"));
		
		HttpVersion tempBest = null;
		for (HttpVersion version : HttpVersion.values()) {
			if (version.literal.equals(literal)) {
				return version;
			} else {
				if (version.major == major) {
					if (version.minor < minor) {
						tempBest = version;
					}
				}
			}
		}
		return tempBest;
	}
	
}
