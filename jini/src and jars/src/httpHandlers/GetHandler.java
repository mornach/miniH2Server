package httpHandlers;
import java.io.IOException;
import java.io.OutputStream;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
/**
 * handle HTTP get request
 * @author morg
 */
import database.H2DatabaseManager;
import tools.Student;

public class GetHandler implements HttpHandler {
	H2DatabaseManager dataBaseManager;
	public GetHandler(H2DatabaseManager dbmanager) {
		super();
		this.dataBaseManager = dbmanager;
	}	
	public void handle(HttpExchange t) throws IOException {
		Student student = tools.ParameterParser.getUrlParameters(t.getRequestURI()); // get student parameters from URL
		System.out.println("get request for "+ student .getId()); // log massage
		boolean found = dataBaseManager.search(student.getId()); // check if the student already in the data base
		System.out.println("searched "+student.getId()+" "+found); // log massage
		String response = "";
		Student studentRet = null; // the student from the data base
		if(found){// if found - get the student details from data base and send ACK response
			studentRet = dataBaseManager.getStudent(student.getId());
			response = "[ACK] student found in db, info: "+studentRet;
		}
		else{ // if already in data base send NACK response
			response = "[NACK] student "+ student.getId()+" not found in db";
		}
		
		//stream the response to client
		t.sendResponseHeaders(200, response.length());
		OutputStream os = t.getResponseBody();
		os.write(response.getBytes());
		os.close();
	}
}