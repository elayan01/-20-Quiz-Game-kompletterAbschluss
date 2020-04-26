package control.clientserver;

import control.MainController;
import view.AbstractClientPanelHandler;

public class QuizGameClient extends Client {

    private String serverIP;
    private int serverPort;
    private AbstractClientPanelHandler clientPanelHandler;
    private int currentWaitTimeToAnswer;
    private String serverState; //Der Client merkt sich den Serverstatus. Dieses wird durch den Server ständig aktualisiert, da der Client ständig anfragt.
    private MainController mainController;

    public QuizGameClient(String pServerIP, int pServerPort, MainController mainController) {
        super(pServerIP, pServerPort);
        this.serverIP = pServerIP;
        this.serverPort = pServerPort;
        currentWaitTimeToAnswer = 10;
        this.mainController = mainController;
    }

    /**
     * Da die Panels, die auf den Server zugreifen, unterschiedlich sind, aber alle im Falle des Zugriffs dem Server-Objekt bekannt sein müssen,
     * müssen sie sich registrieren. So haben Sie für das Server-Objekt "priorität".
     * @param clientPanelHandler
     */
    public void registerClientPanel(AbstractClientPanelHandler clientPanelHandler){
        this.clientPanelHandler = clientPanelHandler;
    }

    @Override
    public void processMessage(String pMessage) {
        //TODO Umsetzung des Client-Protokolls.
        clientPanelHandler.addToChatArea("UBEKANNT",pMessage);
        clientPanelHandler.addToOutput("Nachricht vom Server empfangen, nicht nicht verarbeitet: "+pMessage);
    }

    /**
     * Die Informationen, welche Spieler beim Server registriert sind, werden vom Server erfragt und in einem eindimensionalem Array gespeichert.
     * Ein Client darf dabei nur die Spielernamen wissen, nicht jedoch deren IPs und Ports.
     * @return Namen zu allen Spielern. Das erste Objekt ist der Server-Spieler selbst.
     */
    public String[] getPlayerInfos(){
        String[] result = new String[0];

        //TODO Der Client fragt beim Server die Anzahl der Spieler an, damit das Array mit passender Größe erstellt werden kann. Im Anschluss werden die Spielernamen erfragt und nach und nach im Array gespeichert.

        return result;
    }

    /**
     * Über den Server soll ein Text an alle Nutzer gesendet werden.
     * Da der Server den Text an alle Nutzer schickt, erhält der sendene Client den Text ebenfalls und muss ihn dann dementsprechend an die View weiterleiten. Dies muss in processMessage des QuizGameClients geregelt werden.
     * @param user
     * @param text
     */
    public void sendChatTextToAll(String user, String text){
        //TODO Chat-Text des Clients mit passendem Befehlswort an den Server schicken (Protokoll beachten!).
    }

    /**
     * Eine Antwort wurde in der View gewählt, woraufhin die Methode sendAnswer() aufgerufen wurde. Die Antwort wird an den Server geschickt.
     * @param answer
     */
    public void sendAnswer(char answer){
        //TODO Die Antwort eines Clients muss an den Server geschickt werden (Protokoll beachten!)
    }

    /**
     * Der Client wurde vom Server aufgefordert seine in der DB hinterlegten Fragen, Antworten und Lösungen dem Server zur Verfügung zu stellen.
     * Diese müssen nun gemäß Protokoll an den Server übermittelt werden.
     *
     * Diese Fragen und Antworten kommen natürlich aus der Client-DB, auf die der QuizGameClient per MainController zugreifen kann.
     */
    public void sendQuestionsAndAnswersAndSolutionFromDB(){
        //TODO Senden aller Fragen und Antworten einer Client-DB an den Server (Protokoll beachten!)
    }

    /**
     * Liefert die Zeit zurück, die der Server zum Warten festlegt.
     * @return
     */
    public double getCurrentWaitTimeToAnswer(){
        return currentWaitTimeToAnswer;
    }

    /**
     * Damit die Progressbar in der Client View das richtige anzeigt, wird der Server-Status erfragt. Der Server muss darauf achten, dass er seinen Satus den Clients stets mitteilt und aktualisierungen ggf. angibt (Protokoll!)
      * @return
     */
    public String getServerState(){
        return serverState;
    }

    public String getServerIP() {
        return serverIP;
    }

    public int getServerPort() {
        return serverPort;
    }
}
