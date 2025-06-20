// 1st Interface
interface FirstInterface {
    public void firstMethod();
}
// 2nd Interface
interface SecondInterface {
    public void secondMethod();
}

// Class
class DemoClass implements FirstInterface, SecondInterface {
    public void firstMethod() {
        System.out.println("This is the first method...");
    }
    public void secondMethod() {
        System.out.println("This is the second method...");
    }
}

class Main {
    public static void main(String[] args) {
        DemoClass myObj = new DemoClass();
        myObj.firstMethod();
        myObj.secondMethod();
    }
}