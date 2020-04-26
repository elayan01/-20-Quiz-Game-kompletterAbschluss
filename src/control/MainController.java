package control;

import control.clientserver.QuizGameClient;
import control.clientserver.QuizGameServer;
import control.database.DatabaseConnector;
import model.QuestionAndAnswers;

public class MainController {

    //Attribute
    private String userName = "Nutzer unbekannt";

    //Referenzen für die DB
    private DatabaseConnector dbConnector;

    //Referenz für das eigentliche Spiel
    private QuestionAndAnswers[] allQs;
    private QuestionAndAnswers currentQuestion;

    //Referenzen für Client/Server
    private QuizGameServer server;
    private QuizGameClient client;

    /**
     * Achtung: dieser MainController übernimmt ALLE Arbeiten in diesem Programm. An sich wäre eine Aufteilung im Sinne einer hohen Kohäsion besser.
     */
    public MainController(){
        dbConnector = new DatabaseConnector("127.0.0.0",1337,"\\model\\database\\QuizDB.sqlite","admin","12346");
    }


    // Methoden für die Administration der DB //

    /**
     * Eine Frage samt Antwortmöglichkeiten und richtiger Antwort wird der Datenbank hinzugefügt.
     * Hierzu wird zunächst überprüft, ob die Frage samt Antwortwortmöglichkeiten (in dieser Reihenfolge!) noch nicht in der DB existiert.
     * Falls noch nicht, dann wird die Frage hinzugefügt und true zurückgegeben.
     * Ansonsten false.
     * @param q Frage
     * @param a Antworttext für Antwort A
     * @param b Antworttext für Antwort B
     * @param c Antworttext für Antwort C
     * @param d Antworttext für Antwort D
     * @param rightAnswer Buchstabe der korrekten Antwort
     * @return true, falls eine neue Frage erfolgreich hinzugefügt werden konnte, ansonsten false.
     */
    public boolean insertQuestion(String q, String a, String b, String c, String d, char rightAnswer){
        //TODO Eine einzelne Frage der DB hinzufügen. Ohne Fragen, kein Spiel :)
        return false;
    }

    /**
     * Der eingetragene SQL-Befehl auf der Administrationsseite wird an die Datenbank weitergeleitet.
     * @param sqlCommand
     */
    public void execute(String sqlCommand){
        dbConnector.executeStatement(sqlCommand);
    }

    /**
     * Es wird überprüft, ob der Nutzer+Passwort in der Datenbank hinterlegt sind.
     * Falls ja, so wird der aktuelle userName gesetzt und true zurückgegeben, ansonsten false.
     * @param user
     * @param password
     * @return true, falls ja, ansonsten false.
     */
    public boolean login(String user, String password){
        //TODO Login programmieren. Aktuell liefert die Methode immer true zurück, damit man sich überhaupt am Spiel anmelden kann. Dies muss noch gemäß Dokumentation überarbeitet werden.
        if(!user.equals("")){
            this.userName = user;
        }
        return true;
    }

    /**
     * Es wird zunächst überprüft, ob die Parameterwert einem Grammatik- und einem Automatentest genügen.
     * Falls ja, wird anschließend geprüft, ob die Kombination aus Nutzer+Passwort in der DB noch nicht vorhanden ist.
     * Falls ja, wird der Nutzer in der DB angelegt und true zurückgegeben.
     *
     * Sollte irgendetwas schief gehen, so wird false zurückgegeben.
     * @param user
     * @param password
     * @return
     */
    public boolean createUser(String user, String password){
        //TODO Anlegen eines Nutzers programmieren.
        return false;
    }

    /**
     * Methode liefert eine aufbereitete Darstellung der Ausgabe des letzten SQL-Kommandos, sofern eine existiert.
     * Falls keine existiert weil es einen Fehler gab, so wird der Fehler ausgegeben.
     * Ansonsten wird ein Standardtext zurückgegeben, um dem Nutzer mitzuteilen, dass das Kommando angenommen wurde.
     * @return
     */
    public String getSQLOutput(){
        //TODO Rückmeldung des Datenbankzugriffs ausgeben.
        return "blubb";
    }








    // Methoden für die Automaten- und Grammatik-Theorie //

    /**
     * Der Nutzername wird dahingehend geprüft, ob er zur Sprache einer Grammatik gehört.
     * Zur Prüfung soll mit einem seperatem Scanner mit Tokenliste und einem Parser gearbeitet werden.
     * Der Nutzername soll eine E-Mailadresse mit folgenden Eigenschaften sein:
     *  - Vor dem @ Zeichen können ein bis beliebig viele Labels getrennt durch einen Punkt stehen.
     *  - Jedes Label beginnt mit einem Buchstaben und kann dann beliebige weitere Buchstaben/Ziffern in wilder Kombination haben.
     *  - Nach dem @ steht eine Buchstabenkombination, die mindestens 3 Buchstaben entwählt.
     *  - Nach dieser Kombination folgt ein Punkt.
     *  - Nach diesem letzten Punkt folgen 2 bis 4 Buchstaben.
     * @param user
     * @return true, falls user zur Grammatik gehört, sonst false.
     */
    public boolean grammaTest(String user){
        //TODO Implementierung eines Scanner+Parsers zur Prüfung, ob der Nutzername zu einer Grammatik gehört.
        return false;
    }

