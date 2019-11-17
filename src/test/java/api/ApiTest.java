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
            .execute(90)
            .withUrl("https://www.kp.ru/fsdfs")
            .withMethod(HttpMethod.GET)
            .execute(280)
            //codechecker
            .checkCode(404)
            //cookies
            .withUrl("https://www.kp.ru/")
            .withMethod(HttpMethod.GET)
            .execute()
            .checkCode(200);
    }
}
