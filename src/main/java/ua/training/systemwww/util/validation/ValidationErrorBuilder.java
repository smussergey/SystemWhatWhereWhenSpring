package ua.training.systemwww.util.validation;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import ua.training.systemwww.util.ResourceBundleUtil;
import ua.training.systemwww.util.UTF8Control;

import java.util.ResourceBundle;

public class ValidationErrorBuilder {
    private static String MESSAGES_BUNDLE_NAME = "messages";

    public static ValidationError fromBindingErrors(Errors errors) {
        ValidationError validationError = new ValidationError();
        for (ObjectError objectError : errors.getAllErrors()) {
            validationError.addToErrorsList(ResourceBundleUtil.getBundleString(objectError.getDefaultMessage()));

        }
        return validationError;
    }

    //TODO check it
    private static ResourceBundle getResourceBundleForCurrentLocale(UTF8Control utf8Control) {
        return ResourceBundle.getBundle(
                MESSAGES_BUNDLE_NAME, LocaleContextHolder.getLocale(), utf8Control);
    }

}

