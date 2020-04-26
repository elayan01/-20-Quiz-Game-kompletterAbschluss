package model;

public class Player {

    private String userName;
    private String ip;
    private int port;
    private char answer;

    public Player(String userName, String ip, int port){
        this.userName = userName;
        this.ip = ip;
        this.port = port;
    }

    public String getUserName(){
        return userName;
    }

    public String getIP(){
        return ip;
    }

    public int getPort(){
        return port;
    }

    public void setAnswer(char answer){
        this.answer = answer;
    }

    public char getLastAnswer(){
        return answer;
    }
}
