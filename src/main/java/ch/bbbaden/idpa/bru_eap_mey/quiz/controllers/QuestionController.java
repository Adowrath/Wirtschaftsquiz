package ch.bbbaden.idpa.bru_eap_mey.quiz.controllers;

import org.eclipse.jdt.annotation.Nullable;


import ch.bbbaden.idpa.bru_eap_mey.quiz.model.QuizModel;
import ch.bbbaden.idpa.bru_eap_mey.quiz.model.question.Question;
import javafx.event.ActionEvent;

/**
 * Die Grundklasse für alle Controller im Quiz.
 * 
 * @param <QuestionType>
 *        der Typ der Frage, relevant für
 *        {@link #setQuestion(Question) setQuestion}.
 */
public abstract class QuestionController<QuestionType extends Question<?>> {
	
	/**
	 * Das Quizmodel wird hier für Rückmeldungen (bspw. Frage
	 * korrekt/inkorrekt)
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
	
	/**
	 * Legt die momentane Frage fest.
	 * 
	 * @param question
	 *        die Frage
	 */
	public abstract void setQuestion(QuestionType question);
	
	/**
	 * Diese Methode wird aufgerufen, wenn man auf den "Weiter"-Knopf
	 * drückt. Die Antwort wird überprüft und das Resultat wird an das
	 * Model geschickt.
	 * 
	 * @param event
	 *        das Event für den Knopfdruck
	 */
	public abstract void accept(ActionEvent event);
	
	/**
	 * Diese Methode wird aufgerufen, wenn man auf den
	 * "Abbrechen"-Knopf
	 * drückt.
	 * 
	 * @param event
	 *        das Event für den Knopfdruck
	 */
	public abstract void cancel(ActionEvent event);
	
}
