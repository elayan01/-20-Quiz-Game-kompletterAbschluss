package view.multiplayerPanels;

import control.MainController;
import control.clientserver.QuizGameServer;
import view.AbstractServerPanelHandler;
import view.MainFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ServerGamePanelHandler extends AbstractServerPanelHandler {
    private JTextArea output;
    private JButton backButton;
    private JPanel panel;
    private JTextField chatTextField;
    private JButton sendTextButton;
    private JTextArea chatArea;
    private JList playerList;
    private JLabel timerLabel;
    private JProgressBar progressBar;
    private JLabel questionLabel;
    private JLabel answerA;
    private JLabel answerB;
    private JLabel answerC;
    private JLabel answerD;
    private JButton answerAButton;
    private JButton answerBButton;
    private JButton answerCButton;
    private JButton answerDButton;
    private JLabel userNameLabel;

    public ServerGamePanelHandler(MainFrame mainFrame, MainController mainController){
        super(mainFrame,mainController);
        super.setPointer(panel, output, progressBar, chatArea);
        super.setJList(playerList);
        super.setPointerForLabels(userNameLabel,timerLabel,questionLabel,answerA,answerB,answerC,answerD);
        super.setPointerForAnswerButtons(answerAButton,answerBButton,answerCButton,answerDButton);
        createButtons();
    }

    protected void createButtons() {
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                server.backToLobby();
                mainFrame.switchToServerLobby();
            }
        });
        sendTextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                server.sendChatTextToAll(mainController.getUserName(),chatTextField.getText());
                addToChatArea(mainController.getUserName(),chatTextField.getText());
            }
        });
    }

    public void prepare(){
        super.prepare();
        addToOutput("PANEL: ServerGamePanel wurde frisch vorbereitet.");
    }

    @Override
    protected String[] setQuestion(){
        String[] qAndAs = super.setQuestion(); //Frage und Antworten kommen aus der eigenen Datenbank.
        server.sendQuestionToAll(qAndAs);

        return qAndAs;
    }

    @Override
    protected void setAnswer(char answer){
        setAnswerButtonsStates(false);
        server.setAPlayersAnswer("127.0.0.1",server.getPort(),answer);
        addToOutput("PANEL: Es wurde Antwort " + answer + " gewählt und an den Server geschickt.");
        addToOutput("PANEL: Wir warten nun, bis alle Clients eine Antwort gegeben haben.");
    }

    @Override
    protected void createTimer(){
        nextQuestionTimer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timerLabel.setText(String.valueOf((int)(time+0.9)));
                time = time - 0.1;
                progressBar.setValue((int)(time*100/divisor));
                if(time < 0 && server.getServerState().equals(QuizGameServer.WAITFORQUESTION)){
                    nextQuestionTimer.stop();
                    setQuestion();
                    addToOutput("PANEL: Neue Frage, neue Antworten! Geben Sie schnell eine Antwort!");
                    time = divisor = server.getTimeToAnswer();
                    nextQuestionTimer.start();
                }else if(time < 0 && server.getServerState().equals(QuizGameServer.WAITFORANSWERS)){
                    nextQuestionTimer.stop();
                    setAnswerButtonsStates(false);
                    server.evaluateAllAnswers();
                    addToOutput("PANEL: Die Antworten aller Spieler wurden ausgewertet. Wir warten nun auf eine neue Frage.");
                    time = divisor = 3;
                    nextQuestionTimer.start();
                }
            }
        });
    }
}
