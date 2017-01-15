package ch.bbbaden.idpa.bru_eap_mey.quiz.controllers;

import org.eclipse.jdt.annotation.Nullable;


import ch.bbbaden.idpa.bru_eap_mey.quiz.model.question.Question;
import javafx.scene.control.ListCell;

/**
 * Die ListCell wird für die Anzeige der Fragen benötigt.
 */
public class QuestionListCell extends ListCell<Question<?>> {
	
	@Override
	public void updateItem(@Nullable Question<?> item, boolean empty) {
		super.updateItem(item, empty);
		
		this.setText(empty || item == null ? null : item.getQuestion());
	}
}
