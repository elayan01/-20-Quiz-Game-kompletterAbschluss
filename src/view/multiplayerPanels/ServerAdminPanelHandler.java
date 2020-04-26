package view.multiplayerPanels;

import control.MainController;
import view.AbstractPanelHandler;
import view.MainFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ServerAdminPanelHandler extends AbstractPanelHandler {

    private JButton backButton;
    private JTextArea output;
    private JPanel panel;
    private JTextField serverPortTextField;
    private JTextField maxClientsTextField;
    private JButton openServerButton;

    public ServerAdminPanelHandler(MainFrame mainFrame, MainController mainController){
        super(mainFrame,mainController);
        super.setPointer(panel, output);
        createButtons();
    }

    @Override
    protected void createButtons() {
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.switchToServerOrClient();
            }
        });
        openServerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainController.openNewServer(Integer.parseInt(serverPortTextField.getText()),Integer.parseInt(maxClientsTextField.getText()));
                mainFrame.switchToServerLobby();
            }
        });
    }

    public void welcomeText(){
        output.setText("");
        addToOutput("PANEL: Legen Sie einen Port fest und starten Sie einen Server.");
        addToOutput("PANEL: Innerhalb eines LANs müssen Sie ihre LAN-IP und den festgelegten Ports an die Clients weiterleiten (z.B. per Discord).");
        addToOutput("PANEL: Für ein Spiel über das Internet müssen Sie die IP des Router ermitteln, die der Router von ihrem InternetServerProvider erhalten hat. Gehen Sie dazu z.B. auf die Internetseite www.wieistmeineIP.de");
    }
}
