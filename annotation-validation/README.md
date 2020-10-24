#### Практическое задание

Навыки: Создание аннотаций, использование javax.validation.*, регулярок, gradle, junit5, ParameterizedTest, TDD

Ограничения: Java 8

1. Создать аннотацию ```@CyrillicConstraint```, которая будет валидировать текстовое поле String в CyrillicDTO (data transfer object),
затем запустить тесты ```java/validation/util/validation/CyrillicConstraintValidatorTest.java```

    т.е. создать интерфейс ```java/validation/util/validation/CyrillicConstraint.java``` и
     создать ```java/validation/util/validation/CyrillicConstraintValidator.java```, который будет реализовывать
     ```ConstraintValidator<CyrillicConstraint, String>```

     У интерфейса CyrillicConstraint значение для поля message взять из теста

     Эта аннотация декларирует проверку валидности строки, которая может содержать только кириллицу, пробелы, цифры


2. Сначала написать тесты, наподобие тестов ```java/validation/util/validation/CyrillicConstraintValidatorTest.java```, для проверки
ещё не созданной аннотации ```@PhoneConstraint``` для PhoneDto.

Запускаем тесты видим, что тесты красные, пишем аннотацию и валидатор, запускаем тесты и добиваемся, чтобы тесты стали зеленые.
То есть используем в чистом виде Test Driven Development (Red-Green-Refactor)

 Эта аннотация будет проверять, что строка соответствует телефонному номеру в формате "+d<ddd><ddddddd>", где d - цифра,
 например "+7<999><9999999>"

