/**
 * Represents an employee in an organization.
 * Inherits attributes and methods from the Person class.
 */
public class Employee extends Person {
    private int employeeNumber; // Unique identifier for the employee
    private String deptName;     // Name of the department the employee belongs to

    /**
     * Constructs a new Employee with the specified details.
     *
     * @param name          the name of the employee
     * @param birthYear     the birth year of the employee
     * @param employeeNumber the unique employee number
     * @param deptName      the name of the department
     */
    public Employee(String name, int birthYear, int employeeNumber, String deptName) {
        super(name, birthYear); // Call to the superclass constructor to initialize common attributes
        this.employeeNumber = employeeNumber; // Set the employee number
        this.deptName = deptName; // Set the department name
    }

    /**
     * Gets the employee's unique identifier.
     *
     * @return the employee number
     */
    public int getEmployeeNumber() {
        return employeeNumber; // Return the employee number
    }

    /**
     * Gets the name of the department the employee belongs to.
     *
     * @return the department name
     */
    public String getDeptName() {
        return deptName; // Return the department name
    }
}