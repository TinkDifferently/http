package api;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import sun.net.www.protocol.http.Handler;
import sun.net.www.protocol.http.HttpURLConnection;

public class ApiConnection {
    /*
     * request
     */
    private String url;
    private URL requestUrl;
    private String requestBody;
    private String requestMethod;
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
    //private HttpClient client;
    private boolean isBad;
    private boolean isExecuted;

    public ApiConnection(){
        requestHeaders=new HashMap<>();
        isBad=false;
        isExecuted=false;
        responseCode=200;
    }

    public ApiConnection withUrl(String url){
        this.url=url;
        try {
            requestUrl=new URL(url);
        } catch (MalformedURLException e) {
            System.out.println("bad url");
            isBad=true;
        }
        return this;
    }

    public ApiConnection withBody(String body){
        if (body==null)
            isBad=true;
        requestBody=body;
        return this;
    }

    public ApiConnection withMethod(HttpMethod method){
        this.requestMethod=method.name();
        return this;
    }

    public ApiConnection withHeader(String headerName, String headerValue){
        requestHeaders.put(headerName,headerValue);
        return this;
    }

    public ApiConnection execute(){
        try {
            URLConnection connection= new URL(url).openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            OutputStreamWriter writer=new OutputStreamWriter(connection.getOutputStream());
            writer.write(requestBody);
            writer.close();
            InputStreamReader reader=new InputStreamReader(connection.getInputStream());
            BufferedReader r=new BufferedReader(reader);
            StringBuilder builder=new StringBuilder();
            r.lines().forEach(builder::append);
            connection.getHeaderFields();
            System.out.println(builder.toString());


        } catch (IOException e) {
            e.printStackTrace();
        }
        if (!isBad)
            isExecuted = true;
        else {
            isExecuted=false;
            System.out.println("can not execute request");
        }
        return this;
    }

    public ApiConnection checkCode(int code){
        if (!isExecuted) {
            System.out.println("request was not executed");
            return this;
        }
        String msg=code==responseCode
            ? "it's ok"
            : "fail";
        System.out.println(msg);
        return this;
    }
}
