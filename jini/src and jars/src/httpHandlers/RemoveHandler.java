package httpHandlers;
import java.io.IOException;
import java.io.OutputStream;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import database.H2DatabaseManager;
import tools.Student;

public class RemoveHandler implements HttpHandler {
	H2DatabaseManager dataBaseManager;
	public RemoveHandler(H2DatabaseManager dbmanager) {
		super();
		this.dataBaseManager = dbmanager;
	}
	@Override
	public void handle(HttpExchange t) throws IOException {
		Student student = tools.ParameterParser.getUrlParameters(t.getRequestURI()); // get student parameters from URL
		System.out.println("remove request for "+ student .getId()); // log massage
		boolean found = dataBaseManager.search(student.getId()); // check if the student already in the data base
		System.out.println("searched "+student.getId()+" "+found); // log massage
		String response = "";
		if(found){ // if not found - remove the student from data base and send ACK response
			dataBaseManager.removeStudent(student.getId());
			response = "[ACK] student "+ student.getId()+" removed from db";
		}
		else{ // if not in data base send NACK response
			response = "[NACK] student "+ student.getId()+" not found in db";
		}
		
		//stream the response to client
		t.sendResponseHeaders(200, response.length());
		OutputStream os = t.getResponseBody();
		os.write(response.getBytes());
		os.close();
	}

}
