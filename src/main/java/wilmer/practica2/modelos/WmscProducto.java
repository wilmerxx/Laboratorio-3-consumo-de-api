package wilmer.practica2.modelos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class WmscProducto {
        private String id;
        private String name;
        private String brand;
        private Double price;
        private String sku;
        private int wmscCantidad;
        private String wmscTipo;
        //atributo de tipo fecha sin hora con formato yyyy-MM-dd
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        private Date wmscFechaFabricacion;
        public WmscProducto(String name, String brand, Double price, String sku, int wmscCantidad, String wmscTipo, Date wmscFechaFabricacion) {
            super();
            this.id="";
            this.name = name;
            this.brand = brand;
            this.price = price;
            this.sku = sku;
            this.wmscCantidad = wmscCantidad;
            this.wmscTipo = wmscTipo;
            this.wmscFechaFabricacion = wmscFechaFabricacion;
        }
}
