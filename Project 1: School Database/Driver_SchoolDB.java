// My YouTube Project Video: https://youtu.be/S5SMEminoqI

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.*;
import java.util.*;

public class Driver_SchoolDB {

    public static void main(String[] args) {
        // Load data from the file and create the object lists
        List<Course> courses = loadCoursesFromFile("SchoolDB_Initial.txt");
        List<Person> persons = loadPersonsFromFile("SchoolDB_Initial.txt");

        // Create a set for unique employees to avoid duplicates
        Set<Employee> employeeSet = new HashSet<>(filterEmployees(persons));
        
        List<Employee> employees = new ArrayList<>(employeeSet);
        List<GeneralStaff> generalStaffList = filterGeneralStaff(persons);
        List<Faculty> facultyMembers = filterFaculty(persons);
        List<Student> students = filterStudents(persons);

        // Sort the lists
        Collections.sort(courses, Comparator.comparing(Course::getDept).thenComparing(Course::getCourseNumber));
        Collections.sort(employees, Comparator.comparing(Employee::getName));
        Collections.sort(generalStaffList, Comparator.comparing(GeneralStaff::getName));
        Collections.sort(facultyMembers, Comparator.comparing(Faculty::getName));
        Collections.sort(students, Comparator.comparing(Student::getName));
        Collections.sort(persons, Comparator.comparing(Person::getName));

        // Display file content
        displayPlainTextFileContent("SchoolDB_Initial.txt");

        // Display organized school database information
        displaySchoolDatabaseInfo(courses, persons, employees, generalStaffList, facultyMembers, students);

        // Display menu
        displayMenu(courses, persons, employees, generalStaffList, facultyMembers, students);

        // Write all objects to output file
        writeObjectsToFile("SchoolDB_Initial.txt", courses, persons, employees, generalStaffList, facultyMembers, students);
    }
    
