package view;

import control.MainController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class AbstractGamePanelHandler extends AbstractPanelHandler {

    protected JButton answerAButton, answerBButton, answerCButton, answerDButton;
    protected JLabel userNameLabel, timerLabel, questionLabel, answerA, answerB, answerC, answerD;
    protected JProgressBar progressBar;
    protected JTextArea chatArea;

    protected Timer nextQuestionTimer;
    protected double time, divisor;


    public AbstractGamePanelHandler(MainFrame mainFrame, MainController mainController) {
        super(mainFrame, mainController);
        createTimer();
    }


    protected void setPointer(JPanel panel, JTextArea output, JTextArea chatArea){
        super.setPointer(panel, output);
        this.chatArea = chatArea;
    }

    protected void setPointer(JPanel panel, JTextArea output, JProgressBar progressBar){
        super.setPointer(panel, output);
        this.progressBar = progressBar;
    }

    protected void setPointer(JPanel panel, JTextArea output, JProgressBar progressBar, JTextArea chatArea){
        super.setPointer(panel, output);
        this.progressBar = progressBar;
        this.chatArea = chatArea;
    }

    protected void setPointerForLabels(JLabel userName, JLabel timer, JLabel q, JLabel a, JLabel b, JLabel c, JLabel d){
        this.userNameLabel = userName;
        timerLabel = timer;
        questionLabel = q;
        answerA = a;
        answerB = b;
        answerC = c;
        answerD = d;
    }

    protected void setPointerForAnswerButtons(JButton a, JButton b, JButton c, JButton d){
        answerAButton = a;
        answerBButton = b;
        answerCButton = c;
        answerDButton = d;

        answerAButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setAnswer('A');
            }
        });
        answerBButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setAnswer('B');
            }
        });
        answerCButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setAnswer('C');
            }
        });
        answerDButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setAnswer('D');
            }
        });

        setAnswerButtonsStates(false);
    }

    public void prepare(){
        mainController.generateQuestionsAndAnswers();
        questionLabel.setText("Hier steht gleich eine Frage.");
        answerA.setText("Sind Sie bereit?");
        answerB.setText("Dann kann es ja losgehen!");
        answerC.setText("Viel Erfolg!");
        answerD.setText("Gogogo!");
        setAnswerButtonsStates(false);
        userNameLabel.setText(mainController.getUserName());
        time = 3;
        divisor = time;
        nextQuestionTimer.start();
    }

    protected void createTimer(){
        nextQuestionTimer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timerLabel.setText(String.valueOf((int)(time+0.9)));
                time = time - 0.1;
                progressBar.setValue((int)(time*100/divisor));
                if(time < 0){
                    nextQuestionTimer.stop();
                    setQuestion();
                }
            }
        });
    }

    protected void setAnswerButtonsStates(boolean b){
        answerAButton.setEnabled(b);
        answerBButton.setEnabled(b);
        answerCButton.setEnabled(b);
        answerDButton.setEnabled(b);
    }

    protected void restartTimer(){
        time = 3;
        nextQuestionTimer.start();
    }

    protected String[] setQuestion(){
        String[] qAndAs = mainController.getOneQuestion();
        questionLabel.setText(qAndAs[0]);
        answerA.setText(qAndAs[1]);
        answerB.setText(qAndAs[2]);
        answerC.setText(qAndAs[3]);
        answerD.setText(qAndAs[4]);

        setAnswerButtonsStates(true);

        return qAndAs;
    }

    protected void setAnswer(char answer){

    }

    public void addToChatArea(String user, String text){
        if(!chatArea.getText().isEmpty()){
            chatArea.setText(chatArea.getText() + "\n");
        }
        chatArea.setText(chatArea.getText() + new SimpleDateFormat("HH:mm:ss").format(new Date()) + " - " + user + ": " + text);
    }
}
