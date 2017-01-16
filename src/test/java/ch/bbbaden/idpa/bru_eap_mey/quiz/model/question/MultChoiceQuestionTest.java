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

@RunWith(PowerMockRunner.class)
@PrepareForTest({Util.class})
@SuppressWarnings({"static-method", "javadoc"})
public class MultChoiceQuestionTest {
	
	@Test
	public void testGetAnswerCount() {
		MultChoiceQuestion mcq = new MultChoiceQuestion("", null, "", "", "",
														"");
		
		int answerCount = mcq.getAnswerCount();
		
		assertEquals(	"Multiple-Choice Questions should have four answers.",
						answerCount, 4);
	}
	
	@Test
	public void testGetAnswers() {
		MultChoiceQuestion mcq = new MultChoiceQuestion("", null, "a", "b", "c",
														"d");
		
		String[] answers = mcq.getAnswers();
		
		assertArrayEquals(	"Answers are returned correctly.",
							new String[] {"a", "b", "c", "d"}, answers);
	}
	
	@Test
	public void testSetAnswers() {
		MultChoiceQuestion mcq = new MultChoiceQuestion("", null, "a", "b", "c",
														"d");
		
		mcq.setAnswers(new @NonNull String[] {"e", "f", "g", "h"});
		String[] answers = mcq.getAnswers();
		
		assertArrayEquals(	"Answers are returned correctly.",
							new String[] {"e", "f", "g", "h"}, answers);
	}
	
	@Test
	public void testGetAnswerFieldLabels() {
		MultChoiceQuestion mcq = new MultChoiceQuestion("", null, "", "", "",
														"");
		
		String[] labels = mcq.getAnswerFieldLabels();
		
		assertArrayEquals(	"Answer labels are correct.",
							new String[] {"Korrekte Antwort",
									"Falsche Antwort 1", "Falsche Antwort 2",
									"Falsche Antwort 3"},
							labels);
	}
	
	@Test
	public void testGetFilename() {
		MultChoiceQuestion mcq = new MultChoiceQuestion("", null, "", "", "",
														"");
		
		String filename = mcq.getFilename();
		
		assertEquals(	"Multiple-Choice Filename is correct",
						"multChoiceQuestion.fxml", filename);
	}
	
	@Test
	public void testSave() {
		MultChoiceQuestion mcq = new MultChoiceQuestion("q", null, "a", "b",
														"c", "d");
		
		Element expected = new Element("question")
				.setAttribute("type", "multipleChoice")
				.addContent(new Element("text").setText("q"))
				.addContent(new Element("correctAnswer").setText("a"))
				.addContent(new Element("wrongAnswer1").setText("b"))
				.addContent(new Element("wrongAnswer2").setText("c"))
				.addContent(new Element("wrongAnswer3").setText("d"));
		Element saved = mcq.save();
		
		assertEquals(	"Save works correctly.", expected.getName(),
						saved.getName());
		assertEquals(	"Save works correctly.",
						expected.getAttributeValue("type"),
						saved.getAttributeValue("type"));
		assertEquals(	"Save works correctly.",
						expected.getChild("text").getText(),
						saved.getChild("text").getText());
		assertEquals(	"Save works correctly.",
						expected.getChild("correctAnswer").getText(),
						saved.getChild("correctAnswer").getText());
		assertEquals(	"Save works correctly.",
						expected.getChild("wrongAnswer1").getText(),
						saved.getChild("wrongAnswer1").getText());
		assertEquals(	"Save works correctly.",
						expected.getChild("wrongAnswer2").getText(),
						saved.getChild("wrongAnswer2").getText());
		assertEquals(	"Save works correctly.",
						expected.getChild("wrongAnswer3").getText(),
						saved.getChild("wrongAnswer3").getText());
	}
	
	@Test
	public void testCheck() {
		MultChoiceQuestion mcq = new MultChoiceQuestion("", null, "", "", "",
														"");
		
		boolean answer = mcq.check(Integer.valueOf(0));
		
		assertTrue("0 is the accepted answer.", answer);
	}
	
	@Test
	public void testCheckWrong() {
		MultChoiceQuestion mcq = new MultChoiceQuestion("", null, "", "", "",
														"");
		
		boolean answer = mcq.check(Integer.valueOf(1));
		
		assertFalse("1 is not the accepted answer.", answer);
	}
	
	@Test
	public void testGetAnswer() {
		MultChoiceQuestion mcq = new MultChoiceQuestion("", null, "", "", "",
														"");
		
		Integer answer = mcq.getAnswer();
		
		assertEquals("1 is the correct answer.", answer.intValue(), 0);
	}
	
	@Test
	public void testSelfreferentialEquals() {
		MultChoiceQuestion mcq = new MultChoiceQuestion("ab", null, "abcd",
														"abcdef", "abcdefgh",
														"abcdefghij");
		
		//
		
		assertEquals("Multiple-Choice Question is equal to itself.", mcq, mcq);
	}
	
