package view.multiplayerPanels;

import control.MainController;
import view.AbstractServerPanelHandler;
import view.MainFrame;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ServerLobbyPanelHandler extends AbstractServerPanelHandler {
    private JTextArea output;
    private JButton backButton;
    private JPanel panel;
    private JTextArea chatArea;
    private JTextField chatTextField;
    private JButton sendTextButton;
    private JList playerList;
    private JButton startGameButton;
    private JTextField timeForQuestionTextField;
    private JLabel selectedPlayerLabel;
    private JButton getQAndAsFromPlayerButton;


    public ServerLobbyPanelHandler(MainFrame mainFrame, MainController mainController){
        super(mainFrame,mainController);
        super.setPointer(panel, output, chatArea);
        super.setJList(playerList);
        createButtons();
        playerList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                selectedPlayerLabel.setText((String)playerList.getSelectedValue());
            }
        });
    }

    @Override
    protected void createButtons() {
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainController.closeServer();
                mainFrame.switchToServerAdmin();
            }
        });
        sendTextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                server.sendChatTextToAll(mainController.getUserName(),chatTextField.getText());
                addToChatArea(mainController.getUserName(),chatTextField.getText());
            }
        });
        startGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                server.startGame(Integer.parseInt(timeForQuestionTextField.getText()));
                mainFrame.switchToServerGame();
            }
        });
        getQAndAsFromPlayerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!playerList.isSelectionEmpty()){
                    String player = (String)playerList.getSelectedValue();
                    String ip = player.split(" - ")[1].split(":")[0];
                    int port = Integer.parseInt(player.split(" - ")[1].split(":")[1]);
                    server.askForQuestionsAndAnswers(ip,port);
                }
            }
        });
    }

    public void prepare(){
        super.prepare();
        output.setText("");
        addToOutput("PANEL: Neue Lobby eröffnet.");
        addToOutput("PANEL: Geöffneter Port: " + server.getPort());
        addToOutput("PANEL: Maximale Anzahl an Clients: " + server.getMaxPlayers());
    }
}
