package regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.junit.jupiter.api.Test;

public class RegexTest {

    @Test
    public void regexTest(){
        String a="[nt=1;hk=2]{bc=4;rg=7}<vh=3;ml=2>";
        Matcher matcher= Pattern.compile("[\\],\\},>]").matcher(a);
        System.out.println(matcher.replaceAll("$0\n"));
        /*
        "// \" \"*/ /*""*/String b="";
    }
}
