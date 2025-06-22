abstract class Main {
    public String fname = "Joshua";
    public int age = 24;
    public abstract void study();
}

class Student extends Main {
    public int graduationYear = 2018;
    public void study() {
        System.out.println("He studies all day long!");
    }
}