	@Test
	public void testEquals() {
		MultChoiceQuestion mcq1 = new MultChoiceQuestion(	"ab", null, "abcd",
															"abcdef",
															"abcdefgh",
															"abcdefghij");
		MultChoiceQuestion mcq2 = new MultChoiceQuestion(	"ab", null, "abcd",
															"abcdef",
															"abcdefgh",
															"abcdefghij");
		
		//
		
		assertEquals(	"Multiple-Choice Question is equal to an exact twin version.",
						mcq1, mcq2);
	}
	
	@Test
	public void testEqualsFalseWithNull() {
		MultChoiceQuestion mcq = new MultChoiceQuestion("ab", null, "abcd",
														"abcdef", "abcdefgh",
														"abcdefghij");
		
		//
		
		assertNotEquals("Multiple-Choice Question is not equal to null.", mcq,
						null);
	}
	
	@Test
	public void testEqualsFalseWithObject() {
		MultChoiceQuestion mcq = new MultChoiceQuestion("ab", null, "abcd",
														"abcdef", "abcdefgh",
														"abcdefghij");
		Object obj = new Object();
		
		//
		
		assertNotEquals("Multiple-Choice Question is not equal to a general Object.",
						mcq, obj);
	}
	
	@Test
	public void testEqualsFalseWithQuestion() {
		MultChoiceQuestion mcq1 = new MultChoiceQuestion(	"ab", null, "abcd",
															"abcdef",
															"abcdefgh",
															"abcdefghij");
		MultChoiceQuestion mcq2 = new MultChoiceQuestion(	"ba", null, "abcd",
															"abcdef",
															"abcdefgh",
															"abcdefghij");
		
		//
		
		assertNotEquals("Multiple-Choice Question is not equal to a version with different question.",
						mcq1, mcq2);
	}
	
	@Test
	public void testEqualsFalseWithCorrectAnswer() {
		MultChoiceQuestion mcq1 = new MultChoiceQuestion(	"ab", null, "abcd",
															"abcdef",
															"abcdefgh",
															"abcdefghij");
		MultChoiceQuestion mcq2 = new MultChoiceQuestion(	"ab", null, "dcba",
															"abcdef",
															"abcdefgh",
															"abcdefghij");
		
		//
		
		assertNotEquals("Multiple-Choice Question is not equal to a version with different correct answer.",
						mcq1, mcq2);
	}
	
	@Test
	public void testEqualsFalseWithWrongAnswer1() {
		MultChoiceQuestion mcq1 = new MultChoiceQuestion(	"ab", null, "abcd",
															"abcdef",
															"abcdefgh",
															"abcdefghij");
		MultChoiceQuestion mcq2 = new MultChoiceQuestion(	"ab", null, "abcd",
															"fedcba",
															"abcdefgh",
															"abcdefghij");
		
		//
		
		assertNotEquals("Multiple-Choice Question is not equal to a version with different 1st wrong answer.",
						mcq1, mcq2);
	}
	
	@Test
	public void testEqualsFalseWithWrongAnswer2() {
		MultChoiceQuestion mcq1 = new MultChoiceQuestion(	"ab", null, "abcd",
															"abcdef",
															"abcdefgh",
															"abcdefghij");
		MultChoiceQuestion mcq2 = new MultChoiceQuestion(	"ab", null, "abcd",
															"abcdef",
															"hgfedcba",
															"abcdefghij");
		
		//
		
		assertNotEquals("Multiple-Choice Question is not equal to a version with different 2nd wrong answer.",
						mcq1, mcq2);
	}
	
	@Test
	public void testEqualsFalseWithWrongAnswer3() {
		MultChoiceQuestion mcq1 = new MultChoiceQuestion(	"ab", null, "abcd",
															"abcdef",
															"abcdefgh",
															"abcdefghij");
		MultChoiceQuestion mcq2 = new MultChoiceQuestion(	"ab", null, "abcd",
															"abcdef",
															"abcdefgh",
															"jihgfedcba");
		
		//
		
		assertNotEquals("Multiple-Choice Question is not equal to a version with different 3rd wrong answer.",
						mcq1, mcq2);
	}
	
	@Test
	public void testSelfreferentialHashCode() {
		MultChoiceQuestion mcq = new MultChoiceQuestion("ab", null, "abcd",
														"abcdef", "abcdefgh",
														"abcdefghij");
		
		int hash_1 = mcq.hashCode();
		int hash_2 = mcq.hashCode();
		
		assertEquals(	"Multiple-Choice Question has the same hashCode as itself.",
						hash_1, hash_2);
	}
	
