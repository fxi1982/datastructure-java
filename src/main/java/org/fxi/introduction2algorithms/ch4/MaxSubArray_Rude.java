package org.fxi.introduction2algorithms.ch4;

public class MaxSubArray_Rude {
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
        Result[] results = new Result[src.length];
        for (int i = 0; i < src.length; i++) {
            int low = i;
            int high = i;
            int sum = src[i];
            int max = sum;
            for (int j = i + 1; j < src.length; j++) {
                sum += src[j];
                if (sum >= max) {
                    max = sum;
                    high = j + 1;
                }
            }
            results[i] = Result.from(low, high, max);
        }

        int resultIndex = 0;
        for (int i = 1; i < results.length; i++) {
            if (results[i].sum > results[resultIndex].sum) {
                resultIndex = i;
            }
        }

        return results[resultIndex];
    }

    public static void main(String[] args) {
        int[] src = new int[] {5,-13,12,1,8,-7,10,15,-3,9,-2,4,6,14,11};
        Result maxSubArray = maxSubArray(src);
        System.out.println(maxSubArray);
    }

}
