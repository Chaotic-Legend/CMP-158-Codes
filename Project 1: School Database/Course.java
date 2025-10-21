/**
 * Represents a course in an academic program.
 */
public class Course {
    private String dept;             // Department offering the course
    private int courseNumber;        // Course number (e.g., 101, 202)
    private int credits;             // Number of credits for the course
    private boolean isGraduate;       // Indicates if the course is for graduate students

    /**
     * Constructs a new Course with the specified details.
     *
     * @param dept          the department offering the course
     * @param courseNumber  the course number
     * @param credits       the number of credits awarded for the course
     * @param isGraduate    true if the course is for graduate students; false otherwise
     */
    public Course(String dept, int courseNumber, int credits, boolean isGraduate) {
        this.dept = dept;
        this.courseNumber = courseNumber;
        this.credits = credits;
        this.isGraduate = isGraduate;
    }

    /**
     * Gets the department offering the course.
     *
     * @return the department
     */
    public String getDept() {
        return dept;
    }

    /**
     * Gets the course number.
     *
     * @return the course number
     */
    public int getCourseNumber() {
        return courseNumber;
    }

    /**
     * Gets the number of credits for the course.
     *
     * @return the number of credits
     */
    public int getCredits() {
        return credits;
    }

    /**
     * Checks if the course is for graduate students.
     *
     * @return true if the course is graduate; false otherwise
     */
    public boolean isGraduate() {
        return isGraduate;
    }
}