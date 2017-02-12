package ch.bbbaden.idpa.bru_eap_mey.quiz.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.mock;


import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;


import org.junit.Test;


import ch.bbbaden.idpa.bru_eap_mey.quiz.model.question.Question;

/**
 * Die Tests für die Kategorien.
 */
@SuppressWarnings({"static-method"})
public final class CategoryTest {
	
	/**
	 * Überprüft den Varargs-Konstruktor ohne Argumente.
	 */
	@Test
	public void testCategoryEmptyVarargs() {
		List<Question<?>> list = Collections.emptyList();
		
		Category c = new Category("a", "b");
		List<Question<?>> catList = c.getQuestions();
		
		assertEquals(	"Varargs constructor saves the questions correctly with no questions.",
						list, catList);
	}
	
	/**
	 * Überprüft den Varags-Konstruktor mit Argumenten.
	 */
	@Test
	public void testCategoryVarargs() {
		Question<?> q1 = mock(Question.class, CALLS_REAL_METHODS);
		Question<?> q2 = mock(Question.class, CALLS_REAL_METHODS);
		Question<?> q3 = mock(Question.class, CALLS_REAL_METHODS);
		List<Question<?>> origList = Arrays.asList(q1, q2, q3);
		
		Category c = new Category("a", "b", q1, q2, q3);
		List<Question<?>> catList = c.getQuestions();
		
		assertEquals("Varargs constructor saves correctly.", origList, catList);
	}
	
	/**
	 * Überprüft den Listen-Konstruktor.
	 */
	@Test
	public void testCategoryList() {
		Question<?> q1 = mock(Question.class, CALLS_REAL_METHODS);
		Question<?> q2 = mock(Question.class, CALLS_REAL_METHODS);
		Question<?> q3 = mock(Question.class, CALLS_REAL_METHODS);
		List<Question<?>> origList = Arrays.asList(q1, q2, q3);
		
		Category c = new Category("a", "b", origList);
		List<Question<?>> catList = c.getQuestions();
		
		assertEquals("Varargs constructor saves correctly.", origList, catList);
	}
	
	/**
	 * Überprüft den Stream-Konstruktor.
	 */
	@Test
	public void testCategoryStream() {
		Question<?> q1 = mock(Question.class, CALLS_REAL_METHODS);
		Question<?> q2 = mock(Question.class, CALLS_REAL_METHODS);
		Question<?> q3 = mock(Question.class, CALLS_REAL_METHODS);
		List<Question<?>> origList = Arrays.asList(q1, q2, q3);
		Stream<Question<?>> stream = Stream.of(q1, q2, q3);
		
		Category c = new Category("a", "b", stream);
		List<Question<?>> catList = c.getQuestions();
		
		assertEquals("Varargs constructor saves correctly.", origList, catList);
	}
	
	/**
	 * Überprüft, dass der Konstruktor die Beschreibung richtig
	 * speichert.
	 */
	@Test
	public void testGetDescription() {
		Category c = new Category("a", "b");
		
		String currentDesc = c.getDescription();
		
		assertEquals(	"Constructor saves description correctly.", "b",
						currentDesc);
	}
	
	/**
	 * Überprüft, dass der Setter die Beschreibung richtig
	 * überschreibt.
	 */
	@Test
	public void testSetDescription() {
		Category c = new Category("a", "b");
		
		c.setDescription("c");
		String currentDesc = c.getDescription();
		
		assertEquals(	"setDescription changes description correctly.", "c",
						currentDesc);
	}
	
	/**
	 * Überprüft, dass der Konstruktor den Namen richtig speichert.
	 */
	@Test
	public void testGetName() {
		Category c = new Category("a", "b");
		
		String currentName = c.getName();
		
		assertEquals("Constructor saves name correctly.", "a", currentName);
	}
	
	/**
	 * Überprüft, dass der Setter den Namen richtig überschreibt.
	 */
	@Test
	public void testSetName() {
		Category c = new Category("a", "b");
		
		c.setName("c");
		String currentName = c.getName();
		
		assertEquals("setName changes name correctly.", "c", currentName);
	}
	
	/**
	 * Überprüft, dass die Anzeige von Name und Fragenzahl bei keinen
	 * Fragen richtig funktioniert.
	 */
	@Test
	public void testGetNameAndCountNoQuestions() {
		Category c = new Category("a", "b");
		
		String nameAndCount = c.getNameAndCount();
		
		assertEquals(	"getNameAndCount returns correctly without any questions.",
						"a (0)", nameAndCount);
	}
	
	/**
	 * Überprüft, dass die Anzeige von Name und Fragenzahl bei einer
	 * Frage richtig funktioniert.
	 */
	@Test
	public void testGetNameAndCountWithQuestions() {
		Question<?> q = mock(Question.class, CALLS_REAL_METHODS);
		Category c = new Category("a", "b", q);
		
		String nameAndCount = c.getNameAndCount();
		
		assertEquals(	"getNameAndCount returns correctly without any questions.",
						"a (1)", nameAndCount);
	}
	
