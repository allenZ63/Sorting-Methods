package objectsort;

import java.util.Scanner;

public class ObjectSort {

    public static void main(String[] args) {
        System.out.print("What conversion type? ");
        Scanner scanner = new Scanner(System.in);
        String sortMethod = scanner.nextLine();

        System.out.print("Age, Salary, or Name? ");
        Scanner keyboard = new Scanner(System.in);
        String sortType = scanner.nextLine();

        SortingMethods sm = new SortingMethods();
        sm.setup(sortMethod, sortType);
    }
}



