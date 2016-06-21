package interfaces;

import java.util.ArrayList;
import tools.Student;

/**
 *	H2 SQL data base manager interface
 * @author Filippo-TheAppExpert
 */
public interface ICrud {
    
    public void addStudent(Student student);
    
    public void removeStudent(String keyword);
    
    public ArrayList<Student> getAllStudents();
    
    public void updateStudent(Student student);
    
    public boolean search(String keyword);

    public Student getStudent(String id);
    
}
