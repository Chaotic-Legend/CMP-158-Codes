/**
 * Represents a person with a name and birth year.
 * This is an abstract class, meaning it cannot be instantiated directly.
 * It serves as a base class for other specific types of people (e.g., Student, Employee).
 */
public abstract class Person {
    private String name;        // Field to hold the person's name
    private int birthYear;      // Field to hold the person's birth year

    /**
     * Constructs a new Person with the specified name and birth year.
     *
     * @param name        the name of the person
     * @param birthYear   the birth year of the person
     */
    public Person(String name, int birthYear) {
        this.name = name;           // Initialize the name field
        this.birthYear = birthYear; // Initialize the birth year field
    }

    /**
     * Gets the name of the person.
     *
     * @return the person's name
     */
    public String getName() {
        return name; // Return the name of the person
    }

    /**
     * Gets the birth year of the person.
     *
     * @return the person's birth year
     */
    public int getBirthYear() {
        return birthYear; // Return the birth year of the person
    }
}