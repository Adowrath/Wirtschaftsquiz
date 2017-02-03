package ch.bbbaden.idpa.bru_eap_mey.quiz.controllers;

import static org.eclipse.jdt.annotation.DefaultLocation.PARAMETER;
import static org.eclipse.jdt.annotation.DefaultLocation.RETURN_TYPE;
import static org.eclipse.jdt.annotation.DefaultLocation.TYPE_ARGUMENT;
import static org.eclipse.jdt.annotation.DefaultLocation.TYPE_BOUND;


import java.util.stream.Collectors;
import java.util.stream.Stream;


import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;


import ch.bbbaden.idpa.bru_eap_mey.quiz.model.Category;
import ch.bbbaden.idpa.bru_eap_mey.quiz.model.question.Question;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.util.StringConverter;

/**
 * Der Controller für die Übersicht der Fragen.
 */
@NonNullByDefault({PARAMETER, RETURN_TYPE, TYPE_BOUND, TYPE_ARGUMENT})
public final class QuestionEditController extends MainMenuController {
	
	/**
	 * Die Liste aller Fragen.
	 */
	@FXML
	private ListView<Question<?>> queList;
	
	/**
	 * Das GridPane, in der die Eingabeelemente platziert sind.
	 */
	@FXML
	private GridPane theGrid;
	
	/**
	 * Das Text-Label für die Frageneingabe.
	 */
	@FXML
	private Label textLabel;
	
	/**
	 * Das Label für die Kategorienauswahl.
	 */
	@FXML
	private Label catLabel;
	
	/**
	 * Das Eingabefeld für den Text der Frage.
	 */
	@FXML
	private TextField textField;
	
	/**
	 * Die Dropdown-Box, welche für die Kategorienauswahl benötigt
	 * wird.
	 */
	@FXML
	private ComboBox<Category> catBox;
	
	/**
	 * Ein Array aller Label, die für Antworten benötigt sind.
	 */
	private @NonNull Label[] otherLabels;
	
	/**
	 * Ein Array aller Textfelder, die für Antworten benötigt sind.
	 */
	private @NonNull TextField[] otherTextFields;
	
	/**
	 * Der Behälter für alle Knöpfe.
	 */
	@FXML
	private HBox buttonRow;
	
	/**
	 * Der "Neu"-Knopf.
	 */
	@FXML
	private MenuButton newButton;
	
	/**
	 * Der Temporäre Fragenhalter.
	 */
	private Question<?> tempQues;
	
	/**
	 * Die Initialisierungsmethode initialisiert die Liste mit den
	 * entsprechenden Listenern und CellFactories.
	 */
	public void initialize() {
		this.catBox.setCellFactory(cell -> new CategoryListCell());
		this.catBox.setConverter(new StringConverter<Category>() {
			
			@Override
			public String toString(Category cat) {
				return cat.getNameAndCount();
			}
			
			@Override
			public Category fromString(@Nullable String string) {
				throw new UnsupportedOperationException("Can not convert String to Category.");
			}
		});
		
		this.queList.setCellFactory(cell -> new QuestionListCell());
		this.queList.getSelectionModel().selectedItemProperty()
				.addListener(this::newSelection);
		
		this.newButton.getItems()
				.addAll(Question.getTypes().stream().map(type -> {
					MenuItem mi = new MenuItem(type);
					mi.setOnAction(ae -> {
						QuestionEditController.this.clearFields();
						QuestionEditController.this.tempQues = Question
								.getDummy(type);
						QuestionEditController.this
								.newSelection(	null, null,
												QuestionEditController.this.tempQues);
					});
					return mi;
				}).collect(Collectors.toList()));
	}
	
