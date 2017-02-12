package ch.bbbaden.idpa.bru_eap_mey.quiz.model.question;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;


import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;
import org.jdom2.Element;


import ch.bbbaden.idpa.bru_eap_mey.quiz.Util;
import ch.bbbaden.idpa.bru_eap_mey.quiz.model.Category;

/**
 * Die Standardklasse für alle Fragen.
 * 
 * <p>
 * Jede Sub-Klasse muss sich statisch bei
 * {@link #register(String, Function, Supplier)} registrieren,
 * ansonsten funktioniert der Ladevorgang nicht!
 * 
 * @param <AnswerType>
 *        der Datentyp der Antwort
 */
public abstract class Question<AnswerType> {
	
	/**
	 * Der Titel für das Fehlerfenster beim Lesen einer Frage.
	 */
	public static final String QUESTION_FORMAT_TITLE = "Falsch formatierte Frage";
	
	/**
	 * Der Vorlagetext für das Fehlerfenster beim Lesen einer Frage.
	 */
	public static final String QUESTION_ERROR_FORMAT = "Eine %s hat %s. "
			+ "Wenn die Daten gespeichert werden, wird diese Frage nicht "
			+ "gespeichert und damit effektiv gelöscht. Fortfahren?";
	
	/**
	 * Alle registrierten Ladefunktionen.
	 */
	private static final Map<String, Function<Element, @Nullable Question<?>>> registeredLoaders = new HashMap<>();
	
	/**
	 * Alle registrierten Dummysupplier.
	 */
	private static final Map<String, Supplier<? extends Question<?>>> dummy = new HashMap<>();
	
	/**
	 * Der Text dieser Frage.
	 */
	private String question;
	
	/**
	 * Die Kategorie dieser Frage.
	 */
	private @Nullable Category category;
	
	/**
	 * Erstellt eine neue Frage.
	 * 
	 * @param que
	 *        der Text der Frage
	 * @param cat
	 *        die Kategorie der Frage
	 */
	public Question(String que, @Nullable Category cat) {
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
	 * änderbaren Antworten an.
	 * 
	 * <p>
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
	public final @Nullable Category getCategory() {
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
		if(this.category != null) {
			this.category.removeQuestion(this);
		}
		newCategory.addQuestion(this);
		this.category = newCategory;
	}
	
	/**
	 * Der Dateiname der FXML-Datei, die für das View geladen werden
	 * muss.
	 * 
	 * @return
	 * 		die FXML-Datei
	 */
	public abstract String getFilename();
	
	/**
	 * Legt die Frage in ein JDOM-Element ab.
	 * 
	 * @return
	 * 		das JDOM-Element
	 */
	public abstract Element save();
	
	@Override
	public abstract int hashCode();
	
	@Override
	public abstract boolean equals(@Nullable Object other);
	
	/**
	 * Registriert den Fragetyp.
	 * 
	 * @param type
	 *        der Typ der Frage, wird für das XML verwendet. Sollte
	 *        mit dem Typ, der bei {@link #save()} als Attribut
	 *        festgelegt wird, übereinstimmen.
	 * @param func
	 *        die Funktion, welche die Frage aus einem Element lädt
	 * @param dummyGetter
	 *        die Funktion, welche ein Dummy-Object erzeugt
	 */
	public static void register(String type,
								Function<Element, @Nullable Question<?>> func,
								Supplier<? extends Question<?>> dummyGetter) {
		if(registeredLoaders.containsKey(type))
			throw new IllegalArgumentException(type
					+ " wurde bereits einmal registriert.");
		registeredLoaders.put(type, func);
		dummy.put(type, dummyGetter);
	}
	
	/**
	 * Das ist für den Frageneditcontroller nützlich, dass er alle
	 * registrierten Fragetypen sehen kann.
	 * 
	 * @return
	 * 		ein Set aller registrierten Fragetypen
	 */
	public static Set<String> getTypes() {
		return registeredLoaders.keySet();
	}
	
	/**
	 * Gibt ein Dummy-Objekt der Frage zurück.
	 * 
	 * @param type
	 *        der Typ, mit dem die Frage registriert wurde.
	 * @return
	 * 		eine Dummyfrage
	 * @throws IllegalArgumentException
	 *         wenn der Typ nicht bekannt ist.
	 */
	public static Question<?> getDummy(String type) {
		Supplier<? extends Question<?>> sup = dummy.get(type);
		if(sup == null)
			throw new IllegalArgumentException("\"" + type + "\""
					+ " ist nicht als Frage registriert.");
		return sup.get();
	}
	
	/**
	 * Versucht, eine Frage aus dem Element zu laden. Wenn es niht
	 * möglich ist, bspw. weil der Fragetyp nicht bekannt ist, wird
	 * {@link Util#showErrorExitOnNoOrClose(String, String, Object...)}
	 * aufgerufen und null zurückgegeben.
	 * 
	 * @param el
	 *        das JDOM-Element
	 * @return
	 * 		die geladene Frage, oder bei einem Fehler null
	 */
	public static final @Nullable Question<?> loadFromElement(Element el) {
		String type = el.getAttributeValue("type");
		if(type == null) {
			Util.showErrorExitOnNoOrClose(	QUESTION_FORMAT_TITLE,
											QUESTION_ERROR_FORMAT, "Frage",
											"keinen Fragetyp");
			return null;
		}
		
		Function<Element, @Nullable Question<?>> func = registeredLoaders
				.get(type);
		if(func == null) {
			Util.showErrorExitOnNoOrClose(	QUESTION_FORMAT_TITLE,
											QUESTION_ERROR_FORMAT, "Frage",
											"einen unbekannten Fragetyp \""
													+ type + "\"");
			return null;
		}
		return func.apply(el);
	}
}
