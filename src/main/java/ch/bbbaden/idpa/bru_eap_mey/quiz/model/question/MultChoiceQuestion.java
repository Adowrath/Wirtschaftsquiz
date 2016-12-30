package ch.bbbaden.idpa.bru_eap_mey.quiz.model.question;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;


import ch.bbbaden.idpa.bru_eap_mey.quiz.model.Category;

/**
 * Eine Multiple Choice-Frage zeichnet sich dadurch aus, dass eine
 * Liste von Antwortmöglichkeiten gegeben wird, von denen nur eine
 * korrekt ist.
 * <br>
 * Hier besitzt jede Frage 4 Antwortmöglichkeiten.
 */
public class MultChoiceQuestion extends Question<Integer> {
	
	/**
	 * Die korrekte Antwort.
	 */
	private String correctAnswer;
	
	/**
	 * Die erste falsche Antwort.
	 */
	private String wrongAnswer1;
	
	/**
	 * Die zweite falsche Antwort.
	 */
	private String wrongAnswer2;
	
	/**
	 * Die dritte falsche Antwort.
	 */
	private String wrongAnswer3;
	
	/**
	 * Erstellt eine neue Multiple Choice-Frage.
	 * 
	 * @param que
	 *        der Text der Frage
	 * @param cat
	 *        die Kategorie der Frage
	 * @param cAnsw
	 *        die richtige Antwort
	 * @param wAnsw1
	 *        die erste falsche Antwort
	 * @param wAnsw2
	 *        die zweite falsche Antwort
	 * @param wAnsw3
	 *        die dritte falsche Antwort
	 */
	public MultChoiceQuestion(	String que, @Nullable Category cat, String cAnsw,
								String wAnsw1, String wAnsw2, String wAnsw3) {
		super(que, cat);
		this.correctAnswer = cAnsw;
		this.wrongAnswer1 = wAnsw1;
		this.wrongAnswer2 = wAnsw2;
		this.wrongAnswer3 = wAnsw3;
	}
	
	@Override
	public boolean check(Integer answer) {
		return answer.intValue() == 0;
	}
	
	@Override
	public Integer getAnswer() {
		return Integer.valueOf(0);
	}
	
	@Override
	public @NonNull String[] getAnswers() {
		return new @NonNull String[] {this.correctAnswer, this.wrongAnswer1,
				this.wrongAnswer2, this.wrongAnswer3};
	}
	
	@Override
	public String getFilename() {
		return "multChoiceQuestion.fxml";
	}
	
	@Override
	public int getAnswerCount() {
		return 4;
	}
	
	@Override
	public void setAnswers(@NonNull String[] answers) {
		this.correctAnswer = answers[0];
		this.wrongAnswer1 = answers[1];
		this.wrongAnswer2 = answers[2];
		this.wrongAnswer3 = answers[3];
	}
	
	@Override
	public @NonNull String[] getAnswerFieldLabels() {
		return new @NonNull String[] {"Korrekte Antwort", "Falsche Antwort 1",
				"Falsche Antwort 2", "Falsche Antwort 3"};
	}
	
}
