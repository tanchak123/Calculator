public enum Colums {
    DELETE('C'), PLUS('+'), MINUS('-'), MULTIPLY('*'), DIVIDE('÷'), DIVIDER('%'), DEGREE('^'),
    FRACTION(','), CLEAR('C'), EQUAL('=')
    ;
    char c;
    Colums(char c) {
        this.c = c;
    }

    public char getC() {
        return c;
    }

    public String getS() {
        return "" + c;
    }
}
