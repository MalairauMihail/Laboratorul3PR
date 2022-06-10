package request;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ImageDownloader {

    private static String IMAGE_DESTINATION_FOLDER = "src\\\\main\\\\resources\\\\images";

    public static void main(String[] args) throws IOException {

        String strURL = "https://services.github.com/";

        //ne conectam la web site si luam documentul
        Document document = Jsoup
                .connect(strURL)
                .userAgent("Mozilla/5.0")
                .timeout(10 * 1000)
                .get();

        //selectam toate imaginile care sunt cu extensia img, sau cu tagul img
        Elements imageElements = document.select("img");


        ExecutorService exec = Executors.newFixedThreadPool(10);
        CountDownLatch latch = new CountDownLatch(5);

        //iterate over each image
        // parcurgem imaginile
        for(Element imageElement : imageElements){

            //ne convingem ca URL ul absolut foloseste abs: prefix
            String strImageURL = imageElement.attr("abs:src");

            //descarcam imaginile
            downloadImage(strImageURL);

        }

    }

    private static void downloadImage(String strImageURL){

        // luam numele la file in path imaginii
        String strImageName =
                strImageURL.substring( strImageURL.lastIndexOf("/") + 1 );

        System.out.println("Salvam: " + strImageName + ", din: " + strImageURL);

        try {

            // deschidem stream din URL
            URL urlImage = new URL(strImageURL);
            InputStream in = urlImage.openStream();

            byte[] buffer = new byte[4096];
            int n = -1;

            OutputStream os =
                    new FileOutputStream( IMAGE_DESTINATION_FOLDER + "/" + strImageName );

            //scriem octeti in fluxul de iesire
            while ( (n = in.read(buffer)) != -1 ){
                os.write(buffer, 0, n);
            }

            //inchidem stream
            os.close();

            System.out.println("Imaginile au fost salvate");

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
