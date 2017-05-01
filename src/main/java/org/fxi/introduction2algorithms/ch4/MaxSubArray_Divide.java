package org.fxi.introduction2algorithms.ch4;


public class MaxSubArray_Divide {
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

    private static Result maxSubArray(int[] src, int low, int high) {
        if (low + 1 == high) {
            return Result.from(low, high, src[low]);
        } else {
            int mid = (low + high) / 2;

            Result maxArrayOfFirstHalf = maxSubArray(src, low, mid);
            Result maxArrayOfSecondHalf = maxSubArray(src, mid, high);
            Result maxCrossingSubArray = maxCrossingSubArray(src, low, mid, high);

            if (maxArrayOfFirstHalf.sum >= maxArrayOfSecondHalf.sum) {
                return maxArrayOfFirstHalf.sum >= maxCrossingSubArray.sum ? maxArrayOfFirstHalf : maxCrossingSubArray;
            } else {
                return maxArrayOfSecondHalf.sum >= maxCrossingSubArray.sum ? maxArrayOfSecondHalf : maxCrossingSubArray;
            }
        }
    }

    private static Result maxCrossingSubArray(int[] src, int low, int mid, int high) {
        int firstHalfIndex = mid - 1;
        int firstHalfMax = src[firstHalfIndex];
        int sum = firstHalfMax;
        for (int i = mid - 2; i >= low; i--) {
            sum += src[i];
            if (sum >= firstHalfMax) {
                firstHalfMax = sum;
                firstHalfIndex = i;
            }
        }

        int secondHalfIndex = mid;
        int secondHalfMax = src[mid];
        sum = secondHalfMax;
        for (int i = mid + 1; i < high; i++) {
            sum += src[i];
            if (sum >= secondHalfMax) {
                secondHalfMax = sum;
                secondHalfIndex = i;
            }
        }

        return Result.from(firstHalfIndex, (secondHalfIndex + 1), (firstHalfMax + secondHalfMax));
    }


    public static void main(String[] args) {
        int[] src = new int[] {5,-13,12,1,8,-7,10,15,-3,9,-2,4,6,14,11};
        Result maxSubArray = maxSubArray(src, 0, src.length);
        System.out.println(maxSubArray);
    }
}
