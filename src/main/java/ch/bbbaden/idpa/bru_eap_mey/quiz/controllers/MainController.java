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
public final class MainController extends MainMenuController {
	
	
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
		final List<Node> children = this.theFlow.getChildren();
		children.clear();
		
		if(categories.isEmpty())
			return;
		
		final FlowPane bufferPane = new FlowPane();
		@SuppressWarnings("unused")
		final Scene bufferScene = new Scene(bufferPane);
		
		final List<Region> buffer = new LinkedList<>();
		
		fillNormalBuffer(buffer, categories);
		
		bufferPane.getChildren().addAll(buffer);
		bufferPane.applyCss();
		bufferPane.layout();
		
		final double width = this.theFlow.getPrefWidth() - 5;
		final double height = this.theFlow.getPrefHeight() - 5;
		final double normalHeight = buffer.get(0).getHeight();
		
		spreadOnRowsJustified(buffer, width, height, normalHeight);
		
		children.addAll(buffer);
	}
	
	/**
	 * Füllt den Buffer mit normalen ToggleButtons.
	 * 
	 * @param buffer
	 *        der Buffer, wird erst geleert
	 * @param categories
	 *        die Kategorien
	 */
	private static void fillNormalBuffer(	List<Region> buffer,
											List<Category> categories) {
		buffer.clear();
		
		for(Category cat : categories) { // Füge alle Buttons hinzu
			ToggleButton tb = new ToggleButton(cat.getNameAndCount());
			if(cat.getQuestions().size() == 0) {
				tb.setDisable(true);
			}
			tb.setTooltip(new Tooltip(cat.getDescription()));
			
			buffer.add(tb);
		}
	}
	
	/**
	 * Verteilt die Elemente im Buffer auf Reihen, die maximal
	 * {@code maxWidth} breit sind, und stellt ihre Höhe auf die
	 * maximale Platzverwendung ein.
	 * 
	 * <p>
	 * Wenn {@code maxHeight} auf -1 gesetzt wird, werden die Elemente
	 * nicht in ihrer Höhe beeinflusst.
	 * 
	 * @param buffer
	 *        der Buffer enthält alle zu verteilenden Elemente.
	 * @param maxWidth
	 *        die maximale Breite einer Reihe, wird vollständig
	 *        ausgenutzt.
	 * @param maxHeight
	 *        die maximale normale Höhe für den Container, wird
	 *        überschritten, falls die Elemente kleiner als
	 *        {@code minHeight} werden würden.
	 * @param minHeight
	 *        die minimale Grösse für eine Region, damit es nicht
	 *        <em>zu</em> klein ist.
	 */
	private static void spreadOnRowsJustified(	List<Region> buffer,
												double maxWidth,
												double maxHeight,
												double minHeight) {
		final List<Region> rowBuffer = new LinkedList<>();
		double currentWidth = 0.0D;
		int rows = 1;
		for(Region r : buffer) { // Verlege sie auf Reihen
			double w = r.getWidth();
			if(currentWidth + w > maxWidth) {
				double add = (maxWidth - currentWidth) / rowBuffer.size();
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
		
		double add = (maxWidth - currentWidth) / rowBuffer.size();
		for(Region node : rowBuffer) {
			node.setPrefWidth(node.getWidth() + add);
		}
		
		if(maxHeight != -1) {
			final double newHeight = maxHeight / rows;
			if(newHeight > minHeight) {
				for(Region r : buffer) {
					r.setPrefHeight(newHeight); // Setze die Höhe.
				}
			}
		}
	}
	
	/**
	 * Startet das Spiel mit den ausgewählten Kategorien.
	 */
	public void startGame() {
		List<Node> cNodes = this.theFlow.getChildren();
		List<Category> categories = this.getModel().getCategories();
		this.getModel().startGame(IntStream.range(0, categories.size())
				.filter(i -> ((ToggleButton) cNodes.get(i)).isSelected())
				.mapToObj(categories::get).collect(Collectors.toList()));
	}
}
