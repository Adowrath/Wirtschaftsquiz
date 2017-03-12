package ch.bbbaden.idpa.bru_eap_mey.quiz.model.question;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;
import org.jdom2.Element;


import ch.bbbaden.idpa.bru_eap_mey.quiz.Util;
import ch.bbbaden.idpa.bru_eap_mey.quiz.controllers.QuestionEditController;
import ch.bbbaden.idpa.bru_eap_mey.quiz.model.Category;

/**
 * Eine Multiple Choice-Frage zeichnet sich dadurch aus, dass eine
 * Liste von Antwortmöglichkeiten gegeben wird, von denen nur eine
 * korrekt ist.
 * 
 * <p>
 * Hier besitzt jede Frage 4 Antwortmöglichkeiten.
 */
public final class MultChoiceQuestion extends Question<Integer> {
	
	static {
		Question.register(	"multipleChoice", MultChoiceQuestion::load,
							MultChoiceQuestion::getDummy);
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
	
	@Override
	public int hashCode() {
		int prime = 31;
		int result = 1;
		result = prime * result + this.correctAnswer.hashCode();
		result = prime * result + this.wrongAnswer1.hashCode();
		result = prime * result + this.wrongAnswer2.hashCode();
		result = prime * result + this.wrongAnswer3.hashCode();
		result = prime * result + this.getQuestion().hashCode();
		return result;
	}
	
	@Override
	public boolean equals(@Nullable Object obj) {
		return this == obj || obj instanceof MultChoiceQuestion
				
				&& this.correctAnswer
						.equals(((MultChoiceQuestion) obj).correctAnswer)
				
				&& this.wrongAnswer1
						.equals(((MultChoiceQuestion) obj).wrongAnswer1)
				
				&& this.wrongAnswer2
						.equals(((MultChoiceQuestion) obj).wrongAnswer2)
				
				&& this.wrongAnswer3
						.equals(((MultChoiceQuestion) obj).wrongAnswer3)
				
				&& this.getQuestion()
						.equals(((MultChoiceQuestion) obj).getQuestion());
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
		if(textElement == null) {
			Util.showErrorExitOnNoOrClose(	QUESTION_FORMAT_TITLE,
											QUESTION_ERROR_FORMAT,
											"Multiple Choice-Frage",
											"keinen Fragetext");
			return null;
		}
		
		Element correctAnswerElement = el.getChild("correctAnswer");
		if(correctAnswerElement == null) {
			Util.showErrorExitOnNoOrClose(	QUESTION_FORMAT_TITLE,
											QUESTION_ERROR_FORMAT,
											"Multiple Choice-Frage",
											"keine korrekte Antwort");
			return null;
		}
		
		Element wrongAnswer1Element = el.getChild("wrongAnswer1");
		if(wrongAnswer1Element == null) {
			Util.showErrorExitOnNoOrClose(	QUESTION_FORMAT_TITLE,
											QUESTION_ERROR_FORMAT,
											"Multiple Choice-Frage",
											"keine erste falsche Antwort");
			return null;
		}
		
		Element wrongAnswer2Element = el.getChild("wrongAnswer2");
		if(wrongAnswer2Element == null) {
			Util.showErrorExitOnNoOrClose(	QUESTION_FORMAT_TITLE,
											QUESTION_ERROR_FORMAT,
											"Multiple Choice-Frage",
											"keine zweite falsche Antwort");
			return null;
		}
		
		Element wrongAnswer3Element = el.getChild("wrongAnswer3");
		if(wrongAnswer3Element == null) {
			Util.showErrorExitOnNoOrClose(	QUESTION_FORMAT_TITLE,
											QUESTION_ERROR_FORMAT,
											"Multiple Choice-Frage",
											"keine dritte falsche Antwort");
			return null;
		}
		
		return new MultChoiceQuestion(	textElement.getText()
				.replaceAll("[^ \\S]+", " "),
										null,
										correctAnswerElement.getText()
												.replaceAll("[^ \\S]+", " "),
										wrongAnswer1Element.getText()
												.replaceAll("[^ \\S]+", " "),
										wrongAnswer2Element.getText()
												.replaceAll("[^ \\S]+", " "),
										wrongAnswer3Element.getText()
												.replaceAll("[^ \\S]+", " "));
	}
	
	/**
	 * Erstellt ein Dummy-Objekt. Wird für den
	 * {@link QuestionEditController Frageneditor} verwendet.
	 * 
	 * @return
	 * 		ein leeres, unregistriertes Fragenobjekt.
	 */
	public static MultChoiceQuestion getDummy() {
		return new MultChoiceQuestion("", null, "", "", "", "");
	}
}
