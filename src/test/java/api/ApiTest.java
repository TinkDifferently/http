package api;

import org.junit.jupiter.api.Test;

public class ApiTest {
    @Test
    public void testA(){
        new ApiConnection()
            .withUrl("https://www.kp.ru/")
            .withBody("body")
            .withMethod(HttpMethod.GET)
            .execute()
            .checkCode(200);
    }
}
