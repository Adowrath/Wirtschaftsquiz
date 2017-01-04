package ch.bbbaden.idpa.bru_eap_mey.quiz.controllers;

import static org.eclipse.jdt.annotation.DefaultLocation.PARAMETER;
import static org.eclipse.jdt.annotation.DefaultLocation.RETURN_TYPE;
import static org.eclipse.jdt.annotation.DefaultLocation.TYPE_ARGUMENT;
import static org.eclipse.jdt.annotation.DefaultLocation.TYPE_BOUND;


import org.eclipse.jdt.annotation.NonNullByDefault;


import ch.bbbaden.idpa.bru_eap_mey.quiz.Util;
import ch.bbbaden.idpa.bru_eap_mey.quiz.model.question.MultChoiceQuestion;
import ch.bbbaden.idpa.bru_eap_mey.quiz.model.question.Question;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.paint.Color;

/**
 * Der Controller für die Multiple Choice-Fragen.
 * 
 * @see MultChoiceQuestion
 */
@NonNullByDefault({PARAMETER, RETURN_TYPE, TYPE_BOUND, TYPE_ARGUMENT})
public class MultChoiceController extends QuestionController<MultChoiceQuestion> {
	
	/**
	 * Der Text der Frage.
	 */
	@FXML
	private Label questionText;
	
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
	 * Der dritte Radiobutton.
	 */
	@FXML
	private RadioButton radioButton3;
	
	/**
	 * Der vierte Radiobutton.
	 */
	@FXML
	private RadioButton radioButton4;
	
	/**
	 * Die Werte der Antworten.
	 */
	private int[] answers;
	
	@Override
	public void setQuestion(MultChoiceQuestion question) {
		String[] qAnswers = question.getAnswers();
		this.answers = Util.randomShuffleOf4();
		
		this.radioButton1.setText(qAnswers[this.answers[0]]);
		this.radioButton2.setText(qAnswers[this.answers[1]]);
		this.radioButton3.setText(qAnswers[this.answers[2]]);
		this.radioButton4.setText(qAnswers[this.answers[3]]);
		this.questionText.setText(question.getQuestion());
	}
	
	/*
	 * (non-Javadoc)
	 * ........I don't like writing lengthy if-elseif-else constructs.
	 * So, suit your damn self. ._.
	 */
	@Override
	public void accept(ActionEvent event) {
		RadioButton current;
		int selected = -1;
		
		current = this.radioButton1.isSelected()
				? ((selected = 0) != -1 ? this.radioButton1 : null) : null;
		current = this.radioButton2.isSelected()
				? ((selected = 1) != -1 ? this.radioButton2 : null) : current;
		current = this.radioButton3.isSelected()
				? ((selected = 2) != -1 ? this.radioButton3 : null) : current;
		current = this.radioButton4.isSelected()
				? ((selected = 3) != -1 ? this.radioButton4 : null) : current;
		
		if(selected != -1) {
			assert current != null;
			Integer chosenAnswer = Integer.valueOf(this.answers[selected]);
			Question<Integer> question = this.getModel()
					.testAnswer(chosenAnswer);
			
			if(question.check(chosenAnswer)) {
				current.setTextFill(Color.GREEN);
			} else {
				current.setTextFill(Color.RED);
				int correct = question.getAnswer().intValue();
				
				(this.answers[0] == correct ? this.radioButton1
						: this.answers[1] == correct ? this.radioButton2
								: this.answers[2] == correct ? this.radioButton3
										: this.radioButton4)
												.setTextFill(Color.GREEN);
			}
		}
	}
	
	@Override
	public void cancel(ActionEvent event) {
		this.getModel().cancelRound();
	}
}
