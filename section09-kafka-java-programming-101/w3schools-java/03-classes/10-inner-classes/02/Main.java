class OuterClass {
    int x = 10;

    private class InnerClass {
        int y = 5;
    }
}

public class Main {
    public static void main(String[] args) {
        OuterClass myOuter = new OuterClass();
        OuterClass.InnerClass myInner = myOuter.new InnerClass(); // should return an error
        System.out.println(myOuter.x + myInner.y);
    }
}