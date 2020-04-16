package view.navigationPanels;

import control.MainController;
import view.AbstractPanelHandler;
import view.MainFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SingleOrMultiPanelHandler extends AbstractPanelHandler {
    private JPanel panel;
    private JButton singleplayerButton;
    private JButton multiplayerButton;
    private JTextArea output;
    private JButton backButton;

    public SingleOrMultiPanelHandler(MainFrame mainFrame, MainController mainController) {
        super(mainFrame, mainController);
        super.setPointer(panel, output);
        createButtons();
    }

    @Override
    protected void createButtons() {
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.switchToStart();
            }
        });
        singleplayerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.switchToSingleplayerGame();
            }
        });
        multiplayerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addToOutput("Kommt bald! (Hoffentlich :))");
            }
        });
    }

    public void welcomeText(){
        output.setText("");
        addToOutput("Anmeldung geglückt - willkommen " + mainController.getUserName() + "!");
        addToOutput(mainController.getUserName() + ", bitte wähle einen Spielmodus.");
    }
}
