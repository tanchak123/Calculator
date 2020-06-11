public enum Colums {
    DELETE('˂'), PLUS('+'), MINUS('-'), MULTIPLY('*'), DIVIDE('÷'), DIVIDER('%'), DEGREE('^'),
    FRACTION('.'), CLEAR('C'), EQUAL('=')
    ;
    char character;
    Colums(char c) {
        character = c;
    }

    public char getC() {
        return character;
    }

    public String getS() {
        return "" + character;
    }
}
