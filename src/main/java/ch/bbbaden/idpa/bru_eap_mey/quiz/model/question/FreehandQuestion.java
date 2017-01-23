package ch.bbbaden.idpa.bru_eap_mey.quiz.model.question;

import static ch.bbbaden.idpa.bru_eap_mey.quiz.Util.levenshteinDistance;


import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;
import org.jdom2.Element;


import ch.bbbaden.idpa.bru_eap_mey.quiz.Util;
import ch.bbbaden.idpa.bru_eap_mey.quiz.controllers.QuestionEditController;
import ch.bbbaden.idpa.bru_eap_mey.quiz.model.Category;

/**
 * Die "Freihand"-Frage ist eine Frage, bei der keine
 * Antwortmöglichkeiten gegeben sind. Die Antwort muss als Freitext
 * eingegeben werden und wird mithilfe des
 * {@link Util#levenshteinDistance(CharSequence, CharSequence)
 * Levenshtein-Algorithmus} überprüft.
 */
public class FreehandQuestion extends Question<String> {
	
	static {
		Question.register(	"freehand", FreehandQuestion::load,
							FreehandQuestion::getDummy);
	}
	
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
	public FreehandQuestion(String que, @Nullable Category cat, String answ) {
		super(que, cat);
		this.answer = answ.trim();
	}
	
	@Override
	public boolean check(String answ) {
		return levenshteinDistance(this.answer.toLowerCase(), answ.trim()
				.toLowerCase()) <= this.answer.length() / 5;
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
	
	@Override
	public Element save() {
		return new Element("question").setAttribute("type", "freehand")
				.addContent(new Element("text").setText(this.getQuestion()))
				.addContent(new Element("answer").setText(this.answer));
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (this.answer.hashCode());
		result = prime * result + (this.getQuestion().hashCode());
		return result;
	}
	
	@Override
	public boolean equals(@Nullable Object obj) {
		if(this == obj)
			return true;
		if(obj == null)
			return false;
		if(!(obj instanceof FreehandQuestion))
			return false;
		FreehandQuestion other = (FreehandQuestion) obj;
		if(!this.answer.equals(other.answer))
			return false;
		if(!this.getQuestion().equals(other.getQuestion()))
			return false;
		return true;
	}
	
	/**
	 * Versucht, aus dem Element eine Frage zu entnehmen. Bei einem
	 * Fehler wird
	 * {@link Util#showErrorExitOnNoOrClose(String, String, Object...)}
	 * aufgerufen.
	 * 
	 * @param el
	 *        das JDOM-Element
	 * @return
	 * 		die Frage, oder {@code null} bei einem Fehler.
	 */
	public static @Nullable FreehandQuestion load(Element el) {
		Element textElement = el.getChild("text");
		Element answerElement = el.getChild("answer");
		
		if(textElement == null) {
			Util.showErrorExitOnNoOrClose(	QUESTION_FORMAT_TITLE,
											QUESTION_ERROR_FORMAT,
											"Freihandfrage",
											"keinen Fragetext");
			return null;
		}
		if(answerElement == null) {
			Util.showErrorExitOnNoOrClose(	QUESTION_FORMAT_TITLE,
											QUESTION_ERROR_FORMAT,
											"Freihandfrage", "keine Antwort");
			return null;
		}
		return new FreehandQuestion(textElement.getText().replaceAll(	"[^ \\S]+",
																		" "),
									null, answerElement.getText()
											.replaceAll("[^ \\S]+", " "));
	}
	
	/**
	 * Erstellt ein Dummy-Objekt. Wird für den
	 * {@link QuestionEditController Frageneditor} verwendet.
	 * 
	 * @return
	 * 		ein leeres, unregistriertes Fragenobjekt.
	 */
	public static FreehandQuestion getDummy() {
		return new FreehandQuestion("", null, "");
	}
}
