package request;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


import java.io.IOException;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.util.HashMap;
import java.util.Map;


public class GitAuthentification {
    public static String USER_AGENT = "\"Mozilla/5.0 (Windows NT\" +\n" +
            "          \" 6.1; WOW64) AppleWebKit/535.2 (KHTML, like Gecko) Chrome/15.0.874.120 Safari/535.2\"";
    public static String loginFormUrl = "https://github.com/login";
    public static String loginActionUrl = "https://github.com/session";
    public static String username = "********";
    public static String password = "********";


    final static String authUser = "U-161822777585";
    final static String authPassword = "NEr6K19BV";

    public static Map<String, String> getCookies() throws IOException {

        HashMap<String, String> cookies = new HashMap<>();
        HashMap<String, String> formData = new HashMap<>();

        Authenticator.setDefault(
                new Authenticator() {
                    public PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(
                                authUser, authPassword.toCharArray());
                    }
                }
        );

        System.setProperty("http.proxyHost", "45.132.50.93");
        System.setProperty("http.proxyPort", "45785");


        Connection.Response loginForm = Jsoup.connect(loginFormUrl)
                .method(Connection.Method.GET)
                .userAgent(USER_AGENT)
                .execute();


        Document loginDoc = loginForm.parse(); // acesta este documentul care conține răspunsul html


        cookies.putAll(loginForm.cookies()); // save the cookies, this will be passed on to next request
       // Obtinem valoarea authenticity_token cu selectorul CSS pe care l-am salvat anterior
        String authToken = loginDoc.select("#login > div.auth-form-body.mt-3 > form > input[type=hidden]:nth-child(1)")
                .first()
                .attr("value");


        formData.put("commit", "Sign in");
        formData.put("utf8", "e2 9c 93");
        formData.put("login", username);
        formData.put("password", password);
        formData.put("authenticity_token", authToken);


        Connection.Response homePage = Jsoup.connect(loginActionUrl)
                .cookies(cookies)
                .data(formData)
                .method(Connection.Method.POST)
                .userAgent(USER_AGENT)
                .execute();
        return homePage.cookies();

    }
}
