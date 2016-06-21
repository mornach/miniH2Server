
package database;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import tools.Student;
import tools.Configurations;

/**
 * H2DatabaseManager is a class with all the responsibilities for the SQL data base such as creating, adding, removing, searching and more.
 * this class based on class DataManager from https://www.dropbox.com/s/2hwlaaz2egiznon/EmbeddedDatabaseInJava.zip?dl=0 
 * @author morg
 */
public class H2DatabaseManager implements interfaces.ICrud {
	private Connection connection;
	private Statement stmt;
	static int size = 0 ;
	private static final String CREATE_TABLE = "CREATE TABLE DBJINI ("
			+ "name VARCHAR(255),"
			+ "gender VARCHAR(255),"
			+ "grade VARCHAR(255),"
			+ "sid VARCHAR(255) PRIMARY KEY);";
	public H2DatabaseManager() {
		openConnection();
	}
	
	public void openConnection() {
		try {
			Class.forName("org.h2.Driver");
			connection = DriverManager.getConnection(Configurations.DB_URL, Configurations.DB_USERNAME, Configurations.DB_PASSWORD);
			stmt = connection.createStatement();
			stmt.execute(CREATE_TABLE);
			syncSize();
		} catch (Exception ex) {
		}
	}

	@Override
	public void addStudent(Student student) {
		try {
			String sql = "INSERT INTO DBJINI (name, gender, grade, sid) VALUES ('" + student.getName() + "',"
					+ " '" + student.getGender() + "', '" + student.getGrade() + "', '" + student.getId() + "')";
			stmt.execute(sql);
			size++;
		} catch (SQLException ex) {
			Logger.getLogger(H2DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	@Override
	public Student getStudent(String id) {
		Student student = new Student();
		try {
			String sql = "SELECT * FROM DBJINI "+ " where sid =  '" + id + "'";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				student.setId(rs.getString("sid"));
				student.setName(rs.getString("name"));
				student.setGender(rs.getString("gender"));
				student.setGrade(rs.getString("grade"));
			}
		} catch (SQLException ex) {
			Logger.getLogger(H2DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
		}
		return student;
	}

	@Override
	public void removeStudent(String keyword) {
		try {
			String sql = "DELETE FROM DBJINI where sid =  '" + keyword + "'";
			stmt.execute(sql);
			size--;
		} catch (SQLException ex) {
			Logger.getLogger(H2DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	@Override
	public ArrayList<Student> getAllStudents() {
		ArrayList<Student> students = new ArrayList<>();
		try {
			String sql = "SELECT * FROM DBJINI";
			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				Student student = new Student();
				student.setId(rs.getString("sid"));
				student.setName(rs.getString("name"));
				student.setGender(rs.getString("gender"));
				student.setGrade(rs.getString("grade"));
				students.add(student);
			}
			return students;
		} catch (SQLException ex) {
			Logger.getLogger(H2DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
		}
		return students;
	}
	@Override
	public void updateStudent(Student student) {
		try {
			String sql = "UPDATE DBJINI set name = '" + student.getName() + "', gender = '" + student.getGender() + "',"
					+ " grade = '" + student.getGrade() + "' where sid =  '" + student.getId() + "'";
			stmt.execute(sql);
		} catch (SQLException ex) {
			Logger.getLogger(H2DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	@Override
	public boolean search(String keyword) {
		try {
			String sql = "SELECT * FROM DBJINI WHERE sid = '" + keyword + "'";
			ResultSet rs = stmt.executeQuery(sql);
			int size = 0;
			if (rs != null) {
				rs.beforeFirst();
				rs.last();
				size = rs.getRow();
			}

			return size > 0;
		} catch (SQLException ex) {
			Logger.getLogger(H2DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
		}
		return false;
	}
	/**
	 * sync the size of the data base
	 */
	private void syncSize(){
		String sql = "SELECT * FROM DBJINI";
		ResultSet rs;
		try {
			rs = stmt.executeQuery(sql);
			size = 0;
			if (rs != null) {
				rs.beforeFirst();
				rs.last();
				size = rs.getRow();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	public int getSize() {
		return size;
	}

}