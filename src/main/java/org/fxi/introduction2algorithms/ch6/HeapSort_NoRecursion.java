package org.fxi.introduction2algorithms.ch6;

public class HeapSort_NoRecursion {
    private static void sort(int[] src, int length) {
        buildHeap(src);
        for(int i = length - 1; i > 0; i--) {
            int temp = src[0];
            src[0] = src[i];
            src[i] = temp;

            maxHeapify(src, 0, i);
        }
    }

    private static void buildHeap(int[] src) {
        for (int i = src.length / 2; i >= 0; i--) {
            maxHeapify(src, i, src.length);
        }
    }

    private static void maxHeapify(int[] src, int pos, int length) {
        int largest = pos;
        while (largest >= 0) {
            int left = 2 * pos + 1;
            int right = 2 * pos + 2;

            if (left < length && src[left] > src[largest]) {
                largest = left;
            }

            if (right < length && src[right] > src[largest]) {
                largest = right;
            }

            if (largest != pos) {
                int temp = src[largest];
                src[largest] = src[pos];
                src[pos] = temp;
            } else {
                largest = -1;
            }
        }
    }

    public static void main(String[] args) {
        int[] src = new int[] {5,13,12,1,8,7,10,15,3,9,2,4,6,14,11};
        //int[] src = new int[] {5,-13,12,1,8,-7,10,15,-3,9,-2,4,6,14,11};
        sort(src, src.length);

        for (int i : src) {
            System.out.print(i);
            System.out.print(", ");
        }
    }

}
