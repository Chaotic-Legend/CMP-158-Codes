/**
 * Represents a student in an educational institution.
 * Inherits attributes and methods from the Person class.
 */
public class Student extends Person {
    private String major;          // Field to hold the student's major subject
    private boolean isGraduate;    // Field to indicate if the student is a graduate student

    /**
     * Constructs a new Student with the specified details.
     *
     * @param name        the name of the student
     * @param birthYear   the birth year of the student
     * @param major       the major subject of the student
     * @param isGraduate  indicates if the student is a graduate student
     */
    public Student(String name, int birthYear, String major, boolean isGraduate) {
        super(name, birthYear); // Call to the constructor of Person to initialize common attributes
        this.major = major;     // Set the student's major
        this.isGraduate = isGraduate; // Set the graduate status
    }

    /**
     * Gets the major subject of the student.
     *
     * @return the student's major
     */
    public String getMajor() {
        return major; // Return the major of the student
    }

    /**
     * Checks if the student is a graduate student.
     *
     * @return true if the student is a graduate; false otherwise
     */
    public boolean isGraduate() {
        return isGraduate; // Return the graduate status
    }
}