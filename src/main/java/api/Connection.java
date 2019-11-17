package api;

import java.net.http.HttpClient;
import java.util.HashMap;
import java.util.Map;

public class Connection {
    /*
     * request
     */
    private String requestUrl;
    private String requestBody;
    private Map<String, String> requestHeaders;
    private Map<String, String> requestCookies;
    /*
     * response
     */
    private int responseCode;
    private String responseBody;
    private Map<String, String> responseHeaders;
    private Map<String, String> responseCookies;

    /*
     * internal
     */
    private HttpClient client;

    public Connection(){
        requestHeaders=new HashMap<>();
        responseCode=200;
    }

    public Connection withUrl(String url){
        requestUrl=url;
        return this;
    }

    public Connection withBody(String body){
        requestBody=body;
        return this;
    }

    public Connection withHeader(String headerName, String headerValue){
        requestHeaders.put(headerName,headerValue);
        return this;
    }

    public Connection checkCode(int code){
        String msg=code==responseCode
            ? "it's ok"
            : "fail";
        System.out.println(msg);
        return this;
    }
}
