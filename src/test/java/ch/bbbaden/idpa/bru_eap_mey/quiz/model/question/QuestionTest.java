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
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;


import ch.bbbaden.idpa.bru_eap_mey.quiz.Util;
import ch.bbbaden.idpa.bru_eap_mey.quiz.model.Category;

/**
 * Normale Tests für die Question-Klasse.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Util.class, Category.class})
@SuppressWarnings({"static-method"})
public final class QuestionTest {
	
	/**
	 * Leert die Maps der Ladern und Dummy-Erstellern.
	 * 
	 * @throws NoSuchFieldException
	 *         falls das Feld umbenannt wurde.
	 * @throws SecurityException
	 *         falls ein SecurityManager läuft, der den Zugriff
	 *         verweigert. (sollte nicht auftreten)
	 * @throws IllegalAccessException
	 *         falls der Zugriff auf das Feld verweigert wird (wird er
	 *         nicht, da erst {@link Field#setAccessible(boolean)
	 *         Field.setAccessible(true)} aufgerufen wird.
	 * @throws IllegalArgumentException
	 *         falls das Objekt keine Instanz der Klasse
	 *         {@link Question} wäre. (da es statische Felder sind,
	 *         tritt dies nie auf)
	 */
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
	
	/**
	 * Prüft, dass die Dummy-Funktion aufgerufen wurde.
	 */
	@Test
	public void testGetDummy() {
		Question.register(	"type", e -> null,
							() -> mock(Question.class, CALLS_REAL_METHODS));
		
		Question<?> q = Question.getDummy("type");
		
		assertNotNull("getDummy should return a non-null object.", q);
	}
	
	/**
	 * Wirft einen Fehler bei unbekanntem/nicht registriertem
	 * Fragetyp.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testGetDummyUnregistered() {
		//
		
		Question.getDummy("unregistered-type");
		
		fail("getDummy should throw IllegalArgumentException because type is not registered.");
	}
	
	/**
	 * Ein Fragetyp soll nicht doppelt registriert werden können.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testRegisterDouble() {
		//
		
		Question.register(	"type", e -> null,
							() -> mock(Question.class, CALLS_REAL_METHODS));
		Question.register(	"type", e -> null,
							() -> mock(Question.class, CALLS_REAL_METHODS));
		
		fail("register should throw IllegalArgumentException because the type is being registered twice.");
	}
	
	/**
	 * Normalerweise sind keine Typen registriert.
	 */
	@Test
	public void testGetTypesEmpty() {
		//
		
		Set<String> types = Question.getTypes();
		Set<String> expected = Sets.newSet();
		
		assertEquals(	"The registered types should be empty by default.",
						expected, types);
	}
	
	/**
	 * Die registrierten Typen werden korrekt befüllt.
	 */
	@Test
	public void testGetTypes() {
		Question.register(	"type", e -> null,
							() -> mock(Question.class, CALLS_REAL_METHODS));
		
		Set<String> types = Question.getTypes();
		Set<String> expected = Sets.newSet("type");
		
		assertEquals(	"The registered types accurately recored what was registered.",
						expected, types);
	}
	
	/**
	 * Überprüft, dass der Ladevorgang von einem {@link Element}
	 * richtig funktioniert.
	 */
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
	
	/**
	 * Überprüft, dass der Fehlerdialog angezeigt wird, wenn das
	 * Element keinen Typ hat.
	 */
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
	
	/**
	 * Überprüft, dass der Fehlerdialog angezeigt wird, wenn das
	 * Element einen unbekannten Typ hat.
	 */
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
	
	/**
	 * Überprüft, dass ein Kategorienwechsel die korrekten Methoden
	 * aufruft.
	 */
	@Test
	public void testChangeCategoryTwice() {
		Question<?> q = mock(Question.class, CALLS_REAL_METHODS);
		Category c1 = PowerMockito.mock(Category.class);
		Category c2 = PowerMockito.mock(Category.class);
		
		q.changeCategory(c1);
		q.changeCategory(c2);
		
		verify(c1).addQuestion(q);
		verify(c1).removeQuestion(q);
		verify(c2).addQuestion(q);
		verifyNoMoreInteractions(c1);
		verifyNoMoreInteractions(c2);
		
		assertTrue("Nothing failed.", true);
	}
	
	/**
	 * Testet den normalen Getter.
	 */
	@Test
	public void testGetCategory() {
		Question<?> q = mock(Question.class, CALLS_REAL_METHODS);
		Category c = PowerMockito.mock(Category.class);
		q.changeCategory(c);
		
		Category assigned = q.getCategory();
		
		assertEquals("getCategory works correctly.", c, assigned);
	}
	
	/**
	 * Testet, dass standardmässig keine Kategorie existiert.
	 */
	@Test
	public void testGetCategoryNotInitialized() {
		Question<?> q = mock(Question.class, CALLS_REAL_METHODS);
		
		Category c = q.getCategory();
		
		assertNull("There is no default category.", c);
	}
	
	/**
	 * Überprüft, dass der Setter die Frage richtig ändert.
	 */
	@Test
	public void testSetGetQuestion() {
		Question<?> q = mock(Question.class, CALLS_REAL_METHODS);
		String question = "Some question";
		
		q.setQuestion(question);
		String savedText = q.getQuestion();
		
		assertEquals("Question text was saved correctly.", question, savedText);
	}
}
