package ch.bbbaden.idpa.bru_eap_mey.quiz.model.question;

import ch.bbbaden.idpa.bru_eap_mey.quiz.Util;

/**
 * Die "Freihand"-Frage ist eine Frage, bei der keine
 * Antwortmöglichkeiten gegeben sind. Die Antwort muss als Freitext
 * eingegeben werden und wird mithilfe des
 * {@link Util#levenshteinDistance(CharSequence, CharSequence)
 * Levenshtein-Algorithmus} überprüft.
 */
public class FreehandQuestion extends Question<String> {
	
	@Override
	public boolean check(String answer) {
		// TODO - implement
		throw new UnsupportedOperationException("FreehandQuestion.check() not yet implemented");
	}
	
	@Override
	public String getAnswer() {
		// TODO - implement
		throw new UnsupportedOperationException("FreehandQuestion.getAnswer() not yet implemented");
	}
	
	@Override
	public String[] getAnswers() {
		// TODO - implement
		throw new UnsupportedOperationException("FreehandQuestion.getAnswers() not yet implemented");
	}
	
	@Override
	public String getQuestion() {
		// TODO - implement
		throw new UnsupportedOperationException("FreehandQuestion.getQuestion() not yet implemented");
	}
	
	@Override
	public String getFilename() {
		// TODO - implement
		throw new UnsupportedOperationException("FreehandQuestion.getFilename() not yet implemented");
	}
	
}
