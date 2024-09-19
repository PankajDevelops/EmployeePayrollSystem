# Employee Payroll System

This is a Java-based project that simulates a payroll system for managing employee records and calculating their salaries. The system supports both full-time and part-time employees with different salary calculation methods.

![Employee Payroll System](https://github.com/user-attachments/assets/af95afe8-68d1-4119-ba44-30cd7ae19107)
## Features

- Add and remove employees from the payroll system.
- Calculate salaries for both full-time and part-time employees.
  - **Full-time Employees**: Monthly salary.
  - **Part-time Employees**: Hourly rate multiplied by hours worked.
- Display a list of employees with their details (name, ID, and salary).

## Technologies Used

- **Java**: Core programming language.
- **Object-Oriented Programming (OOP)**: Utilized principles like abstraction, inheritance, and polymorphism.
- **Collections Framework**: Used `ArrayList` for employee management.

## Classes and Structure

1. **Employee (Abstract Class)**: 
   - Represents a general employee with fields like `name` and `id`.
   - Abstract method `calculateSalary()` to be implemented by child classes.

2. **FullTimeEmployee (Class)**: 
   - Represents a full-time employee with a fixed monthly salary.
   - Implements the `calculateSalary()` method.

3. **PartTimeEmployee (Class)**: 
   - Represents a part-time employee with hourly rate and hours worked.
   - Implements the `calculateSalary()` method.

4. **PayrollSystem (Class)**: 
   - Manages a list of employees using `ArrayList`.
   - Functions to add, remove, and display employee details.

5. **Main (Class)**: 
   - Driver class that demonstrates the payroll system's functionality.
