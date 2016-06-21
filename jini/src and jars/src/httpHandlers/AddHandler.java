package httpHandlers;
import java.io.IOException;
import java.io.OutputStream;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import database.H2DatabaseManager;
import tools.Configurations;
import tools.Student;
/**
 * handle HTTP add request
 * @author morg
 */
public class AddHandler implements HttpHandler {
	H2DatabaseManager dataBaseManager; // shared database manager
	public AddHandler(H2DatabaseManager dbmanager) {
		super();
		this.dataBaseManager = dbmanager;
	}
	@Override
	public void handle(HttpExchange t) throws IOException {
		Student student = tools.ParameterParser.getUrlParameters(t.getRequestURI()); // get student parameters from URL
		System.out.println("add request for "+ student .getId()); // log massage
		String response = "";
		if(dataBaseManager.getSize() < Configurations.databaseSizeLimit){ // if data base is not full
			boolean found = dataBaseManager.search(student.getId()); // check if the student already in the data base
			System.out.println("searched "+student.getId()+" "+found); // log massage

			if(!found){ // if not found - add the new student to data base and send ACK response
				dataBaseManager.addStudent(student);
				response = "[ACK] student "+ student+" added to db";
			}
			else{ // if already in data base send NACK response
				response = "[NACK] student "+ student.getId()+" already exist in db";
			}
		}
		else{
			response = "[NACK] data base is full";
		}
		//stream the response to client
		t.sendResponseHeaders(200, response.length());
		OutputStream os = t.getResponseBody();
		os.write(response.getBytes());
		os.close();
	}

}
