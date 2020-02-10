package ua.training.game.util;

import org.springframework.context.i18n.LocaleContextHolder;

import java.util.ResourceBundle;

public class ResourceBundleUtil {
    private static String APPEAL_STAGE = "appeal.stage.";
    private static String GAME_STATUS = "game.status.";
    private static String MESSAGES_BUNDLE_NAME = "locale/messages";
    private static UTF8Control utf8Control = new UTF8Control();

    public static ResourceBundle getBundle() {
        return ResourceBundle.getBundle(MESSAGES_BUNDLE_NAME, LocaleContextHolder.getLocale(), utf8Control);
    }


    public static String getBundleString(String message) {
        return getBundle().getString(message);
    }

    public static String getBundleStringForAppealStage(String appealStage) {
        return getBundle().getString(concatenateStrings(APPEAL_STAGE, appealStage));
    }

    private static String concatenateStrings(String... messages) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String v : messages) {
            stringBuilder = stringBuilder.append(v);
        }
        return stringBuilder.toString();
    }
}
