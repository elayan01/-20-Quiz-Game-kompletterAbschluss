package view;

import control.MainController;
import control.clientserver.QuizGameClient;

import javax.swing.*;

public abstract class AbstractClientPanelHandler extends AbstractGamePanelHandler{

    protected JList playerList;
    protected QuizGameClient client;  //Weil es weniger umständlich ist, auch wenn es MVC widerspricht: Das Panel erhält eine Referenz auf das Client-Objekt und an passender Stelle das Client-Objekt eine Referenz auf das Panel-Objekt.

    public AbstractClientPanelHandler(MainFrame mainFrame, MainController mainController){
        super(mainFrame,mainController);
    }

    public void setJList(JList jList){
        playerList = jList;
    }

    public void prepare(){
        client = mainController.getClient();
        client.registerClientPanel(this);

        updateJList();
    }

    public void updateJList(){
        playerList.removeAll();

        DefaultListModel listModel = new DefaultListModel();

        String[] players = client.getPlayerInfos();
        for(int i = 0; players != null && i < players.length; i++){
            listModel.addElement(players[i]);
        }

        playerList.setModel(listModel);
    }
}
