package httpHandlers;

import java.io.IOException;
import java.io.OutputStream;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import database.H2DatabaseManager;
/**
 * handle HTTP default request - welcome 
 * @author morg
 */
public class DefaultHandler implements HttpHandler {
	H2DatabaseManager dataBaseManager;
	public DefaultHandler(H2DatabaseManager dbmanager) {
		super();
		this.dataBaseManager = dbmanager;
	}	

	@Override
	public void handle(HttpExchange t) throws IOException {
		System.out.println("default request ");  // log massage
		String response = "welcome to JINI MORG student http server\n"
				+ "to see all the data base add '/info' to url\n"
				+ "to add a new student add the template '/add?id=12324&name=Israel Israeli&gender=male&grade=90' to url\n"
				+ "to remove a student add the template '/remove?id=12324' to url\n"
				+ "to see student details add the template '/get?id=12324' to url\n";
		//stream the response to client
		t.sendResponseHeaders(200, response.length());
		OutputStream os = t.getResponseBody();
		os.write(response.getBytes());
		os.close();
	}

}
