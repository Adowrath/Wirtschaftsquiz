package ch.bbbaden.idpa.bru_eap_mey.quiz.model.question;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.anyVararg;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;


import org.eclipse.jdt.annotation.NonNull;
import org.jdom2.Element;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;


import ch.bbbaden.idpa.bru_eap_mey.quiz.Util;

/**
 * Die Tests für die {@link FreehandQuestion}-Klasse.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Util.class})
@SuppressWarnings({"static-method"})
public final class FreehandQuestionTest {
	
	/**
	 * Eine Freihandfrage soll 1 Antwort haben.
	 */
	@Test
	public void testGetAnswerCount() {
		FreehandQuestion bq = new FreehandQuestion("", null, "");
		
		int answerCount = bq.getAnswerCount();
		
		assertEquals(	"Freehand Questions should have one answer.", answerCount,
						1);
	}
	
	/**
	 * Der Konstruktor setzt die Antworten korrekt.
	 */
	@Test
	public void testGetAnswers() {
		FreehandQuestion bq = new FreehandQuestion("", null, "a");
		
		String[] answers = bq.getAnswers();
		
		assertArrayEquals(	"Answers are returned correctly.", new String[] {"a"},
							answers);
	}
	
	/**
	 * Der Setter ändert die Antworten korrekt.
	 */
	@Test
	public void testSetAnswers() {
		FreehandQuestion bq = new FreehandQuestion("", null, "a");
		
		bq.setAnswers(new @NonNull String[] {"b"});
		String[] answers = bq.getAnswers();
		
		assertArrayEquals(	"Answers are returned correctly.", new String[] {"b"},
							answers);
	}
	
	/**
	 * Die Antwortfeld-Labels sind korrekt.
	 */
	@Test
	public void testGetAnswerFieldLabels() {
		FreehandQuestion bq = new FreehandQuestion("", null, "");
		
		String[] labels = bq.getAnswerFieldLabels();
		
		assertArrayEquals(	"Answer labels are correct.",
							new String[] {"Antwort"}, labels);
	}
	
	/**
	 * Der Dateiname der FXML-Datei ist korrekt.
	 */
	@Test
	public void testGetFilename() {
		FreehandQuestion bq = new FreehandQuestion("", null, "");
		
		String filename = bq.getFilename();
		
		assertEquals(	"Freehand Filename is correct", "freehandQuestion.fxml",
						filename);
	}
	
	/**
	 * Die Speichermethode funktioniert richtig.
	 */
	@Test
	public void testSave() {
		FreehandQuestion bq = new FreehandQuestion("q", null, "a");
		
		Element expected = new Element("question")
				.setAttribute("type", "freehand")
				.addContent(new Element("text").setText("q"))
				.addContent(new Element("answer").setText("a"));
		Element saved = bq.save();
		
		assertEquals(	"Save works correctly.", expected.getName(),
						saved.getName());
		assertEquals(	"Save works correctly.",
						expected.getAttributeValue("type"),
						saved.getAttributeValue("type"));
		assertEquals(	"Save works correctly.",
						expected.getChild("text").getText(),
						saved.getChild("text").getText());
		assertEquals(	"Save works correctly.",
						expected.getChild("answer").getText(),
						saved.getChild("answer").getText());
	}
	
	/**
	 * Die als korrekt markierende Antwort des Levenshtein-Algorithmus
	 * wird als solche akzeptiert.
	 */
	@SuppressWarnings("boxing")
	@Test
	public void testCheck() {
		mockStatic(Util.class);
		Mockito.when(Util.levenshteinDistance(anyString(), anyString()))
				.thenReturn(0);
		FreehandQuestion bq = new FreehandQuestion("", null, "answer");
		
		boolean answer = bq.check("answer");
		
		verifyStatic();
		Util.levenshteinDistance(anyString(), anyString());
		assertTrue("Correct answer is accepted.", answer);
	}
	
	/**
	 * Die als falsch markierende Antwort des Levenshtein-Algorithmus
	 * wird als solche akzeptiert.
	 */
	@SuppressWarnings("boxing")
	@Test
	public void testCheckWrong() {
		mockStatic(Util.class);
		Mockito.when(Util.levenshteinDistance(anyString(), anyString()))
				.thenReturn(Integer.MAX_VALUE);
		FreehandQuestion bq = new FreehandQuestion("", null, "answer");
		
		boolean answer = bq.check("Hans-Dieter");
		
		verifyStatic();
		Util.levenshteinDistance(anyString(), anyString());
		assertFalse("Incorrect answer is accepted.", answer);
	}
	
	/**
	 * Der Getter für die Antwort gibt die Antwort zurück.
	 */
	@Test
	public void testGetAnswer() {
		FreehandQuestion bq = new FreehandQuestion("", null, "answer");
		
		String answer = bq.getAnswer();
		
		assertEquals("Correct answer is returned.", "answer", answer);
	}
	
	/**
	 * Eine Frage ist zu sich selbst gleich.
	 */
	@Test
	public void testSelfreferentialEquals() {
		FreehandQuestion bq = new FreehandQuestion("ab", null, "abcd");
		
		//
		
		assertEquals("Freehand Question is equal to itself.", bq, bq);
	}
	
	/**
	 * Zwei inhaltlich gleiche Fragen sind auch laut {@code equals}
	 * gleich.
	 */
	@Test
	public void testEquals() {
		FreehandQuestion bq1 = new FreehandQuestion("ab", null, "abcd");
		FreehandQuestion bq2 = new FreehandQuestion("ab", null, "abcd");
		
		//
		
		assertEquals(	"Freehand Question is equal to an exact twin version.",
						bq1, bq2);
	}
	
	/**
	 * Eine Frage ist ungleich {@code null}.
	 */
	@Test
	public void testEqualsFalseWithNull() {
		FreehandQuestion bq = new FreehandQuestion("ab", null, "abcd");
		
		//
		
		assertNotEquals("Freehand Question is not equal to null.", bq, null);
	}
	
	/**
	 * Eine Frage ist ungleich einem normalen Objekt.
	 */
	@Test
	public void testEqualsFalseWithObject() {
		FreehandQuestion bq = new FreehandQuestion("ab", null, "abcd");
		Object obj = new Object();
		
		//
		
		assertNotEquals("Freehand Question is not equal to a general Object.",
						bq, obj);
	}
	
	/**
	 * Der Fragetext muss gleich sein.
	 */
	@Test
	public void testEqualsFalseWithQuestion() {
		FreehandQuestion bq1 = new FreehandQuestion("ab", null, "abcd");
		FreehandQuestion bq2 = new FreehandQuestion("bc", null, "abcd");
		
		//
		
		assertNotEquals("Freehand Question is not equal to a version with different question.",
						bq1, bq2);
	}
	
	/**
	 * Die Antwort muss gleich sein.
	 */
	@Test
	public void testEqualsFalseWithAnswer() {
		FreehandQuestion bq1 = new FreehandQuestion("ab", null, "abcd");
		FreehandQuestion bq2 = new FreehandQuestion("ab", null, "dcba");
		
		//
		
		assertNotEquals("Freehand Question is not equal to a version with different answer.",
						bq1, bq2);
	}
	
	/**
	 * Der Hashcode ist deterministisch.
	 */
	@Test
	public void testSelfreferentialHashCode() {
		FreehandQuestion bq = new FreehandQuestion("ab", null, "abcd");
		
		int hash_1 = bq.hashCode();
		int hash_2 = bq.hashCode();
		
		assertEquals(	"Freehand Question has the same hashCode as itself.",
						hash_1, hash_2);
	}
	
	/**
	 * Inhaltlich gleiche Fragen haben denselben Hashcode.
	 */
	@Test
	public void testHashCode() {
		FreehandQuestion bq1 = new FreehandQuestion("ab", null, "abcd");
		FreehandQuestion bq2 = new FreehandQuestion("ab", null, "abcd");
		
		int hash1 = bq1.hashCode();
		int hash2 = bq2.hashCode();
		
		assertEquals(	"Freehand Question has the same hashCode as a twin version.",
						hash1, hash2);
	}
	
	/**
	 * Die Ladefunktion funktioniert.
	 */
	@Test
	public void testLoad() {
		FreehandQuestion bq = new FreehandQuestion("q", null, "a");
		Element element = new Element("question")
				.addContent(new Element("text").setText("q"))
				.addContent(new Element("answer").setText("a"));
		
		FreehandQuestion loaded = FreehandQuestion.load(element);
		
		assertEquals("Freehand Quesiton loads correctly.", bq, loaded);
	}
	
	/**
	 * Der Fragetext wird benötigt.
	 */
	@Test
	public void testLoadNoText() {
		mockStatic(Util.class);
		
		Element element = new Element("question")
				.addContent(new Element("answer").setText("a"));
		
		FreehandQuestion loaded = FreehandQuestion.load(element);
		
		verifyStatic();
		Util.showErrorExitOnNoOrClose(anyString(), anyString(), anyVararg());
		assertNull("Freehand Quesiton does not load with no text.", loaded);
	}
	
	/**
	 * Die Antwort wird benötigt.
	 */
	@Test
	public void testLoadNoAnswer() {
		mockStatic(Util.class);
		Element element = new Element("question")
				.addContent(new Element("text").setText("q"));
		
		FreehandQuestion loaded = FreehandQuestion.load(element);
		
		verifyStatic();
		Util.showErrorExitOnNoOrClose(anyString(), anyString(), anyVararg());
		assertNull("Freehand Quesiton does not load with no answer.", loaded);
	}
	
	/**
	 * Der Dummy funktioniert.
	 */
	@Test
	public void testGetDummy() {
		FreehandQuestion bq = FreehandQuestion.getDummy();
		
		//
		
		assertNotNull(	"Just the execution with a non-null result of it is enough",
						bq);
	}
	
}
