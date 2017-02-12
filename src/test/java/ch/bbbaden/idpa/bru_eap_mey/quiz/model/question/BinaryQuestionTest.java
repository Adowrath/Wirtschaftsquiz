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
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;


import ch.bbbaden.idpa.bru_eap_mey.quiz.Util;

/**
 * Die Tests für die binären Fragen.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Util.class})
@SuppressWarnings({"static-method"})
public final class BinaryQuestionTest {
	
	/**
	 * Eine binäre Frage muss 2 Antworten haben.
	 */
	@Test
	public void testGetAnswerCount() {
		BinaryQuestion bq = new BinaryQuestion("", null, "", "");
		
		int answerCount = bq.getAnswerCount();
		
		assertEquals(	"Binary Questions should have two answers.", answerCount,
						2);
	}
	
	/**
	 * Konstruktor und Getter hantieren korrekt mit den Antworten.
	 */
	@Test
	public void testGetAnswers() {
		BinaryQuestion bq = new BinaryQuestion("", null, "a", "b");
		
		String[] answers = bq.getAnswers();
		
		assertArrayEquals(	"Answers are returned correctly.",
							new String[] {"a", "b"}, answers);
	}
	
	/**
	 * Der Setter hantiert korrekt mit den Antworten.
	 */
	@Test
	public void testSetAnswers() {
		BinaryQuestion bq = new BinaryQuestion("", null, "a", "b");
		
		bq.setAnswers(new @NonNull String[] {"d", "e"});
		String[] answers = bq.getAnswers();
		
		assertArrayEquals(	"Answers are returned correctly.",
							new String[] {"d", "e"}, answers);
	}
	
	/**
	 * Die richtigen Labels sind für die binäre Frage.
	 */
	@Test
	public void testGetAnswerFieldLabels() {
		BinaryQuestion bq = new BinaryQuestion("", null, "", "");
		
		String[] labels = bq.getAnswerFieldLabels();
		
		assertArrayEquals("Answer labels are correct.", new String[] {
				"Richtige Antwort", "Falsche Antwort"}, labels);
	}
	
	/**
	 * Der richtige Dateiname ist registriert.
	 */
	@Test
	public void testGetFilename() {
		BinaryQuestion bq = new BinaryQuestion("", null, "", "");
		
		String filename = bq.getFilename();
		
		assertEquals(	"Binary Filename is correct", "binaryQuestion.fxml",
						filename);
	}
	
	/**
	 * Die Speichermethode funktioniert korrekt.
	 */
	@Test
	public void testSave() {
		BinaryQuestion bq = new BinaryQuestion("q", null, "a", "b");
		
		Element expected = new Element("question")
				.setAttribute("type", "binary")
				.addContent(new Element("text").setText("q"))
				.addContent(new Element("trueAnswer").setText("a"))
				.addContent(new Element("falseAnswer").setText("b"));
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
						expected.getChild("trueAnswer").getText(),
						saved.getChild("trueAnswer").getText());
		assertEquals(	"Save works correctly.",
						expected.getChild("falseAnswer").getText(),
						saved.getChild("falseAnswer").getText());
	}
	
	/**
	 * {@code true} ist die akzeptierte Antwort.
	 */
	@Test
	public void testCheck() {
		BinaryQuestion bq = new BinaryQuestion("", null, "", "");
		
		boolean answer = bq.check(Boolean.TRUE);
		
		assertTrue("true is the accepted answer.", answer);
	}
	
	/**
	 * {@code false} ist die abgelehnte Antwort.
	 */
	@Test
	public void testCheckWrong() {
		BinaryQuestion bq = new BinaryQuestion("", null, "", "");
		
		boolean answer = bq.check(Boolean.FALSE);
		
		assertFalse("false is not the accepted answer.", answer);
	}
	
	/**
	 * Die richtige Antwort ist {@code true}.
	 */
	@Test
	public void testGetAnswer() {
		BinaryQuestion bq = new BinaryQuestion("", null, "", "");
		
		Boolean answer = bq.getAnswer();
		
		assertTrue("true is the correct answer.", answer);
	}
	
	/**
	 * Binäre Frage ist gleich zu sich selbst.
	 */
	@Test
	public void testSelfreferentialEquals() {
		BinaryQuestion bq = new BinaryQuestion("ab", null, "abcd", "abcdef");
		
		//
		
		assertEquals("Binary Question is equal to itself.", bq, bq);
	}
	
	/**
	 * Binäre Frage ist gleich zu einer Kopie.
	 */
	@Test
	public void testEquals() {
		BinaryQuestion bq1 = new BinaryQuestion("ab", null, "abcd", "abcdef");
		BinaryQuestion bq2 = new BinaryQuestion("ab", null, "abcd", "abcdef");
		
		//
		
		assertEquals(	"Binary Question is equal to an exact twin version.", bq1,
						bq2);
	}
	
	/**
	 * Binäre Frage ist ungleich {@code null}.
	 */
	@Test
	public void testEqualsFalseWithNull() {
		BinaryQuestion bq = new BinaryQuestion("ab", null, "abcd", "abcdef");
		
		//
		
		assertNotEquals("Binary Question is not equal to null.", bq, null);
	}
	
	/**
	 * Binäre Frage ist ungleich einem generischen Objekt.
	 */
	@Test
	public void testEqualsFalseWithObject() {
		BinaryQuestion bq = new BinaryQuestion("ab", null, "abcd", "abcdef");
		Object obj = new Object();
		
		//
		
		assertNotEquals("Binary Question is not equal to a general Object.", bq,
						obj);
	}
	
	/**
	 * Fragetext muss gleich sein.
	 */
	@Test
	public void testEqualsFalseWithQuestion() {
		BinaryQuestion bq1 = new BinaryQuestion("ab", null, "abcd", "abcdef");
		BinaryQuestion bq2 = new BinaryQuestion("bc", null, "abcd", "abcdef");
		
		//
		
		assertNotEquals("Binary Question is not equal to a version with different question.",
						bq1, bq2);
	}
	
	/**
	 * Wahre Antwort muss gleich sein.
	 */
	@Test
	public void testEqualsFalseWithTrueAnswer() {
		BinaryQuestion bq1 = new BinaryQuestion("ab", null, "abcd", "abcdef");
		BinaryQuestion bq2 = new BinaryQuestion("ab", null, "dcba", "abcdef");
		
		//
		
		assertNotEquals("Binary Question is not equal to a version with different true answer.",
						bq1, bq2);
	}
	
	/**
	 * Falsche Antwort muss gleich sein.
	 */
	@Test
	public void testEqualsFalseWithFalseAnswer() {
		BinaryQuestion bq1 = new BinaryQuestion("ab", null, "abcd", "abcdef");
		BinaryQuestion bq2 = new BinaryQuestion("ab", null, "abcd", "fedcba");
		
		//
		
		assertNotEquals("Binary Question is not equal to a version with different false answer.",
						bq1, bq2);
	}
	
	/**
	 * HashCode ist deterministisch.
	 */
	@Test
	public void testSelfreferentialHashCode() {
		BinaryQuestion bq = new BinaryQuestion("ab", null, "abcd", "abcdef");
		
		int hash_1 = bq.hashCode();
		int hash_2 = bq.hashCode();
		
		assertEquals(	"Binary Question has the same hashCode as itself.", hash_1,
						hash_2);
	}
	
	/**
	 * HashCode ist gleich bei Kopien.
	 */
	@Test
	public void testHashCode() {
		BinaryQuestion bq1 = new BinaryQuestion("ab", null, "abcd", "abcdef");
		BinaryQuestion bq2 = new BinaryQuestion("ab", null, "abcd", "abcdef");
		
		int hash1 = bq1.hashCode();
		int hash2 = bq2.hashCode();
		
		assertEquals(	"Binary Question has the same hashCode as a twin version.",
						hash1, hash2);
	}
	
	/**
	 * Ladevorgang funktioniert korrekt.
	 */
	@Test
	public void testLoad() {
		BinaryQuestion bq = new BinaryQuestion("q", null, "a", "b");
		Element element = new Element("question")
				.addContent(new Element("text").setText("q"))
				.addContent(new Element("trueAnswer").setText("a"))
				.addContent(new Element("falseAnswer").setText("b"));
		
		BinaryQuestion loaded = BinaryQuestion.load(element);
		
		assertEquals("Binary Quesiton loads correctly.", bq, loaded);
	}
	
	/**
	 * Text ist notwendig.
	 */
	@Test
	public void testLoadNoText() {
		mockStatic(Util.class);
		Element element = new Element("question")
				.addContent(new Element("trueAnswer").setText("a"))
				.addContent(new Element("falseAnswer").setText("b"));
		
		BinaryQuestion loaded = BinaryQuestion.load(element);
		
		verifyStatic();
		Util.showErrorExitOnNoOrClose(anyString(), anyString(), anyVararg());
		assertNull("Binary Quesiton does not load with no text.", loaded);
	}
	
	/**
	 * Wahre Antwort ist notwendig.
	 */
	@Test
	public void testLoadNoTrue() {
		mockStatic(Util.class);
		Element element = new Element("question")
				.addContent(new Element("text").setText("q"))
				.addContent(new Element("falseAnswer").setText("b"));
		
		BinaryQuestion loaded = BinaryQuestion.load(element);
		
		verifyStatic();
		Util.showErrorExitOnNoOrClose(anyString(), anyString(), anyVararg());
		assertNull(	"Binary Quesiton does not load with no true answer.",
					loaded);
	}
	
	/**
	 * Falsche Antwort ist notwendig.
	 */
	@Test
	public void testLoadNoFalse() {
		mockStatic(Util.class);
		Element element = new Element("question")
				.addContent(new Element("text").setText("q"))
				.addContent(new Element("trueAnswer").setText("a"));
		
		BinaryQuestion loaded = BinaryQuestion.load(element);
		
		verifyStatic();
		Util.showErrorExitOnNoOrClose(anyString(), anyString(), anyVararg());
		assertNull(	"Binary Quesiton does not load with no false answer.",
					loaded);
	}
	
	/**
	 * Dummy funktioniert.
	 */
	@Test
	public void testGetDummy() {
		BinaryQuestion bq = BinaryQuestion.getDummy();
		
		//
		
		assertNotNull(	"Just the execution with a non-null result of it is enough",
						bq);
	}
}
