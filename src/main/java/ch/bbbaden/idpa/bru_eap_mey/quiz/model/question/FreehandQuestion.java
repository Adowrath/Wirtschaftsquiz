package ch.bbbaden.idpa.bru_eap_mey.quiz.model.question;

import static ch.bbbaden.idpa.bru_eap_mey.quiz.Util.levenshteinDistance;
import static ch.bbbaden.idpa.bru_eap_mey.quiz.Util.showParseError;


import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;
import org.jdom2.Element;


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
	
	static {
		Question.register("freehand", FreehandQuestion::load);
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
				.addContent(new Element("answer")
							.setText(this.answer));
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
	public static @Nullable FreehandQuestion load(Element el) {
		Element textElement = el.getChild("text");
		Element answerElement = el.getChild("answer");
		
		if(textElement == null) {
			showParseError(	"Falsch formatierte Frage",
							"Eine Frage hat keinen Fragentext. "
									+ "Wenn die Daten gespeichert werden, "
									+ "wird diese Frage nicht gespeichert "
									+ "und damit effektiv gelöscht. "
									+ "Fortfahren?");
			return null;
		}
		if(answerElement == null) {
			showParseError(	"Falsch formatierte Frage",
							"Eine Freihandfrage hat keine Antwort. "
									+ "Wenn die Daten gespeichert werden, wird "
									+ "diese Frage nicht gespeichert und damit "
									+ "effektiv gelöscht. Fortfahren?");
			return null;
		}
		return new FreehandQuestion(textElement.getText(), null,
									answerElement.getText());
	}
}
