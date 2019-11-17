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
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

public class ApiConnection {
    /*
     * request
     */
    private String url;
    private URL requestUrl;
    private IBodyProvider requestBodyProvider;
    private ICodeChecker codeChecker;
    private String requestMethod;
    private Map<String, String> requestHeaders;
    private Map<String, String> requestCookies;
    /*
     * response
     */
    private ThreadLocal<Integer> responseCode;
    private ThreadLocal<String> responseBody;
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

    private void badVarArg(Object... objects){

    }

    public ApiConnection withBody(String body){
        if (body==null)
            isBad=true;
        requestBodyProvider= () -> body;
        return this;
    }

    public ApiConnection withBodyProvider(IBodyProvider bodyProvider){
        if (bodyProvider==null)
            isBad=true;
        requestBodyProvider= bodyProvider;
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

    private HttpURLConnection getConnector() throws IOException {
        HttpURLConnection connection= (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod(requestMethod);
        connection.setDoOutput(true);
        connection.setDoInput(true);
        return connection;
    }

    private void send(HttpURLConnection connection) throws IOException {
        System.out.println(requestBodyProvider.provide());
        OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
        writer.write(requestBodyProvider.provide());
        writer.close();
    }

    private void extractBody(HttpURLConnection connection) throws IOException {
        InputStreamReader reader = new InputStreamReader(connection.getInputStream());
        BufferedReader r = new BufferedReader(reader);
        StringBuilder builder = new StringBuilder();
        r.lines().forEach(builder::append);
        responseBody.set(builder.toString());
        System.out.println(responseBody.get());
    }

    private void extractCookies(HttpURLConnection connection){

    }

    private Supplier<Thread> runProvider=()->new Thread(){
        @Override
        public void run(){
            if (isBad) {
                    isExecuted = false;
                    System.out.println("can not execute request");
                    return;
                }
                try {
                    responseBody=new ThreadLocal<>();
                    responseCode=new ThreadLocal<>();
                    var connector=getConnector();
                    try {
                        send(connector);
                        codeChecker.check(connector.getResponseCode());
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
        }
    };

    public ApiConnection execute(){
        runProvider.get().run();
        return this;
    }

    public ApiConnection execute(int number){
        Set<Thread> threads=new HashSet<>();
        for (int i=0;i<number;i++){
            threads.add(runProvider.get());
        }
        threads.forEach(Thread::start);
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return execute();
    }

    public ApiConnection withCodeChecker(){
        return withCodeChecker(new DefaultCodeChecker());
    }

    public ApiConnection withCodeChecker(ICodeChecker codeChecker){
        this.codeChecker=codeChecker;
        return this;
    }

    public ApiConnection withCodeChecker(StandardCodeChecker codeChecker){
        this.codeChecker=codeChecker.checker;
        return this;
    }

//    public ApiConnection checkCode(int code){
//        if (!isExecuted) {
//            System.out.println("request was not executed");
//            return this;
//        }
//        String msg=code==responseCode.get()
//            ? "it's ok"
//            : "fail";
//        System.out.println(msg);
//        return this;
//    }
}
