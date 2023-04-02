package atm;
/**
 *
 * @author evgenylukyanov
 */
import static atm.Employee.ShowUser;
import static atm.Users.Show_Balance;
import java.io.*;
import java.util.*;

public class ATM {

    static HashMap<Integer, Employee> employeeList = new HashMap<>();
    static HashMap<Integer, Users> userList = new HashMap<>();
    static boolean inProgramm = true;
    static File EmpFile = new File("/Users/evgenylukyanov/Desktop/EmployeeList.txt");
    static File UserFile = new File("/Users/evgenylukyanov/Desktop/UsersList.txt");

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        FileInputStream fis = new FileInputStream(EmpFile);
        try {
            ObjectInputStream ois = new ObjectInputStream(fis);
            employeeList = (HashMap<Integer, Employee>) ois.readObject();
            ois.close();
            fis.close();
            if (employeeList != null) {
                for (Map.Entry<Integer, Employee> entry : employeeList.entrySet()) {
                    Integer key = entry.getKey();
                    Employee emp = (Employee) entry.getValue();
                }
            }
        } catch (EOFException e) {
            e.printStackTrace();
        }
        try {
            FileInputStream fis2 = new FileInputStream(UserFile);
            ObjectInputStream ois2 = new ObjectInputStream(fis2);
            userList = (HashMap<Integer, Users>) ois2.readObject();
            ois2.close();
            fis2.close();

            if (userList != null) {
                for (Map.Entry<Integer, Users> entry : userList.entrySet()) {
                    Integer key = entry.getKey();
                    Users us = (Users) entry.getValue();    

                }
            }
        } catch (EOFException e) {
            e.printStackTrace();
        }
        while (inProgramm) {
            Employee emp1 = new Employee("Evgeny", "Lukyanov", 2411);
            employeeList.put(emp1.PIN, emp1);
            System.out.println("For EXIT press 0");
            System.out.println("Please insert you PIN: ");

            int insertPin = scanner.nextInt();
            if (userList.containsKey(insertPin)) {
                Show_Balance(insertPin);
                System.out.println("press 1 for withdraw you money");
                System.out.println("press 2 for add funds");
                System.out.println("press 0 for exit");
                int choice1 = scanner.nextInt();
                switch (choice1) {
                    case 0 -> {
                        inProgramm = false;
                    }
                    case 1 -> {
                        System.out.println("Enter the value: ");
                        userList.get(insertPin).Withdraw(scanner.nextInt());
                    }
                    case 2 -> {
                        System.out.println("Enter the value: ");
                        userList.get(insertPin).AddFunds(scanner.nextInt());
                    }
                    default -> {
                        System.out.println("no correct opperation");
                    }
                }
            } else if (employeeList.containsKey(insertPin)) {
                System.out.println("Hello " + employeeList.get(insertPin).Name + " " + employeeList.get(insertPin).Surname);
                System.out.println("press 1 to show User");
                System.out.println("press 2 for create new Employee");
                System.out.println("press 3 for create new User");
                System.out.println("press 4 for remove Employee");
                System.out.println("press 5 for remove User");
                System.out.println("press 0 for exit");
                int choice2 = scanner.nextInt();
                switch (choice2) {
                    case 0 -> {
                        inProgramm = false;
                    }
                    case 1 -> {
                        System.out.println("please insert user's PIN");
                        ShowUser(scanner.nextInt());
                    }
                    case 2 -> {
                        System.out.println("Please insert Name, Surname, PIN");
                        Employee emp = new Employee(scanner.next(), scanner.next(), scanner.nextInt());
                        employeeList.put(emp.PIN, emp);

                    }
                    case 3 -> {
                        System.out.println("Please insert Name, Surname, PIN");
                        Users us = new Users(scanner.next(), scanner.next(), scanner.nextInt(), 0);
                        userList.put(us.PIN, us);
                    }
                    case 4 -> {
                        System.out.println("Please insert Employee's PIN for remove Employee");
                        int EmpPIN = scanner.nextInt();
                        if (employeeList.containsKey(EmpPIN)) {
                            System.out.println("Are you sure?");
                            System.out.println("1 - yes, 0 - no");
                            int c = scanner.nextInt();
                            if (c == 1) {
                                employeeList.remove(EmpPIN);
                                System.out.println("Employee REMOVED");
                            } else {
                                inProgramm = false;
                            }
                        } else {
                            System.out.println("Employee not FOUND");
                        }

                    }
                    case 5 -> {
                        System.out.println("Please insert User's PIN for remove User");
                        int Pin = scanner.nextInt();
                        if (userList.containsKey(Pin)) {
                            System.out.println("Are you sure?");
                            System.out.println("1 - yes, 0 - no");
                            int c = scanner.nextInt();
                            if (c == 1) {
                                userList.remove(Pin);
                                System.out.println("User REMOVED");
                            } else {
                                inProgramm = false;
                            }
                        } else {
                            System.out.println("User not FOUND");
                        }
                    }

                    default ->
                        System.out.println("Number is not corectly");
                }
            } else if (insertPin == 0) {
                inProgramm = false;
            } else {
                System.out.println("PIN is wrong");
            }

        }
        try {
            FileOutputStream fos = new FileOutputStream(EmpFile);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(employeeList);
            oos.close();
            fos.close();
        } catch (EOFException e) {
            e.printStackTrace();
        }

