package ch.bbbaden.idpa.bru_eap_mey.quiz.model.question;

/**
 * Eine binäre Frage - auch als Wahr/Falsch oder Ja/Nein-Frage bekannt
 * - ist dadurch gekennzeichnet, dass sich eine Frage nur mit Ja oder
 * Nein bzw. Wahr oder Falsch beantworten lässt. <br>
 * 
 * Ein Beispiel: "Alle Pudel sind Hunde." Die Antwort ist hierbei
 * <strong>Wahr</strong>.
 */
public class BinaryQuestion extends Question<Boolean> {
	
	@Override
	public boolean check(Boolean answer) {
		// TODO - implement
		throw new UnsupportedOperationException("BinaryQuestion.check() not yet implemented");
	}
	
	@Override
	public Boolean getAnswer() {
		// TODO - implement
		throw new UnsupportedOperationException("BinaryQuestion.getAnswer() not yet implemented");
	}
	
	@Override
	public String[] getAnswers() {
		// TODO - implement
		throw new UnsupportedOperationException("BinaryQuestion.getAnswers() not yet implemented");
	}
	
	@Override
	public String getQuestion() {
		// TODO - implement
		throw new UnsupportedOperationException("BinaryQuestion.getQuestion() not yet implemented");
	}
	
	@Override
	public String getFilename() {
		// TODO - implement
		throw new UnsupportedOperationException("BinaryQuestion.getFilename() not yet implemented");
	}
	
}
