package ch.bbbaden.idpa.bru_eap_mey.quiz.model.question;

import static ch.bbbaden.idpa.bru_eap_mey.quiz.Util.showErrorExitOnNoOrClose;


import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;
import org.jdom2.Element;


import ch.bbbaden.idpa.bru_eap_mey.quiz.Util;
import ch.bbbaden.idpa.bru_eap_mey.quiz.model.Category;

/**
 * Eine Multiple Choice-Frage zeichnet sich dadurch aus, dass eine
 * Liste von Antwortmöglichkeiten gegeben wird, von denen nur eine
 * korrekt ist.
 * <br>
 * Hier besitzt jede Frage 4 Antwortmöglichkeiten.
 */
public class MultChoiceQuestion extends Question<Integer> {
	
	static {
		Question.register("multipleChoice", MultChoiceQuestion::load);
	}
	
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
	
	@Override
	public Element save() {
		return new Element("question").setAttribute("type", "multipleChoice")
				.addContent(new Element("text").setText(this.getQuestion()))
				.addContent(new Element("correctAnswer")
						.setText(this.correctAnswer))
				.addContent(new Element("wrongAnswer1")
						.setText(this.wrongAnswer1))
				.addContent(new Element("wrongAnswer2")
						.setText(this.wrongAnswer2))
				.addContent(new Element("wrongAnswer3")
						.setText(this.wrongAnswer3));
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
	public static @Nullable MultChoiceQuestion load(Element el) {
		Element textElement = el.getChild("text");
		Element correctAnswerElement = el.getChild("correctAnswer");
		Element wrongAnswer1Element = el.getChild("wrongAnswer1");
		Element wrongAnswer2Element = el.getChild("wrongAnswer2");
		Element wrongAnswer3Element = el.getChild("wrongAnswer3");
		
		if(textElement == null) {
			showErrorExitOnNoOrClose(	"Falsch formatierte Frage",
							"Eine Frage hat keinen Fragentext. "
									+ "Wenn die Daten gespeichert werden, "
									+ "wird diese Frage nicht gespeichert "
									+ "und damit effektiv gelöscht. "
									+ "Fortfahren?");
			return null;
		}
		if(correctAnswerElement == null) {
			showErrorExitOnNoOrClose(	"Falsch formatierte Frage",
							"Eine Multiple Choice-Frage hat keine korrekte Antwort. "
									+ "Wenn die Daten gespeichert werden, wird "
									+ "diese Frage nicht gespeichert und damit "
									+ "effektiv gelöscht. Fortfahren?");
			return null;
		}
		if(wrongAnswer1Element == null) {
			showErrorExitOnNoOrClose(	"Falsch formatierte Frage",
							"Eine Multiple Choice-Frage hat keine erste falsche Antwort. "
									+ "Wenn die Daten gespeichert werden, wird "
									+ "diese Frage nicht gespeichert und damit "
									+ "effektiv gelöscht. Fortfahren?");
			return null;
		}
		if(wrongAnswer2Element == null) {
			showErrorExitOnNoOrClose(	"Falsch formatierte Frage",
							"Eine Multiple Choice-Frage hat keine zweite falsche Antwort. "
									+ "Wenn die Daten gespeichert werden, wird "
									+ "diese Frage nicht gespeichert und damit "
									+ "effektiv gelöscht. Fortfahren?");
			return null;
		}
		if(wrongAnswer3Element == null) {
			showErrorExitOnNoOrClose(	"Falsch formatierte Frage",
							"Eine Multiple Choice-Frage hat keine dritte falsche Antwort. "
									+ "Wenn die Daten gespeichert werden, wird "
									+ "diese Frage nicht gespeichert und damit "
									+ "effektiv gelöscht. Fortfahren?");
			return null;
		}
		return new MultChoiceQuestion(	textElement.getText(), null,
										correctAnswerElement.getText(),
										wrongAnswer1Element.getText(),
										wrongAnswer2Element.getText(),
										wrongAnswer3Element.getText());
	}
}
