package view.singleplayerPanels;

import control.MainController;
import view.AbstractGamePanelHandler;
import view.MainFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SingleplayerPanelHandler extends AbstractGamePanelHandler {
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

    public SingleplayerPanelHandler(MainFrame mainFrame, MainController mainController) {
        super(mainFrame, mainController);
        super.setPointer(panel, output, progressBar);
        super.setPointerForLabels(userNameLabel, timerLabel, questionLabel, answerA, answerB, answerC, answerD);
        super.setPointerForAnswerButtons(answerAButton, answerBButton, answerCButton, answerDButton);
        createButtons();
        createTimer();
    }

    @Override
    protected void createButtons() {
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.switchToSingleOrMultiSelection();
            }
        });
    }

    @Override
    protected void setAnswer(char answer){
        setAnswerButtonsStates(false);
        addToOutput("PANEL: Es wurde Antwort " + answer + " gewählt!");
        if(mainController.evaluateAnswer(answer)){
            addToOutput("MAINCONTROLLER: Die Antwort " + answer + " ist richtig!");
        }else{
            addToOutput("MAINCONTROLLER: Die Antwort " + answer + " ist leider falsch. Richtig wäre " + mainController.getCorrectAnswer());
        }
        restartTimer();
    }

}
