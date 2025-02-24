public class Main {

    // Static Method
    static void static_method() {
        System.out.println("Static methods can be called without creating objects");
    }
    // Public Method
    public void public_method() {
        System.out.println("Public methods must be called by creating objects");
    }

    public static void main(String[] args) {
        static_method();
        Main myObj = new Main();
        myObj.public_method();
    }

}