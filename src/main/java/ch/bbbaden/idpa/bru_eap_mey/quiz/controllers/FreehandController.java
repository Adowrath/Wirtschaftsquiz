package ch.bbbaden.idpa.bru_eap_mey.quiz.controllers;

import org.eclipse.jdt.annotation.NonNull;


import ch.bbbaden.idpa.bru_eap_mey.quiz.model.question.*;
import javafx.event.ActionEvent;

/**
 * Der Controller für die Freihand-Fragen.
 * 
 * @see FreehandQuestion
 */
public class FreehandController extends QuestionController<FreehandQuestion> {
	
	@Override
	public void setQuestion(FreehandQuestion question) {
		// TODO - implement
		throw new UnsupportedOperationException("FreehandController.setQuestion() not yet implemented");
	}
	
	@Override
	public void accept(@NonNull ActionEvent event) {
		// TODO - implement
		throw new UnsupportedOperationException("FreehandController.accept() not yet implemented");
	}
	
	@Override
	public void cancel(ActionEvent event) {
		this.getModel().cancelRound();
	}
}
