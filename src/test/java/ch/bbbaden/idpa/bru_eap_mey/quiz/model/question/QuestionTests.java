package ch.bbbaden.idpa.bru_eap_mey.quiz.model.question;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Eine Suite für alle Tests der Fragenklassen.
 */
@RunWith(Suite.class)
@SuiteClasses({BinaryQuestionTest.class, FreehandQuestionTest.class,
		MultChoiceQuestionTest.class, QuestionTest.class})
public final class QuestionTests {
	//
}
