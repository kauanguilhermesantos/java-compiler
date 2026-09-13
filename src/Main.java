public class Main {
    public static void main(String[] args) {
        String input = "45  + 89   -       876";
        Parser p = new Parser (input.getBytes());
        p.parse();
    }
}