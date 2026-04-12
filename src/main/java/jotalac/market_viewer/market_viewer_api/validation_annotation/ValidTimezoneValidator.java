package jotalac.market_viewer.market_viewer_api.validation_annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jotalac.market_viewer.market_viewer_api.util.TimezoneProvider;

public class ValidTimezoneValidator implements ConstraintValidator<ValidTimezone, String> {

    @Override
    public boolean isValid(String timezone, ConstraintValidatorContext context) {
        if (timezone == null) {
            return true;
        }
        return TimezoneProvider.getTimezones().containsKey(timezone);
    }

}
