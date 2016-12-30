package ch.bbbaden.idpa.bru_eap_mey.quiz.model;

import java.util.ArrayList;
import java.util.List;


import ch.bbbaden.idpa.bru_eap_mey.quiz.Util;
import ch.bbbaden.idpa.bru_eap_mey.quiz.model.question.Question;

/**
 * Das Hauptmodel für das gesamte Wirtschaftsquiz.
 */
public class QuizModel {
	
	/**
	 * Eine Liste der Kategorien.
	 */
	private List<Category> availableCategories = new ArrayList<>();
	
	/**
	 * Eine Liste der Fragen.
	 */
	private List<Question<?>> availableQuestions = new ArrayList<>();
	
	/**
	 * Lädt die Daten für das Spiel aus der Standarddatei.
	 * 
	 * @see Util#loadData(java.net.URL, List, List)
	 */
	public void loadData() {
		Util.loadData(QuizModel.class.getResource("game.xml"), this.availableCategories, this.availableQuestions);
	}
}
