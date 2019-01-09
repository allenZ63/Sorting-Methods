 package objectsort;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class SortingMethods {

    private ArrayList<Employee> employees = new ArrayList<Employee>();
    private int num = 100000; //number of elements

    public SortingMethods() {
        Random rand = new Random();
        float max = 170000;
        for (int i = 0; i < num; i++) {
            Employee e = new Employee(0, 0, "");
            e.setAge(rand.nextInt(51) + 20);
            e.setSalary(rand.nextFloat() * max + 30000);
            e.setName(e.addNames());
            employees.add(e);
        }
    }

    public static void main(String[] args) {

    }

    public void setup(String sortMethod, String sortType) {

        if (sortType.equals("Age")) {
            for (int i = 0; i < num; i++) {
                employees.get(i).setType(1);
            }
        }

        if (sortType.equals("Salary")) {
            for (int i = 0; i < num; i++) {
                employees.get(i).setType(2);
            }
        }

        if (sortType.equals("Name")) {
            for (int i = 0; i < num; i++) {
                employees.get(i).setType(3);
            }
        }

        long start = timerStart();

        switch (sortMethod) {
            case "Insertion":
                printArray(insertionSort(employees));
                break;

            case "Selection":
                printArray(selectionSort(employees));
                break;

            case "Bubble":
                printArray(bubbleSort(employees));
                break;

            case "Shell":
                printArray(shellSort(employees));
                break;

            case "Merge":
                printArray(mergeSort(employees));
                break;

            case "Heap":
                printArray(heapSort(employees));
                break;

            case "Quick":
                printArray(quickSort(employees, 0, employees.size() - 1));
                break;

            default:
                System.out.println("Invalid Input");
        }
        timerEnd(start);
    }

    public long timerStart() {
        long start = System.currentTimeMillis(); //begins timer
        ArrayList a = new ArrayList();
        for (int i = 1; i <= 1000000; i++) {
            a.add(i);
        }
        return start;
    }

    public void timerEnd(long start) {
        long end = System.currentTimeMillis(); //stops timer
        System.out.println(end - start);
    }

    public void shuffleArray(int[] array) {
        Random rnd = ThreadLocalRandom.current();
        for (int i = array.length - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            // Simple swap
            int a = array[index];
            array[index] = array[i];
            array[i] = a;
        }
    }

    public static ArrayList<Employee> insertionSort(ArrayList<Employee> employees) {
        Employee temp;
        for (int i = 1; i < employees.size(); i++) {
            if (employees.get(i).compareTo(employees.get(i - 1)) < 0) { //if this is smaller than other
                int count = i;
                while (employees.get(count).compareTo(employees.get(count - 1)) < 0) { //while this is smaller than other
                    temp = employees.get(count);
                    employees.set(count, employees.get(count - 1));
                    employees.set(count - 1, temp);
                    if (count > 1) {
                        count--;
                    }
                }
            }
        }
        return employees;
    }

    public static ArrayList<Employee> selectionSort(ArrayList<Employee> employees) {
        Employee temp;
        int count = 0;
        for (int j = 0; j < employees.size(); j++) {
            Employee min = employees.get(j);
            for (int i = j; i < employees.size(); i++) {
                if (min.compareTo(employees.get(i)) > 0) { //this is larger than other
                    min = employees.get(i);
                    count = i;
                }
            }
            temp = employees.get(j);
            employees.set(j, min);
            employees.set(count, temp);
        }
        return employees;
    }

    public static ArrayList<Employee> bubbleSort(ArrayList<Employee> employees) {
        Employee temp;
        for (int j = 1; j < employees.size(); j++) {
            for (int i = 1; i < employees.size(); i++) {
                if (employees.get(i).compareTo(employees.get(i - 1)) < 0) { //this is smaller than other
                    temp = employees.get(i);
                    employees.set(i, employees.get(i - 1));
                    employees.set(i - 1, temp);
                }
            }
        }
        return employees;
    }

    public static ArrayList<Employee> shellSort(ArrayList<Employee> employees) {
        // first part uses the Knuth's interval sequence
        int h = 1;
        while (h <= employees.size() / 3) {
            h = 3 * h + 1; // h is equal to highest sequence of h<=length/3
            // (1,4,13,40...)
        }

        // next part
        while (h > 0) { // for array of length 10, h=4
            // This step is similar to insertion sort below
            for (int i = 0; i < employees.size(); i++) {

                Employee temp = employees.get(i);
                int j;

                for (j = i; j > h - 1 && employees.get(j - h).compareTo(temp) >= 0; j = j - h) { //this is greater than other
                    employees.set(j, employees.get(j - h));
                }
                employees.set(j, temp);
            }
            h = (h - 1) / 3;
        }
        return employees;
    }

    public static ArrayList<Employee> mergeSort(ArrayList<Employee> employees) {
        if (employees.size() > 1) {
            int i, mid = employees.size() / 2;
            ArrayList<Employee> half1 = new ArrayList<Employee>(mid);
            ArrayList<Employee> half2 = new ArrayList<Employee>(employees.size() - mid);
            for (i = 0; i < mid; i++) {
                half1.add(employees.get(i));
            }
            for (; i < employees.size(); i++) {
                half2.add(employees.get(i));
            }
            mergeSort(half1);
            mergeSort(half2);
            int j = 0, k = 0;
            for (i = 0; j < half1.size() && k < half2.size(); i++) {
                if (half1.get(j).compareTo(half2.get(k)) < 0) { //this is smaller than other 
                    employees.set((i), half1.get(j));
                    j++;
                } else {
                    employees.set(i, half2.get(k));
                    k++;
                }
            }
            for (; j < half1.size(); i++, j++) {
                employees.set((i), half1.get(j));
            }
            for (; k < half2.size(); i++, k++) {
                employees.set((i), half2.get(k));
            }
        }
        return employees;
    }

    public static ArrayList<Employee> heapSort(ArrayList<Employee> employees) {
        int N = employees.size();
        //creating a heap
        MaxHeap heap = createHeap(employees, N);

        //Repeating the below steps till the size of the heap is 1.
        while (heap.len > 1) {
            //Replacing largest element with the last item of the heap
            heapSwap(heap, 0, heap.len - 1);
            heap.len--;//Reducing the heap size by 1
            heapify(heap, 0);
        }
        return heap.arr;
    }

    public static MaxHeap createHeap(ArrayList<Employee> arr, int N) {
        MaxHeap maxheap = new MaxHeap(N, arr);
        int i = (maxheap.len - 2) / 2;

        while (i >= 0) {
            maxheap = heapify(maxheap, i);
            i--;
        }
        return maxheap;
    }

    public static MaxHeap heapify(MaxHeap maxheap, int N) {
        int largest = N;
        int left = 2 * N + 1; //index of left child
        int right = 2 * N + 2; //index of right child

        if (left < maxheap.len && maxheap.arr.get(left).compareTo(maxheap.arr.get(largest)) > 0) { //this is larger than other
            largest = left;
        }

        if (right < maxheap.len && maxheap.arr.get(right).compareTo(maxheap.arr.get(largest)) > 0) { //this is larger than other
            largest = right;
        }

        if (largest != N) {
            heapSwap(maxheap, largest, N);
            heapify(maxheap, largest);
        }

        return maxheap;
    }

    public static void heapSwap(MaxHeap maxheap, int i, int j) {
        Employee temp;
        temp = maxheap.arr.get(i);
        maxheap.arr.set(i, maxheap.arr.get(j));
        maxheap.arr.set(j, temp);
    }

    static class MaxHeap {

        int len;
        ArrayList<Employee> arr;

        MaxHeap(int l, ArrayList<Employee> a) {
            len = l;
            arr = a;
        }
    }

    private static ArrayList<Employee> quickSort(ArrayList<Employee> employees, int i, int j) {
        if (i < j) {
            int pos = partition(employees, i, j);
            quickSort(employees, i, pos - 1);
            quickSort(employees, pos + 1, j);
        }
        return employees;
    }

    private static int partition(ArrayList<Employee> arrayList, int i, int j) {
        Employee pivot = arrayList.get(j);
        int small = i - 1;

        for (int k = i; k < j; k++) {
            if (arrayList.get(k).compareTo(pivot) <= 0) { //this is smaller than other
                small++;
                quickSwap(arrayList, k, small);
            }
        }
        quickSwap(arrayList, j, small + 1);
        return small + 1;
    }

    private static void quickSwap(ArrayList<Employee> arrayList, int k, int small) {
        Employee temp;
        temp = arrayList.get(k);
        arrayList.set(k, arrayList.get(small));
        arrayList.set(small, temp);
    }

    public void printArray(ArrayList<Employee> employees) {
        for (int i = 0; i < num; i++) {
            System.out.println(employees.get(i).toString());
        }
    }
}



