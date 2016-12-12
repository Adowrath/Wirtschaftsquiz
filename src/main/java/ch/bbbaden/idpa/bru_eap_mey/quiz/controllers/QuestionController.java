package ch.bbbaden.idpa.bru_eap_mey.quiz.controllers;

import ch.bbbaden.idpa.bru_eap_mey.quiz.model.question.*;

/**
 * Die Grundklasse für alle Controller im Quiz.
 * 
 * @param <QuestionType>
 *        der Typ der Frage, relevant für
 *        {@link #setQuestion(Question) setQuestion}.
 */
public abstract class QuestionController<QuestionType extends Question<?>> {
	
	/**
	 * Legt die momentane Frage fest.
	 * 
	 * @param question
	 *        die Frage
	 */
	public abstract void setQuestion(QuestionType question);
	
}
