package validation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import validation.util.validation.phone.PhoneConstraint;

@Data
@AllArgsConstructor
public class PhoneDto {

    @PhoneConstraint
    private String name;
}
