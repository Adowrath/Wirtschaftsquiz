package ch.bbbaden.idpa.bru_eap_mey.quiz.model;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.mockito.Mockito.mock;


import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;


import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;


import ch.bbbaden.idpa.bru_eap_mey.quiz.model.question.Question;

/**
 * Die Tests für die Kategorien.
 */
@SuppressWarnings({"boxing"})
public final class CategoryTest {
	
	/**
	 * Der ErrorCollector.
	 */
	@Rule
	public ErrorCollector now = new ErrorCollector();
	
	/**
	 * Überprüft den Varargs-Konstruktor ohne Argumente.
	 */
	@Test
	public void testCategoryEmptyVarargs() {
		List<Question<?>> list = Collections.emptyList();
		
		Category c = new Category("a", "b");
		List<Question<?>> catList = c.getQuestions();
		
		this.now.checkThat(	"Varargs constructor saves the questions correctly with no questions.",
							catList, is(equalTo(list)));
	}
	
	/**
	 * Überprüft den Varags-Konstruktor mit Argumenten.
	 */
	@Test
	public void testCategoryVarargs() {
		Question<?> q1 = mock(Question.class);
		Question<?> q2 = mock(Question.class);
		Question<?> q3 = mock(Question.class);
		List<Question<?>> origList = Arrays.asList(q1, q2, q3);
		
		Category c = new Category("a", "b", q1, q2, q3);
		List<Question<?>> catList = c.getQuestions();
		
		this.now.checkThat(	"Varargs constructor saves correctly.", catList,
							is(equalTo(origList)));
	}
	
	/**
	 * Überprüft den Listen-Konstruktor.
	 */
	@Test
	public void testCategoryList() {
		Question<?> q1 = mock(Question.class);
		Question<?> q2 = mock(Question.class);
		Question<?> q3 = mock(Question.class);
		List<Question<?>> origList = Arrays.asList(q1, q2, q3);
		
		Category c = new Category("a", "b", origList);
		List<Question<?>> catList = c.getQuestions();
		
		this.now.checkThat(	"List constructor saves correctly.", catList,
							is(equalTo(origList)));
	}
	
	/**
	 * Überprüft den Stream-Konstruktor.
	 */
	@Test
	public void testCategoryStream() {
		Question<?> q1 = mock(Question.class);
		Question<?> q2 = mock(Question.class);
		Question<?> q3 = mock(Question.class);
		List<Question<?>> origList = Arrays.asList(q1, q2, q3);
		Stream<Question<?>> stream = Stream.of(q1, q2, q3);
		
		Category c = new Category("a", "b", stream);
		List<Question<?>> catList = c.getQuestions();
		
		this.now.checkThat(	"Stream constructor saves correctly.", catList,
							is(equalTo(origList)));
	}
	
	/**
	 * Überprüft, dass der Konstruktor die Beschreibung richtig
	 * speichert.
	 */
	@Test
	public void testGetDescription() {
		Category c = new Category("a", "b");
		
		String currentDesc = c.getDescription();
		
		this.now.checkThat(	"Constructor saves description correctly.",
							currentDesc, is(equalTo("b")));
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
		
		this.now.checkThat(	"setDescription changes description correctly.",
							currentDesc, is(equalTo("c")));
	}
	
	/**
	 * Überprüft, dass der Konstruktor den Namen richtig speichert.
	 */
	@Test
	public void testGetName() {
		Category c = new Category("a", "b");
		
		String currentName = c.getName();
		
		this.now.checkThat(	"Constructor saves name correctly.", currentName,
							is(equalTo("a")));
	}
	
	/**
	 * Überprüft, dass der Setter den Namen richtig überschreibt.
	 */
	@Test
	public void testSetName() {
		Category c = new Category("a", "b");
		
		c.setName("c");
		String currentName = c.getName();
		
		this.now.checkThat(	"setName changes name correctly.", currentName,
							is(equalTo("c")));
	}
	
	/**
	 * Überprüft, dass die Anzeige von Name und Fragenzahl bei keinen
	 * Fragen richtig funktioniert.
	 */
	@Test
	public void testGetNameAndCountNoQuestions() {
		Category c = new Category("a", "b");
		
		String nameAndCount = c.getNameAndCount();
		
		this.now.checkThat(	"getNameAndCount returns correctly without any questions.",
							nameAndCount, is(equalTo("a (0)")));
	}
	
