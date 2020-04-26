package control.clientserver;

import control.MainController;
import model.Player;
import model.datastructures.List;
import view.AbstractServerPanelHandler;

public class QuizGameServer extends Server {

    private int port;
    private int maxPlayers;

    private List<Player> allPlayers; //Diese Liste dient der Verwaltung einfacher Informationen zu den einzelnen Clients. An die Objekte in dieser Liste kann NICHTS gesendet werden. Dazu muss man die Server-Methoden send bzw. sendToAll verwenden und bei Bedarf die IPs und Ports der Spieler auslesen.
    private AbstractServerPanelHandler serverPanelHandler;
    private String serverState;
    private int timeToAnswer;

    private MainController mainController;

    //Meine Vorschläge für verschiedene Stati des QuizGameServers
    public final static String LOBBY = "Lobby-Phase";
    public final static String WAITFORQUESTION = "Auf-neue-Frage-warten";
    public final static String WAITFORANSWERS = "Auf-Antworten-warten";
    public final static String END = "Spiel-Ende";

    /**
     * Ein QuiGameServer verwaltet alles: Chatten der Spieler untereinander, Spielgeschehen und was es sonst noch gibt.
     * Dabei ist der Nutzer, der den Server steuert, ebenfalls ein teilnehmder Spieler, jedoch natürlich kein Client.
     * D.h. der Benutzer, der den Server steuert, ist als Objekt in der Liste allPlayers zu finden, jedoch nicht in der von der Oberklasse Server verwalteten Liste der Clients.
     * @param port
     * @param maxPlayers
     * @param mainController - das MainController-Objekt wird benötigt, um auf die Datenbank zugreifen zu können.
     */
    public QuizGameServer(int port, int maxPlayers, MainController mainController) {
        super(port);
        this.port = port;
        this.maxPlayers = maxPlayers;
        allPlayers = new List<>();
        serverState = LOBBY;
        this.mainController = mainController;
    }

    /**
     * Prüfe, ob ein Client noch hinzustoßen kann, also die Maximalzahl an Spielern noch nicht erreicht ist.
     * Falls ja, so füge dessen Nutzernamen (per Protokoll erfragen) und IP und Port als neues Objekt in die Liste allPlayers ein. Rufe anschließend die Methode updateJList() des AbstractServerPanelHandler-Objekts auf.
     * Falls nein, so teile dem Client mit, dass die maximale Anzahl bereits erreicht worden ist (per Protokoll!) und schließe die Verbindung zu diesem Client wieder.
     *
     * Beachte laut Dokumentation vom Land: wenn der SERVER die Methode zum Schließen einer Verbindung aufruft, wird AUTOMATISCH VORHER (!) die Methode processClosingConnection aufgerufen.
     * Darum kümmert sich die Oberklasse Server. Wir müssen jedoch spezifizieren, was vor dem Schließen der Verbindung zu einem Client passieren soll - siehe unten.
     *
     * @param pClientIP
     * @param pClientPort
     */
    @Override
    public void processNewConnection(String pClientIP, int pClientPort) {
        //TODO Umsetzung der Methode, falls sich ein Client anmeldet.
    }

    /**
     * Hier werden alle eingehenden Nachrichten verarbeitet. Es gibt zwei grundlegende Situationen, die eventuell (!) brücksichtig werden sollten:
     *
     * 1. Lobby-Phase - im Prinzip wird hier nur gechattet. Das Protokoll legt fest, was möglich ist.
     * 2. Spiel-Phase - hier müssen Fragen und Antworten gesendet werden. Zudem eventuelle Spieldaten und Auswertungen von Antworten. Auch hier legt das Protokoll fest, was möglich ist. Beachte, dass es zwischen den Fragen eine "Pause" gibt und man eine gewisse Zeit hat, eine Antwort zu geben.
     *
     * Oft ist es sinnvoll, wenn man Nachrichten an Methoden weiterleitet, die für diese Nachrichten zuständig sind. Befehleswort + ggf. Zustand des Servers legen fest, welche Methoden aufgerufen werden.
     * Es wird festgelegt, dass die Nachrichten (also Inhalte von pMessage) stets den Aufbau BEFEHLSWORT#Informatinonen:ggf. durch Doppelpunkt:getrennt haben.
     *
     * @param pClientIP
     * @param pClientPort
     * @param pMessage
     */
    @Override
    public void processMessage(String pClientIP, int pClientPort, String pMessage) {
        serverPanelHandler.addToOutput("SERVER: "+pClientIP+":"+port+" sende " +pMessage);
        //TODO Protokoll für die Lobby- und Spielphase umsetzen
        if(serverState.equals(LOBBY)){

        }else if(serverState.equals(WAITFORQUESTION)) {

        }else if(serverState.equals(WAITFORANSWERS)){
            //Anworten der Clients müssen in ihren jeweiligen Objekt gespeichert werden.
        }else if(serverState.equals(END)){

        }
    }