    /**
     * Das Passwort wird dahingehend geprüft, ob es zur Sprache eines Automaten gehört.
     * Der Automat kann ein DEA oder ein Kellerautomat sein. Traut sich jemand an einen nichtdeterministischen (Keller)Automaten? :)
     *
     * @param password
     * @return true, falls password vom Automaten akzeptiert wird, sonst false.
     */
    public boolean automatonTest(String password){
        //TODO Automatentest implementieren. Kann DEA oder endlicher Kellerautomat sein. Regeln für Passwörter legt ihr fest. Ich empfehle einen Kellerautomaten zu programmieren.
        return false;
    }



    // Methoden für Client-Server-Kommunikation //

    /**
     * Ein neues Server-Objekt wird erstellt. Falls ein altes Server-Objekt mit bestehender Verbindung vorhanden ist, wird dieses zuvor gelöscht.
     * @param port Gibt den Port der Anwendung an.
     * @param maxClients Legt die maximale Anzahl an Clients fest. Beachte: Der Server wird als Spieler zusätzlich gezählt. D.h. maxClients = 3 -> 4 Spieler insgesamt
     */
    public void openNewServer(int port, int maxClients){
        closeServer();
        server = new QuizGameServer(port,maxClients,this);
    }

    /**
     * Falls ein Server-Objekt mit aktiver Verbindung vorhanden ist, wird diese Verbindung geschlossen.
     */
    public void closeServer(){
        if(server != null && server.isOpen()){
            server.close();
        }
    }

    /**
     * Rückgabe der Referenz auf den Server. Ist wichtig, damit die Kommunikation zwischen Server-Lobby und Server-Objekt einfach von statten geht.
     * Achtung: widerspricht dem MVC-Konzept.
     * @return
     */
    public QuizGameServer getServer(){
        return server;
    }


    /**
     * Falls es eine bestehe Verbindung zu einem Server gibt, wird diese zunächst geschlossen.
     *
     * Danach wird versucht, eine neue Verbindung zu einem Server herzustellen und das Ergebnis dieses Versuchs wird zurückgegeben.
     * @param ip
     * @param port
     * @return true, falls der Client eine Verbindung herstellen konnte, ansonsten false.
     */
    public boolean createClientToServerConnection(String ip, int port){
        closeClient();
        client = new QuizGameClient(ip, port, this);
        return client.isConnected();
    }

    /**
     * Falls ein Client-Objekt mit aktiver Verbindung vorhanden ist, wird diese Verbindung geschlossen.
     */
    public boolean closeClient(){
        if(client != null && client.isConnected()){
            client.close();
            return true;
        }
        return false;
    }

    /**
     * Rückgabe der Referenz auf den Client. Ist wichtig, damit die Kommunikation zwischen Client-Lobby und Client-Objekt einfach von statten geht.
     * Achtung: widerspricht dem MVC-Konzept.
     * @return
     */
    public QuizGameClient getClient(){
        return client;
    }



    // Methoden für das eigentliche Spiel - mit DB-Zugriff //

    /**
     * Das Array allQs wird mit den Fragen, die in der Datenbank vorhanden sind, gefüllt.
     * Hierzu werden die einzelnen Einträge in der DB in jeweils ein Objekt der Klasse QuestionAndAnswers übertragen.
     * Jedes Objekt bekommt dann einen Platz im Array allQs.
     */
    public void generateQuestionsAndAnswers(){
        //TODO Auslesen der DB für Fragen und Antworten implementieren.
    }

    /**
     * Aus dem Array wird eine einzelne Fragen per Zufall bestimmt.
     * Die Referenz currentQuestion verweist dann auf diese (aktuelle) Frage.
     * Die für das Nutzererlebnis relevanten Informationen werden in einem eindimensionalen Array der Größe 5 gespeichert und zurückgegeben.
     * @return
     */
    public String[] getOneQuestion(){
        //TODO Eine konkrete Frage auswählen und String-Array erstellen.
        String[] result = new String[5];
        result[0] = "Frage >8)=";
        result[1] = "Antwort A :O";
        result[2] = "Antwort B (O_o)";
        result[3] = "Antwort C D8=";
        result[4] = "Antwort D :OÖO:";
        return result;
    }

    /**
     * Sobald eine Antwort vom Nutzer gewählt wurde, wird diese Methode aufgerufen.
     * Sie gibt true zurück, falls der übergebene Parameter answer gleich der correctAnswer der aktuellen Frage currentQuestion ist. Sonst false.
     * Falls vorhanden (müsst ihr selber in der DB anlegen und in der GUI ausgeben lassen),
     * werden Nutzerinformationen in der DB aktualisiert, z.B. wird der Counter "Anzahl beantworteter Fragen" hochgezählt. Ähnliches für "Anzahl richtiger Antworten".
     * @param answer
     * @return
     */
    public boolean evaluateAnswer(char answer){
        //TODO Antwort prüfen.
        return false;
    }

    /**
     * Es wird von der aktuellen Frage der korrekte Antwortbuchstabe zurückgegeben.
     * @return
     */
    public char getCorrectAnswer(){
        //TODO Korrekte Antwort zurückgeben.
        return 'Z';
    }

    // Sonstige Methoden //

    public String getUserName(){
        return userName;
    }
}
