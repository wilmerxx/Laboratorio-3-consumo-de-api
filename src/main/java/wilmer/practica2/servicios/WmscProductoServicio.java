package wilmer.practica2.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wilmer.practica2.modelos.WmscProducto;
import wilmer.practica2.repositorio.WmscProductoRepositorio;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class WmscProductoServicio {
    @Autowired
    private WmscProductoRepositorio wmscProductoRepositorio;
    public WmscProducto wmscObtenerProducto(String productId){

        return wmscProductoRepositorio.wmscObtenerProducto(productId);
    }
    public List<WmscProducto> wmscProductoList(){return wmscProductoRepositorio.wmscProductoList();}
    public void createProduct( WmscProducto product){
        wmscProductoRepositorio.createProduct(product);
    }
    public void updateProduct(WmscProducto product){
        wmscProductoRepositorio.updateProduct(product);
    }
    public void deleteProduct( WmscProducto product){
        wmscProductoRepositorio.deleteProduct(product);
    }
    //Al momento de obtener la información de distintos tipos (lista o individual) obtener información adicional que sea el valor
    //total del producto.
    public List<String> wmscObtenerValorTotal(){
       List<String> wmscTotalProdcutos = new ArrayList<>();
       double wmscTotal = 0;
        for ( WmscProducto product: wmscProductoRepositorio.wmscProductoList()) {
           wmscTotal = product.getPrice() * product.getWmscCantidad();
              wmscTotalProdcutos.add("El valor total del producto "+product.getName()+" es: "+wmscTotal);
        }

        return wmscTotalProdcutos;

    }
}
