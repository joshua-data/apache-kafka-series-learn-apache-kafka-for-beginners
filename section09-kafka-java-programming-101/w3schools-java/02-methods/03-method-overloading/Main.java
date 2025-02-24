public class Main {

    static int plusMethod(int x, int y) {
        return x + y;
    }
    static double plusMethod(double x, double y) {
        return x + y;
    }

    public static void main(String[] args) {
        int myNum1 = plusMethod(3, 6);
        double myNum2 = plusMethod(1.1, 2.2);
        System.out.println("int: " + myNum1);
        System.out.println("double: " + myNum2);
    }

}
