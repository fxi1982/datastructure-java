package org.fxi.introduction2algorithms.ch2;

public class BubbleSort {
    private static void sort(int[] src) {
        for (int i = 0; i < src.length - 1; i++) {
            for (int j = 1; j < src.length - i; j++) {
                if (src[j] < src[j - 1]) {
                    int min = src[j];
                    src[j] = src[j - 1];
                    src[j - 1] = min;
                }
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
