package ch.bbbaden.idpa.bru_eap_mey.quiz.model.question;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;
import org.jdom2.Element;


import ch.bbbaden.idpa.bru_eap_mey.quiz.Util;
import ch.bbbaden.idpa.bru_eap_mey.quiz.controllers.QuestionEditController;
import ch.bbbaden.idpa.bru_eap_mey.quiz.model.Category;

/**
 * Eine binäre Frage - auch als Wahr/Falsch oder Ja/Nein-Frage bekannt
 * - ist dadurch gekennzeichnet, dass sich eine Frage nur mit Ja oder
 * Nein bzw. Wahr oder Falsch beantworten lässt.
 * 
 * <p>
 * Ein Beispiel: "Alle Pudel sind Hunde." Die Antwort ist hierbei
 * <em>Wahr</em>.
 */
public class BinaryQuestion extends Question<Boolean> {
	
	static {
		Question.register(	"binary", BinaryQuestion::load,
							BinaryQuestion::getDummy);
	}
	
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
	 * @param cat
	 *        die Kategorie der Frage
	 * @param cAnswer
	 *        die korrekte Antwort
	 * @param wAnswer
	 *        die falsche Antwort
	 */
	public BinaryQuestion(	String que, @Nullable Category cat, String cAnswer,
							String wAnswer) {
		super(que, cat);
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
	
	@Override
	public Element save() {
		return new Element("question").setAttribute("type", "binary")
				.addContent(new Element("text").setText(this.getQuestion()))
				.addContent(new Element("trueAnswer")
						.setText(this.correctAnswer))
				.addContent(new Element("falseAnswer")
						.setText(this.wrongAnswer));
	}
	
	@Override
	public int hashCode() {
		int prime = 31;
		int result = 1;
		result = prime * result + this.correctAnswer.hashCode();
		result = prime * result + this.wrongAnswer.hashCode();
		result = prime * result + this.getQuestion().hashCode();
		return result;
	}
	
	@Override
	public boolean equals(@Nullable Object obj) {
		return this == obj || obj != null && obj instanceof BinaryQuestion
				
				&& this.correctAnswer
						.equals(((BinaryQuestion) obj).correctAnswer)
				
				&& this.wrongAnswer.equals(((BinaryQuestion) obj).wrongAnswer)
				
				&& this.getQuestion()
						.equals(((BinaryQuestion) obj).getQuestion());
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
	public static @Nullable BinaryQuestion load(Element el) {
		Element textElement = el.getChild("text");
		if(textElement == null) {
			Util.showErrorExitOnNoOrClose(	QUESTION_FORMAT_TITLE,
											QUESTION_ERROR_FORMAT,
											"binäre Frage", "keinen Fragetext");
			return null;
		}
		
		Element trueAnswerElement = el.getChild("trueAnswer");
		if(trueAnswerElement == null) {
			Util.showErrorExitOnNoOrClose(	QUESTION_FORMAT_TITLE,
											QUESTION_ERROR_FORMAT,
											"binäre Frage",
											"keine richtige Antwort");
			return null;
		}
		
		Element falseAnswerElement = el.getChild("falseAnswer");
		if(falseAnswerElement == null) {
			Util.showErrorExitOnNoOrClose(	QUESTION_FORMAT_TITLE,
											QUESTION_ERROR_FORMAT,
											"binäre Frage",
											"keine falsche Antwort");
			return null;
		}
		
		return new BinaryQuestion(	textElement.getText().replaceAll("[^ \\S]+",
																	" "),
									null,
									trueAnswerElement.getText()
											.replaceAll("[^ \\S]+", " "),
									falseAnswerElement.getText()
											.replaceAll("[^ \\S]+", " "));
	}
	
	/**
	 * Erstellt ein Dummy-Objekt. Wird für den
	 * {@link QuestionEditController Frageneditor} verwendet.
	 * 
	 * @return
	 * 		ein leeres, unregistriertes Fragenobjekt.
	 */
	public static BinaryQuestion getDummy() {
		return new BinaryQuestion("", null, "", "");
	}
}
