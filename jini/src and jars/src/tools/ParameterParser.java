package tools;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.util.LinkedHashMap;
import java.util.Map;
/**
 * this class role is to parse url/uri to parameters
 * @author morg
 */
public class ParameterParser {
	/**
	 * convert uri to map
	 * @param uri
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private static Map<String, String> splitQuery(URI uri) throws UnsupportedEncodingException {
	    Map<String, String> query_pairs = new LinkedHashMap<String, String>();
	    String query = uri.getQuery();
	    String[] pairs = query.split("&");
	    for (String pair : pairs) {
	        int idx = pair.indexOf("=");
	        query_pairs.put(URLDecoder.decode(pair.substring(0, idx), "UTF-8"), URLDecoder.decode(pair.substring(idx + 1), "UTF-8"));
	    }
	    return query_pairs;
	}
	/**
	 * convert uri to student
	 * @param uri
	 * @return
	 */
	public static Student getUrlParameters(URI uri){
		Map<String, String> parameterMap = null;
		try {
			parameterMap = splitQuery(uri);
			return new Student(parameterMap.get("id"), parameterMap.get("name"), parameterMap.get("gender"), parameterMap.get("grade"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
}
