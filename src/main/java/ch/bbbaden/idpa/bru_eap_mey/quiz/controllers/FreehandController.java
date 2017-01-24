package ch.bbbaden.idpa.bru_eap_mey.quiz.controllers;

import static org.eclipse.jdt.annotation.DefaultLocation.PARAMETER;
import static org.eclipse.jdt.annotation.DefaultLocation.RETURN_TYPE;
import static org.eclipse.jdt.annotation.DefaultLocation.TYPE_ARGUMENT;
import static org.eclipse.jdt.annotation.DefaultLocation.TYPE_BOUND;


import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;


import ch.bbbaden.idpa.bru_eap_mey.quiz.model.question.FreehandQuestion;
import ch.bbbaden.idpa.bru_eap_mey.quiz.model.question.Question;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 * Der Controller für die Freihand-Fragen.
 * 
 * @see FreehandQuestion
 */
@NonNullByDefault({PARAMETER, RETURN_TYPE, TYPE_BOUND, TYPE_ARGUMENT})
public class FreehandController extends QuestionController<FreehandQuestion> {
	
	/**
	 * Der Text der Frage.
	 */
	@FXML
	private Text questionText;
	
	/**
	 * Das Antwort-Label.
	 */
	@FXML
	private Label antwortLabel;
	
	/**
	 * Das Eingabefeld.
	 */
	@FXML
	private TextField eingabe;
	
	@Override
	public void setQuestion(FreehandQuestion question) {
		this.questionText.setText(question.getQuestion());
		this.eingabe.textProperty().addListener(this::textEntered);
	}
	
	/**
	 * Wenn der Text bearbeitet wird, sollte die Breite des
	 * Eingabefeldes verändert werden.
	 * 
	 * @param ov
	 *        das Observierte
	 * @param prevText
	 *        der vorherige Text
	 * @param currText
	 *        der neue Text
	 */
	@SuppressWarnings("unused")
	public void textEntered(ObservableValue<? extends @Nullable String> ov,
							@Nullable String prevText,
							@Nullable String currText) {
		
		// http://stackoverflow.com/a/25643696/5236247
		Platform.runLater(() -> {
			Text text = new Text(currText);
			text.setFont(this.eingabe.getFont());
			double width = text.getLayoutBounds().getWidth()
					+ this.eingabe.getPadding().getLeft()
					+ this.eingabe.getPadding().getRight() + 2d;
			this.eingabe.setPrefWidth(width);
			this.eingabe.positionCaret(this.eingabe.getCaretPosition());
		});
	}
	
	@Override
	public void accept(ActionEvent event) {
		String answerEntered = this.eingabe.getText();
		if(answerEntered != null && !answerEntered.isEmpty()) {
			Question<String> question = this.getModel()
					.testAnswer(answerEntered);
			
			this.antwortLabel.setText(question.getAnswer());
			if(question.check(answerEntered)) {
				this.antwortLabel.setTextFill(Color.GREEN);
			} else {
				this.antwortLabel.setTextFill(Color.RED);
			}
		}
	}
	
	@Override
	public void cancel(ActionEvent event) {
		this.getModel().cancelRound();
	}
}