    // Method to display the contents of a plain text file
    public static void displayPlainTextFileContent(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            System.out.println("Displaying Data From: " + filename + "\n");
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
            System.out.println();
        } catch (IOException e) {
        e.printStackTrace();
        }
    }

    // Updated writeObjectsToFile method to include any new objects or assignments
    public static void writeObjectsToFile(String filename, List<Course> courses, List<Person> persons, List<Employee> employees, List<GeneralStaff> generalStaff, List<Faculty> faculty, List<Student> students) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            // Write all course data to the file
            for (Course course : courses) {
                bw.write(String.format("Course: %s, %d, %d, %b\n", course.getDept(), course.getCourseNumber(), course.getCredits(), course.isGraduate()));
            }
        // Write all persons data to the file
        for (Person person : persons) {
            if (person instanceof Faculty) {
                Faculty f = (Faculty) person;
                bw.write(String.format("Faculty: %s, %d, %d, %s, %s\n", f.getName(), f.getBirthYear(), f.getEmployeeNumber(), f.getDeptName(), f.isTenured() ? "Yes" : "No"));
                // Removed the course output for faculty
            } else if (person instanceof Student) {
                Student s = (Student) person;
                bw.write(String.format("Student: %s, %d, %s, %s\n", s.getName(), s.getBirthYear(), s.getMajor(), s.isGraduate() ? "Yes" : "No"));
                // Removed the course output for students
            } else if (person instanceof GeneralStaff) {
                GeneralStaff g = (GeneralStaff) person;
                bw.write(String.format("GeneralStaff: %s, %d, %d, %s, %s\n", g.getName(), g.getBirthYear(), g.getEmployeeNumber(), g.getDeptName(), g.getDuty()));
            } else if (person instanceof Employee) {
                Employee e = (Employee) person;
                bw.write(String.format("Employee: %s, %d, %d, %s\n", e.getName(), e.getBirthYear(), e.getEmployeeNumber(), e.getDeptName()));
            }
        }
        System.out.println("\nAll Data Was Written To: " + filename + "\n");
    } catch (IOException e) {
        e.printStackTrace();
    }
}

    // Interactive menu for adding and manipulating data
    public static void displayMenu(List<Course> courses, List<Person> persons, List<Employee> employees,
                                    List<GeneralStaff> generalStaff, List<Faculty> faculty, List<Student> students) {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("\nUSER DATABASE MENU");
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
            System.out.println("1. Add One New Course");
            System.out.println("2. Add One New Faculty Member");
            System.out.println("3. Add One New General Staff Member");
            System.out.println("4. Add One New Student");
            System.out.println("5. Add Courses To Faculty");
            System.out.println("6. Add Courses To Student");
            System.out.println("7. Display New Database");
            System.out.println("8. Write New Data To Text File");
            System.out.println("9. Exit");
            System.out.print("\nEnter An Option: ");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine()); // Read input as a string and parse it
            } catch (NumberFormatException e) {
                System.out.println("\nInvalid Input. Please enter a number corresponding to the menu options.");
                continue; // Skip the rest of the loop if the input is invalid
            }

            switch (choice) {
                case 1:
                    addCourses(courses, scanner);
                    break;
                case 2:
                    addFaculty(faculty, employees, persons, scanner);
                    break;
                case 3:
                    addGeneralStaff(generalStaff, employees, persons, scanner);
                    break;
                case 4:
                    addStudents(students, persons, scanner);
                    break;
                case 5:
                    addCoursesToFaculty(faculty, courses, scanner);
                    break;
                case 6:
                    addCoursesToStudent(students, courses, scanner);
                    break;
                case 7:
                    displaySchoolDatabaseInfo(courses, persons, employees, generalStaff, faculty, students);
                    break;
                case 8:
                    writeObjectsToFile("SchoolDB_Initial.txt", courses, persons, employees, generalStaff, faculty, students);
                    break;
                case 9:
                    exit = true;
                    System.out.println("\nExiting The Program. Thank You!");
                    break;
                default:
                    System.out.println("\nInvalid Input. Please enter a number corresponding to the menu options.\n");
            }
        }
        scanner.close(); // Closing the scanner when done
    }

    // Methods for adding data
    public static void addCourses(List<Course> courses, Scanner scanner) {
        System.out.println("\nLet's Add A New Course.\n");
        System.out.print("Enter Course Department: ");
        String dept = scanner.nextLine(); // Changed to nextLine() to avoid issues
        System.out.print("Enter Course Number: ");
        int courseNumber = scanner.nextInt();
        System.out.print("Enter Number of Credits: ");
        int credits = scanner.nextInt();
        System.out.print("Is This A Graduate Course? (true / false): ");
        boolean isGraduate = scanner.nextBoolean();
        scanner.nextLine(); // Consume the newline character after boolean input

        // Add the new course
        courses.add(new Course(dept, courseNumber, credits, isGraduate));
        System.out.println("\nCourse Added Successfully!\n");

        // Sort the courses after adding a new one
        Collections.sort(courses, Comparator.comparing(Course::getDept)
                .thenComparing(Course::getCourseNumber));

        // Print the updated course list
        System.out.println("Updated Course List:");
        for (Course course : courses) {
            System.out.printf("Course: %s-%d | Number of Credits: %02d | %s\n",
                    course.getDept(), course.getCourseNumber(), course.getCredits(),
                    course.isGraduate() ? "Graduate" : "Undergraduate");
        }
    }
    public static void addFaculty(List<Faculty> faculty, List<Employee> employees, List<Person> persons, Scanner scanner) {
    System.out.println("\nLet's Add A New Faculty Member.\n");
    System.out.print("Enter Faculty's Full Name: ");
    String name = scanner.nextLine().trim(); // Capture full name including spaces

    int birthYear = 0;
    while (true) {
        System.out.print("Enter Faculty's Birth Year: ");
        if (scanner.hasNextInt()) {
            birthYear = scanner.nextInt();
            if (String.valueOf(birthYear).length() == 4) {
                break; // Exit the loop if a valid four-digit year is entered
            } else {
                System.out.println("\nPlease enter a valid four-digit birth year.");
            }
        } else {
            System.out.println("\nInvalid Input. Please enter a numeric four-digit birth year.");
            scanner.next(); // Clear the invalid input
        }
    }
    scanner.nextLine(); // Clear the newline left by nextInt

    int employeeNumber = 0;
    System.out.print("Enter Faculty's Employee Number: ");
    while (true) {
        if (scanner.hasNextInt()) {
            employeeNumber = scanner.nextInt();
            break; // Exit the loop if a valid integer is entered
        } else {
            System.out.println("Invalid input. Please enter a numeric Employee Number.");
            scanner.next(); // Clear the invalid input
        }
    }
    scanner.nextLine(); // Clear the newline left by nextInt

    System.out.print("Enter Faculty's Department: ");
    String department = scanner.nextLine().trim(); // Use nextLine for department

    boolean isTenured = false;
    System.out.print("Is This Faculty Member Tenured? (true / false): ");
    while (true) {
        if (scanner.hasNextBoolean()) {
            isTenured = scanner.nextBoolean();
            break; // Exit loop if valid boolean input is provided
        } else {
            System.out.println("Invalid input. Please enter true or false.");
            scanner.next(); // Clear the invalid input
        }
    }
    scanner.nextLine(); // Clear the newline left by nextBoolean

    // Create the new Faculty member
    Faculty newFaculty = new Faculty(name, birthYear, employeeNumber, department, isTenured);

    // Add to faculty, employees, and persons lists
    faculty.add(newFaculty);
    employees.add(newFaculty);
    persons.add(newFaculty);

    System.out.println("\nFaculty Member Added Successfully!\n");

    // Sort the faculty list after adding a new member
    Collections.sort(faculty, Comparator.comparing(Faculty::getName));

    // Print the updated faculty list
    System.out.println("Updated Faculty List:");
    for (Faculty f : faculty) {
        System.out.printf("Name: %s | Birth Year: %d | Employee Number: %d | Department: %s | Tenured: %b\n",
                f.getName(), f.getBirthYear(), f.getEmployeeNumber(), f.getDeptName(), f.isTenured());
    }
}
    public static void addGeneralStaff(List<GeneralStaff> generalStaff, List<Employee> employees, List<Person> persons, Scanner scanner) {
    System.out.println("\nLet's Add A New General Staff Member.\n");
    System.out.print("Enter General Staff Member's Full Name: ");
    String name = scanner.nextLine().trim(); // Capture full name including spaces

    int birthYear;
    while (true) {
        System.out.print("Enter General Staff Member's Birth Year: ");
        if (scanner.hasNextInt()) {
            birthYear = scanner.nextInt();
            if (String.valueOf(birthYear).length() == 4) {
                scanner.nextLine(); // Clear the newline left by nextInt
                break; // Exit loop if valid four-digit birth year is provided
            } else {
                System.out.println("\nPlease enter a valid four-digit birth year.");
            }
        } else {
            System.out.println("\nInvalid Input. Please enter a numeric four-digit birth year.");
            scanner.next(); // Clear the invalid input
        }
    }

    System.out.print("Enter General Staff Member's Employee Number: ");
    int employeeNumber;
    while (true) {
        if (scanner.hasNextInt()) {
            employeeNumber = scanner.nextInt();
            scanner.nextLine(); // Clear the newline left by nextInt
            break; // Exit loop if valid input is provided
        } else {
            System.out.println("Invalid input. Please enter a numeric Employee Number.");
            scanner.next(); // Clear the invalid input
        }
    }

    System.out.print("Enter General Staff Member's Department: ");
    String department = scanner.nextLine().trim(); // Use nextLine for department
    System.out.print("Enter General Staff Member's Duty: ");
    String duty = scanner.nextLine().trim(); // Use nextLine for duty

    // Create the new General Staff member
    GeneralStaff newStaff = new GeneralStaff(name, birthYear, employeeNumber, department, duty);

    // Add to general staff, employees, and persons lists
    generalStaff.add(newStaff);
    employees.add(newStaff);
    persons.add(newStaff);

    System.out.println("\nGeneral Staff Member Added Successfully!\n");

    // Sort the general staff list after adding a new member
    Collections.sort(generalStaff, Comparator.comparing(GeneralStaff::getName));

    // Print the updated general staff list
    System.out.println("Updated General Staff List:");
    for (GeneralStaff gs : generalStaff) {
        System.out.printf("Name: %s | Birth Year: %d | Employee Number: %d | Department: %s | Duty: %s\n",
                gs.getName(), gs.getBirthYear(), gs.getEmployeeNumber(), gs.getDeptName(), gs.getDuty());
    }
}
    public static void addStudents(List<Student> students, List<Person> persons, Scanner scanner) {
    System.out.println("\nLet's Add A New Student.\n");
    System.out.print("Enter Student's Full Name: ");
    String name = scanner.nextLine().trim(); // Capture full name including spaces

    int birthYear;
    while (true) {
        System.out.print("Enter Student's Birth Year: ");
        if (scanner.hasNextInt()) {
            birthYear = scanner.nextInt();
            if (String.valueOf(birthYear).length() == 4) {
                scanner.nextLine(); // Clear the newline left by nextInt
                break; // Exit loop if valid four-digit birth year is provided
            } else {
                System.out.println("\nPlease enter a valid four-digit birth year.");
            }
        } else {
            System.out.println("\nInvalid Input. Please enter a numeric four-digit birth year.");
            scanner.next(); // Clear the invalid input
        }
    }

    System.out.print("Enter Student's Major: ");
    String major = scanner.nextLine().trim(); // Use nextLine for major
    System.out.print("Is This Student A Graduate? (true / false): ");
    boolean isGraduate;
    while (true) {
        if (scanner.hasNextBoolean()) {
            isGraduate = scanner.nextBoolean();
            scanner.nextLine(); // Clear the newline left by nextBoolean
            break; // Exit loop if valid boolean input is provided
        } else {
            System.out.println("Invalid input. Please enter true or false.");
            scanner.next(); // Clear the invalid input
        }
    }

    // Create the new Student
    Student newStudent = new Student(name, birthYear, major, isGraduate);

    // Add to students list
    students.add(newStudent);

    // Also add to persons list
    persons.add(newStudent); // This is fine if Student extends Person

    System.out.println("\nStudent Added Successfully!\n");

    // Sort the students list after adding a new member
    Collections.sort(students, Comparator.comparing(Student::getName));

    // Print the updated student list
    System.out.println("Updated Student List:");
    for (Student s : students) {
        System.out.printf("Name: %s | Birth Year: %d | Major: %s | Graduate: %b\n",
                s.getName(), s.getBirthYear(), s.getMajor(), s.isGraduate());
    }
}
    public static void addCoursesToFaculty(List<Faculty> faculty, List<Course> courses, Scanner scanner) {
    System.out.print("\nEnter Faculty Member's Full Name: ");
    String facultyName = scanner.nextLine().trim(); // Use nextLine to capture the full name

    // Check if the faculty exists in the list
    Faculty foundFaculty = null;
    for (Faculty f : faculty) {
        if (f.getName().equalsIgnoreCase(facultyName)) {
            foundFaculty = f; // Faculty member found
            break;
        }
    }

    // If the faculty member does not exist, notify the user
    if (foundFaculty == null) {
        System.out.println("\nFaculty Member not found. Please check the name and try again.");
        return; // Exit the method to allow the user to re-enter
    }

    // Logic for assigning courses to the found faculty member
    System.out.println("\nAssigning Courses to Faculty Member: " + foundFaculty.getName() + " (dummy method).\n");
}

