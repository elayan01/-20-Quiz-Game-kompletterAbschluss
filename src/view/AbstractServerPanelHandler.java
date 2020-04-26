package view;

import control.MainController;
import control.clientserver.QuizGameServer;

import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Achtung, schlechte Modellierung: ich benötige Methoden vom AbstractGamePanelHandler UND vom AbstractServerPanelHandler für die Klasse ServerGamePanelHandler.
 * Erben von zwei Klassen ist in Java nicht möglich. Quick and Dirty: AbstractServerPanelHandler erbt nun von AbstractGamePanelHandler, auch wenn es nicht immer ein Spiel ist (z.B. in der Lobby).
 */
public abstract class AbstractServerPanelHandler extends AbstractGamePanelHandler{

    protected JList playerList;
    protected QuizGameServer server;  //Weil es weniger umständlich ist, auch wenn es MVC widerspricht: Das Panel erhält eine Referenz auf das Server-Objekt und an passender Stelle das Server-Objekt eine Referenz auf das Panel-Objekt.

    public AbstractServerPanelHandler(MainFrame mainFrame, MainController mainController){
        super(mainFrame,mainController);
    }

    public void setJList(JList jList){
        playerList = jList;
    }

    public void prepare(){
        server = mainController.getServer();
        server.registerServerPanel(this);
        server.registerANewPlayer(mainController.getUserName(), "127.0.0.1", server.getPort());
        if(!server.getServerState().equals(QuizGameServer.LOBBY)){
            super.prepare();
        }

        updateJList();
    }

    public void updateJList(){
        playerList.removeAll();

        DefaultListModel listModel = new DefaultListModel();

        String[][] players = server.getPlayerInfos();
        for(int i = 0; players != null && i < players.length; i++){
            String outputText = players[i][0] + " - " + players[i][1] +":"+players[i][2];
            listModel.addElement(outputText);
        }

        playerList.setModel(listModel);
    }
}
