package ch.bbbaden.idpa.bru_eap_mey.quiz.model.question;

import static org.hamcrest.CoreMatchers.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.anyVararg;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.verifyNoMoreInteractions;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;


import org.eclipse.jdt.annotation.NonNull;
import org.jdom2.Element;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;


import ch.bbbaden.idpa.bru_eap_mey.quiz.Util;

/**
 * Die Tests für die binären Fragen.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Util.class})
@SuppressWarnings({"boxing"})
public final class BinaryQuestionTest {
	
	/**
	 * Der ErrorCollector.
	 */
	@Rule
	public ErrorCollector now = new ErrorCollector();
	
	/**
	 * Eine binäre Frage muss 2 Antworten haben.
	 */
	@Test
	public void testGetAnswerCount() {
		BinaryQuestion bq = new BinaryQuestion("", null, "", "");
		
		int answerCount = bq.getAnswerCount();
		
		this.now.checkThat(	"Binary Questions should have two answers.",
							answerCount, is(equalTo(2)));
	}
	
	/**
	 * Konstruktor und Getter hantieren korrekt mit den Antworten.
	 */
	@Test
	public void testGetAnswers() {
		BinaryQuestion bq = new BinaryQuestion("", null, "a", "b");
		
		String[] answers = bq.getAnswers();
		
		this.now.checkThat(	"Answers are returned correctly.", answers,
							is(equalTo(new String[] {"a", "b"})));
	}
	
	/**
	 * Der Setter hantiert korrekt mit den Antworten.
	 */
	@Test
	public void testSetAnswers() {
		BinaryQuestion bq = new BinaryQuestion("", null, "a", "b");
		
		bq.setAnswers(new @NonNull String[] {"d", "e"});
		String[] answers = bq.getAnswers();
		
		this.now.checkThat(	"Answers are returned correctly.", answers,
							is(equalTo(new String[] {"d", "e"})));
	}
	
	/**
	 * Die richtigen Labels sind für die binäre Frage.
	 */
	@Test
	public void testGetAnswerFieldLabels() {
		BinaryQuestion bq = new BinaryQuestion("", null, "", "");
		
		String[] labels = bq.getAnswerFieldLabels();
		
		this.now.checkThat(	"Answer labels are correct.", labels,
							is(equalTo(new String[] {"Richtige Antwort",
									"Falsche Antwort"})));
	}
	
	/**
	 * Der richtige Dateiname ist registriert.
	 */
	@Test
	public void testGetFilename() {
		BinaryQuestion bq = new BinaryQuestion("", null, "", "");
		
		String filename = bq.getFilename();
		
		this.now.checkThat(	"Binary Filename is correct", filename,
							is(equalTo("binaryQuestion.fxml")));
	}
	
	/**
	 * Die Speichermethode funktioniert korrekt.
	 */
	@Test
	public void testSave() {
		BinaryQuestion bq = new BinaryQuestion("q", null, "a", "b");
		
		Element saved = bq.save(); // TODO Better Element comparison.
		
		this.now.checkThat(	"Save works correctly.", saved.getName(),
							is(equalTo("question")));
		this.now.checkThat(	"Save works correctly.",
							saved.getAttributeValue("type"),
							is(equalTo("binary")));
		this.now.checkThat(	"Save works correctly.", saved.getChild("text"),
							is(notNullValue()));
		this.now.checkThat(	"Save works correctly.",
							saved.getChild("text").getText(), is(equalTo("q")));
		this.now.checkThat(	"Save works correctly.",
							saved.getChild("trueAnswer"), is(notNullValue()));
		this.now.checkThat(	"Save works correctly.",
							saved.getChild("trueAnswer").getText(),
							is(equalTo("a")));
		this.now.checkThat(	"Save works correctly.",
							saved.getChild("falseAnswer"), is(notNullValue()));
		this.now.checkThat(	"Save works correctly.",
							saved.getChild("falseAnswer").getText(),
							is(equalTo("b")));
	}
	
	/**
	 * {@code true} ist die akzeptierte Antwort.
	 */
	@Test
	public void testCheck() {
		BinaryQuestion bq = new BinaryQuestion("", null, "", "");
		
		boolean answer = bq.check(Boolean.TRUE);
		
		this.now.checkThat(	"true is the accepted answer.", answer,
							is(equalTo(true)));
	}
	
	/**
	 * {@code false} ist die abgelehnte Antwort.
	 */
	@Test
	public void testCheckWrong() {
		BinaryQuestion bq = new BinaryQuestion("", null, "", "");
		
		boolean answer = bq.check(Boolean.FALSE);
		
		this.now.checkThat(	"false is not the accepted answer.", answer,
							is(equalTo(false)));
	}
	
	/**
	 * Die richtige Antwort ist {@code true}.
	 */
	@Test
	public void testGetAnswer() {
		BinaryQuestion bq = new BinaryQuestion("", null, "", "");
		
		Boolean answer = bq.getAnswer();
		
		this.now.checkThat(	"true is the correct answer.", answer,
							is(equalTo(Boolean.TRUE)));
	}
	
	/**
	 * Binäre Frage ist gleich zu sich selbst.
	 */
	@Test
	public void testSelfreferentialEquals() {
		BinaryQuestion bq = new BinaryQuestion("ab", null, "abcd", "abcdef");
		
		//
		
		this.now.checkThat(	"Binary Question is equal to itself.", bq,
							is(equalTo(bq)));
	}
	
	/**
	 * Binäre Frage ist gleich zu einer Kopie.
	 */
	@Test
	public void testEquals() {
		BinaryQuestion bq1 = new BinaryQuestion("ab", null, "abcd", "abcdef");
		BinaryQuestion bq2 = new BinaryQuestion("ab", null, "abcd", "abcdef");
		
		//
		
		this.now.checkThat(	"Binary Question is equal to an exact twin version.",
							bq1, is(equalTo(bq2)));
	}
	
	/**
	 * Binäre Frage ist ungleich {@code null}.
	 */
	@Test
	public void testEqualsFalseWithNull() {
		BinaryQuestion bq = new BinaryQuestion("ab", null, "abcd", "abcdef");
		
		//
		
		this.now.checkThat(	"Binary Question is not equal to null.", bq,
							is(not(equalTo(null))));
	}
	
	/**
	 * Binäre Frage ist ungleich einem generischen Objekt.
	 */
	@Test
	public void testEqualsFalseWithObject() {
		BinaryQuestion bq = new BinaryQuestion("ab", null, "abcd", "abcdef");
		Object obj = new Object();
		
		//
		
		this.now.checkThat(	"Binary Question is not equal to a general Object.",
							bq, is(not(equalTo(obj))));
	}
	
	/**
	 * Fragetext muss gleich sein.
	 */
	@Test
	public void testEqualsFalseWithQuestion() {
		BinaryQuestion bq1 = new BinaryQuestion("ab", null, "abcd", "abcdef");
		BinaryQuestion bq2 = new BinaryQuestion("bc", null, "abcd", "abcdef");
		
		//
		
		this.now.checkThat("Binary Question is not equal to a version with different question.",
						bq1, is(not(equalTo(bq2))));
	}
	
	/**
	 * Wahre Antwort muss gleich sein.
	 */
	@Test
	public void testEqualsFalseWithTrueAnswer() {
		BinaryQuestion bq1 = new BinaryQuestion("ab", null, "abcd", "abcdef");
		BinaryQuestion bq2 = new BinaryQuestion("ab", null, "dcba", "abcdef");
		
		//
		
		this.now.checkThat("Binary Question is not equal to a version with different true answer.",
						bq1, is(not(equalTo(bq2))));
	}
	
	/**
	 * Falsche Antwort muss gleich sein.
	 */
	@Test
	public void testEqualsFalseWithFalseAnswer() {
		BinaryQuestion bq1 = new BinaryQuestion("ab", null, "abcd", "abcdef");
		BinaryQuestion bq2 = new BinaryQuestion("ab", null, "abcd", "fedcba");
		
		//
		
		this.now.checkThat("Binary Question is not equal to a version with different false answer.",
						bq1, is(not(equalTo(bq2))));
	}
	
	/**
	 * HashCode ist deterministisch.
	 */
	@Test
	public void testSelfreferentialHashCode() {
		BinaryQuestion bq = new BinaryQuestion("ab", null, "abcd", "abcdef");
		
		int hash_1 = bq.hashCode();
		int hash_2 = bq.hashCode();
		
		this.now.checkThat(	"Binary Question has the same hashCode as itself.", hash_1,
		                   	is(equalTo(hash_2)));
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
		
		this.now.checkThat(	"Binary Question has the same hashCode as a twin version.",
						hash1, is(equalTo(hash2)));
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
		
		this.now.checkThat("Binary Quesiton loads correctly.", bq, is(equalTo(loaded)));
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
		
		this.now.checkSucceeds(() -> {
			verifyStatic();
			Util.showErrorExitOnNoOrClose(anyString(), anyString(), anyVararg());
			return null;
		});
		this.now.checkSucceeds(() -> {
			verifyNoMoreInteractions(Util.class);
			return null;
		});
		this.now.checkThat("Binary Quesiton does not load with no text.", loaded, is(nullValue()));
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
		
		this.now.checkSucceeds(() -> {
			verifyStatic();
			Util.showErrorExitOnNoOrClose(anyString(), anyString(), anyVararg());
			return null;
		});
		this.now.checkSucceeds(() -> {
			verifyNoMoreInteractions(Util.class);
			return null;
		});
		this.now.checkThat(	"Binary Quesiton does not load with no true answer.",
					loaded, is(nullValue()));
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
		
		this.now.checkSucceeds(() -> {
			verifyStatic();
			Util.showErrorExitOnNoOrClose(anyString(), anyString(), anyVararg());
			return null;
		});
		this.now.checkSucceeds(() -> {
			verifyNoMoreInteractions(Util.class);
			return null;
		});
		this.now.checkThat(	"Binary Quesiton does not load with no false answer.",
					loaded, is(nullValue()));
	}
	
	/**
	 * Dummy funktioniert.
	 */
	@Test
	public void testGetDummy() {
		BinaryQuestion bq = BinaryQuestion.getDummy();
		
		//
		
		this.now.checkThat(	"Just the execution with a non-null result of it is enough",
						bq, is(notNullValue()));
	}
}
