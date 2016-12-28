package ch.bbbaden.idpa.bru_eap_mey.quiz.model.question;

import org.eclipse.jdt.annotation.NonNull;


import ch.bbbaden.idpa.bru_eap_mey.quiz.model.Category;

/**
 * Die Standardklasse für alle Fragen.
 * 
 * @param <AnswerType>
 *        der Datentyp der Antwort
 */
public abstract class Question<AnswerType> {
	
	/**
	 * Der Text dieser Frage.
	 */
	private String question;
	
	/**
	 * Die Kategorie dieser Frage.
	 */
	private Category category;
	
	/**
	 * Erstellt eine neue Frage.
	 * 
	 * @param que
	 *        der Text der Frage
	 * @param cat
	 *        die Kategorie der Frage
	 */
	public Question(String que, Category cat) {
		this.question = que;
		this.category = cat;
	}
	
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
	 * Wird für die Bearbeitungs-GUI verwendet und gibt die Anzahl an
	 * änderbaren Antworten an.<br>
	 * <strong>Muss</strong> mit der Länge von {@link #getAnswers()},
	 * {@link #getAnswerFieldLabels()} und der Länge der akzeptierten
	 * Parameter für {@link #setAnswers(String...)} übereinstimmen!
	 * 
	 * @return
	 * 		die Anzahl der Antworten
	 */
	public abstract int getAnswerCount();
	
	/**
	 * Alle Antworten als ihre anzuzeigenden Strings.
	 * 
	 * @return
	 * 		ein Array aller möglichen Antworten
	 */
	public abstract @NonNull String[] getAnswers();
	
	/**
	 * Ersetzt die Texte der Antworten. Die Länge von {@code answers}
	 * muss mit dem Rückgabewert von {@link #getAnswerCount()}
	 * übereinstimmen.
	 * 
	 * @param answers
	 *        die neuen Antworten
	 * 
	 * @see #getAnswerCount()
	 */
	public abstract void setAnswers(@NonNull String[] answers);
	
	/**
	 * Die Antwortfeldlabels werden für die Übersicht benötigt, in der
	 * man die Frage bearbeitet.
	 * 
	 * @return
	 * 		ein Array aller Antwortfeldnamen
	 */
	public abstract @NonNull String[] getAnswerFieldLabels();
	
	/**
	 * Die Frage, die angezeigt wird und zu der die Antwort gefunden
	 * werden soll.
	 * 
	 * @return
	 * 		die Frage
	 */
	public final String getQuestion() {
		return this.question;
	}
	
	/**
	 * Überschreibt den Fragentext.
	 * 
	 * @param nQuestion
	 *        der neue Text
	 */
	public final void setQuestion(String nQuestion) {
		this.question = nQuestion;
	}
	
	/**
	 * Die Kategorie dieser Frage.
	 * 
	 * @return
	 * 		die Kategorie
	 */
	public final Category getCategory() {
		return this.category;
	}
	
	/**
	 * Entfernt die Frage aus ihrer aktuellen Kategorie und trägt sie
	 * in die angegebene ein.
	 * 
	 * @param newCategory
	 *        die neue Kategorie der Frage
	 */
	public final void changeCategory(Category newCategory) {
		this.category.removeQuestion(this);
		(this.category = newCategory).addQuestion(this);
	}
	
	/**
	 * Der Dateiname der FXML-Datei, die für das View geladen werden
	 * muss.
	 * 
	 * @return
	 * 		die FXML-Datei
	 */
	public abstract String getFilename();
	
}
