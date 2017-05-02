package org.fxi.introduction2algorithms.ch7;

public class QuickSort {
    private static void swap(int[] src, int i, int j) {
        int temp = src[i];
        src[i] = src[j];
        src[j] = temp;
    }

    private static int partition(int[] src, int start, int end) {
        int flag = src[end - 1];
        int mid = start;

        for (int i = start; i < end; i++) {
            if (flag > src[i]) {
                swap(src, mid, i);

                mid++;
            }
        }

        src[end - 1] = src[mid];
        src[mid] = flag;

        return mid;
    }

    private static void sort(int[] src, int start, int end) {
        if (start < end - 1) {
            int mid = partition(src, start, end);
            sort(src, start, mid);
            sort(src, mid + 1, end);
        }
    }

    public static void main(String[] args) {
        int[] src = new int[] {5,13,12,1,8,7,10,15,3,9,2,4,6,14,11};
        //int[] src = new int[] {5,-13,12,1,8,-7,10,15,-3,9,-2,4,6,14,11};
        sort(src, 0, src.length);

        for (int i : src) {
            System.out.print(i);
            System.out.print(", ");
        }
    }
}
