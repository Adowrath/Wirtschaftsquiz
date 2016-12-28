package ch.bbbaden.idpa.bru_eap_mey.quiz.model.question;

import static ch.bbbaden.idpa.bru_eap_mey.quiz.Util.levenshteinDistance;


import org.eclipse.jdt.annotation.NonNull;


import ch.bbbaden.idpa.bru_eap_mey.quiz.Util;
import ch.bbbaden.idpa.bru_eap_mey.quiz.model.Category;

/**
 * Die "Freihand"-Frage ist eine Frage, bei der keine
 * Antwortmöglichkeiten gegeben sind. Die Antwort muss als Freitext
 * eingegeben werden und wird mithilfe des
 * {@link Util#levenshteinDistance(CharSequence, CharSequence)
 * Levenshtein-Algorithmus} überprüft.
 */
public class FreehandQuestion extends Question<String> {
	
	/**
	 * Die Antwort dieser Frage.
	 */
	private String answer;
	
	/**
	 * Erstellt eine neue Freihandfrage.
	 * 
	 * @param que
	 *        der Text der Frage
	 * @param cat
	 *        die Kategorie der Frage
	 * @param answ
	 *        die Antwort
	 */
	public FreehandQuestion(String que, Category cat, String answ) {
		super(que, cat);
		this.answer = answ;
	}
	
	@Override
	public boolean check(String answ) {
		return levenshteinDistance(this.answer, answ) <= this.answer.length()
				/ 5;
	}
	
	@Override
	public String getAnswer() {
		return this.answer;
	}
	
	@Override
	public @NonNull String[] getAnswers() {
		return new @NonNull String[] {this.answer};
	}
	
	@Override
	public String getFilename() {
		return "freehandQuestion.fxml";
	}
	
	@Override
	public int getAnswerCount() {
		return 1;
	}
	
	@Override
	public void setAnswers(@NonNull String[] answers) {
		this.answer = answers[0];
	}
	
	@Override
	public @NonNull String[] getAnswerFieldLabels() {
		return new @NonNull String[] {"Antwort"};
	}
	
}
