package api;

import org.junit.jupiter.api.Test;

public class ApiTest {
    @Test
    public void testA(){
        new ApiConnection()
            .withUrl("https://www.kpsfsdf.ru/")
            // Supplier<String>
            .withBody("body")
            //
            .withMethod(HttpMethod.GET)
            //многопоточка
            .execute(9)
            .withUrl("https://www.kp.ru/fsdfs")
            .withMethod(HttpMethod.GET)
            .execute(3)
            //codechecker
            .checkCode(404)
            //cookies
            .withUrl("https://www.kp.ru/")
            .withMethod(HttpMethod.GET)
            .execute(7)
            .checkCode(200);
    }
}
