package org.fxi.introduction2algorithms.ch4;

public class MaxSubArray_Linear {
    static final class Result {
        public int startIndex = -1;
        public int endIndex = -1;
        public int sum = 0;

        static Result from(int startIndex, int endIndex, int sum) {
            Result result = new Result();
            result.startIndex = startIndex;
            result.endIndex = endIndex;
            result.sum = sum;

            return result;
        }

        @Override
        public String toString() {
            return "Result{" +
                    "startIndex=" + startIndex +
                    ", endIndex=" + endIndex +
                    ", sum=" + sum +
                    '}';
        }
    }

    private static Result maxSubArray(int[] src) {
        Result result = Result.from(0, 1, src[0]);


        int low = 0;
        int high = 1;
        int max = src[0];
        for (int i = 1; i < src.length; i++) {
            Result current = result;

            if (max < 0) {
                low = i;
                high = i + 1;
                max = src[i];
            } else {
                high = i + 1;
                max += src[i];
            }
            Result temp = Result.from(low, high, max);

            if (current.sum < temp.sum) {
                result = temp;
            }
        }

        return result;
    }

    public static void main(String[] args) {
        int[] src = new int[] {5,-13,12,1,8,-7,10,15,-3,9,-2,4,6,14,11};
        Result maxSubArray = maxSubArray(src);
        System.out.println(maxSubArray);
    }

}
