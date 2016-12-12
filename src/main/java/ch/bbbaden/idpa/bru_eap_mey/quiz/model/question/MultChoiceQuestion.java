package ch.bbbaden.idpa.bru_eap_mey.quiz.model.question;

/**
 * Eine Multiple Choice-Frage zeichnet sich dadurch aus, dass eine
 * Liste von Antwortmöglichkeiten gegeben wird, von denen nur eine
 * korrekt ist.
 * <br>
 * Hier besitzt jede Frage 4 Antwortmöglichkeiten.
 */
public class MultChoiceQuestion extends Question<Integer> {
	
	@Override
	public boolean check(Integer answer) {
		// TODO - implement
		throw new UnsupportedOperationException("MultChoiceQuestion.check() not yet implemented");
	}
	
	@Override
	public Integer getAnswer() {
		// TODO - implement
		throw new UnsupportedOperationException("MultChoiceQuestion.getAnswer() not yet implemented");
	}
	
	@Override
	public String[] getAnswers() {
		// TODO - implement
		throw new UnsupportedOperationException("MultChoiceQuestion.getAnswers() not yet implemented");
	}
	
	@Override
	public String getQuestion() {
		// TODO - implement
		throw new UnsupportedOperationException("MultChoiceQuestion.getQuestion() not yet implemented");
	}
	
	@Override
	public String getFilename() {
		// TODO - implement
		throw new UnsupportedOperationException("MultChoiceQuestion.getFilename() not yet implemented");
	}
	
}
