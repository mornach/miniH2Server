package httpHandlers;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import database.H2DatabaseManager;
import tools.Student;
/**
 * handle HTTP info request
 * @author morg
 */
public class InfoHandler implements HttpHandler {
	H2DatabaseManager dataBaseManager; // shared database manager
	public InfoHandler(H2DatabaseManager dbmanager) {
		super();
		this.dataBaseManager = dbmanager;
	}
	public void handle(HttpExchange t) throws IOException {
		System.out.println("info request"); // log massage
		ArrayList<Student> list = dataBaseManager.getAllStudents(); // get all students to ArrayList
		String response = "[ACK] all studants ("+list.size()+") :\n";
		for (Iterator<Student> iterator = list.iterator(); iterator.hasNext();) {
			Student student = (Student) iterator.next();
			response+=student+"\n";
		}
		//stream the response to client
		t.sendResponseHeaders(200, response.length());
		OutputStream os = t.getResponseBody();
		os.write(response.getBytes());
		os.close();
	}
}