package view;

import control.MainController;
import view.dbAdminPanels.DatabaseAdministrationPanelHandler;
import view.multiplayerPanels.*;
import view.navigationPanels.ServerOrClientPanelHandler;
import view.navigationPanels.SingleOrMultiSelectionPanelHandler;
import view.navigationPanels.LoginPanelHandler;
import view.singleplayerPanels.SingleplayerPanelHandler;

import javax.swing.*;

/**
 * Created by Jean-Pierre on 15.11.2016.
 */
public class MainFrame extends JFrame {

    // Attribute

    // Referenzen
    private AbstractPanelHandler loginPanelHandler, databaseAdministrationPanelHandler, singleOrMultiSelectionPanelHandler, serverOrClientPanelHandler;
    private AbstractPanelHandler singleplayerGameHandler;
    private AbstractPanelHandler serverAdminPanelHandler, serverLobbyPanelHandler, serverGamePanelHandler;
    private AbstractPanelHandler clientConnectToServerPanelHandler, clientLobbyPanelHandler, clientGamePanelHandler;
    private JPanel loginPanel, databaseAdministrationPanel, singleOrMultiSelectionPanel, serverOrClientPanel;
    private JPanel singleplayerGamePanel;
    private JPanel serverAdminPanel, serverLobbyPanel, serverGamePanel;
    private JPanel clientConnectToServerPanel, clientLobbyPanel, clientGamePanel;


    /**
     * Konstruktor
     * @param name Der Titel des Fensters
     * @param x Die obere linke x-Koordinate des Fensters bzgl. des Bildschirms
     * @param y Die obere linke y-Koordinaite des Fensters bzgl. des Bildschirms
     * @param width Die Breite des Fensters
     * @param height Die Höhe des Fensters
     */
    public MainFrame(MainController mainController, String name, int x, int y, int width, int height) {
        this.createPanels(mainController);

        this.setLocation(x,y);
        this.setSize(width,height);
        this.setTitle(name);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);

        this.setVisible(true);
    }

    private void createPanels(MainController mainController){
        loginPanelHandler = new LoginPanelHandler(this, mainController);
        loginPanel = loginPanelHandler.getPanel();

        databaseAdministrationPanelHandler  = new DatabaseAdministrationPanelHandler(this,mainController);
        databaseAdministrationPanel         = databaseAdministrationPanelHandler.getPanel();

        singleOrMultiSelectionPanelHandler  = new SingleOrMultiSelectionPanelHandler(this,mainController);
        singleOrMultiSelectionPanel         = singleOrMultiSelectionPanelHandler.getPanel();

        singleplayerGameHandler             = new SingleplayerPanelHandler(this,mainController);
        singleplayerGamePanel               = singleplayerGameHandler.getPanel();

        serverOrClientPanelHandler          = new ServerOrClientPanelHandler(this,mainController);
        serverOrClientPanel                 = serverOrClientPanelHandler.getPanel();

        serverAdminPanelHandler             = new ServerAdminPanelHandler(this,mainController);
        serverAdminPanel                    = serverAdminPanelHandler.getPanel();

        serverLobbyPanelHandler             = new ServerLobbyPanelHandler(this,mainController);
        serverLobbyPanel                    = serverLobbyPanelHandler.getPanel();

        serverGamePanelHandler              = new ServerGamePanelHandler(this,mainController);
        serverGamePanel                     = serverGamePanelHandler.getPanel();

        clientConnectToServerPanelHandler   = new ClientConnectToServerPanelHandler(this,mainController);
        clientConnectToServerPanel          = clientConnectToServerPanelHandler.getPanel();

        clientLobbyPanelHandler             = new ClientLobbyPanelHandler(this,mainController);
        clientLobbyPanel                    = clientLobbyPanelHandler.getPanel();

        clientGamePanelHandler              = new ClientGamePanelHandler(this,mainController);
        clientGamePanel                     = clientGamePanelHandler.getPanel();

        switchToPanel(loginPanel);
    }

    private void switchToPanel(JPanel panel){
        this.getContentPane().removeAll();
        this.getContentPane().add(panel);
        this.validate();
        this.repaint();
    }

    public void switchToDatabaseAdministration(){
        switchToPanel(databaseAdministrationPanel);
    }

    public void switchToLogin(){
        switchToPanel(loginPanel);
    }

    public void switchToSingleOrMultiSelection(){
        switchToPanel(singleOrMultiSelectionPanel);
        ((SingleOrMultiSelectionPanelHandler) singleOrMultiSelectionPanelHandler).welcomeText();
    }

    public void switchToSingleplayerGame(){
        ((SingleplayerPanelHandler)singleplayerGameHandler).prepare();
        switchToPanel(singleplayerGamePanel);
    }

    public void switchToServerOrClient(){
        switchToPanel(serverOrClientPanel);
        ((ServerOrClientPanelHandler)serverOrClientPanelHandler).welcomeText();
    }

    public void switchToServerAdmin(){
        switchToPanel(serverAdminPanel);
        ((ServerAdminPanelHandler)serverAdminPanelHandler).welcomeText();
    }

    public void switchToServerLobby(){
        ((ServerLobbyPanelHandler) serverLobbyPanelHandler).prepare();
        switchToPanel(serverLobbyPanel);
    }

    public void switchToServerGame(){
        ((AbstractGamePanelHandler)serverGamePanelHandler).prepare();
        switchToPanel(serverGamePanel);
    }

    public void switchToClientConnectToServer(){
        ((ClientConnectToServerPanelHandler)clientConnectToServerPanelHandler).prepare();
        switchToPanel(clientConnectToServerPanel);
    }

    public void switchToClientLobby(){
        ((ClientLobbyPanelHandler) clientLobbyPanelHandler).prepare();
        switchToPanel(clientLobbyPanel);
    }

    public void switchToClientGame(){
        //((AbstractGamePanelHandler)serverGamePanelHandler).prepare();
        switchToPanel(clientGamePanel);
    }
}
