package ua.com.alevel.facade.auth_registration.impl;

import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import ua.com.alevel.config.security.SecurityService;
import ua.com.alevel.facade.auth_registration.AuthValidatorFacade;
import ua.com.alevel.web.dto.request.register.AuthDto;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class AuthValidatorFacadeImpl implements AuthValidatorFacade {

    public static final Pattern emailPattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    private final SecurityService securityService;

    public AuthValidatorFacadeImpl(SecurityService securityService) {
        this.securityService = securityService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return AuthDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        AuthDto dto = (AuthDto) target;
        Matcher matcher = emailPattern.matcher(dto.getEmail());
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "NotEmpty");
        if (!matcher.find()) {
            errors.rejectValue("email", "Size.authForm.email");
        }

        if (securityService.existsByEmail(dto.getEmail())) {
            errors.rejectValue("email", "Duplicate.authForm.email");
        }
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
        if (dto.getPassword().length() < 8 || dto.getPassword().length() > 32) {
            errors.rejectValue("password", "Size.authForm.password");
        }
        if (!dto.getPasswordConfirm().equals(dto.getPassword())) {
            errors.rejectValue("passwordConfirm", "Diff.authForm.passwordConfirm");
        }
    }
}
