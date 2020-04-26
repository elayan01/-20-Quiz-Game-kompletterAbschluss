package view.navigationPanels;

import control.MainController;
import view.AbstractPanelHandler;
import view.MainFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SingleOrMultiSelectionPanelHandler extends AbstractPanelHandler {
    private JPanel panel;
    private JButton singleplayerButton;
    private JButton multiplayerButton;
    private JTextArea output;
    private JButton backButton;

    public SingleOrMultiSelectionPanelHandler(MainFrame mainFrame, MainController mainController) {
        super(mainFrame, mainController);
        super.setPointer(panel, output);
        createButtons();
    }

    @Override
    protected void createButtons() {
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.switchToLogin();
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
                mainFrame.switchToServerOrClient();
            }
        });
    }

    public void welcomeText(){
        output.setText("");
        addToOutput("PANEL: Anmeldung geglückt - willkommen " + mainController.getUserName() + "!");
        addToOutput("PANEL: "+mainController.getUserName() + ", bitte wählen Sie einen Spielmodus.");
    }
}
