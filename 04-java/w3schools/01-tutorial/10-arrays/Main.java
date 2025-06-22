public class Main {
    public static void main(String[] args) {

        String[] cars = {"Volvo", "BMW", "Ford", "Mazda"};
        cars[0] = "Nissan";
        System.out.println(cars[0]);
        System.out.println(cars.length);

        for (int i = 0; i < cars.length; i++) {
            System.out.println(cars[i]);
        }
        for (String car : cars) {
            System.out.println(car);
        }

        // Multidimensional Arrays
        int[][] myNumbers = { {1, 2, 3, 4}, {5, 6, 7} };
        System.out.println(myNumbers[1][2]);

        for (int i = 0; i < myNumbers.length; i++) {
            for (int j = 0; j < myNumbers[i].length; j++) {
                System.out.println(myNumbers[i][j]);
            }
        }

        for (int[] row : myNumbers) {
            for (int number : row) {
                System.out.println(number);
            }
        }

    }
}