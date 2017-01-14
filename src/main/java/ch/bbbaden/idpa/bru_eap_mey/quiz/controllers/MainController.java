package ch.bbbaden.idpa.bru_eap_mey.quiz.controllers;

import static org.eclipse.jdt.annotation.DefaultLocation.PARAMETER;
import static org.eclipse.jdt.annotation.DefaultLocation.RETURN_TYPE;
import static org.eclipse.jdt.annotation.DefaultLocation.TYPE_ARGUMENT;
import static org.eclipse.jdt.annotation.DefaultLocation.TYPE_BOUND;


import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


import org.eclipse.jdt.annotation.NonNullByDefault;


import ch.bbbaden.idpa.bru_eap_mey.quiz.model.Category;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;

/**
 * Der Hauptcontroller für das View, bei dem die Menüs aufgerufen
 * werden oder das Spiel gestartet wird.
 */
@NonNullByDefault({PARAMETER, RETURN_TYPE, TYPE_BOUND, TYPE_ARGUMENT})
public class MainController extends MainMenuController {
	
	
	/**
	 * Das FlowPane mit allen Kategorien.
	 */
	@FXML
	private FlowPane theFlow;
	
	@Override
	public void signalDataLoaded() {
		this.loadCategories(this.getModel().getCategories());
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
			ToggleButton tb = new ToggleButton(cat.getNameAndCount());
			if(cat.getQuestions().size() == 0) {
				tb.setDisable(true);
			}
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
		List<Node> cNodes = this.theFlow.getChildren();
		List<Category> categories = this.getModel().getCategories();
		this.getModel().startGame(IntStream.range(0, categories.size())
				.filter(i -> ((ToggleButton) cNodes.get(i)).isSelected())
				.mapToObj(categories::get).collect(Collectors.toList()));
	}
}
