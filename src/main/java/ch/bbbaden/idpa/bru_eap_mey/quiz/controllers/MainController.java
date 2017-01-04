package ch.bbbaden.idpa.bru_eap_mey.quiz.controllers;

import org.eclipse.jdt.annotation.Nullable;


import ch.bbbaden.idpa.bru_eap_mey.quiz.model.QuizModel;

/**
 * Der Hauptcontroller für das View, bei dem die Menüs aufgerufen
 * werden oder das Spiel gestartet wird.
 */
public class MainController {
	
	/**
	 * Das Quizmodel 
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
	}
	
	/**
	 * @return
	 * 		das Quizmodel dieses Controllers
	 */
	public final QuizModel getModel() {
		assert this.quizModel != null;
		return this.quizModel;
	}
}
