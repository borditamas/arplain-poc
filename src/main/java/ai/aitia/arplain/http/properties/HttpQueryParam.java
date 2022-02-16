package ai.aitia.arplain.http.properties;

public class HttpQueryParam {

	private final String key;
	private String value;
	private final boolean mandatory;
	
	public HttpQueryParam(final String key, final String value, final boolean mandatory) {
		this.key = key;
		this.value = value;
		this.mandatory = mandatory;
	}

	public String getKey() { return key; }
	public String getValue() { return value; }
	public boolean isMandatory() { return mandatory; }

	public void setValue(final String value) { this.value = value; }
}
