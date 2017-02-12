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
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;


import ch.bbbaden.idpa.bru_eap_mey.quiz.Util;

/**
 * Die Tests für die {@link FreehandQuestion}-Klasse.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Util.class})
@SuppressWarnings({"boxing"})
public final class FreehandQuestionTest {
	
	/**
	 * Der ErrorCollector.
	 */
	@Rule
	public ErrorCollector now = new ErrorCollector();
	
	/**
	 * Eine Freihandfrage soll 1 Antwort haben.
	 */
	@Test
	public void testGetAnswerCount() {
		FreehandQuestion bq = new FreehandQuestion("", null, "");
		
		int answerCount = bq.getAnswerCount();
		
		this.now.checkThat(	"Freehand Questions should have one answer.",
							answerCount, is(equalTo(1)));
	}
	
	/**
	 * Der Konstruktor setzt die Antworten korrekt.
	 */
	@Test
	public void testGetAnswers() {
		FreehandQuestion bq = new FreehandQuestion("", null, "a");
		
		String[] answers = bq.getAnswers();
		
		this.now.checkThat(	"Answers are returned correctly.", answers,
							is(equalTo(new String[] {"a"})));
	}
	
	/**
	 * Der Setter ändert die Antworten korrekt.
	 */
	@Test
	public void testSetAnswers() {
		FreehandQuestion bq = new FreehandQuestion("", null, "a");
		
		bq.setAnswers(new @NonNull String[] {"b"});
		String[] answers = bq.getAnswers();
		
		this.now.checkThat(	"Answers are returned correctly.", answers,
							is(equalTo(new String[] {"b"})));
	}
	
	/**
	 * Die Antwortfeld-Labels sind korrekt.
	 */
	@Test
	public void testGetAnswerFieldLabels() {
		FreehandQuestion bq = new FreehandQuestion("", null, "");
		
		String[] labels = bq.getAnswerFieldLabels();
		
		this.now.checkThat(	"Answer labels are correct.", labels,
							is(equalTo(new String[] {"Antwort"})));
	}
	
	/**
	 * Der Dateiname der FXML-Datei ist korrekt.
	 */
	@Test
	public void testGetFilename() {
		FreehandQuestion bq = new FreehandQuestion("", null, "");
		
		String filename = bq.getFilename();
		
		this.now.checkThat(	"Freehand Filename is correct", filename,
							is(equalTo("freehandQuestion.fxml")));
	}
	
	/**
	 * Die Speichermethode funktioniert richtig.
	 */
	@Test
	public void testSave() {
		FreehandQuestion bq = new FreehandQuestion("q", null, "a");
		
		Element saved = bq.save(); // TODO Better Element comparison.
		
		this.now.checkThat(	"Save works correctly.", saved.getName(),
							is(equalTo("question")));
		this.now.checkThat(	"Save works correctly.",
							saved.getAttributeValue("type"),
							is(equalTo("freehand")));
		this.now.checkThat(	"Save works correctly.", saved.getChild("text"),
							is(notNullValue()));
		this.now.checkThat(	"Save works correctly.",
							saved.getChild("text").getText(), is(equalTo("q")));
		this.now.checkThat(	"Save works correctly.", saved.getChild("answer"),
							is(notNullValue()));
		this.now.checkThat(	"Save works correctly.",
							saved.getChild("answer").getText(),
							is(equalTo("a")));
	}
	
	/**
	 * Die als korrekt markierende Antwort des Levenshtein-Algorithmus
	 * wird als solche akzeptiert.
	 */
	@Test
	public void testCheck() {
		mockStatic(Util.class);
		Mockito.when(Util.levenshteinDistance(anyString(), anyString()))
				.thenReturn(0);
		FreehandQuestion bq = new FreehandQuestion("", null, "answer");
		
		boolean answer = bq.check("answer");
		
		this.now.checkSucceeds(() -> {
			verifyStatic();
			Util.levenshteinDistance(anyString(), anyString());
			return null;
		});
		this.now.checkSucceeds(() -> {
			verifyNoMoreInteractions(Util.class);
			return null;
		});
		this.now.checkThat(	"Correct answer is accepted.", answer,
							is(equalTo(Boolean.TRUE)));
	}
	
	/**
	 * Die als falsch markierende Antwort des Levenshtein-Algorithmus
	 * wird als solche akzeptiert.
	 */
	@Test
	public void testCheckWrong() {
		mockStatic(Util.class);
		Mockito.when(Util.levenshteinDistance(anyString(), anyString()))
				.thenReturn(Integer.MAX_VALUE);
		FreehandQuestion bq = new FreehandQuestion("", null, "answer");
		
		boolean answer = bq.check("answer");
		
		this.now.checkSucceeds(() -> {
			verifyStatic();
			Util.levenshteinDistance(anyString(), anyString());
			return null;
		});
		this.now.checkSucceeds(() -> {
			verifyNoMoreInteractions(Util.class);
			return null;
		});
		this.now.checkThat(	"Incorrect answer is not accepted.", answer,
							is(equalTo(Boolean.FALSE)));
	}
	
	/**
	 * Der Getter für die Antwort gibt die Antwort zurück.
	 */
	@Test
	public void testGetAnswer() {
		FreehandQuestion bq = new FreehandQuestion("", null, "answer");
		
		String answer = bq.getAnswer();
		
		this.now.checkThat(	"Correct answer is returned.", answer,
							is(equalTo("answer")));
	}
	
	/**
	 * Eine Frage ist zu sich selbst gleich.
	 */
	@Test
	public void testSelfreferentialEquals() {
		FreehandQuestion bq = new FreehandQuestion("ab", null, "abcd");
		
		//
		
		this.now.checkThat(	"Freehand Question is equal to itself.", bq,
							is(equalTo(bq)));
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
		
		this.now.checkThat(	"Freehand Question is equal to an exact twin version.",
							bq1, is(equalTo(bq2)));
	}
	
	/**
	 * Eine Frage ist ungleich {@code null}.
	 */
	@Test
	public void testEqualsFalseWithNull() {
		FreehandQuestion bq = new FreehandQuestion("ab", null, "abcd");
		
		//
		
		this.now.checkThat(	"Freehand Question is not equal to null.", bq,
							is(not(equalTo(null))));
	}
	
	/**
	 * Eine Frage ist ungleich einem normalen Objekt.
	 */
	@Test
	public void testEqualsFalseWithObject() {
		FreehandQuestion bq = new FreehandQuestion("ab", null, "abcd");
		Object obj = new Object();
		
		//
		
		this.now.checkThat(	"Freehand Question is not equal to a general Object.",
							bq, is(not(equalTo(obj))));
	}
	
	/**
	 * Der Fragetext muss gleich sein.
	 */
	@Test
	public void testEqualsFalseWithQuestion() {
		FreehandQuestion bq1 = new FreehandQuestion("ab", null, "abcd");
		FreehandQuestion bq2 = new FreehandQuestion("bc", null, "abcd");
		
		//
		
		this.now.checkThat(	"Freehand Question is not equal to a version with different question.",
							bq1, is(not(equalTo(bq2))));
	}
	
	/**
	 * Die Antwort muss gleich sein.
	 */
	@Test
	public void testEqualsFalseWithAnswer() {
		FreehandQuestion bq1 = new FreehandQuestion("ab", null, "abcd");
		FreehandQuestion bq2 = new FreehandQuestion("ab", null, "dcba");
		
		//
		
		this.now.checkThat(	"Freehand Question is not equal to a version with different answer.",
							bq1, is(not(equalTo(bq2))));
	}
	
	/**
	 * Der Hashcode ist deterministisch.
	 */
	@Test
	public void testSelfreferentialHashCode() {
		FreehandQuestion bq = new FreehandQuestion("ab", null, "abcd");
		
		int hash_1 = bq.hashCode();
		int hash_2 = bq.hashCode();
		
		this.now.checkThat(	"Freehand Question has the same hashCode as itself.",
							hash_1, is(equalTo(hash_2)));
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
		
		this.now.checkThat(	"Freehand Question has the same hashCode as a twin version.",
							hash1, is(equalTo(hash2)));
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
		
		this.now.checkThat(	"Freehand Quesiton loads correctly.", bq,
							is(equalTo(loaded)));
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
		
		this.now.checkSucceeds(() -> {
			verifyStatic();
			Util.showErrorExitOnNoOrClose(	anyString(), anyString(),
											anyVararg());
			return null;
		});
		this.now.checkSucceeds(() -> {
			verifyNoMoreInteractions(Util.class);
			return null;
		});
		this.now.checkThat(	"Freehand Quesiton does not load with no text.",
							loaded, is(nullValue()));
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
		
		this.now.checkSucceeds(() -> {
			verifyStatic();
			Util.showErrorExitOnNoOrClose(	anyString(), anyString(),
											anyVararg());
			return null;
		});
		this.now.checkSucceeds(() -> {
			verifyNoMoreInteractions(Util.class);
			return null;
		});
		this.now.checkThat(	"Freehand Quesiton does not load with no answer.",
							loaded, is(nullValue()));
	}
	
	/**
	 * Der Dummy funktioniert.
	 */
	@Test
	public void testGetDummy() {
		FreehandQuestion bq = FreehandQuestion.getDummy();
		
		//
		
		this.now.checkThat(	"Just the execution with a non-null result of it is enough",
							bq, is(notNullValue()));
	}
	
}