    /**
     * Erläuterung: Falls der Server die Verbindung zum Client bewusst schließt, wird diese Methode automatsich VORHER aufgerufen.
     * Erläuterung: Falls ein Verbindungsverlust "aus Versehen" passiert oder vom Client initiiert wird, wird die Methode DANACH aufgerufen.
     *
     * Was ist zu tun:
     * Es wird in der Liste allPlayers nach dem zu IP und Port passendem Client-Objekt gesucht. Falls dies vorhanden ist, wird es entfernt und die Methode updateJList() des AbstractServerPanelHandler-Objekts aufgerufen.
     * Falls eine Vebindung zum Client besteht (das Schließen der Verbindung also vom Server initiiert wurde), wird der Client darüber informiert, dass nun die Verbindung geschlossen wird.
     *
     * @param pClientIP
     * @param pClientPort
     */

    @Override
    public void processClosingConnection(String pClientIP, int pClientPort) {
        //TODO Umsetzung der Methode, falls die Verbindung zu einem Client geschlossen wird.
    }

    /**
     * Eine Text-Nachricht wird an alle Spieler gesendet.
     * Hierzu muss das Protokoll eingehalten werden.
     * Die Textnachricht soll bei jedem Client erscheinen.
     * Dass die eigene Nachricht ebenfalls erscheint, darum kümmert sich die AbstractServerPanelHandler-View.
     * @param user Nutzer, der die Nachricht abschickt.
     * @param text Der zu sendende Text.
     */
    public void sendChatTextToAll(String user, String text){
        //TODO Protokoll richtig umsetzen
        sendToAll("BEFEHLSWERTFESTLEGEN:Nachricht ebenfalls");
    }

    public int getPort(){
        return port;
    }

    /**
     * Bei Aufruf der Methode wird die Liste allPlayers durchgegangen und die Objekte werden gezählt.
     * @return
     */
    public int getPlayerCount(){
        //TODO Zählen der Objekte innerhalb einer Liste muss umgesetzt werden. Es kann sein, dass dies zur Laufzeit zu Fehlern führt (wenn während des Zählens ein Objekt hinzugefügt wird). Mal gucken :)
        return 1;
    }

    public int getMaxPlayers(){
        return maxPlayers;
    }

    /**
     * Da die Panels, die auf den Server zugreifen, unterschiedlich sind, aber alle im Falle des Zugriffs dem Server-Objekt bekannt sein müssen,
     * müssen sie sich registrieren. So haben Sie für das Server-Objekt "priorität".
     * @param serverPanelHandler
     */
    public void registerServerPanel(AbstractServerPanelHandler serverPanelHandler){
        this.serverPanelHandler = serverPanelHandler;
    }

    /**
     * Überprüfe, ob der username nicht schon in der Liste allPlayers vorhanden ist. Falls nicht, wird ein entsprechendes Objekt der Liste hinzugefügt.
     * @param username
     * @param pClientIP
     * @param pClientPort
     */
    public void registerANewPlayer(String username, String pClientIP, int pClientPort){
        //TODO Überprüfung, so dass kein Spielername doppelt vorhanden ist, muss noch umgesetzt werden.
        Player player = new Player(username,pClientIP,pClientPort);
        allPlayers.append(player);
    }

    /**
     * Die Informationen, die in der Liste allPlayers zu finden sind, werden in einem zweidimensionalen Array gespeichert.
     * Die erste Dimension legt quasi das Player-Objekt fest. Die zweite Dimension ist für die Informationen Name, IP und Port.
     * @return Informationen zu allen Spielern. Das erste Objekt ist der Server-Spieler selbst.
     */
    public String[][] getPlayerInfos(){
        String[][] result = new String[getPlayerCount()][3];
        allPlayers.toFirst();
        Player p = allPlayers.getContent();
        result[0][0] = p.getUserName();
        result[0][1] = p.getIP();
        result[0][2] = String.valueOf(p.getPort());

        //TODO Aus der kompletten (!) allPlayers-Liste muss ein zweidimensionales Array gemacht werden. Hier sieht man nur ein Beispiel für das erste Objekt in der Liste.

        return result;
    }

    /**
     * Die Clients werden über den Start des Spiels informiert.
     * Zudem wird mitgeteilt, wie viel Zeit man zum Beantworten einer Frage hat.
     * Der Server geht in den Zustand WAITFORQUESTION über, es wird also Zeit ablaufen, bevor die erste Frage gestellt wird.
     * Der neue Server-Status muss den Clients mitgeteilt werden.
     * @param timeToAnswer
     */
    public void startGame(int timeToAnswer){
        serverState = WAITFORQUESTION;
        this.timeToAnswer = timeToAnswer;
        //TODO Spielstart beim Server regeln.
    }