public static void addCoursesToStudent(List<Student> students, List<Course> courses, Scanner scanner) {
    System.out.print("\nEnter Student's Full Name: ");
    String studentName = scanner.nextLine().trim(); // Use nextLine to capture the full name

    // Check if the student exists in the list
    Student foundStudent = null;
    for (Student s : students) {
        if (s.getName().equalsIgnoreCase(studentName)) {
            foundStudent = s; // Student found
            break;
        }
    }

    // If the student does not exist, notify the user
    if (foundStudent == null) {
        System.out.println("\nStudent not found. Please check the name and try again.");
        return; // Exit the method to allow the user to re-enter
    }

    // Logic for assigning courses to the found student
    System.out.println("\nAssigning Courses to Student: " + foundStudent.getName() + " (dummy method).\n");
}

    // Method to display organized information based on the sample format
    public static void displaySchoolDatabaseInfo(
        List<Course> courses,
        List<Person> persons,
        List<Employee> employees,
        List<GeneralStaff> generalStaff,
        List<Faculty> faculty,
        List<Student> students) {

        // Print header
        System.out.println("\n************************************************************");
        System.out.println("*****************SCHOOL DATABASE INFORMATION****************");
        System.out.println("************************************************************\n");

        // Display Courses
        System.out.println("== COURSES ==");
        for (int i = 0; i < courses.size(); i++) {
            Course course = courses.get(i);
            System.out.printf("%d. Course: %s-%03d | Number of Credits: %02d | %s\n", 
                i + 1, course.getDept(), course.getCourseNumber(), course.getCredits(), 
                course.isGraduate() ? "Graduate" : "Undergraduate");
        }
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");

        // Display Persons
        System.out.println("== PERSONS ==");
        for (int i = 0; i < persons.size(); i++) {
            Person person = persons.get(i);
            System.out.printf("%d. Person: Name: %s | Birth Year: %d\n", 
                i + 1, person.getName(), person.getBirthYear());
        }
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");

        // Display Employees
        System.out.println("== EMPLOYEES ==");
        for (int i = 0; i < employees.size(); i++) {
            Employee employee = employees.get(i);
            System.out.printf("%d. Employee: Name: %s | Birth Year: %d | Employee Number: %d | Department: %s\n", 
                i + 1, employee.getName(), employee.getBirthYear(), 
                employee.getEmployeeNumber(), employee.getDeptName());
        }
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");

        // Display General Staff
        System.out.println("== GENERAL STAFF ==");
        for (int i = 0; i < generalStaff.size(); i++) {
            GeneralStaff gs = generalStaff.get(i);
            System.out.printf("%d. GeneralStaff: Name: %s | Birth Year: %d | Employee Number: %d | Department: %s | Duty: %s\n", 
                i + 1, gs.getName(), gs.getBirthYear(), 
                gs.getEmployeeNumber(), gs.getDeptName(), gs.getDuty());
        }
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");

        // Display Faculty
        System.out.println("== FACULTY ==");
        for (int i = 0; i < faculty.size(); i++) {
            Faculty f = faculty.get(i);
            System.out.printf("%d. Faculty: Name: %s | Birth Year: %d | Employee Number: %d | Department: %s | Tenured: %s\n", 
                i + 1, f.getName(), f.getBirthYear(), f.getEmployeeNumber(), 
                f.getDeptName(), f.isTenured() ? "Yes" : "No");
        }
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");

        // Display Students
        System.out.println("== STUDENTS ==");
        for (int i = 0; i < students.size(); i++) {
            Student s = students.get(i);
            System.out.printf("%d. Student: Name: %s | Birth Year: %d | Major: %s | Graduate: %s\n", 
                i + 1, s.getName(), s.getBirthYear(), s.getMajor(), s.isGraduate() ? "Yes" : "No");
        }
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
    }

    // Filter employee objects from the person list
    public static List<Employee> filterEmployees(List<Person> persons) {
        List<Employee> employees = new ArrayList<>();
        for (Person person : persons) {
            if (person instanceof Employee) {
                employees.add((Employee) person);
            }
        }
        return employees;
    }

    // Filter faculty objects from the person list
    public static List<Faculty> filterFaculty(List<Person> persons) {
        List<Faculty> faculty = new ArrayList<>();
        for (Person person : persons) {
            if (person instanceof Faculty) {
                faculty.add((Faculty) person);
            }
        }
        return faculty;
    }

    // Filter general staff objects from the person list
    public static List<GeneralStaff> filterGeneralStaff(List<Person> persons) {
        List<GeneralStaff> generalStaff = new ArrayList<>();
        for (Person person : persons) {
            if (person instanceof GeneralStaff) {
                generalStaff.add((GeneralStaff) person);
            }
        }
        return generalStaff;
    }

    // Filter student objects from the person list
    public static List<Student> filterStudents(List<Person> persons) {
        List<Student> students = new ArrayList<>();
        for (Person person : persons) {
            if (person instanceof Student) {
                students.add((Student) person);
            }
        }
        return students;
    }

    // Load courses from file
    public static List<Course> loadCoursesFromFile(String filename) {
        List<Course> courses = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("Course:")) {
                    String[] parts = line.split(", ");
                    String dept = parts[0].split(": ")[1];
                    int courseNumber = Integer.parseInt(parts[1]);
                    int credits = Integer.parseInt(parts[2]);
                    boolean isGraduate = Boolean.parseBoolean(parts[3]);
                    courses.add(new Course(dept, courseNumber, credits, isGraduate));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return courses;
    }

    // Load persons from file
    public static List<Person> loadPersonsFromFile(String filename) {
        List<Person> persons = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("Faculty:")) {
                    String[] parts = line.split(", ");
                    String name = parts[0].split(": ")[1];
                    int birthYear = Integer.parseInt(parts[1]);
                    int employeeNumber = Integer.parseInt(parts[2]);
                    String deptName = parts[3];
                    boolean isTenured = parts[4].equals("Yes");
                    persons.add(new Faculty(name, birthYear, employeeNumber, deptName, isTenured));
                } else if (line.startsWith("Student:")) {
                    String[] parts = line.split(", ");
                    String name = parts[0].split(": ")[1];
                    int birthYear = Integer.parseInt(parts[1]);
                    String major = parts[2];
                    boolean isGraduate = parts[3].equals("Yes");
                    persons.add(new Student(name, birthYear, major, isGraduate));
                } else if (line.startsWith("GeneralStaff:")) {
                    String[] parts = line.split(", ");
                    String name = parts[0].split(": ")[1];
                    int birthYear = Integer.parseInt(parts[1]);
                    int employeeNumber = Integer.parseInt(parts[2]);
                    String deptName = parts[3];
                    String duty = parts[4];
                    persons.add(new GeneralStaff(name, birthYear, employeeNumber, deptName, duty));
                } else if (line.startsWith("Employee:")) {
                    String[] parts = line.split(", ");
                    String name = parts[0].split(": ")[1];
                    int birthYear = Integer.parseInt(parts[1]);
                    int employeeNumber = Integer.parseInt(parts[2]);
                    String deptName = parts[3];
                    persons.add(new Employee(name, birthYear, employeeNumber, deptName));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return persons;
    }
}

