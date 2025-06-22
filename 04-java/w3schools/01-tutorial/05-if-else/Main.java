public class Main {
    public static void main(String[] args) {

        int time = 20;

        if (time < 12) {
            System.out.println("Good morning!");
        } else if (time < 18) {
            System.out.println("Good afternoon!");
        } else {
            System.out.println("Good evening!");
        }

        String result = (time < 18) ? "Good afternoon!" : "Good evening!";
        System.out.println(result);

    }
}