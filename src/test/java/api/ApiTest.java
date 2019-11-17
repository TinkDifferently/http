package api;

import org.junit.jupiter.api.Test;

public class ApiTest {
    @Test
    public void testA(){
        new Connection().withUrl("url")
            .withBody("body")
            .checkCode(200);
    }
}
