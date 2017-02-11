package ch.bbbaden.idpa.bru_eap_mey.quiz.controllers;

import static org.eclipse.jdt.annotation.DefaultLocation.PARAMETER;
import static org.eclipse.jdt.annotation.DefaultLocation.RETURN_TYPE;
import static org.eclipse.jdt.annotation.DefaultLocation.TYPE_ARGUMENT;
import static org.eclipse.jdt.annotation.DefaultLocation.TYPE_BOUND;


import org.eclipse.jdt.annotation.NonNullByDefault;


import ch.bbbaden.idpa.bru_eap_mey.quiz.model.QuizModel;
import ch.bbbaden.idpa.bru_eap_mey.quiz.model.question.Question;
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

/**
 * Die Grundklasse für alle Controller im Quiz.
 * 
 * @param <QuestionType>
 *        der Typ der Frage, relevant für
 *        {@link #setQuestion(Question) setQuestion}.
 */
@NonNullByDefault({PARAMETER, RETURN_TYPE, TYPE_BOUND, TYPE_ARGUMENT})
public abstract class QuestionController<QuestionType extends Question<?>> {
	
	/**
	 * Die Pseudoklasse für eine falsche Antwort.
	 */
	public static final PseudoClass wrongAnswer = PseudoClass.getPseudoClass("wrongAnswer");
	
	/**
	 * Die Pseudoklasse für eine richtige Antwort.
	 */
	public static final PseudoClass correctAnswer = PseudoClass.getPseudoClass("correctAnswer");
	
	/**
	 * Der Weiter-Knopf.
	 */
	@FXML
	protected Button continueButton;
	
	/**
	 * Das Quizmodel wird hier für Rückmeldungen (bspw. Frage
	 * korrekt/inkorrekt)
	 */
	private QuizModel quizModel;
	
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
	 * Ein einfacher Getter.
	 * 
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
