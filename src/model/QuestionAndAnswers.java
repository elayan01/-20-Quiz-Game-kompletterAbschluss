package model;

public class QuestionAndAnswers {

    private String q, a, b, c, d;
    private char correctAnswer;

    public QuestionAndAnswers(String q, String a, String b, String c, String d, char correctAnswer) {
        this.q = q;
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.correctAnswer = correctAnswer;
    }

    public String getQ() {
        return q;
    }

    public void setQ(String q) {
        this.q = q;
    }

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    public String getB() {
        return b;
    }

    public void setB(String b) {
        this.b = b;
    }

    public String getC() {
        return c;
    }

    public void setC(String c) {
        this.c = c;
    }

    public String getD() {
        return d;
    }

    public void setD(String d) {
        this.d = d;
    }

    public char getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(char correctAnswer) {
        this.correctAnswer = correctAnswer;
    }
}
