package view.navigationPanels;

import control.MainController;
import view.AbstractPanelHandler;
import view.MainFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ServerOrClientPanelHandler extends AbstractPanelHandler {
    private JPanel panel;
    private JButton serverButton;
    private JTextArea output;
    private JButton backButton;
    private JButton clientButton;

    public ServerOrClientPanelHandler(MainFrame mainFrame, MainController mainController) {
        super(mainFrame, mainController);
        super.setPointer(panel, output);
        createButtons();
    }

    @Override
    protected void createButtons() {
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.switchToSingleOrMultiSelection();
            }
        });
        serverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.switchToServerAdmin();
            }
        });
        clientButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.switchToClientConnectToServer();
            }
        });
    }

    public void welcomeText(){
        output.setText("");
        addToOutput("PANEL: Als Server legen Sie die Einstellungen für ein Spiel fest.\n");
        addToOutput("PANEL: Als Client können Sie bei einem fremd-gehosteten Spiel einsteigen. Dazu müssen Sie nur die IP und den Port kennen.\n");
    }
}
