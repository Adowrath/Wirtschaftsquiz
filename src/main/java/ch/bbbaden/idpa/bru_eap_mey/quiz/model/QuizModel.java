package ch.bbbaden.idpa.bru_eap_mey.quiz.model;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;


import org.eclipse.jdt.annotation.Nullable;


import ch.bbbaden.idpa.bru_eap_mey.quiz.MainframeControl;
import ch.bbbaden.idpa.bru_eap_mey.quiz.Util;
import ch.bbbaden.idpa.bru_eap_mey.quiz.model.question.Question;
import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Das Hauptmodel für das gesamte Wirtschaftsquiz.
 */
public class QuizModel {
	
	/**
	 * Eine Liste der Kategorien.
	 */
	private ObservableList<Category> availableCategories = FXCollections
			.observableArrayList();
	
	/**
	 * Eine Liste der Fragen.
	 */
	private ObservableList<Question<?>> availableQuestions = FXCollections
			.observableArrayList();
	
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
	public ObservableList<Category> getCategories() {
		return this.availableCategories;
	}
	
	/**
	 * @return
	 * 		die Liste aller Fragen.
	 */
	public ObservableList<Question<?>> getQuestions() {
		return this.availableQuestions;
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
		if(!this.loadDataDialog()) {
			System.exit(1);
		}
	}
	
	/**
	 * Lädt die Daten für das Spiel aus der in einem Dialog
	 * ausgewählten Datei.
	 * 
	 * @return
	 * 		{@code true}, wenn die Daten geladen wurden, sonst
	 *         {@code false}
	 * @see Util#loadData(java.net.URL, List, List)
	 */
	public boolean loadDataDialog() {
		FileChooser fc = new FileChooser();
		fc.getExtensionFilters()
				.addAll(new FileChooser.ExtensionFilter("XML-Dateien", "*.xml"),
						new FileChooser.ExtensionFilter("Alle Dateien", "*.*"));
		fc.setTitle("Spieldaten auswählen");
		fc.setInitialDirectory(new File("."));
		File file = fc.showOpenDialog(this.getStage());
		if(file != null) {
			this.availableCategories.clear();
			this.availableQuestions.clear();
			try {
				Util.loadData(	file.toURI().toURL(), this.availableCategories,
								this.availableQuestions);
			} catch(MalformedURLException e) {
				throw new RuntimeException(e);
			}
			return true;
		}
		return false;
	}
	
	/**
	 * Öffnet einen Speicherdialog und speichert die Daten in die
	 * Datei
	 */
	public void saveDataDialog() {
		FileChooser fc = new FileChooser();
		fc.getExtensionFilters()
				.addAll(new FileChooser.ExtensionFilter("XML-Datei", "*.xml"),
						new FileChooser.ExtensionFilter("Alle Dateien", "*.*"));
		fc.setTitle("Speichern nach");
		fc.setInitialFileName("game.xml");
		fc.setInitialDirectory(new File("."));
		fc.setInitialFileName("game");
		File file = fc.showSaveDialog(this.getStage());
		if(file != null) {
			Util.saveData(file, this.availableCategories);
		}
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
		
		List<? extends Question<?>> questions = selectedCategories.stream()
				.flatMap(c -> c.getQuestions().stream())
				.collect(Collectors.toList());
		
		Collections.shuffle(questions);
		
		this.currentQuestions.clear();
		this.currentQuestions.addAll(questions);
		
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
			this.getStage().setScene(MainframeControl.categoryEditScene(this));
		} catch(IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Öffnet den Kategorieneditor.
	 */
	public void openQuestionEditor() {
		try {
			this.getStage().setScene(MainframeControl.questionEditScene(this));
		} catch(IOException e) {
			throw new RuntimeException(e);
		}
	}
}
