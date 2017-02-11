package ch.bbbaden.idpa.bru_eap_mey.quiz.controllers;

import java.util.function.Function;


import org.eclipse.jdt.annotation.Nullable;


import javafx.scene.control.ListCell;
import javafx.scene.control.Tooltip;

/**
 * Die ListCell wird für die Anzeige der Kategorien und Fragen
 * benötigt.
 * 
 * @param <DataType>
 *        der Datentyp, welchen die Listenzelle kontrolliert.
 */
public final class CustomListCell<DataType> extends ListCell<DataType> {
	
	/**
	 * Die Funktion gibt den Text für eine Zelle zurück.
	 */
	private final Function<DataType, String> textFunction;
	
	/**
	 * Die Funktion gibt (allfällig) das Tooltip für die Zelle zurück.
	 */
	private final Function<DataType, @Nullable String> tooltipFunction;
	
	/**
	 * Konstruiert eine Listenzelle ohne Tooltip.
	 * 
	 * @param tFunction
	 *        die Funktion für den Text.
	 */
	public CustomListCell(Function<DataType, String> tFunction) {
		this(tFunction, i -> null);
	}
	
	
	/**
	 * Konstruiert eine Listenzelle mit Tooltip.
	 * 
	 * @param tFunction
	 *        die Funktion für den Text.
	 * @param ttFunction
	 *        die Funktion für den Tooltip.
	 */
	public CustomListCell(	Function<DataType, String> tFunction,
							Function<DataType, @Nullable String> ttFunction) {
		this.textFunction = tFunction;
		this.tooltipFunction = ttFunction;
	}
	
	@Override
	public void updateItem(@Nullable DataType item, boolean empty) {
		super.updateItem(item, empty);
		
		this.setText(empty || item == null ? null
				: this.textFunction.apply(item));
		
		if(!empty && item != null) {
			String result = this.tooltipFunction.apply(item);
			this.setTooltip(result == null ? null : new Tooltip(result));
		}
	}
}
