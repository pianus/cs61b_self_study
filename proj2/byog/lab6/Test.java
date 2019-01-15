package byog.lab6;

public class Test {
    public static void main(String[] args) {
        String s = "";
        s = s + "a";
        s = s + "n";
        System.out.println(s);
    }

    @org.junit.Test
    public void inputTest() {
        MemoryGameSolution game = new MemoryGameSolution(40, 40, 123);
        String i = game.solicitNCharsInput(5);
        System.out.println(i);
    }
}
