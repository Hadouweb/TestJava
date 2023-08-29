package com.oc.testing;

import static org.assertj.core.api.Assertions.assertThat;

import java.text.MessageFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.Set;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

class CalculatorTest {

	private Calculator calculatorUnderTest;
	private static Instant startedAt;

	@BeforeAll
	public static void initStartingTime() {
		System.out.println("Appel avant tous les tests");
		startedAt = Instant.now();
	}

	@AfterAll
	public static void showTestDuration() {
		System.out.println("Appel après tous les tests");
		Instant endedAt = Instant.now();
		long duration = Duration.between(startedAt, endedAt).toMillis();
		System.out.println(MessageFormat.format("Durée des tests : {0} ms", duration));
	}

	@BeforeEach
	public void initCalculator() {
		System.out.println("Appel avant chaque test");
		calculatorUnderTest = new Calculator();
	}

	@AfterEach
	public void undefCalculator() {
		System.out.println("Appel après chaque test");
		calculatorUnderTest = null;
	}

	@Test
	void testAddTwoPositiveNumbers() {
		// ARRANGE
		int a = 2;
		int b = 3;

		// ACT
		int somme = calculatorUnderTest.add(a, b);

		// ASSERT
		assertThat(somme).isEqualTo(5);
	}

	@Test
	void testMultiplyTwoPositiveNumbers() {
		// ARRANGE
		int a = 2;
		int b = 3;

		// ACT
		int somme = calculatorUnderTest.multiply(a, b);

		// ASSERT
		assertThat(somme).isEqualTo(6);
	}

	@ParameterizedTest(name = "{0} x 0 doit être égal à 0")
	@ValueSource(ints = { 1, 2, 42, 1001, 5089 })
	public void multiply_shouldReturnZero_ofZeroWithMultipleIntegers(int arg) {
		int actualResult = calculatorUnderTest.multiply(arg, 0);
		assertThat(actualResult).isEqualTo(0);
	}

	@ParameterizedTest(name = "{0} + {1} doit être égal à {2}")
	@CsvSource({ "1,1,2", "2,3,5", "42,57,99", "1001,999,2000" })
	public void add_shouldReturnTheSum_ofMultipleIntegers(int arg1, int arg2, int expectResult) {
		int actualResult = calculatorUnderTest.add(arg1, arg2);
		assertThat(actualResult).isEqualTo(expectResult);
	}

	@Test
	@Timeout(1)
	public void longCalcul_shouldComputeInLessTha1Second() {
		calculatorUnderTest.longCalculation();
	}

	@Test
	public void listDigits_shouldReturnsTheListOfDigits_ofPositiveInteger() {
		// GIVEN
		int number = 95897;

		// WHEN
		Set<Integer> actualDigits = calculatorUnderTest.digitsSet(number);

		// THEN

		// JUnit
		// Set<Integer> expectedDigits = Stream.of(5, 7, 8,
		// 9).collect(Collectors.toSet());
		// assertEquals(expectedDigits, actualDigits);

		// AssertJ
		assertThat(actualDigits).containsExactlyInAnyOrder(5, 7, 8, 9);
	}

	@Test
	public void listDigits_shouldReturnsTheListOfDigits_ofNegativeInteger() {
		int number = -124432;
		Set<Integer> actualDigits = calculatorUnderTest.digitsSet(number);
		assertThat(actualDigits).containsExactlyInAnyOrder(1, 2, 3, 4);
	}

	@Test
	public void listDigits_shouldReturnsTheListOfZero_ofZero() {
		int number = 0;
		Set<Integer> actualDigits = calculatorUnderTest.digitsSet(number);
		assertThat(actualDigits).containsExactly(0);
	}
}
