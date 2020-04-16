package view.navigationPanels;

import control.MainController;
import view.AbstractPanelHandler;
import view.MainFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartingPanelHandler extends AbstractPanelHandler {

    private JPanel panel;
    private JTextField usernameTextField;
    private JTextField passwordTextField;
    private JButton usernameTestButton;
    private JButton passwordTestButton;
    private JButton createUserButton;
    private JButton loginButton;
    private JButton adminDBButton;
    private JTextArea output;

    public StartingPanelHandler(MainFrame mainFrame, MainController mainController){
        super(mainFrame,mainController);
        super.setPointer(panel, output);
        createButtons();
    }

    @Override
    protected void createButtons(){
        usernameTestButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                userNameTest();
            }
        });
        passwordTestButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                passwordTest();
            }
        });
        createUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createUser();
            }
        });
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });
        adminDBButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.switchToAdminDB();
            }
        });
    }

    public JPanel getPanel(){
        return panel;
    }

    private void userNameTest(){
        if(mainController.grammaTest(usernameTextField.getText())){
            addToOutput("Der Nutzername gehört zur Sprache der Grammatik.");
        }else{
            addToOutput("FEHLER: Der Nutzername gehört zur nicht Sprache der Grammatik. Bitte einen anderen Namen wählen.");
        }
    }

    private void passwordTest(){
        if(mainController.automatonTest(passwordTextField.getText())){
            addToOutput("Das Passwort wurde vom Automaten akzeptiert.");
        }else{
            addToOutput("FEHLER: Das Passwort wurde nicht vom Automaten akzeptiert. Bitte einen anderes Passwort wählen.");
        }
    }

    private void createUser(){
        if(mainController.createUser(usernameTextField.getText(),passwordTextField.getText())){
            addToOutput("Der Nutzer mit dem Namen " + usernameTextField.getText() + " und dem Passwort "+ passwordTextField.getText()+" wurde in der Datenbank hinterlegt.");
        }else{
            addToOutput("FEHLER: Mit der eingegebenen Kombination konnte kein Nutzer angelegt werden. Bitte prüfen Sie Ihre Eingabe!");
        }
    }

    private void login(){
        if(mainController.login(usernameTextField.getText(),passwordTextField.getText())){
            mainFrame.switchSingleOrMulti();
        }else{
            addToOutput("FEHLER: Login fehlgeschlagen. Bitte prüfen Sie Ihre Eingabe.");
        }
    }


}
