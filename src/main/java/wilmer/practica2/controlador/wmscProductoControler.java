package wilmer.practica2.controlador;

import jakarta.websocket.server.PathParam;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wilmer.practica2.modelos.WmscProducto;

import java.util.List;
import java.util.UUID;

@RestController
public class wmscProductoControler {
    private List<WmscProducto> products = List.of(
            new WmscProducto("Television", "Samsung",1145.67,"S001"),
            new WmscProducto("Washing Machine", "LG",114.67,"L001"),
            new WmscProducto("Laptop", "Apple",11453.67,"A001")
    );

    @GetMapping(value="/products/{id}", produces= MediaType.APPLICATION_XML_VALUE)
    public @ResponseBody WmscProducto fetchProducts(@PathParam("id") String productId){
        return products.get(1);
    }
    @GetMapping("/products")
    public List<WmscProducto> fetchProducts(){return products;}

    @PostMapping("/products")
    public ResponseEntity<?> createProduct(
            @RequestBody WmscProducto product){
        // Create product with ID;
        String productID = UUID.randomUUID().toString();
        product.setId(productID);
        products.add(product);
        return ResponseEntity.ok().body(
                "{\"productID\":\""+productID+"\"}");
    }
    @PutMapping("/products")
    public ResponseEntity<String> updateProduct(
        @RequestBody WmscProducto product){
        products.set(1, product);
        // Update product. Return success or failure without response body
        return ResponseEntity.ok().build();
    }
    @DeleteMapping("/products")
    public ResponseEntity<String> deleteProduct(@RequestBody WmscProducto product){
        products.remove(1);
        // Update product. Return success or failure without response body
        return ResponseEntity.ok().build();
    }
}
