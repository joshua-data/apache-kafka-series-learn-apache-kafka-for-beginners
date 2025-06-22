public class Main {

    static void myMethod(String name, int age) {
        System.out.println(name + " is " + age);
    }
    static int add5(int x) {
        return 5 + x;
    }

    public static void main(String[] args) {
        myMethod("Joshua", 20);
        myMethod("Liam", 5);
        myMethod("Jenny", 8);
        System.out.println(add5(3));
    }

}
