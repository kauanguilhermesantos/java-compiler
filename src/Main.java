public class Main {
    public static void main(String[] args) throws Exception {
        String input = "8+5-7+9"; // Entrada a ser analisada pelo compilador
        Parser p = new Parser (input.getBytes());
        p.parse();

    }
}