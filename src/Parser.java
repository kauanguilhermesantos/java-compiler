public class Parser {

    private Scanner scan;
    private Token currentToken;

    private StringBuilder output = new StringBuilder();

    public Parser(byte[] input) {
        scan = new Scanner(input);
        currentToken = scan.nextToken();
    }

    private void nextToken() {
        currentToken = scan.nextToken();
    }

    private void match(TokenType t) {
        if (currentToken.type == t) {
            nextToken();
        } else {
            throw new Error("syntax error");
        }
    }

    void number() {
        output.append("push ")
                .append(currentToken.lexeme)
                .append("\n");

        match(TokenType.NUMBER);
    }

    void oper() {
        if (currentToken.type == TokenType.PLUS) {

            match(TokenType.PLUS);
            term();

            output.append("add\n");

            oper();

        } else if (currentToken.type == TokenType.MINUS) {

            match(TokenType.MINUS);
            term();

            output.append("sub\n");

            oper();
        }
    }

    void expr() {
        term();
        oper();
    }

    void term() {

        if (currentToken.type == TokenType.NUMBER) {

            number();

        } else if (currentToken.type == TokenType.IDENT) {

            output.append("push ")
                    .append(currentToken.lexeme)
                    .append("\n");

            match(TokenType.IDENT);

        } else {
            throw new Error("syntax error");
        }
    }

    void letStatement() {

        match(TokenType.LET);

        var id = currentToken.lexeme;

        match(TokenType.IDENT);
        match(TokenType.EQ);

        expr();

        output.append("pop ")
                .append(id)
                .append("\n");

        match(TokenType.SEMICOLON);
    }

    void printStatement() {

        match(TokenType.PRINT);

        expr();

        output.append("print\n");

        match(TokenType.SEMICOLON);
    }

    void statement() {

        if (currentToken.type == TokenType.PRINT) {

            printStatement();

        } else if (currentToken.type == TokenType.LET) {

            letStatement();

        } else {

            throw new Error("syntax error");
        }
    }

    void statements() {

        while (currentToken.type != TokenType.EOF) {
            statement();
        }
    }

    public void parse() {
        statements();
    }

    public String output() {
        return output.toString();
    }
}