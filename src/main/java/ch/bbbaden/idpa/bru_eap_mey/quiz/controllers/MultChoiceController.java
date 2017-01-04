package ch.bbbaden.idpa.bru_eap_mey.quiz.controllers;

import org.eclipse.jdt.annotation.NonNull;


import ch.bbbaden.idpa.bru_eap_mey.quiz.model.question.MultChoiceQuestion;
import javafx.event.ActionEvent;

/**
 * Der Controller für die Multiple Choice-Fragen.
 * 
 * @see MultChoiceQuestion
 */
public class MultChoiceController extends QuestionController<MultChoiceQuestion> {
	
	@Override
	public void setQuestion(MultChoiceQuestion question) {
		// TODO - implement
		throw new UnsupportedOperationException("MultChoiceController.setQuestion() not yet implemented");
	}
	
	@Override
	public void accept(@NonNull ActionEvent event) {
		// TODO - implement
		throw new UnsupportedOperationException("MultChoiceController.accept() not yet implemented");
	}
	
	@Override
	public void cancel(ActionEvent event) {
		this.getModel().cancelRound();
	}
}
