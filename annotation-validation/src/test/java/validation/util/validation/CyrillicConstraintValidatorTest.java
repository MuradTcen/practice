package validation.util.validation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.testng.annotations.Test;
import validation.dto.CyrillicDto;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class CyrillicConstraintValidatorTest {
    private Validator validator;

    private final static String MESSAGE = "Only cyrillic, spaces and digits";

    @BeforeEach
    public void setUpClass() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    private static CyrillicDto getCyrillicDto(String name) {
        return new CyrillicDto(name);
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
        CyrillicDto cyrillicDto = getCyrillicDto(name);
        Set<ConstraintViolation<CyrillicDto>> constraintViolations = validator.validate(cyrillicDto);

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
        CyrillicDto cyrillicDto = getCyrillicDto(name);
        Set<ConstraintViolation<CyrillicDto>> constraintViolations = validator.validate(cyrillicDto);

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
                Arguments.of("123", 0),
                Arguments.of(" ", 0),
                Arguments.of("Ёё", 0),
                Arguments.of("Кириллица Ёё", 0)
        );
    }

    /**
     * Получаем аргументы для параметризованного теста, для "плохих" строк
     * @return список аргументов
     */
    private static Stream<Arguments> getBadStringAndExpectedCountOfViolations() {
        return Stream.of(
                Arguments.of("Latin", 1),
                Arguments.of("Latin ёёЁ", 1)
        );
    }
}