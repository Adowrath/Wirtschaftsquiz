package ch.bbbaden.idpa.bru_eap_mey.quiz.model.question;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.anyVararg;
import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;


import java.lang.reflect.Field;
import java.util.Map;
import java.util.Set;


import org.jdom2.Element;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.util.collections.Sets;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;


import ch.bbbaden.idpa.bru_eap_mey.quiz.Util;
import ch.bbbaden.idpa.bru_eap_mey.quiz.model.Category;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Util.class})
@SuppressWarnings({"static-method", "javadoc"})
public final class QuestionTest {
	
	@Before
	public void clearTypes() throws IllegalArgumentException,
			IllegalAccessException, NoSuchFieldException, SecurityException {
		Field f1 = Question.class.getDeclaredField("registeredLoaders");
		f1.setAccessible(true);
		((Map<?, ?>) f1.get(null)).clear();
		
		Field f2 = Question.class.getDeclaredField("dummy");
		f2.setAccessible(true);
		((Map<?, ?>) f2.get(null)).clear();
	}
	
	@Test
	public void testGetDummy() {
		Question.register(	"type", e -> null,
							() -> mock(Question.class, CALLS_REAL_METHODS));
		
		Question<?> q = Question.getDummy("type");
		
		assertNotNull("getDummy should return a non-null object.", q);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testGetDummyUnregistered() {
		//
		
		Question.getDummy("unregistered-type");
		
		fail("getDummy should throw IllegalArgumentException because type is not registered.");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testRegisterDouble() {
		//
		
		Question.register(	"type", e -> null,
							() -> mock(Question.class, CALLS_REAL_METHODS));
		Question.register(	"type", e -> null,
							() -> mock(Question.class, CALLS_REAL_METHODS));
		
		fail("register should throw IllegalArgumentException because the type is being registered twice.");
	}
	
	@Test
	public void testGetTypesEmpty() {
		//
		
		Set<String> types = Question.getTypes();
		Set<String> expected = Sets.newSet();
		
		assertEquals(	"The registered types should be empty by default.",
						expected, types);
	}
	
	@Test
	public void testGetTypes() {
		Question.register(	"type", e -> null,
							() -> mock(Question.class, CALLS_REAL_METHODS));
		
		Set<String> types = Question.getTypes();
		Set<String> expected = Sets.newSet("type");
		
		assertEquals(	"The registered types accurately recored what was registered.",
						expected, types);
	}
	
	@Test
	public void testLoadFromElement() {
		Question<?> q = mock(Question.class, CALLS_REAL_METHODS);
		Question.register(	"type", e -> q,
							() -> mock(Question.class, CALLS_REAL_METHODS));
		
		Question<?> loaded = Question
				.loadFromElement(new Element("a").setAttribute("type", "type"));
		
		assertEquals(	"loadFromElement uses correct registered type.", q,
						loaded);
	}
	
	@Test
	public void testLoadFromElementNoText() {
		mockStatic(Util.class);
		Question.register(	"type", e -> mock(Question.class, CALLS_REAL_METHODS),
							() -> mock(Question.class, CALLS_REAL_METHODS));
		
		Question<?> loaded = Question.loadFromElement(new Element("a"));
		
		verifyStatic();
		Util.showErrorExitOnNoOrClose(anyString(), anyString(), anyVararg());
		assertNull("loadFromElement complains if type was not found.", loaded);
	}
	
	@Test
	public void testLoadFromElementUnknownType() {
		mockStatic(Util.class);
		
		Question<?> loaded = Question
				.loadFromElement(new Element("a").setAttribute("type", "some"));
		
		verifyStatic();
		Util.showErrorExitOnNoOrClose(anyString(), anyString(), anyVararg());
		assertNull(	"loadFromElement complains if the type was not registered.",
					loaded);
	}
	
	@Test
	public void testChangeCategoryTwice() {
		Question<?> q = mock(Question.class, CALLS_REAL_METHODS);
		Category c1 = mock(Category.class);
		Category c2 = mock(Category.class);
		
		q.changeCategory(c1);
		q.changeCategory(c2);
		
		verify(c1).addQuestion(q);
		verify(c1).removeQuestion(q);
		verify(c2).addQuestion(q);
		verifyNoMoreInteractions(c1);
		verifyNoMoreInteractions(c2);
		
		assertTrue("Nothing failed.", true);
	}
	
	@Test
	public void testGetCategory() {
		Question<?> q = mock(Question.class, CALLS_REAL_METHODS);
		Category c = mock(Category.class);
		q.changeCategory(c);
		
		Category assigned = q.getCategory();
		
		assertEquals("getCategory works correctly.", c, assigned);
	}
	
	@Test
	public void testGetCategoryNotInitialized() {
		Question<?> q = mock(Question.class, CALLS_REAL_METHODS);
		
		Category c = q.getCategory();
		
		assertNull("There is no default category.", c);
	}
	
	@Test
	public void testSetGetQuestion() {
		Question<?> q = mock(Question.class, CALLS_REAL_METHODS);
		String question = "Some question";
		
		q.setQuestion(question);
		String savedText = q.getQuestion();
		
		assertEquals("Question text was saved correctly.", question, savedText);
	}
}