	/**
	 * Diese Methode reagiert anstelle einer Subklasse von
	 * {@link ChangeListener} auf Änderung der Auswahl.
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
	private void newSelection(	@Nullable ObservableValue<? extends @Nullable Question<?>> obs,
								@Nullable Question<?> oldValue,
								@Nullable Question<?> newValue) {
		
		this.textField.setDisable(newValue == null);
		this.catBox.setDisable(newValue == null);
		if(newValue != this.tempQues) {
			this.tempQues = null;
		}
		
		
		this.theGrid.getRowConstraints()
				.remove(3, this.theGrid.getRowConstraints().size());
		this.theGrid.getChildren().clear();
		this.theGrid.getChildren().addAll(	this.textLabel, this.catLabel,
											this.textField, this.catBox,
											this.buttonRow);
		this.textField.setText(newValue == null ? "" : newValue.getQuestion());
		this.catBox.getSelectionModel().clearSelection();
		if(newValue != null) {
			this.catBox.getSelectionModel().select(newValue.getCategory());

			String[] labelTexts = newValue.getAnswerFieldLabels();
			String[] answers = newValue.getAnswers();
			int size = newValue.getAnswerCount();
			
			this.otherLabels = new @NonNull Label[size];
			this.otherTextFields = new @NonNull TextField[size];
			
			for(int i = 0; i < size; i++) {
				this.theGrid.getRowConstraints().add(new RowConstraints());
				this.otherLabels[i] = new Label(labelTexts[i]);
				GridPane.setRowIndex(	this.otherLabels[i],
										Integer.valueOf(i + 2));
				this.otherTextFields[i] = new TextField(answers[i]);
				GridPane.setRowIndex(	this.otherTextFields[i],
										Integer.valueOf(i + 2));
				GridPane.setColumnIndex(this.otherTextFields[i],
										Integer.valueOf(1));
			}
			this.theGrid.getChildren().addAll(this.otherLabels);
			this.theGrid.getChildren().addAll(this.otherTextFields);
		}
		
		
		GridPane.setRowIndex(this.buttonRow, Integer
				.valueOf(this.theGrid.getRowConstraints().size() - 1));
		
		this.getModel().getStage().sizeToScene();
	}
	
	@Override
	public void signalDataLoaded() {
		this.queList.setItems(this.getModel().getQuestions());
		this.catBox.setItems(this.getModel().getCategories());
	}
	
	/**
	 * Speichert eine Frage anhand der momentanen Angaben.
	 */
	public void saveQuestion() {
		Category category = this.catBox.getSelectionModel().getSelectedItem();
		String quesText = this.textField.getText();
		if(quesText == null || quesText.isEmpty() || category == null)
			return;
		
		@NonNull
		String[] answers = Stream.of(this.otherTextFields).map(tf -> {
			String s = tf.getText();
			return s == null ? "" : s;
		}).toArray(i -> new @NonNull String[i]);
		
		for(String a : answers) {
			if(a.isEmpty())
				return;
		}
		
		Question<?> q = this.queList.getSelectionModel().getSelectedItem();
		if(q == null) {
			q = this.tempQues;
			if(q == null)
				throw new IllegalStateException("No question selected on save but the temporary question is null as well.");
		}
		
		q.setQuestion(quesText);
		q.changeCategory(category);
		q.setAnswers(answers);
		
		if(q == this.tempQues) {
			this.getModel().getQuestions().add(q);
		} else {
			this.getModel().getQuestions()
					.set(	this.queList.getSelectionModel().getSelectedIndex(),
							q);
		}
		
		this.clearFields();
	}
	
	/**
	 * Leert die Felder.
	 */
	public void clearFields() {
		this.queList.getSelectionModel().clearSelection();
		if(this.tempQues != null) {
			this.tempQues = null;
			this.newSelection(null, null, null);
		}
	}
	
	/**
	 * Löscht die momentan gewählte Frage.
	 */
	public void deleteQuestion() {
		Question<?> q = this.queList.getSelectionModel().getSelectedItem();
		if(q != null) {
			q.getCategory().removeQuestion(q);
			this.getModel().getQuestions().remove(q);
			this.clearFields();
		}
	}
}
