package org.fxi.introduction2algorithms.ch2;

public class InsertionSort {
    private static void sort(int[] src) {
        for (int i = 1; i < src.length; i++) {
            int val = src[i];
            int j = i -1;
            while (j >= 0  && src[j] > val) {
                src[j + 1] = src[j];
                j -= 1;
            }
            src[j + 1] = val;
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
