public class Main {

    // Static Method
    static void method_static() {
        System.out.println("Static Method");
    }
    // Public Method
    public void method_public() {
        System.out.println("Public Method");
    }

    public void fullThrottle() {
        System.out.println("The car is going as fast as it can!");
    }
    public void speed(int maxSpeed) {
        System.out.println("Max speed is: " + maxSpeed);
    }

    public static void main(String[] args) {
        
        method_static();
        Main myObj = new Main();
        myObj.method_public();

        Main myCar = new Main();
        myCar.fullThrottle();
        myCar.speed(200);
    }

}