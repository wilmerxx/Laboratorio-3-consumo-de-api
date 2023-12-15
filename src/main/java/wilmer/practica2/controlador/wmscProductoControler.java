package wilmer.practica2.controlador;

import jakarta.websocket.server.PathParam;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wilmer.practica2.modelos.WmscProducto;

import java.util.*;

@RestController
public class wmscProductoControler {
    //formato con fecha sin hora
    Date date = new GregorianCalendar(2021, Calendar.JANUARY, 1).getTime();
    private List<WmscProducto> products = new ArrayList<>(List.of(
            new WmscProducto("Television", "Samsung",1145.67,"S001", 16, "Tecnologia", date),
            new WmscProducto("Washing Machine", "LG",114.67,"L001", 16, "Electrodomestico", date),
            new WmscProducto("Laptop", "Apple",11453.67,"A001", 16, "Tecnologia", date)
    ));

    @GetMapping(value="/products/{id}", produces= MediaType.APPLICATION_XML_VALUE)
    public @ResponseBody WmscProducto fetchProducts(@PathParam("id") String productId){
        return products.get(1);
    }
    @GetMapping("/products")
    public List<WmscProducto> fetchProducts(){return products;}

    @PostMapping("/products")
    public ResponseEntity<String> createProduct(@RequestBody WmscProducto product){
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
        //products.remove(1);
        for ( WmscProducto product1: products) {
            if(product1.getId().equals(product.getId())){
                products.remove(product1);
                return ResponseEntity.ok().body("Producto eliminado");
            }else{
                return ResponseEntity.ok().body("No se encontro el producto");
            }
        }
        // Update product. Return success or failure without response body
        return ResponseEntity.ok().build();
    }

    @GetMapping("/products/total")
    public ResponseEntity<String> wmscObtenerValorTotal(){
        List<String> wmscTotalProdcutos = new ArrayList<>();
        double wmscTotal = 0;
        for ( WmscProducto product: products) {
            wmscTotal = product.getPrice() * product.getWmscCantidad();
            wmscTotalProdcutos.add("El valor total del producto "+product.getName()+" es: "+wmscTotal);
        }
        return ResponseEntity.ok().body(wmscTotalProdcutos.toString());
    }
}
