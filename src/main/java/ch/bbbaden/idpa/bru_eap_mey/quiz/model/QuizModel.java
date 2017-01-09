package ch.bbbaden.idpa.bru_eap_mey.quiz.model;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;


import org.eclipse.jdt.annotation.Nullable;


import ch.bbbaden.idpa.bru_eap_mey.quiz.MainframeControl;
import ch.bbbaden.idpa.bru_eap_mey.quiz.Util;
import ch.bbbaden.idpa.bru_eap_mey.quiz.model.question.Question;
import javafx.animation.PauseTransition;
import javafx.stage.Stage;
import javafx.util.Duration;

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
	 * Die Stage der Applikation.
	 */
	private @Nullable Stage stage;
	
	/**
	 * Setzt die Stage.
	 * 
	 * @param st
	 *        die Stage
	 */
	public void setStage(Stage st) {
		this.stage = st;
	}
	
	/**
	 * @return
	 * 		die Stage
	 */
	public Stage getStage() {
		assert this.stage != null;
		return this.stage;
	}
	
	/**
	 * @return
	 * 		die Liste aller Kategorien.
	 */
	public List<Category> getCategories() {
		return this.availableCategories;
	}
	
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
	 * Speichert die Daten in die gegebene Datei.
	 * 
	 * @param file
	 *        die Datei, in welche geschrieben werden soll
	 */
	public void saveToFile(File file) {
		Util.saveData(file, this.availableCategories);
	}
	
	/**
	 * Startet das Spiel mit den gegebenen Kategorien
	 * 
	 * @param selectedCategories
	 *        alle gewählten Kategorien.
	 */
	public void startGame(List<Category> selectedCategories) {
		if(selectedCategories.size() == 0)
			return;
		this.currentQuestions.addAll(selectedCategories.stream()
				.flatMap(c -> c.getQuestions().stream())
				.collect(Collectors.toList()));
		try {
			this.getStage().setScene(MainframeControl
					.loadQuestion(this, this.currentQuestions.getFirst()));
		} catch(IOException e) {
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
			PauseTransition delay = new PauseTransition(Duration.seconds(5));
			delay.setOnFinished(event -> {
				try {
					if(this.currentQuestions.isEmpty()) {
						this.openMainPage();
					} else {
						this.getStage().setScene(MainframeControl
								.loadQuestion(this, this.currentQuestions
										.getFirst()));
					}
				} catch(IOException e) {
					e.printStackTrace();
				}
			});
			delay.play();
			
			return question;
		} catch(ClassCastException e) {
			throw new IllegalStateException("Fragetyp und Antworttyp waren nicht passend.",
											e);
		}
	}
	
	/**
	 * Bricht eine Runde ab.
	 */
	public void cancelRound() {
		this.currentQuestions.clear();
		this.openMainPage();
	}
	
	/**
	 * Öffnet die Hauptansicht.
	 */
	public void openMainPage() {
		try {
			this.getStage().setScene(MainframeControl.mainPage(this));
		} catch(IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Öffnet den Kategorieneditor.
	 */
	public void openCategoryEditor() {
		try {
			this.getStage().setScene(MainframeControl.mainPage(this));
		} catch(IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Öffnet den Kategorieneditor.
	 */
	public void openQuestionEditor() {
		try {
			this.getStage().setScene(MainframeControl.mainPage(this));
		} catch(IOException e) {
			throw new RuntimeException(e);
		}
	}
}
