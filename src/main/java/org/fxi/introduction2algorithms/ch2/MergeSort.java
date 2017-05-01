package org.fxi.introduction2algorithms.ch2;

public class MergeSort {

    private static void sort(int[] src) {
        merge(src, 0, src.length / 2, src.length);
    }

    private static void merge(int[] src, int start, int middle, int end) {
        if (start != (middle -1)) {
            merge(src, start, (start + middle) / 2, middle);
        }

        if (middle != (end - 1)) {
            merge(src, middle, (middle + end) / 2, end);
        }

        int[] firstHalf = new int[middle - start];
        System.arraycopy(src, start, firstHalf, 0, firstHalf.length);
        int[] secondHalf = new int[end - middle];
        System.arraycopy(src, middle, secondHalf, 0, secondHalf.length);

        for (int i = 0, j = 0, k = start; k < end; k++) {
            if (i >= firstHalf.length) {
                System.arraycopy(secondHalf, j, src, k, end - k);
                k = end -1;
                break;
            }
            if (j >= secondHalf.length) {
                System.arraycopy(firstHalf, i, src, k, end - k);
                k = end -1;
                break;
            }

            if (firstHalf[i] <= secondHalf[j]) {
                src[k] = firstHalf[i];
                i++;
            } else {
                src[k] = secondHalf[j];
                j++;
            }
        }
    }

    public static void main(String[] args) {
        int[] src = new int[] {5,13,12,1,8,7,10,15,3,9,2,4,6,14,11};
        sort(src);

        for (int i : src) {
            System.out.print(i);
            System.out.print(", ");
        }
    }
}
