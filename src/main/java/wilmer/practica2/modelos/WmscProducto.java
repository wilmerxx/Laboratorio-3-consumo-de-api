package wilmer.practica2.modelos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;
@Data
@NoArgsConstructor
public class WmscProducto {
        public WmscProducto(String name, String brand, Double price, String sku) {
            super();
            id = UUID.randomUUID().toString();
            this.name = name;
            this.brand = brand;
            this.price = price;
            this.sku = sku;
        }
        private String id;
        private String name;
        private String brand;
        private Double price;
        private String sku;

}
