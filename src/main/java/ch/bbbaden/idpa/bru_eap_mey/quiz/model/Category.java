package ch.bbbaden.idpa.bru_eap_mey.quiz.model;

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
	 * Der Konstruktor einer Kategorie.
	 * 
	 * @param nName
	 *        der Name der Kategorie
	 * @param nDescription
	 *        die Beschreibung dieser Kategorie
	 */
	public Category(String nName, String nDescription) {
		this.name = nName;
		this.description = nDescription;
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
	
}
