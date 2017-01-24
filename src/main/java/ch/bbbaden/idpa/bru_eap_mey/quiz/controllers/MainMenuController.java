package ch.bbbaden.idpa.bru_eap_mey.quiz.controllers;

import java.io.IOException;


import org.eclipse.jdt.annotation.Nullable;


import ch.bbbaden.idpa.bru_eap_mey.quiz.Util;
import ch.bbbaden.idpa.bru_eap_mey.quiz.model.QuizModel;

/**
 * Der Hauptcontroller, der allen Controllern, die das Menü
 * darstellen, unterliegt.
 */
public abstract class MainMenuController {
	
	/**
	 * Das Quizmodel.
	 */
	private @Nullable QuizModel quizModel;
	
	/**
	 * Initialisiert das Quizmodel.
	 * 
	 * @param model
	 *        das Model
	 */
	public final void setModel(QuizModel model) {
		this.quizModel = model;
		this.signalDataLoaded();
	}
	
	/**
	 * @return
	 * 		das Quizmodel dieses Controllers
	 */
	public final QuizModel getModel() {
		assert this.quizModel != null;
		return this.quizModel;
	}
	
	/**
	 * Diese Methode wird aufgerufen, wenn Daten geladen wurden.
	 */
	public abstract void signalDataLoaded();
	
	/**
	 * Öffnet einen Dialog zur Auswahl einer Datei, in welche
	 * gespeichert werden soll.
	 */
	public void saveData() {
		this.getModel().saveDataDialog();
	}
	
	/**
	 * Öffnet einen Dialog zur Auswahl einer Datei, aus welcher
	 * geladen werden soll.
	 */
	public void loadData() {
		if(this.getModel().loadDataDialog()) {
			this.signalDataLoaded();
		}
	}
	
	/**
	 * Ruft den Dialog zum Bearbeiten der Kategorien auf.
	 */
	public void editCategories() {
		try {
			this.getModel().openCategoryEditor();
		} catch(IOException e) {
			Util.showUncaughtErrorWithMessage(Thread
					.currentThread(), e, "Fehler beim Öffnen des Kategorieneditors.");
		}
	}
	
	/**
	 * Ruft den Dialog zum Bearbieten der Fragen auf.
	 */
	public void editQuestions() {
		try {
			this.getModel().openQuestionEditor();
		} catch(IOException e) {
			Util.showUncaughtErrorWithMessage(Thread
					.currentThread(), e, "Fehler beim Öffnen des Frageneditors.");
		}
	}
	
	/**
	 * Ruft die Startseite wieder auf.
	 */
	public void mainPage() {
		try {
			this.getModel().openMainPage();
		} catch(IOException e) {
			Util.showUncaughtErrorWithMessage(Thread
					.currentThread(), e, "Fehler beim Öffnen der Hauptansicht.");
		}
	}
}