	/**
	 * Überprüft, dass die Anzeige von Name und Fragenzahl auch nach
	 * Änderung des Namens funktioniert.
	 */
	@Test
	public void testGetNameAndCountWithQuestionsAfterSetName() {
		Category c = new Category("a", "b");
		
		c.setName("c");
		String nameAndCount = c.getNameAndCount();
		
		assertEquals(	"getNameAndCount returns correctly without any questions.",
						"c (0)", nameAndCount);
	}
	
	/**
	 * Schaut, dass die Frage aus der Liste der registrierten Fragen
	 * gelöscht wird.
	 */
	@Test
	public void testRemoveQuestion() {
		Question<?> q = mock(Question.class, CALLS_REAL_METHODS);
		Category c = new Category("a", "b", q);
		List<Question<?>> list = Collections.emptyList();
		
		c.removeQuestion(q);
		List<Question<?>> catList = c.getQuestions();
		
		assertEquals(	"removeQuestions correctly removes the specified question.",
						list, catList);
	}
	
	/**
	 * Die Kategorie soll zu sich selbst gleich sein.
	 */
	@Test
	public void testSelfreferentialEquals() {
		Category c = new Category("ab", "abcd");
		
		//
		
		assertEquals("Category is equal to itself.", c, c);
	}
	
	/**
	 * Die Grundfunktionalität der {@link Category#equals(Object)
	 * equals}-Methode ist vorhanden.
	 */
	@Test
	public void testEquals() {
		Category c1 = new Category("ab", "abcd");
		Category c2 = new Category("ab", "abcd");
		
		//
		
		assertEquals(	"Category is equal to an exact twin version without questions.",
						c1, c2);
	}
	
	/**
	 * Die Fragen haben keine Auswirkung auf equals.
	 */
	@Test
	public void testEqualsIndifferentOfQuestions() {
		Category c1 = new Category(	"ab", "abcd",
									mock(Question.class, CALLS_REAL_METHODS));
		Category c2 = new Category("ab", "abcd");
		
		//
		
		assertEquals(	"equals does not care about questions.", c1, c2);
	}
	
	/**
	 * Eine Kategorie ist nicht gleich {@code null}.
	 */
	@Test
	public void testEqualsFalseWithNull() {
		Category c = new Category("ab", "abcd");
		
		//
		
		assertNotEquals("Category is not equal to null.", c, null);
	}
	
	/**
	 * Eine Kategorie ist nicht gleich zu einem normalen Objekt.
	 */
	@Test
	public void testEqualsFalseWithObject() {
		Category c = new Category("ab", "abcd");
		Object obj = new Object();
		
		//
		
		assertNotEquals("Category is not equal to a general Object.", c, obj);
	}
	
	/**
	 * Der Name der Kategorie muss gleich sein.
	 */
	@Test
	public void testEqualsFalseWithName() {
		Category c1 = new Category("ab", "abcd");
		Category c2 = new Category("bc", "abcd");
		
		//
		
		assertNotEquals("Category is not equal to a version with different name.",
						c1, c2);
	}
	
	/**
	 * Die Beschreibung der Kategorie muss gleich sein.
	 */
	@Test
	public void testEqualsFalseWithDescription() {
		Category c1 = new Category("ab", "abcd");
		Category c2 = new Category("ab", "dcba");
		
		//
		
		assertNotEquals("Category is not equal to a version with different description.",
						c1, c2);
	}
	
	/**
	 * Der Hashcode ist deterministisch.
	 */
	@Test
	public void testSelfreferentialHashCode() {
		Category c = new Category("ab", "abcd");
		
		int hash_1 = c.hashCode();
		int hash_2 = c.hashCode();
		
		assertEquals(	"Category has the same hashCode as itself.", hash_1,
						hash_2);
	}
	
	/**
	 * Bei inhaltlich gleichen Kategorien ist der Hashcode gleich.
	 */
	@Test
	public void testHashCode() {
		Category c1 = new Category("ab", "abcd");
		Category c2 = new Category("ab", "abcd");
		
		int hash1 = c1.hashCode();
		int hash2 = c2.hashCode();
		
		assertEquals(	"Category has the same hashCode as a twin version.", hash1,
						hash2);
	}
	
	/**
	 * Die Fragen haben keine Auswirkung auf den Hashcode.
	 */
	@Test
	public void testHashCodeIndifferentOfQuestions() {
		Category c1 = new Category(	"ab", "abcd",
									mock(Question.class, CALLS_REAL_METHODS));
		Category c2 = new Category("ab", "abcd");
		
		int hash1 = c1.hashCode();
		int hash2 = c2.hashCode();
		
		assertEquals(	"hashCode does not care about questions.", hash1, hash2);
	}
}
