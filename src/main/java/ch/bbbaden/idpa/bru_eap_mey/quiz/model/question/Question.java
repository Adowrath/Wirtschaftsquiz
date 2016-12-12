package ch.bbbaden.idpa.bru_eap_mey.quiz.model.question;

/**
 * Die Standardklasse für alle Fragen.
 * 
 * @param <AnswerType>
 *        der Datentyp der Antwort
 */
public abstract class Question<AnswerType> {
	
	/**
	 * Überprüft die Antwort und gibt, wenn sie korrekt war,
	 * {@code true} zurück.
	 * 
	 * @param answer
	 *        die zu überprüfende Antwort
	 * @return
	 * 		{@code true} wenn korrekt, sonst {@code false}
	 */
	public abstract boolean check(AnswerType answer);
	
	/**
	 * Gibt die korrekte Antwort zurück, damit sie bei
	 * Falschantwort/angezeigt markiert werden kann.
	 * 
	 * @return
	 * 		die korrekte Antwort
	 */
	public abstract AnswerType getAnswer();
	
	/**
	 * Alle Antworten als ihre anzuzeigenden Strings.
	 * 
	 * @return
	 * 		ein Array aller möglichen Antworten
	 */
	public abstract String[] getAnswers();
	
	/**
	 * Die Frage, die angezeigt wird und zu der die Antwort gefunden
	 * werden soll.
	 * 
	 * @return
	 * 		die Frage
	 */
	public abstract String getQuestion();
	
	/**
	 * Der Dateiname der FXML-Datei, die für das View geladen werden
	 * muss.
	 * 
	 * @return
	 * 		die FXML-Datei
	 */
	public abstract String getFilename();
	
}
