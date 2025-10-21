/**
 * Represents a general staff member, inheriting from the Employee class.
 * Contains specific information about the staff member's duty.
 */
public class GeneralStaff extends Employee {
    private String duty; // Field to hold the duty of the general staff member

    /**
     * Constructs a new GeneralStaff member with the specified details.
     *
     * @param name          the name of the general staff member
     * @param birthYear     the birth year of the general staff member
     * @param employeeNumber the employee number of the general staff member
     * @param deptName      the department name of the general staff member
     * @param duty          the specific duty of the general staff member
     */
    public GeneralStaff(String name, int birthYear, int employeeNumber, String deptName, String duty) {
        super(name, birthYear, employeeNumber, deptName); // Call to the superclass constructor
        this.duty = duty; // Initialize the duty field
    }

    /**
     * Gets the duty of the general staff member.
     *
     * @return the duty of the general staff member
     */
    public String getDuty() {
        return duty; // Return the duty of the general staff member
    }
}