public class Main {
    public static void main(String[] args) {

        for (int i = 0; i < 5; i++) {
            System.out.println(i);
        }

        for (int j = 0; j <= 10; j+=2) {
            System.out.println(j);
        }

        // Nested for loop
        for (int i = 1; i <= 2; i++) {
            System.out.println("Outer: " + i);

            for (int j = 1; j <= 3; j++) {
                System.out.println(" Inner: " + j);
            }
        }

        String[] countries = {"South Korea", "US", "UK", "Canada"};
        for (String country : countries) {
            System.out.println(country);
        }

    }
}