package wilmer.practica2.repositorio;
import org.springframework.stereotype.Repository;
import wilmer.practica2.modelos.WmscProducto;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Repository
public class WmscProductoRepositorio {
    private List<WmscProducto> products = new ArrayList<>();
    public WmscProducto wmscObtenerProducto( String productId){
        for ( WmscProducto product: products) {
            if(product.getId().equals(productId)){
                return product;
            }
        }
        return null;
    }
    public List<WmscProducto> wmscProductoList(){return products;}
    public void createProduct( WmscProducto product){
       if(!Objects.isNull(product)){
           product.setId(UUID.randomUUID().toString());
           products.add(product);
       }
    }
    public void updateProduct(WmscProducto product){
        WmscProducto product1 = wmscObtenerProducto(product.getId());
        if(!Objects.isNull(product1)){
            product1.setName(product.getName());
            product1.setBrand(product.getBrand());
            product1.setPrice(product.getPrice());
            product1.setSku(product.getSku());
            product1.setWmscCantidad(product.getWmscCantidad());
            product1.setWmscTipo(product.getWmscTipo());
            product1.setWmscFechaElavoracion(product.getWmscFechaElavoracion());
        }
    }
    public void deleteProduct( WmscProducto product){
        if(!Objects.isNull(product)){
            products.remove(product);
        }
    }
}
