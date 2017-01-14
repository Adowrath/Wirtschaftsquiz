package ch.bbbaden.idpa.bru_eap_mey.quiz.controllers;

import static org.eclipse.jdt.annotation.DefaultLocation.PARAMETER;
import static org.eclipse.jdt.annotation.DefaultLocation.RETURN_TYPE;
import static org.eclipse.jdt.annotation.DefaultLocation.TYPE_ARGUMENT;
import static org.eclipse.jdt.annotation.DefaultLocation.TYPE_BOUND;


import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;


import ch.bbbaden.idpa.bru_eap_mey.quiz.model.Category;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

/**
 * Der Controller für die Übersicht der Kategorien.
 */
@NonNullByDefault({PARAMETER, RETURN_TYPE, TYPE_BOUND, TYPE_ARGUMENT})
public class CategoryEditController extends MainMenuController {
	
	/**
	 * Die Liste aller Kategorien.
	 */
	@FXML
	private ListView<Category> catList;
	
	/**
	 * Das Eingabefeld für den Namen.
	 */
	@FXML
	private TextField nameField;
	
	/**
	 * Das Eingabefeld für die Beschreibung.
	 */
	@FXML
	private TextField descField;
	
	/**
	 * Die Initialisierungsmethode initialisiert die Liste mit den
	 * entsprechenden Listenern und CellFactories.
	 */
	public void initialize() {
		this.catList.setCellFactory(cell -> new CategoryListCell());
		this.catList.getSelectionModel().selectedItemProperty()
				.addListener(this::newSelection);
	}
	
	/**
	 * Diese Methode reagiert anstelle einer Subklasse von
	 * {@link ChangeListener} auf Änderung der Auswahl
	 * 
	 * @param obs
	 *        die Observable-Value (ungenutzt)
	 * @param oldValue
	 *        die vorherige Auswahl (ungenutzt)
	 * @param newValue
	 *        die neue Auswahl, kann bei Deselektierung {@code null}
	 *        sein
	 */
	@SuppressWarnings("unused")
	private void newSelection(	ObservableValue<? extends @Nullable Category> obs,
								@Nullable Category oldValue,
								@Nullable Category newValue) {
		this.nameField.setText(newValue == null ? null : newValue.getName());
		this.descField
				.setText(newValue == null ? null : newValue.getDescription());
	}
	
	@Override
	public void signalDataLoaded() {
		this.catList.setItems(this.getModel().getCategories());
	}
	
	/**
	 * Speichert eine Kategorie anhand der momentanen Angaben.
	 */
	public void saveCategory() {
		Category c = this.catList.getSelectionModel().getSelectedItem();
		String name = this.nameField.getText();
		String desc = this.descField.getText();
		if(name == null || name.trim().isEmpty())
			return;
		if(desc == null || desc.trim().isEmpty())
			return;
		
		if(c == null) {
			this.getModel().getCategories().add(new Category(name, desc));
			this.catList.getSelectionModel().selectLast();
		} else {
			c.setName(name);
			c.setDescription(desc);
			this.getModel().getCategories()
					.set(	this.catList.getSelectionModel().getSelectedIndex(),
							c);
		}
	}
	
	/**
	 * Leert die Felder.
	 */
	public void clearFields() {
		this.catList.getSelectionModel().clearSelection();
		this.nameField.setText("");
		this.descField.setText("");
	}
	
	/**
	 * Deletes the currently selected Category.
	 */
	public void deleteCategory() {
		Category c = this.catList.getSelectionModel().getSelectedItem();
		this.getModel().getCategories().remove(c);
		this.clearFields();
	}
	
	/**
	 * Die ListCell wird für die Anzeige de
	 */
	private static class CategoryListCell extends ListCell<Category> {
		
		/**
		 * Wird benötigt, dass kein synthetischer Konstruktor erzeugt
		 * werden muss.
		 */
		public CategoryListCell() {}
		
		@Override
		public void updateItem(@Nullable Category item, boolean empty) {
			super.updateItem(item, empty);
			
			this.setText(empty || item == null ? null : item.getNameAndCount());
		}
	}
}
