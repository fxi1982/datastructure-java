package org.fxi.introduction2algorithms.ch2;

public class SelectionSort {
    private static void sort(int[] src) {
        for (int i = 0; i < src.length - 1; i++) {
            int min = src[i];
            int minIdx = i;
            // select minimal from rest
            for (int j = i + 1; j < src.length; j++) {
                if (min > src[j]) {
                    min = src[j];
                    minIdx = j;
                }
            }

            // switch with current as necessary.
            if (minIdx != i) {
                src[minIdx] = src[i];
                src[i] = min;
            }
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
