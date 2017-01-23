package ch.bbbaden.idpa.bru_eap_mey.quiz.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;


import ch.bbbaden.idpa.bru_eap_mey.quiz.model.question.Question;

/**
 * Eine Kategorie in diesem Spiel. Enthält mehrere Fragen.
 * 
 * @see Question
 */
public class Category {
	
	/**
	 * Der Name dieser Kategorie.
	 */
	private String name;
	
	/**
	 * Die Beschreibung dieser Kategorie.
	 */
	private String description;
	
	/**
	 * Die Fragen dieser Kategorie.
	 */
	private List<Question<?>> questions;
	
	/**
	 * Konstruktor, der eine variadische Anzahl an Fragen akzeptiert.
	 * 
	 * @param nName
	 *        der Name
	 * @param nDescription
	 *        die Beschreibung
	 * @param nQuestions
	 *        varargs an Fragen
	 * 
	 * @see #Category(String, String, List)
	 */
	public Category(String nName, String nDescription,
					@NonNull Question<?>... nQuestions) {
		this(nName, nDescription, Arrays.asList(nQuestions));
	}
	
	/**
	 * Konstruktor, der einen Stream als Fragen akzeptiert.
	 * 
	 * @param nName
	 *        der Name
	 * @param nDescription
	 *        die Beschreibung
	 * @param nQuestions
	 *        ein Stream an Fragen
	 * 
	 * @see #Category(String, String, List)
	 */
	public Category(String nName, String nDescription,
					Stream<Question<?>> nQuestions) {
		this(nName, nDescription, nQuestions.collect(Collectors.toList()));
	}
	
	/**
	 * Der Konstruktor einer Kategorie.
	 * 
	 * @param nName
	 *        der Name der Kategorie
	 * @param nDescription
	 *        die Beschreibung dieser Kategorie
	 * @param nQuestions
	 *        die Fragen dieser Kategorie
	 */
	public Category(String nName, String nDescription,
					List<Question<?>> nQuestions) {
		this.name = nName;
		this.description = nDescription;
		this.questions = new LinkedList<>();
		nQuestions.forEach(question -> question.changeCategory(Category.this));
	}
	
	/**
	 * Getter für den Namen
	 * 
	 * @return
	 * 		den Namen
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Setzt einen neuen Namen.
	 * 
	 * @param newName
	 *        der neue Name
	 */
	public void setName(String newName) {
		this.name = newName;
	}
	
	/**
	 * Getter für den Beschreibung
	 * 
	 * @return
	 * 		die Beschreibung
	 */
	public String getDescription() {
		return this.description;
	}
	
	/**
	 * Ändert die Beschreibung ab.
	 * 
	 * @param newDesc
	 *        die neue Beschreibung
	 */
	public void setDescription(String newDesc) {
		this.description = newDesc;
	}
	
	/**
	 * @return
	 * 		alle Fragen dieser Kategorie in einer unmodifizierbaren
	 *         Ansicht
	 */
	public List<Question<?>> getQuestions() {
		return Collections.unmodifiableList(this.questions);
	}
	
	/**
	 * Fügt die angegebene Frage zu dieser Kategorie hinzu. Nur beim
	 * Erstellen direkt benutzen, bei Änderung der Kategorie
	 * {@link Question#changeCategory(Category)} verwenden.
	 * 
	 * @param question
	 *        die hinzuzufügende Frage
	 */
	public void addQuestion(Question<?> question) {
		this.questions.add(question);
	}
	
	/**
	 * Entfernt die angegebene Frage von dieser Kategorie. Nur beim
	 * Löschen direkt benutzen, bei Änderung der Kategorie
	 * {@link Question#changeCategory(Category)} verwenden.
	 * 
	 * @param question
	 *        die zu entfernende Frage
	 */
	public void removeQuestion(Question<?> question) {
		this.questions.remove(question);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (this.description.hashCode());
		result = prime * result + (this.name.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(@Nullable Object obj) {
		return this == obj || obj != null && obj instanceof Category
				
				&& this.description.equals(((Category) obj).description)
				
				&& this.name.equals(((Category) obj).name);
	}
	
	/**
	 * Gibt eine Anzeigefreundliche Repräsentation der Kategorie
	 * zurück.
	 * 
	 * @return
	 * 		ein String im Format "$Name ($AnzahlFragen)"
	 */
	public String getNameAndCount() {
		return String.format(	"%s (%d)", this.getName(),
								Integer.valueOf(this.getQuestions().size()));
	}
}
