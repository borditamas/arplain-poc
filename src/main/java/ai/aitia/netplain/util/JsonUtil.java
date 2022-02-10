package ai.aitia.netplain.util;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;

public class JsonUtil {

	//=================================================================================================
	// members
	
	private static ObjectMapper mapper = defaultMapper();
	
	//=================================================================================================
	// methods
	
	//-------------------------------------------------------------------------------------------------
	public static JsonNode parse(final String jsonStr) throws IOException {
		return mapper.readTree(jsonStr);
	}
	
	//-------------------------------------------------------------------------------------------------
	public static <A> A fromJson(final JsonNode node, final Class<A> clazz) throws JsonProcessingException {
		return mapper.treeToValue(node, clazz);
	}
	
	//-------------------------------------------------------------------------------------------------
	public static JsonNode toJson(final Object obj) {
		return mapper.valueToTree(obj);
	}
	
	//-------------------------------------------------------------------------------------------------
	public static String stringify(final JsonNode node) throws JsonProcessingException {
		return generateJson(node, false);
	}
	
	//-------------------------------------------------------------------------------------------------
	public static String stringifyPretty(final JsonNode node) throws JsonProcessingException {
		return generateJson(node, true);
	}
 	
	//=================================================================================================
	// assistant methods
	
	//-------------------------------------------------------------------------------------------------
	private static ObjectMapper defaultMapper() {
		final ObjectMapper om = new ObjectMapper();
		om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		return om;
	}
	
	//-------------------------------------------------------------------------------------------------
	private static String generateJson(final Object obj, final boolean pretty) throws JsonProcessingException {
		ObjectWriter writer = mapper.writer();
		if (pretty) {
			writer = writer.with(SerializationFeature.INDENT_OUTPUT);
		}
		return writer.writeValueAsString(obj);	
	}
}
