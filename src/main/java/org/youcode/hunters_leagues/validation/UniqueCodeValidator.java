package org.youcode.hunters_leagues.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.youcode.hunters_leagues.repository.CompetitionRepository;

public class UniqueCodeValidator implements ConstraintValidator<UniqueCode, String> {
    @Autowired
    private CompetitionRepository competitionRepository;

    @Override
    public void initialize(UniqueCode constraintAnnotation) {
    }


    @Override
    public boolean isValid(String code, ConstraintValidatorContext context) {
        return code != null && !competitionRepository.existsByCode(code);
    }
}
