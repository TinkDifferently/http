package api.checks;

import api.IBodyProvider;
import java.util.regex.Pattern;

public class RegexBodyChecker implements IBodyChecker {
    private Pattern pattern;
    @Override
    public void check(String responseBody) {
        if (pattern.matcher(responseBody).find()) {
            System.out.println("Ответ корректен");
        } else {
            System.out.println("Ответ некорректен");
        }
    }

    public RegexBodyChecker(String regex){
        this.pattern=Pattern.compile(regex);
    }
}
