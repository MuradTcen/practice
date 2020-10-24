package validation.util.validation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.testng.annotations.Test;
import validation.dto.PhoneDto;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class PhoneConstraintValidatorTest {
    private Validator validator;

    private final static String MESSAGE = "Должно быть в формате +<код страны><номер внутри страны>";

    @BeforeEach
    public void setUpClass() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    private static PhoneDto getPhoneDto(String name) {
        return new PhoneDto(name);
    }

    /**
     * Проверяем валидация "хороших" строк, используется параметризованные тесты: из метода
     * getGoodStringAndExpectedCountOfViolations возвращаются аргументы, которые передаются в аргументы name и countOfViolations
     *
     * @param name строка для создания dto
     * @param countOfViolations кол-во ошибок валидаций
     */
    @Test
    @ParameterizedTest
    @MethodSource("getGoodStringAndExpectedCountOfViolations")
    void validating_whenValidateGoodString_thenCountViolation(String name, int countOfViolations) {
        PhoneDto phoneDto = getPhoneDto(name);
        Set<ConstraintViolation<PhoneDto>> constraintViolations = validator.validate(phoneDto);

        assertThat(constraintViolations).hasSize(countOfViolations);
    }

    /**
     * Проверяем валидация "хороших" строк,
     * @param name строка для создания dto
     * @param countOfViolations кол-во ошибок валидаций
     */
    @Test
    @ParameterizedTest
    @MethodSource("getBadStringAndExpectedCountOfViolations")
    void validating_whenValidateBadString_thenCountViolation(String name, int countOfViolations) {
        PhoneDto phoneDto = getPhoneDto(name);
        Set<ConstraintViolation<PhoneDto>> constraintViolations = validator.validate(phoneDto);

        assertThat(constraintViolations).hasSize(countOfViolations);

        String actualMessage = constraintViolations.iterator().next().getMessageTemplate();
        String expectedMessage = MESSAGE;

        assertThat(actualMessage).isEqualTo(expectedMessage);
    }

    /**
     * Получаем аргументы для параметризованного теста, для "хороших" строк
     * @return список аргументов
     */
    private static Stream<Arguments> getGoodStringAndExpectedCountOfViolations() {
        return Stream.of(
                Arguments.of("+7<999><9999999>", 0),
                Arguments.of("+0<000><0000000>", 0)
        );
    }

    /**
     * Получаем аргументы для параметризованного теста, для "плохих" строк
     * @return список аргументов
     */
    private static Stream<Arguments> getBadStringAndExpectedCountOfViolations() {
        return Stream.of(
                Arguments.of("AF+7<909><0952716>", 1),
                Arguments.of("", 1),
                Arguments.of("+7<999><9999999>+7<999><9999999>", 1),
                Arguments.of("+ <999><9999999>", 1),
                Arguments.of(" ", 1)
        );
    }
}