package httpHandlers;

import java.io.IOException;
import java.io.OutputStream;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import database.H2DatabaseManager;
import tools.Student;
/**
 * handle HTTP update request
 * @author morg
 */
public class UpdateHandler implements HttpHandler {
	H2DatabaseManager dataBaseManager;  // shared database manager
	public UpdateHandler(H2DatabaseManager dbmanager) {
		super();
		this.dataBaseManager = dbmanager;
	}
	@Override
	public void handle(HttpExchange t) throws IOException {
		Student student = tools.ParameterParser.getUrlParameters(t.getRequestURI()); // get student parameters from URL
		System.out.println("update request for "+ student.getId()); // log massage
		boolean found = dataBaseManager.search(student.getId()); // check if the student already in the data base
		System.out.println("searched "+student.getId()+" "+found); // log massage
		String response = "";
		if(found){ // if found - update the student details and send ACK response
			dataBaseManager.updateStudent(student);
			response = "[ACK] student "+ student+" details updated";
		}
		else{ // if not in data base send NACK response
			response = "[NACK] student "+ student.getId()+" not exist in db";
		}
		//stream the response to client
		t.sendResponseHeaders(200, response.length());
		OutputStream os = t.getResponseBody();
		os.write(response.getBytes());
		os.close();
	}

}
