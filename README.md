# Compilador Java
Implementação de um compilador desenvolvido em Java como trabalho da disciplina de Compiladores, abrangendo as principais etapas do processo de compilação

## Sumário

- [1.Problema](#1problema) 
- [2.Código](#2código)
  - [2.1 `Main.java`](#21-mainjava)
  - [2.2 `Parser.java`](#22-parserjava)
    - [2.2.1 `peek()`](#221-peek)
    - [2.2.2 `match()`](#222-match)

## 1.Problema
O front-end de um compilador tem as seguintes funções:
- Converter expressões aritméticas na notação infixada em pós-fixada;
- Entender a notação infixada, onde os operadores aparecem entre os operandos;
- Produzir a notação pós-fixada, onde os operadores aparecerem após os operandos.

```java
// Notação infixada
9 – 5 + 2 
```
```java
// Notação pós-fixada
9 5 – 2 + 
```

Considerando, inicialmente, expressões compostas apenas por números de um dígito (ex.: 1 , 5 , ~~42~~ ) e os operadores aritméticos. Têm-se:

```java
5 + 6
5 + 8 - 9
5 + 8 - 9 + 3
```

Neste caso, **não é preciso** de um analisador léxico. Sendo assim, a gramática para essa expressão pode ser dada da seguinte forma:

```java
expr -> expr + expr
      | expr - expr
      | digit
digit -> 0 | .. | 9
```

Ao desenvolver-se uma árvore de derivações, pode-se perceber que essa gramática é ambígua, uma vez que é possível gerar diferentes árvores para uma mesma sequência de tokens. Dessa forma, pode-se haver diferentes significados, sendo necessário remover essa ambiguidade.

Portanto, para remover a ambiguidade pode-se reescrever a mesma gramática da seguinte forma:

```java
expr -> expr + digit
      | expr - digit
      | digit
digit -> 0 | .. | 9
```

## 2.Código
### 2.1 `Main.java`

**Main:** Classe principal responsável por executar o Parser.

O ``input`` carrega a operação a ser testada.

```java
public class Main {
    public static void main(String[] args) throws Exception {
        String input = "8+5-7+9";
        Parser p = new Parser (input.getBytes());
        p.parse();

    }
}
```

### 2.2 `Parser.java`

**Parser**: responsável por analisar o código de entrada e entender a estrutura dele.

Como não tem um analisador léxico, o **Parser** irá receber uma sequência de caracteres e irá usar um array de bytes para indicar qual é o byte que está sendo lido.

```java
public class Parser {
    private byte[] input;
    private int current;

    public Parser (byte[] input) {
        this.input = input;
    }

    public void parse () {
        // aqui ainda ira o código
    }

}
```

#### 2.2.1 `peek()`

``peek():`` Consulta o próximo caractere da entrada e sem avançar a posição atual ("espia" a próxima posição).

Neste caso, essa função retorna o token (caractere) corrente e retorna 0, ao "final do arquivo".

```java
 private char peek () {
    if (current < input.length)
        return (char)input[current];
    return '\0';
}
```

#### 2.2.2 ``match()``

``match():`` Verifica se o caractere que está sendo lido "casa" com o caracter da entrada.

Se "casar", ele passa para o próximo caractere, se não, dispara um erro de sintaxe.

```java
private void match (char c) {
    if (c == peek()) {
        current++;
    } else {
        throw new Error("syntax error");
    }
}
```

#### 2.2.3 ``expr()``

Para um analisador sintático preditivo recursivo, todo símbolo não terminal é mapeado para um procedimento.

```java
expr -> digit oper
```

``expr():`` regra da expressão

```java
void expr() {
    digit();
    oper();
}
```

#### 2.2.3 ``digit()``

A regra **digit** define que só serão aceitos dígitos de 0 a 9.

```java
digit -> 0 | .. | 9
```

``digit():`` Verifica se o caractere da entrada obedece a regra **digit**

```java
void digit () {
        if (Character.isDigit(peek())) {
            System.out.println("push " + peek());
            match(peek());
        } else {
           throw new Error("syntax error");
        }
}
```

Como resultado, ela imprime o dígito no formato pós-fixado

#### 2.2.4 ``oper()``

A regra **oper** pode cair em 3 casos:

```java
oper -> + digit oper
      | - digit oper
      | ε
```

Neste caso, o ``oper()`` só reconhece as operações de adição e subtração.

```java
	void oper () {
        if (peek() == '+') {
            match('+');
            digit();
            System.out.println("add");
            oper();
        } else if (peek() == '-') {
            match('-');
            digit();
            System.out.println("sub");
            oper();
        } 
    }
```
