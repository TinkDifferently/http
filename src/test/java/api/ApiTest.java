package api;

import org.junit.jupiter.api.Test;

public class ApiTest {
    @Test
    public void testA(){
        new Connection().withUrl("https://www.kp.ru/")
            .withBody("body")
            .checkCode(200);
    }
}
