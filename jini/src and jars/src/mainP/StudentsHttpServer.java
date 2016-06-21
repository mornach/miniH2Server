package mainP;
import java.net.InetSocketAddress;
import com.sun.net.httpserver.HttpServer;
import database.H2DatabaseManager;
import httpHandlers.*;
import httpHandlers.UpdateHandler;
import tools.Configurations;
/**
 * set up a small students system HTTP server with H2 data base 
 * JDK 1.8+
 * @author morg
 * @version 1.0
 */
public class StudentsHttpServer {

	public static void main(String[] args) throws Exception {
		HttpServer server = HttpServer.create(new InetSocketAddress(Configurations.PORT), 0);
		//log massage
		System.out.println("Jini morg student server on \n[URL] "+Configurations.SERVERURL+" \n[PORT] "+Configurations.PORT);
		database.H2DatabaseManager dbmanager = new H2DatabaseManager();
		
		// adding contexts
		server.createContext("/home", new DefaultHandler(dbmanager));
		server.createContext("/info", new InfoHandler(dbmanager));
		server.createContext("/get", new GetHandler(dbmanager));
		server.createContext("/add", new AddHandler(dbmanager));
		server.createContext("/update", new UpdateHandler(dbmanager));
		server.createContext("/remove", new RemoveHandler(dbmanager));
		
		server.setExecutor(null); // creates a default executor
		server.start();
	}
}