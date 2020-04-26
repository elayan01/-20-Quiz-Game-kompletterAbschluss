package view.multiplayerPanels;

import control.MainController;
import view.AbstractPanelHandler;
import view.MainFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClientConnectToServerPanelHandler extends AbstractPanelHandler {
    private JPanel panel;
    private JButton backButton;
    private JButton connectToServerButton;
    private JTextField serverPortTextField;
    private JTextField serverIPTextField;
    private JTextArea output;

    public ClientConnectToServerPanelHandler(MainFrame mainFrame, MainController mainController) {
        super(mainFrame, mainController);
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
        connectToServerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ip   = serverIPTextField.getText();
                int port    = Integer.parseInt(serverPortTextField.getText());
                if(mainController.createClientToServerConnection(ip,port)){
                    mainFrame.switchToClientLobby();
                }else{
                    addToOutput("MAINCONTROLLER-FEHLER: Es konnte keine Verbindung zum gewünschten Server hergestellt werden. Prüfen Sie IP und Port. Falls diese Stimmen, dann stellt sich Ihr Router oder der des Servers quer. Dies lässt sich mit Fachwissen in der jeweiligen Administration des Routers beheben.");
                }
            }
        });
    }

    public void prepare(){
        if(mainController.closeClient()){
            addToOutput("MAINCONTROLLER: Eine bestehende Verbindung zu einem Server wurde geschlossen.");
        }
    }
}
