package com.josthi.web.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import com.josthi.web.constants.Constant;
import com.josthi.web.po.EmailDbBean;

class UtilsTest {

	private static final long ONE_DAY_MILLIS = 24L * 60 * 60 * 1000;

	private static String today() {
		return new SimpleDateFormat("YYMMdd").format(new Date());
	}

	@ParameterizedTest
	@CsvSource({ "ADMIN,AD", "AGENT,AG", "SUBAGENT,SA", "USER,RU", "BENUSER,BE" })
	void getNextCustomerIdUsesPrefixPerUserType(String userType, String prefix) {
		assertEquals(prefix + today() + "0007", Utils.getNextCustomerID(userType, 7));
	}

	@Test
	void getNextCustomerIdIsCaseInsensitiveAndPadsCounter() {
		assertEquals("RU" + today() + "0123", Utils.getNextCustomerID("user", 123));
	}

	@Test
	void getNextCustomerIdHasNoPrefixForUnknownType() {
		assertEquals(today() + "0001", Utils.getNextCustomerID("SOMETHING_ELSE", 1));
	}

	@ParameterizedTest
	@CsvSource({ "TICKET,TKT", "INCIDENT,INC" })
	void getNextTicketIdUsesPrefixPerRequestType(String requestType, String prefix) {
		assertEquals(prefix + today() + "0042", Utils.getNextTicketID(requestType, 42));
	}

	@ParameterizedTest
	@CsvSource({ "Basic,PBAS", "Silver,PSLV", "Gold,PGLD" })
	void getNextPlanInvoiceIdCombinesRequestAndPlanPrefix(String plan, String prefix) {
		assertEquals(prefix + today() + "0005", Utils.getNextPlanInvoiceID(Constant.REQUEST_PLAN, plan, 5));
	}

	@Test
	void emailBeanForPasswordRecoveryIsPopulatedForQueueing() {
		EmailDbBean bean = Utils.getEmailBeanForPasswordRecovery("user@example.com", "{\"k\":\"v\"}");

		assertEquals("user@example.com", bean.getSentTo());
		assertEquals("{\"k\":\"v\"}", bean.getJsonString());
		assertEquals("LOADED", bean.getEmailStatus());
		assertEquals("LOADED", bean.getEmailDelivaryStatus());
		assertNull(bean.getEmailSentAt());
	}

	@Test
	void emailBeanForServiceTicketAppendsTicketNumberToSubject() {
		EmailDbBean bean = Utils.getEmailBeanForServiceTicket("user@example.com", "TKT0001", "{}");

		assertTrue(bean.getSubject().endsWith(" : TKT0001"), bean.getSubject());
	}

	@Test
	void emailBeanForPlanInvoiceAppendsInvoiceNumberToSubject() {
		EmailDbBean bean = Utils.getEmailBeanForPlanInvoive("user@example.com", "PBAS0001", "{}");

		assertTrue(bean.getSubject().endsWith(" : PBAS0001"), bean.getSubject());
	}

	@Test
	void mapToStringSerialisesToJson() throws Exception {
		Map<String, String> map = new HashMap<>();
		map.put("name", "Susovan");

		assertEquals("{\"name\":\"Susovan\"}", Utils.mapToString(map));
	}

	@ParameterizedTest
	@CsvSource({ "sUSovaN,Susovan", "SUSOVAN,Susovan", "s,S" })
	void toCamelCaseCapitalisesFirstLetterOnly(String input, String expected) {
		assertEquals(expected, Utils.toCamelCase(input));
	}

	@Test
	void toCamelCaseReturnsEmptyAndNullInputUnchanged() {
		assertEquals("", Utils.toCamelCase(""));
		assertNull(Utils.toCamelCase(null));
	}

	@Test
	void convertToCamelCaseCapitalisesEveryWord() {
		assertEquals("Susovan Sankar Gumtya", Utils.convertToCamelCase("suSOvan SankAR gumTYa").trim());
	}

	@Test
	void convertToCamelCaseReturnsNullForNull() {
		assertNull(Utils.convertToCamelCase(null));
	}

	@Test
	void getQueryTimeReportsMillisecondsAndSeconds() {
		String message = Utils.getQueryTime("select", System.currentTimeMillis());

		assertTrue(message.startsWith("Execution time for :select is "), message);
		assertTrue(message.endsWith(" sec"), message);
	}