	/**
	 * Überprüft, dass die Anzeige von Name und Fragenzahl bei einer
	 * Frage richtig funktioniert.
	 */
	@Test
	public void testGetNameAndCountWithQuestions() {
		Question<?> q = mock(Question.class);
		Category c = new Category("a", "b", q);
		
		String nameAndCount = c.getNameAndCount();
		
		this.now.checkThat(	"getNameAndCount returns correctly without any questions.",
							nameAndCount, is(equalTo("a (1)")));
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
		
		this.now.checkThat(	"getNameAndCount returns correctly without any questions.",
							nameAndCount, is(equalTo("c (0)")));
	}
	
	/**
	 * Schaut, dass die Frage aus der Liste der registrierten Fragen
	 * gelöscht wird.
	 */
	@Test
	public void testRemoveQuestion() {
		Question<?> q = mock(Question.class);
		Category c = new Category("a", "b", q);
		List<Question<?>> list = Collections.emptyList();
		
		c.removeQuestion(q);
		List<Question<?>> catList = c.getQuestions();
		
		this.now.checkThat(	"removeQuestions correctly removes the specified question.",
							catList, is(equalTo(list)));
	}
	
	/**
	 * Die Kategorie soll zu sich selbst gleich sein.
	 */
	@Test
	public void testSelfreferentialEquals() {
		Category c = new Category("ab", "abcd");
		
		//
		
		this.now.checkThat("Category is equal to itself.", c, is(equalTo(c)));
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
		
		this.now.checkThat(	"Category is equal to an exact twin version without questions.",
							c1, is(equalTo(c2)));
	}
	
	/**
	 * Die Fragen haben keine Auswirkung auf equals.
	 */
	@Test
	public void testEqualsIndifferentOfQuestions() {
		Category c1 = new Category("ab", "abcd", mock(Question.class));
		Category c2 = new Category("ab", "abcd");
		
		//
		
		this.now.checkThat(	"equals does not care about questions.", c1,
							is(equalTo(c2)));
	}
	
	/**
	 * Eine Kategorie ist nicht gleich {@code null}.
	 */
	@Test
	public void testEqualsFalseWithNull() {
		Category c = new Category("ab", "abcd");
		
		//
		
		this.now.checkThat(	"Category is not equal to null.", c,
							is(not(equalTo(null))));
	}
	
	/**
	 * Eine Kategorie ist nicht gleich zu einem normalen Objekt.
	 */
	@Test
	public void testEqualsFalseWithObject() {
		Category c = new Category("ab", "abcd");
		Object obj = new Object();
		
		//
		
		this.now.checkThat(	"Category is not equal to a general Object.", c,
							is(not(equalTo(obj))));
	}
	
	/**
	 * Der Name der Kategorie muss gleich sein.
	 */
	@Test
	public void testEqualsFalseWithName() {
		Category c1 = new Category("ab", "abcd");
		Category c2 = new Category("bc", "abcd");
		
		//
		
		this.now.checkThat(	"Category is not equal to a version with different name.",
							c1, is(not(equalTo(c2))));
	}
	
	/**
	 * Die Beschreibung der Kategorie muss gleich sein.
	 */
	@Test
	public void testEqualsFalseWithDescription() {
		Category c1 = new Category("ab", "abcd");
		Category c2 = new Category("ab", "dcba");
		
		//
		
		this.now.checkThat(	"Category is not equal to a version with different description.",
							c1, is(not(equalTo(c2))));
	}
	
	/**
	 * Der Hashcode ist deterministisch.
	 */
	@Test
	public void testSelfreferentialHashCode() {
		Category c = new Category("ab", "abcd");
		
		int hash_1 = c.hashCode();
		int hash_2 = c.hashCode();
		
		this.now.checkThat(	"Category has the same hashCode as itself.", hash_1,
							is(equalTo(hash_2)));
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
		
		this.now.checkThat(	"Category has the same hashCode as a twin version.",
							hash1, is(equalTo(hash2)));
	}
	
	/**
	 * Die Fragen haben keine Auswirkung auf den Hashcode.
	 */
	@Test
	public void testHashCodeIndifferentOfQuestions() {
		Category c1 = new Category("ab", "abcd", mock(Question.class));
		Category c2 = new Category("ab", "abcd");
		
		int hash1 = c1.hashCode();
		int hash2 = c2.hashCode();
		
		this.now.checkThat(	"hashCode does not care about questions.", hash1,
							is(equalTo(hash2)));
	}
}