	@Test
	public void testHashCode() {
		MultChoiceQuestion mcq1 = new MultChoiceQuestion(	"ab", null, "abcd",
															"abcdef",
															"abcdefgh",
															"abcdefghij");
		MultChoiceQuestion mcq2 = new MultChoiceQuestion(	"ab", null, "abcd",
															"abcdef",
															"abcdefgh",
															"abcdefghij");
		
		int hash1 = mcq1.hashCode();
		int hash2 = mcq2.hashCode();
		
		assertEquals(	"Multiple-Choice Question has the same hashCode as a twin version.",
						hash1, hash2);
	}
	
	@Test
	public void testLoad() {
		MultChoiceQuestion mcq = new MultChoiceQuestion("q", null, "a", "b",
														"c", "d");
		Element element = new Element("question")
				.addContent(new Element("text").setText("q"))
				.addContent(new Element("correctAnswer").setText("a"))
				.addContent(new Element("wrongAnswer1").setText("b"))
				.addContent(new Element("wrongAnswer2").setText("c"))
				.addContent(new Element("wrongAnswer3").setText("d"));
		
		MultChoiceQuestion loaded = MultChoiceQuestion.load(element);
		
		assertEquals("Multiple-Choice Question loads correctly.", mcq, loaded);
	}
	
	@Test
	public void testLoadNoText() {
		mockStatic(Util.class);
		Element element = new Element("question")
				.addContent(new Element("correctAnswer").setText("a"))
				.addContent(new Element("wrongAnswer1").setText("b"))
				.addContent(new Element("wrongAnswer2").setText("c"))
				.addContent(new Element("wrongAnswer3").setText("d"));
		
		MultChoiceQuestion loaded = MultChoiceQuestion.load(element);
		
		verifyStatic();
		Util.showErrorExitOnNoOrClose(anyString(), anyString(), anyVararg());
		assertNull(	"Multiple-Choice Question does not load with no text.",
					loaded);
	}
	
	@Test
	public void testLoadNoCorrectAnswer() {
		mockStatic(Util.class);
		Element element = new Element("question")
				.addContent(new Element("text").setText("q"))
				.addContent(new Element("wrongAnswer1").setText("b"))
				.addContent(new Element("wrongAnswer2").setText("c"))
				.addContent(new Element("wrongAnswer3").setText("d"));
		
		MultChoiceQuestion loaded = MultChoiceQuestion.load(element);
		
		verifyStatic();
		Util.showErrorExitOnNoOrClose(anyString(), anyString(), anyVararg());
		assertNull(	"Multiple-Choice Question does not load with no text.",
					loaded);
	}
	
	@Test
	public void testLoadNoWrongAnswer1() {
		mockStatic(Util.class);
		Element element = new Element("question")
				.addContent(new Element("text").setText("q"))
				.addContent(new Element("correctAnswer").setText("a"))
				.addContent(new Element("wrongAnswer2").setText("c"))
				.addContent(new Element("wrongAnswer3").setText("d"));
		
		MultChoiceQuestion loaded = MultChoiceQuestion.load(element);
		
		verifyStatic();
		Util.showErrorExitOnNoOrClose(anyString(), anyString(), anyVararg());
		assertNull(	"Multiple-Choice Question does not load with no text.",
					loaded);
	}
	
	@Test
	public void testLoadNoWrongAnswer2() {
		mockStatic(Util.class);
		Element element = new Element("question")
				.addContent(new Element("text").setText("q"))
				.addContent(new Element("correctAnswer").setText("a"))
				.addContent(new Element("wrongAnswer1").setText("b"))
				.addContent(new Element("wrongAnswer3").setText("d"));
		
		MultChoiceQuestion loaded = MultChoiceQuestion.load(element);
		
		verifyStatic();
		Util.showErrorExitOnNoOrClose(anyString(), anyString(), anyVararg());
		assertNull(	"Multiple-Choice Question does not load with no text.",
					loaded);
	}
	
	@Test
	public void testLoadNoWrongAnswer3() {
		mockStatic(Util.class);
		Element element = new Element("question")
				.addContent(new Element("text").setText("q"))
				.addContent(new Element("correctAnswer").setText("a"))
				.addContent(new Element("wrongAnswer1").setText("b"))
				.addContent(new Element("wrongAnswer2").setText("c"));
		
		MultChoiceQuestion loaded = MultChoiceQuestion.load(element);
		
		verifyStatic();
		Util.showErrorExitOnNoOrClose(anyString(), anyString(), anyVararg());
		assertNull(	"Multiple-Choice Question does not load with no text.",
					loaded);
	}
	
	@Test
	public void testGetDummy() {
		MultChoiceQuestion mcq = MultChoiceQuestion.getDummy();
		
		//
		
		assertNotNull(	"Just the execution with a non-null result of it is enough",
						mcq);
	}
}
