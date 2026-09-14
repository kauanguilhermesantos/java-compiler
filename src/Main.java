public class Main {

    public static void main(String[] args) {

        String input = """
            let a = 10 + 5 * 2;
            let b = 20 / 4 + 3;
            print a + b * 2;
        """;

        Parser p = new Parser(input.getBytes());

        p.parse();

        System.out.println("Código intermediário:");
        System.out.println(p.output());

        Interpretador i = new Interpretador(p.output());

        System.out.println("Resultado:");

        i.run();
    }
}