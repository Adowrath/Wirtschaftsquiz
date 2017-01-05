package ch.bbbaden.idpa.bru_eap_mey.quiz.model.question;

import static ch.bbbaden.idpa.bru_eap_mey.quiz.Util.showParseError;


import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;
import org.jdom2.Element;


import ch.bbbaden.idpa.bru_eap_mey.quiz.Util;
import ch.bbbaden.idpa.bru_eap_mey.quiz.model.Category;

/**
 * Eine binäre Frage - auch als Wahr/Falsch oder Ja/Nein-Frage bekannt
 * - ist dadurch gekennzeichnet, dass sich eine Frage nur mit Ja oder
 * Nein bzw. Wahr oder Falsch beantworten lässt. <br>
 * 
 * Ein Beispiel: "Alle Pudel sind Hunde." Die Antwort ist hierbei
 * <strong>Wahr</strong>.
 */
public class BinaryQuestion extends Question<Boolean> {
	
	static {
		Question.register("binary", BinaryQuestion::load);
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
	
	/**
	 * Versucht, aus dem Element eine Frage zu entnehmen. Bei einem
	 * Fehler wird
	 * {@link Util#showParseError(String, String, Object...)}
	 * aufgerufen.
	 * 
	 * @param el
	 *        das JDOM-Element
	 * @return
	 * 		die Frage, oder {@code null} bei einem Fehler.
	 */
	public static @Nullable BinaryQuestion load(Element el) {
		Element textElement = el.getChild("text");
		Element trueAnswerElement = el.getChild("trueAnswer");
		Element falseAnswerElement = el.getChild("falseAnswer");
		
		if(textElement == null) {
			showParseError(	"Falsch formatierte Frage",
							"Eine Frage hat keinen Fragentext. "
									+ "Wenn die Daten gespeichert werden, "
									+ "wird diese Frage nicht gespeichert "
									+ "und damit effektiv gelöscht. "
									+ "Fortfahren?");
			return null;
		}
		if(trueAnswerElement == null) {
			showParseError(	"Falsch formatierte Frage",
							"Eine binäre Frage hat keine richtige Antwort. "
									+ "Wenn die Daten gespeichert werden, "
									+ "wird diese Frage nicht gespeichert "
									+ "und damit effektiv gelöscht. "
									+ "Fortfahren?");
			return null;
		}
		if(falseAnswerElement == null) {
			showParseError(	"Falsch formatierte Frage",
							"Eine binäre Frage hat keine falsche Antwort. "
									+ "Wenn die Daten gespeichert werden, "
									+ "wird diese Frage nicht gespeichert "
									+ "und damit effektiv gelöscht. "
									+ "Fortfahren?");
			return null;
		}
		return new BinaryQuestion(	textElement.getText(), null,
									trueAnswerElement.getText(),
									falseAnswerElement.getText());
	}
}
