package ch.bbbaden.idpa.bru_eap_mey.quiz.model;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
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
	 * Alle momentanen Fragen.
	 */
	private Deque<Question<?>> currentQuestions = new LinkedList<>();
	
	/**
	 * Lädt die Daten für das Spiel aus der Standarddatei.
	 * 
	 * @see Util#loadData(java.net.URL, List, List)
	 */
	public void loadData() {
		try {
			Class.forName("ch.bbbaden.idpa.bru_eap_mey.quiz.model.question.FreehandQuestion");
			Class.forName("ch.bbbaden.idpa.bru_eap_mey.quiz.model.question.MultChoiceQuestion");
			Class.forName("ch.bbbaden.idpa.bru_eap_mey.quiz.model.question.BinaryQuestion");
		} catch(ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
		
		URL gameFile = QuizModel.class.getResource("game.xml");
		if(gameFile == null)
			throw new IllegalStateException("game.xml wurde nicht gefunden - Datei gelöscht?");
		Util.loadData(	gameFile, this.availableCategories,
						this.availableQuestions);
	}
	
	/**
	 * Lädt die Daten für das Spiel aus der angegebenen Datei.
	 * 
	 * @param file
	 *        die Datei
	 * 
	 * @see Util#loadData(java.net.URL, List, List)
	 */
	public void loadData(File file) {
		this.availableCategories.clear();
		this.availableQuestions.clear();
		try {
			Util.loadData(	file.toURI().toURL(), this.availableCategories,
							this.availableQuestions);
		} catch(MalformedURLException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Überprüft die momentane Frage und initiiert das Laden der
	 * nächsten.
	 * 
	 * @param <T>
	 *        Typ der Antwort
	 * @param t
	 *        die gegebene Antwort
	 * @return
	 * 		die momentane Frage
	 */
	@SuppressWarnings("unchecked")
	public <T> Question<T> testAnswer(T t) {
		try {
			Question<T> question = (Question<T>) this.currentQuestions
					.removeFirst();
			if(!question.check(t)) {
				this.currentQuestions.addLast(question);
			}
			System.err.println("Next question should be prepared."); // TODO
			return question;
		} catch(ClassCastException e) {
			throw new IllegalStateException("Fragetyp und Antworttyp waren nicht passend.",
											e);
		}
	}
	
	/**
	 * Bricht eine Runde vorzeitig ab.
	 */
	public void cancelRound() {
		// TODO
	}
}
