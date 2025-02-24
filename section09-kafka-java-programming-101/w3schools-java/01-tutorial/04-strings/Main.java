public class Main {
    public static void main(String[] args) {

        String txt = "Joshua";

        System.out.println("The length of txt string is: " + txt.length());

        System.out.println(txt.toUpperCase());
        System.out.println(txt.toLowerCase());

        System.out.println(txt.indexOf("sh"));

        String firstName = "Joshua";
        String lastName = "Kim";
        System.out.println(firstName.concat(" ").concat(lastName));
        System.out.println(firstName + " " + lastName);

    }
}