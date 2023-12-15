package wilmer.practica2.wmscConsumidor;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.*;
import wilmer.practica2.controlador.wmscProductoControler;
import wilmer.practica2.modelos.WmscProducto;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class WmscRestConsumer {
    public void getProductAsJson() {
        RestTemplate restTemplate = new RestTemplate();
        String resourceUrl = "http://localhost:8080/products";
        // Fetch JSON response as String wrapped in ResponseEntity
        ResponseEntity<String> response = restTemplate.getForEntity(resourceUrl, String.class);
        String productsJson = response.getBody();
        System.out.println(productsJson);
    }
    public void getProducts() {
        RestTemplate restTemplate = new RestTemplate();
        String resourceUrl = "http://localhost:8080/products";
        // Fetch response as List wrapped in ResponseEntity
        var response = restTemplate.getForEntity(resourceUrl, List.class);
        var products = response.getBody();
        System.out.println(products + "\n");
    }

    public void wmscObtenerValorTotal() {
        RestTemplate restTemplate = new RestTemplate();
        String resourceUrl = "http://localhost:8080/products/total";
        // Fetch response as List wrapped in ResponseEntity
        var response = restTemplate.getForEntity(resourceUrl, String.class);
        var products = response.getBody();
        System.out.println(products + "\n");
    }
    public void getProductObjects() {

        RestTemplate restTemplate = new RestTemplate();
        String resourceUrl = "http://localhost:8080/products";
        // Fetching response as Object
        List<?> products = restTemplate.getForObject(resourceUrl, List.class);
        System.out.println(products);
    }

    public void createProduct() {
        try {
            RestTemplate restTemplate = new RestTemplate();
            String resourceUrl = "http://localhost:8080/products";
            // Create the request body by wrapping
            // the object in HttpEntity
            Date wmscFechaNac = new GregorianCalendar( 1993, 1, 20).getTime();

            WmscProducto product = new WmscProducto("Television", "Samsung", 1145.67, "S001", 16, "Tecnologia", wmscFechaNac);
            HttpEntity<WmscProducto> request = new HttpEntity<>(product);
            String productCreateResponse = restTemplate.postForObject(resourceUrl, request, String.class);
            System.out.println(productCreateResponse);
        }catch (ResourceAccessException e){
            System.out.println("Error: No se puede acceder al recurso. Asegúrate de que el servidor esté en funcionamiento.");
        }catch (HttpClientErrorException | HttpServerErrorException e){
            System.out.println("Error HTTP: " + e.getStatusCode());
        }catch (Exception e){
            System.out.println("Se produjo un error: " + e.getMessage());
        }
    }

    public void createProductWithExchange() {
        try {
            RestTemplate restTemplate = new RestTemplate();
            String resourceUrl = "http://localhost:8080/products";
            Date wmscFechaNac = new Date(1993, 1, 20);
            HttpEntity<WmscProducto> request = new HttpEntity<>(new WmscProducto("Television", "Samsung",1145.67,"S001", 14, "M", wmscFechaNac));
            ResponseEntity<String> productCreateResponse = restTemplate.exchange(resourceUrl, HttpMethod.POST, request, String.class);
            System.out.println(productCreateResponse.getBody());
        } catch (ResourceAccessException e) {
            System.out.println("Error: No se puede acceder al recurso. Asegúrate de que el servidor esté en funcionamiento.");
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            System.out.println("Error HTTP: " + e.getStatusCode());
        } catch (Exception e) {
            System.out.println("Se produjo un error: " + e.getMessage());
        }
    }


    public void updateProductWithExchange() {
        RestTemplate restTemplate = new RestTemplate();
        String resourceUrl = "http://localhost:8080/products";
        // Create the request body by wrapping
        // the object in HttpEntity
        Date wmscFechaNac = new Date(1993, 1, 20);
        HttpEntity<WmscProducto> request = new HttpEntity<>(new WmscProducto("Television", "Samsung",1145.67,"S001", 5, "Tecnologia", wmscFechaNac));
        // Send the PUT method as a method parameter
        restTemplate.exchange( resourceUrl, HttpMethod.PUT, request, Void.class);
        System.out.println("Producto actualizado con exito");
    }

    //eliminar un producto
    public void eliminarProducto() {
        RestTemplate restTemplate = new RestTemplate();
        String resourceUrl = "http://localhost:8080/products";
        // Create the request body by wrapping
        // the object in HttpEntity
        Date wmscFechaNac = new Date(1993, 1, 20);
        HttpEntity<WmscProducto> request = new HttpEntity<>(new WmscProducto("Television", "Samsung",1145.67,"S001", 5, "Tecnologia", wmscFechaNac));
        // Send the PUT method as a method parameter
        //restTemplate.exchange( resourceUrl, HttpMethod.DELETE, request, String.class);
        System.out.println(restTemplate.exchange( resourceUrl, HttpMethod.DELETE, request, String.class));
    }


    public void getProductasStream() {
        Date wmscFechaNac = new Date(1993, 1, 20);
        final WmscProducto fetchProductRequest = new WmscProducto("Television", "Samsung",1145.67,"S001", 14, "Tecnologia", wmscFechaNac);
        RestTemplate restTemplate = new RestTemplate();
        String resourceUrl = "http://localhost:8080/products";
        // Set HTTP headers in the request callback
        RequestCallback requestCallback = request -> {
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(request.getBody(), fetchProductRequest);
            request.getHeaders().setAccept(Arrays.asList(MediaType.APPLICATION_OCTET_STREAM, MediaType.ALL));
        };
        // Processing the response. Here we are extracting the
        // response and copying the file to a folder in the server.
        ResponseExtractor<Void> responseExtractor = response -> {
            Path path = Paths.get("./src/main/resources/product.json");
            Files.copy(response.getBody(), path);
            return null;
        };
        restTemplate.execute(resourceUrl,
                HttpMethod.GET,
                requestCallback,
                responseExtractor
        );
        System.out.println("Product.json creado correctamente");
    }

    public static void main(String[] args){
        try{
            WmscRestConsumer wmscConsumer = new WmscRestConsumer();
            System.out.println("---------------------Laboratorio 3-----------------------------------------");
            System.out.println("---------------------Lista de productos formato json-----------------------------------------");
            wmscConsumer.getProductAsJson();
            System.out.println("---------------------Eliminar producto-----------------------------------------");
            wmscConsumer.eliminarProducto();
            System.out.println("---------------------Lista de productos formato json-----------------------------------------");
            wmscConsumer.getProductAsJson();
            System.out.println("---------------------Lista de productos con el valor total de los productos-----------------");
            wmscConsumer.wmscObtenerValorTotal();
            System.out.println("---------------------Crear producto----------------------------------------------------------");
            wmscConsumer.createProduct();
            System.out.println("---------------------Lista de productos formato lista con nuevo producto---------------------");
            wmscConsumer.getProducts();
            System.out.println("---------------------Actualizar Producto-----------------------------------------------------");
            wmscConsumer.updateProductWithExchange();
            System.out.println("---------------------Lista de productos formato objeto actualizados---------------------------");
            wmscConsumer.getProductObjects();
            System.out.println("---------------------Crear producto con el metodo Exchange------------------------------------");
            wmscConsumer.createProductWithExchange();
            System.out.println("---------------------Lista de productos formato objeto con el producto creado-----------------");
            wmscConsumer.getProductObjects();
            System.out.println("---------------------Crear archivo json con el producto creado-------------------------------");
            wmscConsumer.getProductasStream();

        }catch(Exception e){
            System.out.println(e.getMessage());
        }

    }

}
