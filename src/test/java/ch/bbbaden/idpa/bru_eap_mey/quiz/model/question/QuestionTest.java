package ch.bbbaden.idpa.bru_eap_mey.quiz.model.question;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.anyVararg;
import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.verifyNoMoreInteractions;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;


import java.lang.reflect.Field;
import java.util.Map;
import java.util.Set;


import org.jdom2.Element;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
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
	 * Der ErrorCollector.
	 */
	@Rule
	public ErrorCollector now = new ErrorCollector();
	
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
	public void clearTypes() throws NoSuchFieldException, SecurityException,
			IllegalArgumentException, IllegalAccessException {
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
		Question<?> dummy = mock(Question.class);
		Question.register(	"type", e -> null,
							() -> dummy);
		
		Question<?> q = Question.getDummy("type");
		
		this.now.checkThat("Correct dummy is returned.", q, is(equalTo(dummy)));
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
		Set<String> empty = Sets.newSet();
		
		Set<String> types = Question.getTypes();
		
		this.now.checkThat(	"The registered types should be empty by default.",
							types, is(equalTo(empty)));
	}
	
	/**
	 * Die registrierten Typen werden korrekt befüllt.
	 */
	@Test
	public void testGetTypes() {
		Set<String> expected = Sets.newSet("type");
		Question.register(	"type", e -> null,
							() -> mock(Question.class, CALLS_REAL_METHODS));
		
		Set<String> types = Question.getTypes();
		
		this.now.checkThat(	"The registered types accurately recored what was registered.",
							types, is(equalTo(expected)));
	}
	
	/**
	 * Überprüft, dass der Ladevorgang von einem {@link Element}
	 * richtig funktioniert.
	 */
	@Test
	public void testLoadFromElement() {
		Question<?> loaded = mock(Question.class, CALLS_REAL_METHODS);
		Question.register(	"aType", e -> loaded,
							() -> mock(Question.class, CALLS_REAL_METHODS));
		
		Question<?> actual = Question.loadFromElement(new Element("a")
				.setAttribute("type", "aType"));
		
		this.now.checkThat(	"Correct loaded Question is returned.", actual,
							is(equalTo(loaded)));
	}
	
	/**
	 * Überprüft, dass der Fehlerdialog angezeigt wird, wenn das
	 * Element keinen Typ hat.
	 */
	@Test
	public void testLoadFromElementNoText() {
		mockStatic(Util.class);
		
		Question<?> loaded = Question.loadFromElement(new Element("a"));
		
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
		this.now.checkThat(	"loadFromElement complains if type was not found.",
							loaded, is(nullValue()));
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
		this.now.checkThat(	"loadFromElement complains if the type was not registered.",
							loaded, is(nullValue()));
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
		
		this.now.checkSucceeds(() -> {
			verify(c1).addQuestion(q);
			return null;
		});
		this.now.checkSucceeds(() -> {
			verify(c1).removeQuestion(q);
			return null;
		});
		this.now.checkSucceeds(() -> {
			verifyNoMoreInteractions(c1);
			return null;
		});
		this.now.checkSucceeds(() -> {
			verify(c2).addQuestion(q);
			return null;
		});
		this.now.checkSucceeds(() -> {
			verifyNoMoreInteractions(c2);
			return null;
		});
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
		
		this.now.checkThat(	"getCategory works correctly.", assigned,
							is(equalTo(c)));
	}
	
	/**
	 * Testet, dass standardmässig keine Kategorie existiert.
	 */
	@Test
	public void testGetCategoryNotInitialized() {
		Question<?> q = mock(Question.class, CALLS_REAL_METHODS);
		
		Category c = q.getCategory();
		
		this.now.checkThat("There is no default category.", c, is(nullValue()));
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
		
		this.now.checkThat(	"Question text was saved correctly.", savedText,
							is(equalTo(question)));
	}
}
