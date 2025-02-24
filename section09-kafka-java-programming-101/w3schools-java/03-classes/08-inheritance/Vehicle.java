// Superclass
class Vehicle {
    protected String brand = "Toyota";

    public static void main(String[] args) {
        System.out.println("Vehicle Class is now on!");
    }

    public void honk() {
        System.out.println("Tuut, tuut!");
    }
}

// Subclass
class Car extends Vehicle {
    private String modelName = "Camry";

    public static void main(String[] args) {
        Car myCar = new Car();
        myCar.honk();
        System.out.println(myCar.brand + " " + myCar.modelName);
    }
}