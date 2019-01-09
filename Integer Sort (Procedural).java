package sortingmethods;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class SortingMethods {

    public static void main(String[] args) {
        int[] array = new int[10]; //input 
        for (int i = 0; i < array.length; i++) {
            array[i] = i;
        }
        shuffleArray(array);

        System.out.print("What conversion type? ");
        Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine();
        long start = timerStart();

        switch (s) {
            case "Insertion":
                printArray(insertionSort(array));
                break;

            case "Selection":
                printArray(selectionSort(array));
                break;

            case "Bubble":
                printArray(bubbleSort(array));
                break;

            case "Shell":
                printArray(shellSort(array));
                break;

            case "Merge":
                printArray(mergeSort(array));
                break;

            case "Heap":
                printArray(heapSort(array));
                break;

            case "Quick":
                printArray(quickSort(array, 0, array.length - 1));
                break;

            default:
                System.out.println("Invalid Input");
        }
        timerEnd(start);
    }

    public static long timerStart() {
        long start = System.currentTimeMillis(); //begins timer
        ArrayList a = new ArrayList();
        for (int i = 1; i <= 1000000; i++) {
            a.add(i);
        }
        return start;
    }

    public static void timerEnd(long start) {
        long end = System.currentTimeMillis(); //stops timer
        System.out.println(end - start);
    }

    public static void shuffleArray(int[] array) {
        // If running on Java 6 or older, use `new Random()` on RHS here
        Random rnd = ThreadLocalRandom.current();
        for (int i = array.length - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            // Simple swap
            int a = array[index];
            array[index] = array[i];
            array[i] = a;
        }
    }

    public static int[] insertionSort(int[] array) {
        int temp = 0;
        for (int i = 1; i < array.length; i++) {
            if (array[i] < array[i - 1]) {
                int count = i;
                while (array[count] < array[count - 1]) {
                    temp = array[count];
                    array[count] = array[count - 1];
                    array[count - 1] = temp;
                    if (count > 1) {
                        count--;
                    }
                }
            }
        }
        return array;
    }

    public static int[] selectionSort(int[] array) {
        int temp = 0;
        int count = 0;
        int min = Integer.MAX_VALUE;
        for (int j = 0; j < array.length; j++) {
            for (int i = j; i < array.length; i++) {
                if (min > array[i]) {
                    min = array[i];
                    count = i;
                }
            }
            temp = array[j];
            array[j] = min;
            array[count] = temp;
            min = Integer.MAX_VALUE;
        }
        return array;
    }

    public static int[] bubbleSort(int[] array) {
        int temp = 0;
        for (int j = 1; j < array.length; j++) {
            for (int i = 1; i < array.length; i++) {
                if (array[i] < array[i - 1]) {
                    temp = array[i];
                    array[i] = array[i - 1];
                    array[i - 1] = temp;
                }
            }
        }
        return array;
    }

    public static int[] shellSort(int[] array) {
        // first part uses the Knuth's interval sequence
        int h = 1;
        while (h <= array.length / 3) {
            h = 3 * h + 1; // h is equal to highest sequence of h<=length/3
            // (1,4,13,40...)
        }

        // next part
        while (h > 0) { // for array of length 10, h=4

            // This step is similar to insertion sort below
            for (int i = 0; i < array.length; i++) {

                int temp = array[i];
                int j;

                for (j = i; j > h - 1 && array[j - h] >= temp; j = j - h) {
                    array[j] = array[j - h];
                }
                array[j] = temp;
            }
            h = (h - 1) / 3;
        }
        return array;
    }

    public static int[] mergeSort(int[] array) {
        if (array.length > 1) {
            int i, mid = array.length / 2;
            int[] half1 = new int[mid];
            int[] half2 = new int[array.length - mid];
            for (i = 0; i < mid; i++) {
                half1[i] = array[i];
            }
            for (; i < array.length; i++) {
                half2[i - mid] = array[i];
            }
            mergeSort(half1);
            mergeSort(half2);
            int j = 0, k = 0;
            for (i = 0; j < half1.length && k < half2.length; i++) {
                if (half1[j] < half2[k]) {
                    array[i] = half1[j];
                    j++;
                } else {
                    array[i] = half2[k];
                    k++;
                }
            }
            for (; j < half1.length; i++, j++) {
                array[i] = half1[j];
            }
            for (; k < half2.length; i++, k++) {
                array[i] = half2[k];
            }
        }
        return array;
    }

    public static int[] heapSort(int[] array) {
        int N = array.length;
        //creating a heap
        MaxHeap heap = createHeap(array, N);

        //Repeating the below steps till the size of the heap is 1.
        while (heap.len > 1) {
            //Replacing largest element with the last item of the heap
            heapSwap(heap, 0, heap.len - 1);
            heap.len--;//Reducing the heap size by 1
            heapify(heap, 0);
        }
        return heap.arr;
    }

    public static MaxHeap createHeap(int arr[], int N) {
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

        if (left < maxheap.len && maxheap.arr[left] > maxheap.arr[largest]) {
            largest = left;
        }

        if (right < maxheap.len && maxheap.arr[right] > maxheap.arr[largest]) {
            largest = right;
        }

        if (largest != N) {
            heapSwap(maxheap, largest, N);
            heapify(maxheap, largest);
        }

        return maxheap;
    }

    public static void heapSwap(MaxHeap maxheap, int i, int j) {
        int temp;
        temp = maxheap.arr[i];
        maxheap.arr[i] = maxheap.arr[j];
        maxheap.arr[j] = temp;
    }

    static class MaxHeap {

        int len;
        int arr[];

        MaxHeap(int l, int a[]) {
            len = l;
            arr = a;
        }
    }

    private static int[] quickSort(int[] array, int i, int j) {
        if (i < j) {
            int pos = partition(array, i, j);
            quickSort(array, i, pos - 1);
            quickSort(array, pos + 1, j);
        }
        return array;
    }

    private static int partition(int[] array, int i, int j) {
        int pivot = array[j];
        int small = i - 1;

        for (int k = i; k < j; k++) {
            if (array[k] <= pivot) {
                small++;
                quickSwap(array, k, small);
            }
        }
        quickSwap(array, j, small + 1);
        return small + 1;
    }

    private static void quickSwap(int[] array, int k, int small) {
        int temp;
        temp = array[k];
        array[k] = array[small];
        array[small] = temp;
    }

    public static void printArray(int[] array) {
        System.out.println(Arrays.toString(array));
    }
}



