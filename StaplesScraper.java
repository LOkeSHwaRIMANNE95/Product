package Test;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class StaplesScraper {
    public static void main(String[] args) throws IOException {
        String url = "https://www.staples.com/Computer-office-desks/cat_CL210795/663ea?icid=BTS:2020:STUDYSPACE:DESKS";
        Document document = Jsoup.connect(url).get();
        
        Elements productElements = document.select("div.sku-details");
        List<Product> products = new ArrayList<>();

        for (Element productElement : productElements) {
            String name = productElement.select("a.sku-name").text();
            String brand = productElement.select("div.sku-brand").text();
            String sku = productElement.select("div.sku-model").text();
            String price = productElement.select("span.price-reg").text();
            String rating = productElement.select("div.rating-stars").attr("data-rating-value");

            products.add(new Product(name, brand, sku, price, rating));
        }

        FileWriter writer = new FileWriter("staples_products.csv");
        writer.write("Name,Brand,SKU,Price,Rating\n");

        int count = 0;
        for (Product product : products) {
            if (count == 10) {
                break;
            }
            writer.write(product.getName() + "," + product.getBrand() + "," + product.getSku() + "," + product.getPrice() + "," + product.getRating() + "\n");
            count++;
        }

        writer.close();
        System.out.println("Data has been exported to staples_products.csv");
    }

    private static class Product {
        private String name;
        private String brand;
        private String sku;
        private String price;
        private String rating;

        public Product(String name, String brand, String sku, String price, String rating) {
            this.name = name;
            this.brand = brand;
            this.sku = sku;
            this.price = price;
            this.rating = rating;
        }

        public String getName() {
            return name;
        }

        public String getBrand() {
            return brand;
        }

        public String getSku() {
            return sku;
        }

        public String getPrice() {
            return price;
        }

        public String getRating() {
            return rating;
        }
    }
}