        try {
            FileOutputStream fos1 = new FileOutputStream(UserFile);
            ObjectOutputStream oos1 = new ObjectOutputStream(fos1);
            oos1.writeObject(userList);
            oos1.close();
            fos1.close();
        } catch (EOFException e) {
            e.printStackTrace();
        }

    }
}

class Employee implements Comparable<Employee>, Serializable {

    String Name;
    String Surname;
    int PIN;

    public Employee(String Name, String Surname, int PIN) {
        this.Name = Name;
        this.Surname = Surname;
        this.PIN = PIN;
        System.out.println("Employee Created");
    }

    public static void ShowUser(int PIN) {
        if (ATM.userList.containsKey(PIN)) {
            Users u = ATM.userList.get(PIN);
            System.out.println(u);
        } else {
            System.out.println("PIN is wrong");
        }

    }

    @Override
    public int compareTo(Employee o) {
        if (this.PIN == o.PIN) {
            return 0;
        } else {
            return 1;
        }
    }

    @Override
    public String toString() {
        return "Employee{" + "Name=" + Name + ", Surname=" + Surname + ", PIN=" + PIN + '}';
    }
}

class Users implements Comparable<Users>, Serializable {

    String Name;
    String Surname;
    int PIN;
    double Balance;

    Users(String Name, String Surname, int PIN, double Balance) {
        this.Name = Name;
        this.Surname = Surname;
        this.PIN = PIN;
        this.Balance = Balance;
        System.out.println("User Created");
    }

    public static void Show_Balance(int PIN) {
        if (ATM.userList.containsKey(PIN)) {
            System.out.println("Hello " + ATM.userList.get(PIN).Name + " " + ATM.userList.get(PIN).Surname);
            System.out.println("You balance: " + ATM.userList.get(PIN).Balance);
        } else {
            System.out.println("PIN is wrong");
        }
    }

    public void AddFunds(int Sum) {
        Balance += Sum;
        System.out.println("Cash deposits: " + Sum);
        System.out.println("You balance: " + Balance);

    }

    public void Withdraw(int Sum) {
        if (Sum <= Balance) {
            Balance -= Sum;
            System.out.println("You withdraw: " + Sum);
            System.out.println("You balance: " + Balance);
        } else {
            System.out.println("There is insufficient funds in your account to process this transaction");
        }
    }

    @Override
    public String toString() {
        return "Users{" + "Name=" + Name + ", Surname=" + Surname + ", PIN=" + PIN + ", Balance=" + Balance + '}';
    }

    @Override
    public int compareTo(Users o) {
        if (this.PIN == o.PIN) {
            return 0;
        } else {
            return 1;
        }
    }
}