/*
 * SchoolDB_Initial.txt File Format:
 * 
 * To ensure the program reads data correctly, enter each data type in the following format:
 * 
 * 1. Courses:
 *    Format: Course: <Department>, <Course Number>, <Number of Credits>, <Graduate Status>
 *    Example: Course: CMP, 168, 4, false
 *    - <Department>: String (e.g., CMP)
 *    - <Course Number>: Integer (e.g., 168)
 *    - <Number of Credits>: Integer (e.g., 4)
 *    - <Graduate Status>: Boolean (true for Graduate, false for Undergraduate)
 * 
 * 2. Faculty Members:
 *    Format: Faculty: <Name>, <Birth Year>, <Employee Number>, <Department>, <Tenured Status>
 *    Example: Faculty: Jane Smith, 1975, 12345, Computer Science, Yes
 *    - <Name>: String (e.g., Jane Smith)
 *    - <Birth Year>: Integer (e.g., 1975)
 *    - <Employee Number>: Integer (e.g., 12345)
 *    - <Department>: String (e.g., Computer Science)
 *    - <Tenured Status>: Yes or No
 * 
 * 3. Students:
 *    Format: Student: <Name>, <Birth Year>, <Major>, <Graduate Status>
 *    Example: Student: John Doe, 2002, Mathematics, No
 *    - <Name>: String (e.g., John Doe)
 *    - <Birth Year>: Integer (e.g., 2002)
 *    - <Major>: String (e.g., Mathematics)
 *    - <Graduate Status>: Boolean (Yes for Graduate, No for Undergraduate)
 * 
 * 4. General Staff Members:
 *    Format: GeneralStaff: <Name>, <Birth Year>, <Employee Number>, <Department>, <Duty>
 *    Example: GeneralStaff: Lisa Brown, 1985, 54321, Admissions, Clerical
 *    - <Name>: String (e.g., Lisa Brown)
 *    - <Birth Year>: Integer (e.g., 1985)
 *    - <Employee Number>: Integer (e.g., 54321)
 *    - <Department>: String (e.g., Admissions)
 *    - <Duty>: String (e.g., Clerical)
 * 
 * 5. Employees (General entries without specific faculty or staff attributes):
 *    Format: Employee: <Name>, <Birth Year>, <Employee Number>, <Department>
 *    Example: Employee: Mark Davis, 1979, 67890, Maintenance
 *    - <Name>: String (e.g., Mark Davis)
 *    - <Birth Year>: Integer (e.g., 1979)
 *    - <Employee Number>: Integer (e.g., 67890)
 *    - <Department>: String (e.g., Maintenance)
 * 
 * Note: Ensure each entry starts with the correct prefix (Course:, Faculty:, Student:, GeneralStaff:, Employee:)
 * and that values are separated by commas. Each entry should be on its own line.
 */