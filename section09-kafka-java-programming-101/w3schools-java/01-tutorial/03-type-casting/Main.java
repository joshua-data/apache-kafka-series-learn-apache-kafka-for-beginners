public class Main {
    public static void main(String[] args) {

        // Widening Casting (automatically)
        int myInt = 9;
        double myDouble = myInt;
        System.out.println(myInt);
        System.out.println(myDouble);

        // Narrowing Casting (manually)
        double newDouble = 9.78d;
        int newInt = (int) myDouble;
        System.out.println(newDouble);
        System.out.println(newInt);

        int maxScore = 500;
        int userScore = 423;
        float percentage = (float) userScore / maxScore * 100.0f;
        System.out.println("User's percentage is " + percentage);

    }
}