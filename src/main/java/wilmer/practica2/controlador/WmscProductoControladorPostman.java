package wilmer.practica2.controlador;

import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wilmer.practica2.modelos.WmscProducto;
import wilmer.practica2.servicios.WmscProductoServicio;

import java.util.*;

@RestController
public class WmscProductoControladorPostman {
    @Autowired
    private WmscProductoServicio wmscProductoServicio;

    @GetMapping(value="/wmscProducts/{id}", produces= MediaType.APPLICATION_XML_VALUE)
    public @ResponseBody WmscProducto fetchProducts(@PathParam("id") String productId){
        return wmscProductoServicio.wmscObtenerProducto(productId);
    }
    @GetMapping("/wmscProducts")
    public ResponseEntity<?> fetchProducts(){
        if(wmscProductoServicio.wmscProductoList().isEmpty()){
            Date date = new GregorianCalendar(0000, Calendar.JANUARY, 0).getTime();
            WmscProducto product = new WmscProducto("", "",00.00,"", 0, "", date);
            return ResponseEntity.ok().body(product);
        }else{
            return ResponseEntity.ok().body(wmscProductoServicio.wmscProductoList());
        }

    }

    @PostMapping("/wmscProducts")
    public ResponseEntity<?> createProduct(@RequestBody WmscProducto product){
        wmscProductoServicio.createProduct(product);
        return ResponseEntity.ok().body(product);
    }
    @PutMapping("/wmscProducts/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable(value = "id") String id, @RequestBody WmscProducto product){
        for ( WmscProducto product1: wmscProductoServicio.wmscProductoList()) {
            if(product1.getId().equals(id)){
                product.setId(id);
                wmscProductoServicio.updateProduct(product);
                return ResponseEntity.ok().body(product);

            }
        }
        return ResponseEntity.ok().body("No se encontro el producto");
    }
    @DeleteMapping("/wmscProducts/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable(value = "id") String id){
        for ( WmscProducto product1: wmscProductoServicio.wmscProductoList()) {
            if(product1.getId().equals(id)){
                wmscProductoServicio.deleteProduct(product1);
                return ResponseEntity.ok().body("Producto eliminado");
            }
        }
        return ResponseEntity.ok("No se encontro el producto");

    }

    @GetMapping("/wmscProducts/total")
    public ResponseEntity<?> wmscTotalProducts(){
        return ResponseEntity.ok().body(wmscProductoServicio.wmscObtenerValorTotal());
    }
}
