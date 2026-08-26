package com.josthi.web.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.xml.transform.TransformerFactory;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

class SanitizeTest {

	@Test
	void pathManipulationNormalisesBackslashesAndTraversal() {
		assertEquals("/tmp/file.txt", Sanitize.pathManipulation("\\tmp\\file.txt"));
		assertEquals("/tmp/file.txt", Sanitize.pathManipulation("/tmp/sub/../file.txt"));
		assertNull(Sanitize.pathManipulation(null));
	}

	@Test
	void logForgingEscapesNewlinesAndTabs() {
		assertEquals("line1\\nline2\\tend", Sanitize.logForging("line1\nline2\tend"));
		assertNull(Sanitize.logForging(null));
	}

	@Test
	void logForgingSplunkKeepsQuotesUnescaped() {
		assertEquals("say \"hi\"\\nbye", Sanitize.logForgingSplunk("say \"hi\"\nbye"));
	}

	@ParameterizedTest
	@ValueSource(strings = { "", "  " })
	void logForgingSplunkReturnsEmptyStringForBlankish(String input) {
		assertEquals(input.isEmpty() ? "" : "  ", Sanitize.logForgingSplunk(input));
	}

	@Test
	void logForgingSplunkReturnsEmptyStringForNull() {
		assertEquals("", Sanitize.logForgingSplunk(null));
	}

	@Test
	void privacyViolationOnlyReplacesBackslashesWhenTreatedAsPath() {
		assertEquals("/tmp/file.txt", Sanitize.privacyViolation("\\tmp\\file.txt", true));
		assertEquals("plain-value", Sanitize.privacyViolation("plain-value", false));
		assertNull(Sanitize.privacyViolation(null, true));
	}

	@Test
	void serverSideRequestForgeryConvertsSeparatorsToUnix() {
		assertEquals("C:/tmp/file.txt", Sanitize.serverSideRequestForgery("C:\\tmp\\file.txt"));
		assertNull(Sanitize.serverSideRequestForgery(null));
	}

	@ParameterizedTest
	@ValueSource(strings = { "<script>alert(1)</script>", "javascript:void(0)", "1 || 1", "a--b", "x HAVING y",
			"DELETE FROM t", "EXEC sp", "DECLARE @x", "<img src=x>", "{}", "50%" })
	void isDateValidRejectsSuspiciousInput(String input) {
		assertFalse(new Sanitize().isDateValid(input));
	}

	@ParameterizedTest
	@ValueSource(strings = { "Susovan Gumtya", "user@example.com", "12/25/2020" })
	void isDateValidAcceptsPlainInput(String input) {
		assertTrue(new Sanitize().isDateValid(input));
	}

	@Test
	void isDateValidAcceptsNull() {
		assertTrue(new Sanitize().isDateValid(null));
	}

	@ParameterizedTest
	@ValueSource(strings = { "", "   " })
	void crossSiteScriptingReturnsBlankInputUnchanged(String input) {
		assertEquals(input, Sanitize.crossSiteScripting(input));
	}

	@Test
	void crossSiteScriptingReturnsNullForNull() {
		assertNull(Sanitize.crossSiteScripting(null));
	}

	/**
	 * Documents the current (broken) behaviour: the method dereferences its
	 * uninitialised local result variable instead of the request parameter.
	 */
	@Test
	void crossSiteScriptingThrowsForNonBlankInput() {
		assertThrows(NullPointerException.class, () -> Sanitize.crossSiteScripting("<script>alert(1)</script>"));
	}

	@ParameterizedTest
	@CsvSource({ "'ABC123','ABC123'", "'A-B_C.D','A-B_C.D'", "'a-b_c.d',' - _ . '", "'<script>','        '",
			"'{}','} '" })
	void crossSiteScriptingWithWhiteListedCharKeepsOnlyAllowedChars(String input, String expected) {
		assertEquals(expected, Sanitize.crossSiteScriptingWithWhiteListedChar(input));
	}

	@Test
	void crossSiteScriptingWithWhiteListedCharReturnsNullForNull() {
		assertNull(Sanitize.crossSiteScriptingWithWhiteListedChar(null));
	}

	@Test
	void passwordManagementNormalisesPathLikeValues() {
		assertEquals("/tmp/secret", Sanitize.passwordManagement("/tmp/./secret"));
		assertNull(Sanitize.passwordManagement(null));
	}

	@Test
	void systemInfoLeakExternalNormalisesWindowsSeparators() {
		assertEquals("/tmp/file.txt", Sanitize.systemInfoLeakExternal("\\tmp\\sub\\..\\file.txt"));
		assertNull(Sanitize.systemInfoLeakExternal(null));
	}

	@Test
	void trustedBoundaryViolationPassesThroughLettersDigitsAndPathChars() {
		assertEquals("Susovan/Gumtya.1,2:3", Sanitize.trustedBoundaryViolation("Susovan/Gumtya.1,2:3"));
		assertEquals("", Sanitize.trustedBoundaryViolation(null));
	}

	@Test
	void xmlExternalEntityInjectionDisablesExternalDtdAccess() {
		TransformerFactory factory = TransformerFactory.newInstance();

		Sanitize.xmlExternalEntityInjection(factory);

		assertEquals("", factory.getAttribute("http://javax.xml.XMLConstants/property/accessExternalDTD"));
	}

	@Test
	void getErrorStackReturnsEscapedStackTrace() {
		String stack = Sanitize.getErrorStack(new IllegalStateException("boom"));

		assertTrue(stack.contains("java.lang.IllegalStateException: boom"), stack);
		assertFalse(stack.contains("\n"), "stack trace newlines should be escaped");
	}
}
