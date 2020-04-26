package view.dbAdminPanels;

import control.MainController;
import view.AbstractPanelHandler;
import view.MainFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DatabaseAdministrationPanelHandler extends AbstractPanelHandler {

    private JPanel panel;
    private JButton backButton;
    private JTextArea sqlInput;
    private JButton executeButton;
    private JTextArea output;
    private JTextField question;
    private JTextField answerA;
    private JTextField answerB;
    private JTextField answerC;
    private JTextField answerD;
    private JRadioButton radioButtonA;
    private JRadioButton radioButtonB;
    private JRadioButton radioButtonC;
    private JRadioButton radioButtonD;
    private JButton insertQuestionButton;


    public DatabaseAdministrationPanelHandler(MainFrame mainFrame, MainController mainController) {
        super(mainFrame, mainController);
        createButtons();
        super.setPointer(panel, output);
    }

    @Override
    protected void createButtons() {
        executeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                execute();
            }
        });
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.switchToLogin();
            }
        });
        insertQuestionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                insertQuestionToDB();
            }
        });
    }

    private void execute(){
        mainController.execute(sqlInput.getText());
        addToOutput(mainController.getSQLOutput());
    }

    private void insertQuestionToDB(){
        char rightAnswer ='A';

        if(radioButtonB.isSelected()){
            rightAnswer = 'B';
        }else if(radioButtonC.isSelected()){
            rightAnswer = 'C';
        }else if(radioButtonD.isSelected()){
            rightAnswer = 'D';
        }
        boolean didIt = mainController.insertQuestion(question.getText(),answerA.getText(),answerB.getText(),answerC.getText(),answerD.getText(),rightAnswer);

        if(didIt){
            addToOutput("MAINCONTROLLER: Frage erfolgreich hinzugefügt.");
        }else{
            addToOutput("MAINCONTROLLER-FEHLER: Frage samt Antwortne existiert bereits in der DB oder es gab einen anderweitigen Fehler. Der Befehl wurde nicht ausgeführt.");
        }

        addToOutput(mainController.getSQLOutput());
    }


}
