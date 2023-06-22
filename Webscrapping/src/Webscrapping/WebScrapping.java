package Webscrapping;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileWriter;
import java.io.IOException;

public class WebScrapping {
    public static void main(String[] args) {
        // Ruta del archivo CSV donde se guardará la información
        String csvFile = "productos_amazon.csv";

        try {
            // Conectar con la página de resultados de Amazon
            Document doc = Jsoup.connect("https://www.amazon.com/s?k=juegos").get();

            // Encontrar todos los elementos de resultados de búsqueda
            Elements results = doc.select("div[data-component-type='s-search-result']");

            // Crear un archivo CSV
            FileWriter writer = new FileWriter(csvFile);

            // Escribir encabezados en el archivo CSV
            writer.write("Título,Precio\n");

            // Recorrer los resultados y extraer el título y el precio de cada producto
            for (Element result : results) {
                Element titleElement = result.selectFirst("h2 a span");
                String title = titleElement.text();

                Element priceElement = result.selectFirst(".a-price-whole");
                String price = (priceElement != null) ? priceElement.text() + " €" : "N/A";

                // Escribir el producto en el archivo CSV solo si el título no está vacío
                if (!title.isEmpty()) {
                    writer.write(title + "," + price + "\n");
                }
            }

            // Cerrar el archivo CSV
            writer.close();

            System.out.println("Scraping completado. Los productos se han guardado en el archivo " + csvFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}



