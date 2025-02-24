public class Main {

    public static void main(String[] args) {
        int result1 = sum1(10);
        System.out.println("first result: " + result1);
        int result2 = sum2(5, 10);
        System.out.println("second result: " + result2);
    }

    public static int sum1(int k) {
        if (k > 0) {
            return k + sum1(k - 1);
        } else {
            return 0;
        }
    }

    public static int sum2(int start, int end) {
        if (end > start) {
            return end + sum2(start, end - 1);
        } else {
            return end;
        }

    }
}