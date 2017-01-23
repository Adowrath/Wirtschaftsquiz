package ch.bbbaden.idpa.bru_eap_mey.quiz.controllers;

import static org.eclipse.jdt.annotation.DefaultLocation.PARAMETER;
import static org.eclipse.jdt.annotation.DefaultLocation.RETURN_TYPE;
import static org.eclipse.jdt.annotation.DefaultLocation.TYPE_ARGUMENT;
import static org.eclipse.jdt.annotation.DefaultLocation.TYPE_BOUND;


import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;


import ch.bbbaden.idpa.bru_eap_mey.quiz.Util;
import ch.bbbaden.idpa.bru_eap_mey.quiz.model.question.MultChoiceQuestion;
import ch.bbbaden.idpa.bru_eap_mey.quiz.model.question.Question;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

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
	
	@Override
	public void accept(ActionEvent event) {
		int selection = this.getSelection();
		if(selection == -1)
			return;
		
		RadioButton current = this.getButtonForIndex(selection);
		assert current != null : "A valid index was selected, so "
				+ "a valid RadioButton should be returned.";
		
		Integer chosen = Integer.valueOf(this.answers[selection]);
		Question<Integer> question = this.getModel().testAnswer(chosen);
		
		if(question.check(chosen)) {
			
			current.setTextFill(Color.GREEN);
			
		} else {
			
			current.setTextFill(Color.RED);
			int correct = question.getAnswer().intValue();
			RadioButton correctButton = this.getButtonForAnswer(correct);
			assert correctButton != null : "The answer should be "
					+ "\"linked\" to a valid button.";
			
			correctButton.setTextFill(Color.GREEN);
		}
	}
	
	/**
	 * Sucht, welcher RadioButton ausgewählt ist.
	 * 
	 * @return
	 * 		der Index, der zum ausgewählten RadioButton passt, oder
	 *         -1, wenn keiner ausgewählt ist.
	 */
	private int getSelection() {
		return this.radioButton1.isSelected() ? 0
				: this.radioButton2.isSelected() ? 1
						: this.radioButton3.isSelected() ? 2
								: this.radioButton4.isSelected() ? 3 : -1;
		
	}
	
	/**
	 * Sucht den nten (0-basiert) RadioButton.
	 * 
	 * @param answer
	 *        die korrekte Antwort, gibt nur ein Resultat zwischen 0
	 *        und 3 (inkl.).
	 * @return
	 * 		der RadioButton, oder {@code null} wenn die Antwort
	 *         nicht passt.
	 */
	
	private @Nullable RadioButton getButtonForAnswer(int answer) {
		return this.getButtonForIndex(this.answers[0] == answer ? 0
				: this.answers[1] == answer ? 1
						: this.answers[2] == answer ? 2
								: this.answers[3] == answer ? 3 : -1);
	}
	
	/**
	 * Sucht den nten (0-basiert) RadioButton.
	 * 
	 * @param index
	 *        der ausgewählte Index, gibt nur ein Resultat bei
	 *        zwischen 0
	 *        und 3 (inkl.).
	 * @return
	 * 		der RadioButton, oder {@code null} wenn der index nicht
	 *         passt.
	 */
	private @Nullable RadioButton getButtonForIndex(int index) {
		return index == 0 ? this.radioButton1
				: index == 1 ? this.radioButton2
						: index == 2 ? this.radioButton3
								: index == 3 ? this.radioButton3 : null;
	}
	
	@Override
	public void cancel(ActionEvent event) {
		this.getModel().cancelRound();
	}
}