    public void backToLobby(){
        serverState = LOBBY;
    }

    /**
     * Eine Frage samt möglicher Antworten wird an alle Clients verschickt.
     * @param qAndAs - wird über den ServerGamePanelHandler aus dem MainController (also quasi der DB) geholt und für alle Clients festgelegt. Dies wurde bereits umgesetzt.
     */
    public void sendQuestionToAll(String[] qAndAs){
        //TODO Frage und Antworten an Clients senden (Protokoll beachten!). Die Auswahl der Frage wurde bereits umgesetzt, d.h. qAndAs hat einen von außen festgelegten Inhalt.

        serverState = WAITFORANSWERS;
        serverPanelHandler.addToOutput("SERVER: Es wurde eine Frage samt Antwortmöglichkeit an alle Clients versendet.");
    }

    public String getServerState(){
        return serverState;
    }

    public int getTimeToAnswer(){
        return timeToAnswer;
    }

    /**
     * Liefert die Zeit zurück, die aktuell für die ProgressBar der View wichtig ist.
     * Diese Zeit hängt von
     * @return
     */
    public double getCurrentWaitTime(){
        if(serverState.equals(QuizGameServer.WAITFORQUESTION)){
            return 3;
        }else if(serverState.equals(QuizGameServer.WAITFORANSWERS)){
            return timeToAnswer;
        }else{
            return 3;
        }
    }

    /**
     * Die Antwort, die ein Spieler (sei es ein Client oder der Server selbst) abgibt, muss im entsprechenden Objekt der Liste allPlayers aktualisiert werden.
     *
     * Hinweis: Sollte ein Spieler nicht im Zeitrahmen antworten, so wird sein zuletzt festgelegter Antwortbuchstabe als gewählte Antwort ausgewertet.
     *
     * @param pClientIP
     * @param pPort
     * @param answer
     */
    public void setAPlayersAnswer(String pClientIP, int pPort, char answer){
        //TODO Die Antwort eines Spielers muss im entsprechenden Objekt aktualisiert werden, damit evalueAllAnswers korrekt arbeiten kann.
    }

    /**
     * Die Antworten eines jeden Clients(!) werden evaluiert.
     * Den Clients wird rückgemeldet, ob ihre Antwort richtig war oder ebend nicht.
     *
     * Der Server spielt zwar mit, ist aber kein Client. Deswegen wird seine Antwort direkt vom MainController-Objekt ausgewertet.
     *
     * Hier können zusätzliche Dinge ausgewertet werden, wie z.B. eine Rangliste (dies muss man jedoch vollständig selber ergänzen :))
     *
     * Der neue Server-Status muss den Clients mitgeteilt werden (Protokoll beachten!)
     */
    public void evaluateAllAnswers(){
        char correctAnswer = mainController.getCorrectAnswer();
        //TODO Auswertung der Spieler-Antworten und Mitteilung an die Clients (Protokoll beachten!) und an den Server-Spieler per Panel.

        serverPanelHandler.addToOutput("SERVER: Jedem Spieler wurde mitgeteilt, ob seine Antwort richtig ist. Ggf. wurden weitere Informationen versendet.");
        serverState = WAITFORQUESTION;
    }

    /**
     * Der QuizGameServer hat das Bedürfnis, seine in der eigenen Datenbank gespeicherten Fragen und Anworten (samt Lösungen) durch Fragen und Antworten (samt Lösungen) von anderen Spielern zu erweitern.
     * Bei Aufruf wurde ein verbundener Client ausgewählt. Gemäß Protokoll werden Fragen, Antworten und Lösung vom Client angefordert.
     *
     * Die ankommenden Daten muss der Server dann per ProcessMessage verarbeiten (dazu greift er auf die Methode addNewQAndAs zu.
     * @param pClientIP
     * @param pPort
     */
    public void askForQuestionsAndAnswers(String pClientIP, int pPort){
        //TODO Anfrage für neue Fragen und Antworten gemäß Protokoll umsetzen.
    }

    /**
     * Clients können Fragen und Anworten an den Server schicken, damit dieser seine Sammlung erweitern kann.
     *
     * Dazu muss der übergebene Parameter aufgeschlüsselt und vom MainController-Objekt die richtige Methode aufgerufen werden.
     * @param qAndAsAndSolution
     */
    private void addNewQAndAs(String qAndAsAndSolution){
        //TODO Neue Fragen und Anworten der DB hinzufügen.
    }
}
