package ch.bbbaden.idpa.bru_eap_mey.quiz.model.question;

import org.eclipse.jdt.annotation.NonNull;

/**
 * Eine binäre Frage - auch als Wahr/Falsch oder Ja/Nein-Frage bekannt
 * - ist dadurch gekennzeichnet, dass sich eine Frage nur mit Ja oder
 * Nein bzw. Wahr oder Falsch beantworten lässt. <br>
 * 
 * Ein Beispiel: "Alle Pudel sind Hunde." Die Antwort ist hierbei
 * <strong>Wahr</strong>.
 */
public class BinaryQuestion extends Question<Boolean> {
	
	/**
	 * Der Text der korrekten Antwort.
	 */
	private String correctAnswer;
	
	/**
	 * Der Text der falschen Antwort.
	 */
	private String wrongAnswer;
	
	/**
	 * Erstellt eine neue binäre Frage.
	 * 
	 * @param que
	 *        der Text der neuen Frage
	 * @param cAnswer
	 *        die korrekte Antwort
	 * @param wAnswer
	 *        die falsche Antwort
	 */
	public BinaryQuestion(String que, String cAnswer, String wAnswer) {
		super(que);
		this.correctAnswer = cAnswer;
		this.wrongAnswer = wAnswer;
	}
	
	@Override
	public boolean check(Boolean answer) {
		return answer.booleanValue();
	}
	
	@Override
	public Boolean getAnswer() {
		return Boolean.TRUE;
	}
	
	@Override
	public @NonNull String[] getAnswers() {
		return new @NonNull String[] {this.correctAnswer, this.wrongAnswer};
	}
	
	@Override
	public String getFilename() {
		return "binaryQuestion.fxml";
	}
	
	@Override
	public int getAnswerCount() {
		return 2;
	}
	
	@Override
	public void setAnswers(@NonNull String[] answers) {
		this.correctAnswer = answers[0];
		this.wrongAnswer = answers[1];
	}
	
	@Override
	public @NonNull String[] getAnswerFieldLabels() {
		return new @NonNull String[] {"Richtige Antwort", "Falsche Antwort"};
	}
	
}