	@ParameterizedTest
	@CsvSource({ "0,0", "-5,0", "512,512 B", "1024,1 kB", "1536,1.5 kB", "1048576,1 MB" })
	void readableFileSizeFormatsBinaryUnits(long size, String expected) {
		assertEquals(expected, Utils.readableFileSize(size));
	}

	@Test
	void getBeneficiaryIdReplacesOnlyFirstUserPrefix() {
		assertEquals("BE2009110001", Utils.getBeneficiaryId("RU2009110001"));
		assertEquals("BERU", Utils.getBeneficiaryId("RURU"));
		assertEquals("", Utils.getBeneficiaryId(null));
	}

	@Test
	void getDobParsesTenCharacterDates() throws ParseException {
		Timestamp dob = Utils.getDob("01/31/1990");

		assertEquals("1990-01-31", new SimpleDateFormat("yyyy-MM-dd").format(new Date(dob.getTime())));
	}

	@ParameterizedTest
	@ValueSource(strings = { "1990-01-1", "", "  " })
	void getDobReturnsNullForUnusableInput(String value) throws ParseException {
		assertNull(Utils.getDob(value));
	}

	@Test
	void getDobReturnsNullForNull() throws ParseException {
		assertNull(Utils.getDob(null));
	}

	@Test
	void getRequestFulFillDateParsesIsoDates() throws ParseException {
		Timestamp fulfil = Utils.getRequestFulFillDate("2020-12-25");

		assertEquals("2020-12-25", new SimpleDateFormat("yyyy-MM-dd").format(new Date(fulfil.getTime())));
		assertNull(Utils.getRequestFulFillDate("2020-12-2"));
		assertNull(Utils.getRequestFulFillDate(null));
	}

	@Test
	void timestampToFormattedStringHonoursExplicitFormat() {
		Timestamp ts = Timestamp.valueOf("2020-12-25 10:15:00");

		assertEquals("2020-12-25", Utils.timestampToFormattedString(ts, "yyyy-MM-dd"));
	}

	@Test
	void getDateAdditionValueAddsDaysToNow() throws ParseException {
		long diff = Utils.getDateAdditionValue(3).getTime() - System.currentTimeMillis();

		assertTrue(diff > 2 * ONE_DAY_MILLIS && diff <= 3 * ONE_DAY_MILLIS + 1000, "diff was " + diff);
	}

	@Test
	void calculateAgeCountsWholeYears() throws ParseException {
		String dob = new SimpleDateFormat(Constant.DATE_FORMAT_FOR_DOB)
				.format(new Date(System.currentTimeMillis() - 3 * 365 * ONE_DAY_MILLIS));

		assertEquals("3years", Utils.calculateAge(dob));
	}

	@Test
	void calculateAgeReturnsEmptyForInvalidLength() throws ParseException {
		assertEquals("", Utils.calculateAge("01/01/90"));
		assertEquals("", Utils.calculateAge(null));
	}

	@Test
	void compareTwoTimeStampsReturnsMinutesBetween() {
		Timestamp older = Timestamp.valueOf("2020-12-25 10:00:00");
		Timestamp newer = Timestamp.valueOf("2020-12-25 12:30:00");

		assertEquals(150, Utils.compareTwoTimeStamps(newer, older));
		assertEquals(-150, Utils.compareTwoTimeStamps(older, newer));
	}

	@Test
	void getDayDiffReturnsWholeDaysBetween() {
		Timestamp older = Timestamp.valueOf("2020-12-20 10:00:00");
		Timestamp newer = Timestamp.valueOf("2020-12-25 10:00:00");

		assertEquals(5, Utils.getDayDiff(newer, older));
	}

	@Test
	void daysRemainingAndExpiryAreDerivedFromNow() {
		Timestamp future = new Timestamp(System.currentTimeMillis() + 10 * ONE_DAY_MILLIS);
		Timestamp past = new Timestamp(System.currentTimeMillis() - ONE_DAY_MILLIS);

		assertEquals(10, Utils.getDaysRemainingForPlanToExpire(future));
		assertFalse(Utils.isPlanExpired(future));
		assertTrue(Utils.isPlanExpired(past));
	}

