package api;

import org.junit.jupiter.api.Test;

public class ApiTest {
    @Test
    public void testA(){
        new ApiConnection()
            .withUrl("https://www.kpsfsdf.ru/")
            .withBody("body")
            .withMethod(HttpMethod.GET)
            .execute()
            .withUrl("https://www.kp.ru/")
            .withMethod(HttpMethod.GET)
            .execute()
            .checkCode(200);
    }
}
