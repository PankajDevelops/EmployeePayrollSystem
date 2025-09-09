package Projects.EmployeePayrollSystem;

import java.sql.*;
import java.util.*;

// Abstract Employee
abstract class Employee {
    private String name;
    private int id;

    public Employee(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public abstract double calculateSalary();

    public abstract String getType();

    @Override
    public String toString() {
        return "Employee[name: " + name + ", id = " + id + ", salary = " + calculateSalary() + "]";
    }
}

// Full-time employee
class FullTimeEmployee extends Employee {
    private double monthlySalary;

    public FullTimeEmployee(String name, int id, double monthlySalary) {
        super(name, id);
        this.monthlySalary = monthlySalary;
    }

    @Override
    public double calculateSalary() {
        return monthlySalary;
    }

    @Override
    public String getType() {
        return "FullTime";
    }
}

// Part-time employee
class PartTimeEmployee extends Employee {
    private int hoursWorked;
    private double hourlyRate;

    public PartTimeEmployee(String name, int id, int hoursWorked, double hourlyRate) {
        super(name, id);
        this.hoursWorked = hoursWorked;
        this.hourlyRate = hourlyRate;
    }

    @Override
    public double calculateSalary() {
        return hourlyRate * hoursWorked;
    }

    @Override
    public String getType() {
        return "PartTime";
    }
}

// PayrollSystem with JDBC
class PayrollSystem {
    private Connection conn;

    public PayrollSystem() {
        try {
            String url = "jdbc:mysql://localhost:3306/payroll_system";
            String user = "root";
            String pass = "";

            conn = DriverManager.getConnection(url, user, pass);
            System.out.println("✅ Database Connected!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addEmployee(Employee employee) {
        String query = "INSERT INTO employees (id, name, type, salary) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, employee.getId());
            ps.setString(2, employee.getName());
            ps.setString(3, employee.getType());
            ps.setDouble(4, employee.calculateSalary());
            ps.executeUpdate();
            System.out.println("✅ Employee added: " + employee.getName());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeEmployee(int id) {
        String query = "DELETE FROM employees WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, id);
            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println("❌ Employee removed with id: " + id);
            } else {
                System.out.println("⚠ Employee not found with id: " + id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void displayEmployees() {
        String query = "SELECT * FROM employees";
        try (Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(query)) {

            boolean found = false;
            while (rs.next()) {
                found = true;
                System.out.println("Employee[id=" + rs.getInt("id")
                        + ", name=" + rs.getString("name")
                        + ", type=" + rs.getString("type")
                        + ", salary=" + rs.getDouble("salary") + "]");
            }

            if (!found) {
                System.out.println("⚠ No employees found in the system.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

// Main with Scanner menu
public class Main {
    public static void main(String[] args) {
        PayrollSystem payroll = new PayrollSystem();
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n===== Payroll System Menu =====");
            System.out.println("1. Add Employee");
            System.out.println("2. Remove Employee");
            System.out.println("3. Display Employees");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");

            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter Employee ID: ");
                    int id = sc.nextInt();
                    sc.nextLine(); // consume newline

                    System.out.print("Enter Employee Name: ");
                    String name = sc.nextLine();

                    System.out.print("Enter Employee Type (1 = FullTime, 2 = PartTime): ");
                    int type = sc.nextInt();

                    if (type == 1) {
                        System.out.print("Enter Monthly Salary: ");
                        double salary = sc.nextDouble();
                        Employee fte = new FullTimeEmployee(name, id, salary);
                        payroll.addEmployee(fte);
                    } else {
                        System.out.print("Enter Hours Worked: ");
                        int hours = sc.nextInt();
                        System.out.print("Enter Hourly Rate: ");
                        double rate = sc.nextDouble();
                        Employee pte = new PartTimeEmployee(name, id, hours, rate);
                        payroll.addEmployee(pte);
                    }
                    break;

                case 2:
                    System.out.print("Enter Employee ID to Remove: ");
                    int removeId = sc.nextInt();
                    payroll.removeEmployee(removeId);
                    break;

                case 3:
                    System.out.println("Employee Records:");
                    payroll.displayEmployees();
                    break;

                case 4:
                    System.out.println("Exiting... Thank you!");
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid choice, try again!");
            }
        }
    }
}
