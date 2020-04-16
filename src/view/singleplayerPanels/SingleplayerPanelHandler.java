package view.singleplayerPanels;

import control.MainController;
import view.AbstractPanelHandler;
import view.MainFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SingleplayerPanelHandler extends AbstractPanelHandler {
    private JPanel panel;
    private JTextArea output;
    private JButton backButton;
    private JLabel timerLabel;
    private JLabel questionLabel;
    private JLabel answerA;
    private JLabel answerB;
    private JLabel answerC;
    private JLabel answerD;
    private JButton answerAButton;
    private JButton answerBButton;
    private JButton answerCButton;
    private JButton answerDButton;
    private JProgressBar progressBar;
    private JLabel userNameLabel;


    private Timer nextQuestionTimer;
    private double time;

    public SingleplayerPanelHandler(MainFrame mainFrame, MainController mainController) {
        super(mainFrame, mainController);
        super.setPointer(panel, output);
        createButtons();
        createTimer();
    }

    public void prepare(){
        mainController.generateQuestionsAndAnswers();
        questionLabel.setText("Hier steht gleich eine Frage.");
        answerA.setText("Sind Sie bereit?");
        answerB.setText("Dann kann es ja losgehen!");
        answerC.setText("Viel Erfolg!");
        answerD.setText("Gogogo!");
        setButtonState(false);
        userNameLabel.setText(mainController.getUserName());
        time = 3;
        nextQuestionTimer.start();
    }

    @Override
    protected void createButtons() {
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.switchSingleOrMulti();
            }
        });

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

        setButtonState(false);
    }

    private void setButtonState(boolean b){
        answerAButton.setEnabled(b);
        answerBButton.setEnabled(b);
        answerCButton.setEnabled(b);
        answerDButton.setEnabled(b);
    }

    private void createTimer(){
        nextQuestionTimer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timerLabel.setText(String.valueOf((int)(time+0.9)));
                time = time - 0.1;
                progressBar.setValue((int)(time*100/3));
                if(time < 0){
                    nextQuestionTimer.stop();
                    setQuestion();
                }
            }
        });
    }

    private void restartTimer(){
        time = 3;
        nextQuestionTimer.start();
    }

    private void setQuestion(){
        String[] qAndAs = mainController.getOneQuestion();
        questionLabel.setText(qAndAs[0]);
        answerA.setText(qAndAs[1]);
        answerB.setText(qAndAs[2]);
        answerC.setText(qAndAs[3]);
        answerD.setText(qAndAs[4]);

        setButtonState(true);
    }

    private void setAnswer(char answer){
        setButtonState(false);
        addToOutput("Es wurde Antwort " + answer + " gewählt!");
        if(mainController.evaluateAnswer(answer)){
            addToOutput("Die Antwort " + answer + " ist richtig!");
        }else{
            addToOutput("Die Antwort " + answer + " ist leider falsch. Richtig wäre " + mainController.getCorrectAnswer());
        }
        restartTimer();
    }

}
