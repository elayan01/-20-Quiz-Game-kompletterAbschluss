package view.multiplayerPanels;

import control.MainController;
import view.AbstractClientPanelHandler;
import view.MainFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClientLobbyPanelHandler extends AbstractClientPanelHandler {

    private JButton backButton;
    private JTextField chatTextField;
    private JButton sendTextButton;
    private JTextArea chatArea;
    private JList playerList;
    private JTextArea output;
    private JPanel panel;

    public ClientLobbyPanelHandler(MainFrame mainFrame, MainController mainController) {
        super(mainFrame, mainController);
        super.setPointer(panel, output, chatArea);
        super.setJList(playerList);
        createButtons();
    }

    @Override
    protected void createButtons() {
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                client.sendChatTextToAll(mainController.getUserName(),"Ich verabschiede mich! (Achtung, automatischer Text).");
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
        output.setText("");
        addToOutput("PANEL: Neue Lobby eröffnet.");
        addToOutput("PANEL: Sie sind mit dem Server "+client.getServerIP()+":"+client.getServerPort()+" verbunden.");
    }
}
