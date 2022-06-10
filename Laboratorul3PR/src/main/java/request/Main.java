package request;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Main {
    private static String Link  = "https://github.com/MalairauMihail";



    private static Map<String, String> cookies;

    static {
        try {
            cookies = GitAuthentification.getCookies();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void getAllImagineLinks(){
        Document doc = null;
        try {
            doc = Jsoup.connect(Link).get();
            Elements links = doc.select("a[href]");
            Element link;

            for(int j=0;j<90;j++){
                link=links.get(j);
                System.out.println("a= " +link.attr("abs:href").toString() );
            }

        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    public static void searchS(String myName) {

        Document document = null;
        try {
            document = Jsoup.connect(Link)
                    .cookies(cookies)
                    .method(Connection.Method.GET)
                    .get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(document.text());
        String allText = document.text();
        if (allText.indexOf(myName) != -1) {
            System.out.println("Gaseste cuvintul" +
                    " la indexul" + allText.indexOf(myName));
        } else {
            System.out.println("NU il pot gasi " + myName);
        }

        if (allText.contains(myName)) {
            System.out.println("Ti-am gasit numele!");
        }
    }
    public static String headRequestJava() throws IOException {
        Connection.Response resp = Jsoup.connect(Link).method(Connection.Method.HEAD).cookies(cookies).execute();
        return resp.contentType();
    }

    public static Connection.Response optionsRequestJava() throws IOException {
        Connection.Response resp = Jsoup.connect("https://usm.md").method(Connection.Method.OPTIONS).execute();
        return resp;
    }


    public static void main(String[] args) throws Exception {
        searchS("mihai");
        getAllImagineLinks();
/*        for (Map.Entry<String, List<String>> header:optionsRequestJava().entrySet()) {
            System.out.println(header.getKey());
            for (String field:header.getValue()) {
                System.out.println(field);

            }

        }*/
        System.out.println(optionsRequestJava().multiHeaders());

    }
}
