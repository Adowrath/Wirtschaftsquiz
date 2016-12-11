package ch.bbbaden.idpa.bru_eap_mey.quiz.controllers;

import ch.bbbaden.idpa.bru_eap_mey.quiz.model.question.*;

public class MultChoiceController extends QuestionController {

	@Override
	public void setQuestion(Question<?> question) {
		if(question instanceof MultChoiceQuestion) {
			//
		}
		// TODO - implement
		throw new UnsupportedOperationException("MultChoiceController.setQuestion() not yet implemented");
	}

}