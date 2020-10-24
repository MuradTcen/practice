package validation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import validation.util.validation.cyrillic.CyrillicConstraint;

@Data
@AllArgsConstructor
public class CyrillicDto {

    @CyrillicConstraint
    private String name;
}
