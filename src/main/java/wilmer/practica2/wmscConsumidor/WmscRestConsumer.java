package wilmer.practica2.wmscConsumidor;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;
import wilmer.practica2.modelos.WmscProducto;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class WmscRestConsumer {

    public void getProductAsJson() {
        RestTemplate restTemplate = new RestTemplate();
        String resourceUrl
                = "http://localhost:8080/products";
        // Fetch JSON response as String wrapped in ResponseEntity
        ResponseEntity<String> response
                = restTemplate.getForEntity(resourceUrl, String.class);
        String productsJson = response.getBody();
        System.out.println(productsJson);
    }

    public void getProducts() {
        RestTemplate restTemplate = new RestTemplate();
        String resourceUrl = "http://localhost:8080/products";
        // Fetch response as List wrapped in ResponseEntity
        ResponseEntity<List> response
                = restTemplate.getForEntity(resourceUrl, List.class);

        List<WmscProducto> products = response.getBody();
        System.out.println(products);
    }

    public void createProduct() {
        RestTemplate restTemplate = new RestTemplate();
        String resourceUrl = "http://localhost:8080/products";
        // Create the request body by wrapping
        // the object in HttpEntity
        HttpEntity<WmscProducto> request = new HttpEntity<WmscProducto>(new WmscProducto("Television", "Samsung",1145.67,"S001"));
        // Send the request body in HttpEntity for HTTP POST request
        String productCreateResponse = restTemplate.postForObject(resourceUrl, request, String.class);

        System.out.println(productCreateResponse);
    }


    public void createProductWithExchange() {
        RestTemplate restTemplate = new RestTemplate();
        String resourceUrl
                = "http://localhost:8080/products";
        // Create the request body by wrapping
        // the object in HttpEntity
        HttpEntity<WmscProducto> request =
                new HttpEntity<WmscProducto>(
                        new WmscProducto("Television", "Samsung",1145.67,"S001"));
        ResponseEntity<String> productCreateResponse =
                restTemplate
                        .exchange(resourceUrl,
                                HttpMethod.POST,
                                request,
                                String.class);

        System.out.println(productCreateResponse);
    }

    public void updateProductWithExchange() {
        RestTemplate restTemplate = new RestTemplate();
        String resourceUrl
                = "http://localhost:8080/products";
        // Create the request body by wrapping
        // the object in HttpEntity
        HttpEntity<WmscProducto> request = new HttpEntity<WmscProducto>(
                new WmscProducto("Television", "Samsung",1145.67,"S001"));
        // Send the PUT method as a method parameter
        restTemplate.exchange(
                resourceUrl,
                HttpMethod.PUT,
                request,
                Void.class);
    }

    public void getProductasStream() {
        final WmscProducto fetchProductRequest =
                new WmscProducto("Television", "Samsung",1145.67,"S001");
        RestTemplate restTemplate = new RestTemplate();
        String resourceUrl
                = "http://localhost:8080/products";

        // Set HTTP headers in the request callback
        RequestCallback requestCallback = request -> {
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(request.getBody(),
                    fetchProductRequest);
            request.getHeaders()
                    .setAccept(Arrays.asList(
                            MediaType.APPLICATION_OCTET_STREAM,
                            MediaType.ALL));
        };
        // Processing the response. Here we are extracting the
        // response and copying the file to a folder in the server.
        ResponseExtractor<Void> responseExtractor = response -> {
            Path path = Paths.get("some/path");
            Files.copy(response.getBody(), path);
            return null;
        };
        restTemplate.execute(resourceUrl,
                HttpMethod.GET,
                requestCallback,
                responseExtractor );
    }

    public static void main(String[] args){
        WmscRestConsumer wmscConsumer = new WmscRestConsumer();
        wmscConsumer.getProducts();
        wmscConsumer.createProduct();
        wmscConsumer.getProducts();
    }

}
