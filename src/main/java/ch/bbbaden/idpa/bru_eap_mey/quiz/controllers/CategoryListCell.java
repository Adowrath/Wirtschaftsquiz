package ch.bbbaden.idpa.bru_eap_mey.quiz.controllers;

import org.eclipse.jdt.annotation.Nullable;


import ch.bbbaden.idpa.bru_eap_mey.quiz.model.Category;
import javafx.scene.control.ListCell;
import javafx.scene.control.Tooltip;

/**
 * Die ListCell wird für die Anzeige der Kategorien benötigt.
 */
public final class CategoryListCell extends ListCell<Category> {
	
	@Override
	public void updateItem(@Nullable Category item, boolean empty) {
		super.updateItem(item, empty);
		
		this.setText(empty || item == null ? null : item.getNameAndCount());
		this.setTooltip(empty || item == null ? null
				: new Tooltip(item.getDescription()));
	}
}