	@ParameterizedTest
	@CsvSource({ "abcdefg1,true", "abcdefgh,false", "abcdef1,false", "abcdef1!,false" })
	void passwordLengthRequiresEightAlphanumericsWithADigit(String password, boolean expected) {
		assertEquals(expected, Utils.passwordLength(password));
	}

	@ParameterizedTest
	@CsvSource({ "Passw0rd!,true", "Pass0rd!,true", "password!,false", "Passw0rd,false", "Pw0rd!,false" })
	void passwordValidationRequiresLetterDigitSpecialAndLengthEight(String password, boolean expected) {
		assertEquals(expected, Utils.PasswordValidation(password));
	}

	@ParameterizedTest
	@CsvSource({ "500,'500.00'", "1500,'1,500.00'", "1500.5,'1,500.50'", "1234567,'1,234,567.00'" })
	void formattedCurrencyGroupsThousandsAndKeepsTwoDecimals(String value, String expected) {
		assertEquals(expected, Utils.formattedCurrency(value));
	}

	@Test
	void roundUpCurrencyRoundsToNearestInteger() {
		assertEquals("1500", Utils.roundUpCurrency(1499.5));
		assertEquals("1499", Utils.roundUpCurrency(1499.4));
	}

	@ParameterizedTest
	@CsvSource({ "1BEN,1", "2BEN,2", "3BEN,3" })
	void getBeneficiaryCountFromDropdownValReadsLeadingDigit(String dropdownValue, int expected) {
		assertEquals(expected, Utils.getBeneficiaryCountFromDropdownVal(dropdownValue));
	}

	@Test
	void getBeneficiaryCountFromDropdownValReturnsZeroForEmptyValues() {
		assertEquals(0, Utils.getBeneficiaryCountFromDropdownVal(""));
		assertEquals(0, Utils.getBeneficiaryCountFromDropdownVal(null));
	}

	@Test
	void getDoubleValueStripsThousandSeparators() {
		assertEquals(1500.0, Utils.getDoubleValue("1,500.00"));
		assertThrows(NumberFormatException.class, () -> Utils.getDoubleValue("abc"));
	}

	@Test
	void invoiceIdIsStrippedFromPlanAndTicketIds() {
		assertEquals("2009110001", Utils.getInvoiceIdFromPlan("PBAS2009110001"));
		assertEquals("PBAS", Utils.getInvoiceIdFromPlan("PBAS"));
		assertEquals("2009110001", Utils.getInvoiceIdFromTicket("TKT2009110001"));
		assertEquals("TKT", Utils.getInvoiceIdFromTicket("TKT"));
	}

	@ParameterizedTest
	@CsvSource({ "user@example.com,true", "not-an-email,false", "user@,false", "'',false" })
	void isValidEmailAddressDelegatesToInternetAddress(String email, boolean expected) {
		assertEquals(expected, Utils.isValidEmailAddress(email));
	}

	@Test
	void starRatingForWholeValueUsesFullAndMutedStars() {
		List<String> stars = Utils.CalculateStarRating(4f);

		assertEquals(5, stars.size());
		assertEquals(4, stars.stream().filter(Constant.FULL_STAR::equals).count());
		assertEquals(1, stars.stream().filter(Constant.MUTED_STAR::equals).count());
	}

	@Test
	void starRatingForFractionalValueAddsAHalfStar() {
		List<String> stars = Utils.CalculateStarRating(4.6f);

		assertEquals(5, stars.size());
		assertEquals(4, stars.stream().filter(Constant.FULL_STAR::equals).count());
		assertEquals(1, stars.stream().filter(Constant.HALF_STAR::equals).count());
	}

	@ParameterizedTest
	@ValueSource(floats = { 0f, 5.5f })
	void starRatingFallsBackToDefaultForOutOfRangeValues(float rating) {
		List<String> stars = Utils.CalculateStarRating(rating);

		assertEquals(3, stars.stream().filter(Constant.FULL_STAR::equals).count());
		assertEquals(1, stars.stream().filter(Constant.HALF_STAR::equals).count());
	}

	@Test
	void generateAccountUnlockUrlReturnsSiteUrl() throws Exception {
		assertEquals("https://josthi.com", Utils.generateAccountUnlockUrl("user@example.com"));
	}
}
