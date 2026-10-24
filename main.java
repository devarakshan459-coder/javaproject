import java.util.*;

class Employee {
    int id;
    String name;
    String department;
    Map<String, String> attendance = new LinkedHashMap<>();

    Employee(int id, String name, String department) {
        this.id = id;
        this.name = name;
        this.department = department;
    }

    void markAttendance(String date, String status) {
        attendance.put(date, status);
    }

    void display() {
        System.out.println("ID: " + id
                + " | Name: " + name
                + " | Department: " + department);
    }
}

class LeaveRequest {
    int requestId;
    int employeeId;
    String fromDate;
    String toDate;
    String reason;
    String status;

    LeaveRequest(int requestId, int employeeId,
                 String fromDate, String toDate, String reason) {
        this.requestId = requestId;
        this.employeeId = employeeId;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.reason = reason;
        this.status = "Pending";
    }

    void display() {
        System.out.println("Request ID: " + requestId
                + " | Employee ID: " + employeeId
                + " | From: " + fromDate
                + " | To: " + toDate
                + " | Reason: " + reason
                + " | Status: " + status);
    }
}

public class Main {
    static Scanner sc = new Scanner(System.in);
    static ArrayList<Employee> employees = new ArrayList<>();
    static ArrayList<LeaveRequest> leaveRequests = new ArrayList<>();

    static int nextRequestId = 1;

    static Employee findEmployee(int id) {
        for (Employee e : employees) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }

    static void addEmployee() {
        System.out.print("Enter employee ID: ");
        int id = Integer.parseInt(sc.nextLine());

        if (findEmployee(id) != null) {
            System.out.println("Employee ID already exists!");
            return;
        }

        System.out.print("Enter employee name: ");
        String name = sc.nextLine();

        System.out.print("Enter department: ");
        String department = sc.nextLine();

        employees.add(new Employee(id, name, department));
        System.out.println("Employee added successfully!");
    }

    static void viewEmployees() {
        if (employees.isEmpty()) {
            System.out.println("No employees found.");
            return;
        }

        System.out.println("\n--- Employee List ---");
        for (Employee e : employees) {
            e.display();
        }
    }

    static void markAttendance() {
        System.out.print("Enter employee ID: ");
        int id = Integer.parseInt(sc.nextLine());

        Employee e = findEmployee(id);

        if (e == null) {
            System.out.println("Employee not found!");
            return;
        }

        System.out.print("Enter date (YYYY-MM-DD): ");
        String date = sc.nextLine();

        if (!date.matches("\\d{4}-\\d{2}-\\d{2}")) {
            System.out.println("Invalid date format!");
            return;
        }

        System.out.print("Enter status (Present/Absent): ");
        String status = sc.nextLine();

        if (!status.equalsIgnoreCase("Present")
                && !status.equalsIgnoreCase("Absent")) {
            System.out.println("Invalid attendance status!");
            return;
        }

        e.markAttendance(date, status);
        System.out.println("Attendance marked successfully!");
    }

    static void viewAttendance() {
        System.out.print("Enter employee ID: ");
        int id = Integer.parseInt(sc.nextLine());

        Employee e = findEmployee(id);

        if (e == null) {
            System.out.println("Employee not found!");
            return;
        }

        System.out.println("\n--- Attendance Record ---");
        e.display();

        if (e.attendance.isEmpty()) {
            System.out.println("No attendance records found.");
            return;
        }

        int present = 0;
        int absent = 0;

        for (Map.Entry<String, String> entry
                : e.attendance.entrySet()) {
            System.out.println(entry.getKey()
                    + " : " + entry.getValue());

            if (entry.getValue().equals("Present")) {
                present++;
            } else {
                absent++;
            }
        }

        int total = present + absent;
        double percentage = (present * 100.0) / total;

        System.out.println("Present days: " + present);
        System.out.println("Absent days: " + absent);
        System.out.printf("Attendance percentage: %.2f%%%n",
                percentage);
    }

    static void applyLeave() {
        System.out.print("Enter employee ID: ");
        int id = Integer.parseInt(sc.nextLine());

        if (findEmployee(id) == null) {
            System.out.println("Employee not found!");
            return;
        }

        System.out.print("Enter leave start date (YYYY-MM-DD): ");
        String fromDate = sc.nextLine();

        System.out.print("Enter leave end date (YYYY-MM-DD): ");
        String toDate = sc.nextLine();

        System.out.print("Enter reason for leave: ");
        String reason = sc.nextLine();

        leaveRequests.add(new LeaveRequest(
                nextRequestId++, id, fromDate, toDate, reason));

        System.out.println("Leave application submitted successfully!");
    }

    static void viewLeaveRequests() {
        if (leaveRequests.isEmpty()) {
            System.out.println("No leave requests found.");
            return;
        }

        System.out.println("\n--- Leave Requests ---");
        for (LeaveRequest request : leaveRequests) {
            request.display();
        }
    }

    static void manageLeave() {
        viewLeaveRequests();

        if (leaveRequests.isEmpty()) {
            return;
        }

        System.out.print("Enter request ID to manage: ");
        int id = Integer.parseInt(sc.nextLine());

        LeaveRequest selected = null;

        for (LeaveRequest request : leaveRequests) {
            if (request.requestId == id) {
                selected = request;
                break;
            }
        }

        if (selected == null) {
            System.out.println("Request not found!");
            return;
        }

        if (!selected.status.equals("Pending")) {
            System.out.println("This request has already been processed.");
            return;
        }

        System.out.print("Enter decision (Approve/Reject): ");
        String decision = sc.nextLine();

        if (decision.equalsIgnoreCase("Approve")) {
            selected.status = "Approved";
            System.out.println("Leave approved successfully!");
        } else if (decision.equalsIgnoreCase("Reject")) {
            selected.status = "Rejected";
            System.out.println("Leave rejected.");
        } else {
            System.out.println("Invalid decision!");
        }
    }

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n==================================");
            System.out.println(" EMPLOYEE ATTENDANCE & LEAVE SYSTEM");
            System.out.println("==================================");
            System.out.println("1. Add Employee");
            System.out.println("2. View Employees");
            System.out.println("3. Mark Attendance");
            System.out.println("4. View Attendance");
            System.out.println("5. Apply for Leave");
            System.out.println("6. View Leave Requests");
            System.out.println("7. Approve / Reject Leave");
            System.out.println("8. Exit");
            System.out.print("Enter your choice: ");

            try {
                int choice = Integer.parseInt(sc.nextLine());

                switch (choice) {
                    case 1:
                        addEmployee();
                        break;
                    case 2:
                        viewEmployees();
                        break;
                    case 3:
                        markAttendance();
                        break;
                    case 4:
                        viewAttendance();
                        break;
                    case 5:
                        applyLeave();
                        break;
                    case 6:
                        viewLeaveRequests();
                        break;
                    case 7:
                        manageLeave();
                        break;
                    case 8:
                        System.out.println("Thank you!");
                        sc.close();
                        return;
                    default:
                        System.out.println("Invalid choice! Try again.");
                }
            } catch (NumberFormatException ex) {
                System.out.println("Please enter a valid number.");
            }
        }
    }
}
