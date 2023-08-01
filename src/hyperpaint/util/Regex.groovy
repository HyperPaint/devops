package hyperpaint.util

import java.util.regex.Matcher
import java.util.regex.Pattern
import java.util.regex.PatternSyntaxException

final class Regex {
    private Regex() { }

    private static Matcher getMatcher(String text, String regex) {
        Pattern pattern = null
        try {
            pattern = Pattern.compile(regex)
        } catch (PatternSyntaxException e) {
            Shell.error("Ошибка при компиляции регулярного выражения '${regex}'\n${e.toString()}")
        }
        return pattern.matcher(text)
    }

    static boolean find(String text, String regex) {
        Matcher matcher = getMatcher(text, regex)
        // Есть совпадения
        return matcher.find()
    }

    static String group(String text, String regex, int group = 1) {
        Matcher matcher = getMatcher(text, regex)
        // Есть совпадения
        matcher.find()
        try {
            return matcher.group(group)
        } catch (IllegalStateException e) {
            Shell.error("Не удалось найти совпадение по регулярному выражению '${regex}' среди текста '${text}'\n${e.toString()}")
        } catch (IndexOutOfBoundsException e) {
            Shell.error("Не удалось найти группу по регулярному выражению '${regex}' среди текста '${text}'\n${e.toString()}")
        }
        return null
    }

    static List<String> groupAll(String text, String regex, int group = 1) {
        Matcher matcher = getMatcher(text, regex)
        List<String> resultList = new ArrayList<>()
        // Есть совпадения
        while (matcher.find()) {
            // Есть группы
            if (matcher.groupCount()) {
                // Нет в списке
                String result = matcher.group(group)
                if (!resultList.contains(result)) {
                    resultList.add(result)
                }
            }
        }
        return resultList
    }
}
