package api;

import api.checks.RegexBodyChecker;
import api.checks.StandardCodeChecker;
import org.junit.jupiter.api.Test;

public class ApiTest {
    @Test
    public void testA(){
        new ApiConnection()
            .withUrl("https://www.kpsfsdf.ru/")
            .withCodeChecker()
            // Supplier<String>
            .withBodyProvider(new RandomBodyProvider(12))
            //
            .withMethod(HttpMethod.GET)
            //многопоточка
            .execute(1)
            .withBody("body")
            .withUrl("https://www.kp.ru/fsdfs")
            .withCodeChecker(StandardCodeChecker.STATUS_200)
            .withMethod(HttpMethod.GET)
            .execute(3)
            //codechecker
            //cookies
            .withUrl("https://www.kp.ru/")
            .withMethod(HttpMethod.GET)
            .withBodyChecker(new RegexBodyChecker("<a href=\"(http)|(https)://www.kp.ru/\""))
            .withCodeChecker(StandardCodeChecker.STATUS_200)
            .execute(7);
    }
}
