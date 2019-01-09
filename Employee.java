package objectsort;

import java.util.*;

public class Employee implements Comparable<Employee> {

    private int age;
    private float salary;
    private String name;
    private int typeNumber;

    public Employee(int age, float salary, String name) {
        this.age = age;
        this.salary = salary;
        this.name = name;
    }

    public static void main(String[] args) {
    }

    public String addNames() {
        Random rand = new Random();
        String text = "";
        for (int i = 0; i < 5; i++) {
            text += Character.toString((char) (rand.nextInt(26) + 65));
        }
        return text;
    }

    public int getAge() {
        return age;
    }

    public float getSalary() {
        return salary;
    }

    public String getName() {
        return name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setSalary(float salary) {
        this.salary = salary;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(int x) {
        if (x == 1) {
            typeNumber = 1;
        }
        if (x == 2) {
            typeNumber = 2;
        }
        if (x == 3) {
            typeNumber = 3;
        }
    }

    @Override
    public int compareTo(Employee o) {
        switch (typeNumber) {
            case 1:
                if (this.age > o.age) {
                    return 1;
                }
                if (this.age == o.age) {
                    return 0;
                }
                if (this.age < o.age) {
                    return -1;
                }

            case 2:
                if (this.salary > o.salary) {
                    return 1;
                }
                if (this.salary == o.salary) {
                    return 0;
                }
                if (this.salary < o.salary) {
                    return -1;
                }

            case 3:
                return this.name.compareTo(o.name);

            default:
                System.out.println("Invalid Input");
        }
        return 0;
    }

    @Override
    public String toString() {
        String s1 = String.format("%.2f", salary);
        return "[" + name + ", " + s1 + ", " + age + "]";
    }
}



