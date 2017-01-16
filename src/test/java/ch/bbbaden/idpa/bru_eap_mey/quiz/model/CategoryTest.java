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

@SuppressWarnings({"static-method", "javadoc"})
public class CategoryTest {
	
	@Test
	public void testCategoryEmptyVarargs() {
		List<Question<?>> list = Collections.emptyList();
		
		Category c = new Category("a", "b");
		List<Question<?>> catList = c.getQuestions();
		
		assertEquals(	"Varargs constructor saves the questions correctly with no questions.",
						list, catList);
	}
	
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
	
	@Test
	public void testGetDescription() {
		Category c = new Category("a", "b");
		
		String currentDesc = c.getDescription();
		
		assertEquals(	"Constructor saves description correctly.", "b",
						currentDesc);
	}
	
	@Test
	public void testSetDescription() {
		Category c = new Category("a", "b");
		
		c.setDescription("c");
		String currentDesc = c.getDescription();
		
		assertEquals(	"setDescription changes description correctly.", "c",
						currentDesc);
	}
	
	@Test
	public void testGetName() {
		Category c = new Category("a", "b");
		
		String currentName = c.getName();
		
		assertEquals("Constructor saves name correctly.", "a", currentName);
	}
	
	@Test
	public void testSetName() {
		Category c = new Category("a", "b");
		
		c.setName("c");
		String currentName = c.getName();
		
		assertEquals("setName changes name correctly.", "c", currentName);
	}
	
	@Test
	public void testGetNameAndCountNoQuestions() {
		Category c = new Category("a", "b");
		
		String nameAndCount = c.getNameAndCount();
		
		assertEquals(	"getNameAndCount returns correctly without any questions.",
						"a (0)", nameAndCount);
	}
	
	@Test
	public void testGetNameAndCountWithQuestions() {
		Question<?> q = mock(Question.class, CALLS_REAL_METHODS);
		Category c = new Category("a", "b", q);
		
		String nameAndCount = c.getNameAndCount();
		
		assertEquals(	"getNameAndCount returns correctly without any questions.",
						"a (1)", nameAndCount);
	}
	
	@Test
	public void testGetNameAndCountWithQuestionsAfterSetName() {
		Category c = new Category("a", "b");
		
		c.setName("c");
		String nameAndCount = c.getNameAndCount();
		
		assertEquals(	"getNameAndCount returns correctly without any questions.",
						"c (0)", nameAndCount);
	}
	
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
	
	@Test
	public void testSelfreferentialEquals() {
		Category c = new Category("ab", "abcd");
		
		//
		
		assertEquals("Category is equal to itself.", c, c);
	}
	
	@Test
	public void testEquals() {
		Category c1 = new Category("ab", "abcd");
		Category c2 = new Category("ab", "abcd");
		
		//
		
		assertEquals(	"Category is equal to an exact twin version without questions.",
						c1, c2);
	}
	
	@Test
	public void testEqualsFalseWithNull() {
		Category c = new Category("ab", "abcd");
		
		//
		
		assertNotEquals("Category is not equal to null.", c, null);
	}
	
	@Test
	public void testEqualsFalseWithObject() {
		Category c = new Category("ab", "abcd");
		Object obj = new Object();
		
		//
		
		assertNotEquals("Category is not equal to a general Object.", c, obj);
	}
	
	@Test
	public void testEqualsFalseWithName() {
		Category c1 = new Category("ab", "abcd");
		Category c2 = new Category("bc", "abcd");
		
		//
		
		assertNotEquals("Category is not equal to a version with different name.",
						c1, c2);
	}
	
	@Test
	public void testEqualsFalseWithDescription() {
		Category c1 = new Category("ab", "abcd");
		Category c2 = new Category("ab", "dcba");
		
		//
		
		assertNotEquals("Category is not equal to a version with different description.",
						c1, c2);
	}
	
	@Test
	public void testSelfreferentialHashCode() {
		Category c = new Category("ab", "abcd");
		
		int hash_1 = c.hashCode();
		int hash_2 = c.hashCode();
		
		assertEquals(	"Category has the same hashCode as itself.", hash_1,
						hash_2);
	}
	
	@Test
	public void testHashCode() {
		Category c1 = new Category("ab", "abcd");
		Category c2 = new Category("ab", "abcd");
		
		int hash1 = c1.hashCode();
		int hash2 = c2.hashCode();
		
		assertEquals(	"Category has the same hashCode as a twin version.", hash1,
						hash2);
	}
	
	
}
