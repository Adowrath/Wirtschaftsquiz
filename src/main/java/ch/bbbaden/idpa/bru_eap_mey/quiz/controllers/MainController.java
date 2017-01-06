package ch.bbbaden.idpa.bru_eap_mey.quiz.controllers;

import static org.eclipse.jdt.annotation.DefaultLocation.PARAMETER;
import static org.eclipse.jdt.annotation.DefaultLocation.RETURN_TYPE;
import static org.eclipse.jdt.annotation.DefaultLocation.TYPE_ARGUMENT;
import static org.eclipse.jdt.annotation.DefaultLocation.TYPE_BOUND;


import java.io.File;
import java.util.LinkedList;
import java.util.List;


import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;


import ch.bbbaden.idpa.bru_eap_mey.quiz.model.Category;
import ch.bbbaden.idpa.bru_eap_mey.quiz.model.QuizModel;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import javafx.stage.FileChooser;

/**
 * Der Hauptcontroller für das View, bei dem die Menüs aufgerufen
 * werden oder das Spiel gestartet wird.
 */
@NonNullByDefault({PARAMETER, RETURN_TYPE, TYPE_BOUND, TYPE_ARGUMENT})
public class MainController {
	
	/**
	 * Das Quizmodel
	 */
	private @Nullable QuizModel quizModel;
	
	/**
	 * Das FlowPane mit allen Kategorien.
	 */
	@FXML
	private FlowPane theFlow;
	
	/**
	 * Initialisiert das Quizmodel.
	 * 
	 * @param model
	 *        das Model
	 */
	public final void setModel(QuizModel model) {
		this.quizModel = model;
		this.loadCategories(model.getCategories());
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
	 * Fügt die Kategorien in das FlowPane ein.
	 * 
	 * @param categories
	 *        die Kategorienliste
	 */
	private void loadCategories(List<Category> categories) {
		if(categories.size() == 0) {
			this.theFlow.getChildren().clear();
			return;
		}
		final FlowPane bufferPane = new FlowPane();
		@SuppressWarnings("unused")
		final Scene bufferScene = new Scene(bufferPane);
		
		final List<Node> children = this.theFlow.getChildren();
		final List<Region> buffer = new LinkedList<>();
		final List<Region> rowBuffer = new LinkedList<>();
		double currentWidth = 0.0D;
		int rows = 1;
		
		children.clear();
		for(Category cat : categories) { // Füge alle Buttons hinzu
			ToggleButton tb = new ToggleButton(cat.getName());
			tb.setTooltip(new Tooltip(cat.getDescription()));
			
			buffer.add(tb);
		}
		bufferPane.getChildren().addAll(buffer);
		bufferPane.applyCss();
		bufferPane.layout();
		
		final double width = this.theFlow.getPrefWidth() - 5;
		final double height = this.theFlow.getPrefHeight() - 5;
		final double normalHeight = buffer.get(0).getHeight();
		
		for(Region r : buffer) { // Verlege sie auf Reihen
			double w = r.getWidth();
			if(currentWidth + w > width) {
				double add = (width - currentWidth) / rowBuffer.size();
				for(Region node : rowBuffer) {
					node.setPrefWidth(node.getWidth() + add);
				}
				
				rowBuffer.clear();
				currentWidth = 0.0D;
				rows++;
			}
			currentWidth += w;
			rowBuffer.add(r);
		}
		
		double add = (width - currentWidth) / rowBuffer.size();
		for(Region node : rowBuffer) {
			node.setPrefWidth(node.getWidth() + add);
		}
		
		final double newHeight = height / rows;
		if(newHeight > normalHeight) {
			for(Region r : buffer) {
				r.setPrefHeight(newHeight); // Setze die Höhe.
			}
		}
		children.addAll(buffer);
	}
	
	/**
	 * Startet das Spiel mit den ausgewählten Kategorien
	 */
	public void startGame() {
		this.getModel().startGame();
	}
	
	/**
	 * Öffnet einen Dialog zur Auswahl einer Datei, in welche
	 * gespeichert werden soll.
	 */
	public void saveData() {
		FileChooser fc = new FileChooser();
		fc.getExtensionFilters()
				.addAll(new FileChooser.ExtensionFilter("XML-Datei", "*.xml"),
						new FileChooser.ExtensionFilter("Alle Dateien", "*.*"));
		fc.setTitle("Speichern nach");
		fc.setInitialDirectory(new File("."));
		fc.setInitialFileName("game");
		File file = fc.showSaveDialog(this.getModel().getStage());
		if(file != null) {
			this.getModel().saveToFile(file);
		}
	}
	
	/**
	 * Öffnet einen Dialog zur Auswahl einer Datei, aus welcher
	 * geladen werden soll.
	 */
	public void loadData() {
		FileChooser fc = new FileChooser();
		fc.getExtensionFilters()
				.addAll(new FileChooser.ExtensionFilter("XML-Dateien", "*.xml"),
						new FileChooser.ExtensionFilter("Alle Dateien", "*.*"));
		fc.setTitle("Spieldaten auswählen");
		fc.setInitialDirectory(new File("."));
		File file = fc.showOpenDialog(this.getModel().getStage());
		if(file != null) {
			this.getModel().loadData(file);
			this.loadCategories(this.getModel().getCategories());
		}
	}
	
	/**
	 * Ruft den Dialog zum Bearbeiten der Kategorien auf.
	 */
	public void editCategories() {
		this.getModel().openCategoryEditor();
	}
	
	/**
	 * Ruft den Dialog zum Bearbieten der Fragen auf.
	 */
	public void editQuestions() {
		this.getModel().openQuestionEditor();
	}
	
}
