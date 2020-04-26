package view.multiplayerPanels;

import control.MainController;
import control.clientserver.QuizGameServer;
import view.AbstractClientPanelHandler;
import view.MainFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClientGamePanelHandler extends AbstractClientPanelHandler {

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

    public ClientGamePanelHandler(MainFrame mainFrame, MainController mainController) {
        super(mainFrame, mainController);
        super.setPointer(panel, output, progressBar, chatArea);
        super.setJList(playerList);
        super.setPointerForLabels(userNameLabel,timerLabel,questionLabel,answerA,answerB,answerC,answerD);
        super.setPointerForAnswerButtons(answerAButton,answerBButton,answerCButton,answerDButton);
        createButtons();
    }

    @Override
    protected void createButtons() {
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.switchToClientConnectToServer();
            }
        });
        sendTextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                client.sendChatTextToAll(mainController.getUserName(),chatTextField.getText());
                addToChatArea(mainController.getUserName(),chatTextField.getText());
            }
        });
    }

    public void prepare(){
        super.prepare();
        addToOutput("PANEL: ClientGamePanel wurde frisch vorbereitet.");
    }

    /**
     * Methode wird vom Client-Objekt aufgerufen, sobald dieses eine Frage samt Antwortmöglichkeiten erhalten hat.
     * @param qAndAs
     * @return
     */
    public String[] setQuestion(String[] qAndAs){
        questionLabel.setText(qAndAs[0]);
        answerA.setText(qAndAs[1]);
        answerB.setText(qAndAs[2]);
        answerC.setText(qAndAs[3]);
        answerD.setText(qAndAs[4]);

        setAnswerButtonsStates(true);

        return qAndAs;
    }

    @Override
    protected void setAnswer(char answer){
        setAnswerButtonsStates(false);
        client.sendAnswer(answer);
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
                if(time < 0 && client.getServerState().equals(QuizGameServer.WAITFORQUESTION)){
                    nextQuestionTimer.stop();
                    setQuestion();
                    addToOutput("PANEL: Neue Frage, neue Antworten! Geben Sie schnell eine Antwort!");
                    time = divisor = client.getCurrentWaitTimeToAnswer();
                    nextQuestionTimer.start();
                }else if(time < 0 && client.getServerState().equals(QuizGameServer.WAITFORANSWERS)){
                    nextQuestionTimer.stop();
                    setAnswerButtonsStates(false);
                    addToOutput("PANEL: Die Antworten aller Spieler wurden ausgewertet. Wir warten nun auf eine neue Frage.");
                    time = divisor = 3;
                    nextQuestionTimer.start();
                }
            }
        });
    }
}
