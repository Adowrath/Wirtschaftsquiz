package ch.bbbaden.idpa.bru_eap_mey.quiz.controllers;

import static org.eclipse.jdt.annotation.DefaultLocation.PARAMETER;
import static org.eclipse.jdt.annotation.DefaultLocation.RETURN_TYPE;
import static org.eclipse.jdt.annotation.DefaultLocation.TYPE_ARGUMENT;
import static org.eclipse.jdt.annotation.DefaultLocation.TYPE_BOUND;


import java.util.Random;


import org.eclipse.jdt.annotation.NonNullByDefault;


import ch.bbbaden.idpa.bru_eap_mey.quiz.model.question.BinaryQuestion;
import ch.bbbaden.idpa.bru_eap_mey.quiz.model.question.Question;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 * Der Controller für die Wahr/Falsch-Fragen.
 * 
 * @see BinaryQuestion
 */
@NonNullByDefault({PARAMETER, RETURN_TYPE, TYPE_BOUND, TYPE_ARGUMENT})
public class BinaryController extends QuestionController<BinaryQuestion> {
	
	/**
	 * Der Text der Frage.
	 */
	@FXML
	private Text questionText;
	
	/**
	 * Der erste Radiobutton.
	 */
	@FXML
	private RadioButton radioButton1;
	
	/**
	 * Der zweite Radiobutton.
	 */
	@FXML
	private RadioButton radioButton2;
	
	/**
	 * Die Werte der Antworten.
	 */
	private boolean[] answers;
	
	@Override
	public void setQuestion(BinaryQuestion question) {
		String[] qAnswers = question.getAnswers();
		this.answers = new boolean[] {false, false};
		
		int order = new Random().nextInt(2);
		this.answers[order] = true;
		
		this.radioButton1.setText(qAnswers[order]);
		this.radioButton2.setText(qAnswers[order == 1 ? 0 : 1]);
		this.questionText.setText(question.getQuestion());
	}
	
	@Override
	public void accept(ActionEvent event) {
		boolean selected1 = this.radioButton1.isSelected();
		boolean selected2 = this.radioButton2.isSelected();
		if(selected1 || selected2) {
			Boolean chosenAnswer = Boolean
					.valueOf(this.answers[selected1 ? 0 : 1]);
			Question<Boolean> question = this.getModel()
					.testAnswer(chosenAnswer);
			
			if(question.check(chosenAnswer)) {
				(selected1 ? this.radioButton1 : this.radioButton2)
						.setTextFill(Color.GREEN);
			} else {
				(selected1 ? this.radioButton1 : this.radioButton2)
						.setTextFill(Color.RED);
				(selected1 ? this.radioButton2 : this.radioButton1)
						.setTextFill(Color.GREEN);
			}
		}
	}
	
	@Override
	public void cancel(ActionEvent event) {
		this.getModel().cancelRound();
	}
}
