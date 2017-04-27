package org.fxi.introduction2algorithms.ch2;

public class BinaryInsertionSort {
    private static void sort(int[] src) {
        for (int i = 1; i < src.length; i++) {
            int val = src[i];

            int low = 0, high = i -1;
            while (low <= high) {
                int middle = (low + high) / 2;
                if (src[middle] > val) {
                    high = middle -1;
                } else {
                    low = middle + 1;
                }
            }

            for (int j = i -1; j >= high + 1; j--) {
                src[j + 1] = src[j];
            }
            src[high + 1] = val;
        }
    }

    public static void main(String[] args) {
        int[] src = new int[] {5,2,4,6,1,3};
        sort(src);

        for (int i : src) {
            System.out.print(i);
            System.out.print(", ");
        }
    }
}
