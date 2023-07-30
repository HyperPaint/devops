package hyperpaint.util

import java.util.regex.Matcher

final class Regex {
    private Regex() { }

    static boolean find(String text, String regex) {
        Matcher matcher = (text =~ regex)
        return matcher.find()
    }

    static String group(String text, String regex, int group = 1) {
        try {
            Matcher matcher = (text =~ regex)
            matcher.find()
            return matcher.group(group)
        }
        catch (IllegalStateException ignored) {
            Shell.error("Не удалось найти совпадение по регулярному выражению '${regex}' среди текста '${text}'")
        }
        catch (IndexOutOfBoundsException ignored) {
            Shell.error("Не удалось найти группу по регулярному выражению '${regex}' среди текста '${text}'")
        }
        return null
    }
}
