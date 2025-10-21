import java.util.ArrayList; // Importing ArrayList for dynamic array functionality
import java.util.List; // Importing List interface for type safety

/**
 * Represents a faculty member in an academic institution.
 */
public class Faculty extends Employee {
    private List<Course> courses; // List to hold assigned courses
    private boolean isTenured;     // Field to indicate if the faculty member is tenured

    /**
     * Constructs a new Faculty member with the specified details.
     *
     * @param name          the name of the faculty member
     * @param birthYear     the birth year of the faculty member
     * @param employeeNumber the employee number for the faculty member
     * @param deptName      the department name of the faculty member
     * @param isTenured     true if the faculty member is tenured; false otherwise
     */
    public Faculty(String name, int birthYear, int employeeNumber, String deptName, boolean isTenured) {
        super(name, birthYear, employeeNumber, deptName); // Call to the superclass constructor
        this.courses = new ArrayList<>(); // Initialize the courses list
        this.isTenured = isTenured; // Set the tenured status
    }

    /**
     * Adds a course to the faculty member's list of assigned courses.
     *
     * @param course the Course to be added
     */
    public void addCourse(Course course) {
        courses.add(course); // Add the specified course to the list
    }

    /**
     * Checks if the faculty member is tenured.
     *
     * @return true if the faculty member is tenured; false otherwise
     */
    public boolean isTenured() {
        return isTenured; // Return the tenured status
    }

    /**
     * Gets the list of courses assigned to the faculty member.
     *
     * @return the list of assigned courses
     */
    public List<Course> getCourses() {
        return courses; // Return the list of assigned courses
    }
}