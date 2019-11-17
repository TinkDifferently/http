package api;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

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
        responseCode=-1;
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


    public ApiConnection execute(int number){
        return execute();
    }

    private HttpURLConnection getConnector() throws IOException {
        HttpURLConnection connection= (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod(requestMethod);
        connection.setDoOutput(true);
        connection.setDoInput(true);
        return connection;
    }

    private void send(HttpURLConnection connection) throws IOException {
        OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
        writer.write(requestBody);
        writer.close();
    }

    private void extractBody(HttpURLConnection connection) throws IOException {
        InputStreamReader reader = new InputStreamReader(connection.getInputStream());
        BufferedReader r = new BufferedReader(reader);
        StringBuilder builder = new StringBuilder();
        r.lines().forEach(builder::append);
        responseBody=builder.toString();
        System.out.println(responseBody);
    }

    private void extractCookies(HttpURLConnection connection){

    }

    public ApiConnection execute(){
        if (isBad) {
            isExecuted = false;
            System.out.println("can not execute request");
            return this;
        }
        try {
            var connector=getConnector();
            try {
                send(connector);
                responseCode=connector.getResponseCode();
                extractBody(connector);
                extractCookies(connector);
                isExecuted=true;
            } catch (UnknownHostException e){
                System.out.println("Хост не существует");
                isExecuted=false;
            }
            catch (FileNotFoundException e){
                System.out.println("Ресурс не существует");
                isExecuted=true;
            }
        } catch (IOException e) {
            e.printStackTrace();
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
