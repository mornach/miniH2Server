package tools;
/**
 * student details
 * @author morg
 */
public class Student {
	String id, name= "null" ,gender = "null", grade = "null";
	public Student() {
		super();
		this.id = "noid";
		this.name = "noname";
		this.gender = "nogender";
		this.grade = "nograde";
	}
	public Student(String id, String name, String gender, String average) {
		super();
		this.id = id;
		this.name = name;
		this.gender = gender;
		this.grade = average;
	}

	@Override
	public String toString() {
		return "Student [id=" + id + ", name=" + name + ", gender=" + gender + ", average=" + grade + "]";
	}

	public byte[] getBytes() {
		return (id + " " + name + " " + gender + " " + grade).getBytes();
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getGender() {
		return gender;
	}

	public String getGrade() {
		return grade;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public void setGrade(String average) {
		this.grade = average;
	}
	
}
