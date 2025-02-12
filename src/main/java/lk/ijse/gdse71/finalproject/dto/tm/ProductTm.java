package lk.ijse.gdse71.finalproject.dto.tm;

import lk.ijse.gdse71.finalproject.dto.ProductDto;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class ProductTm extends ProductDto {
    private String id;
    private String name;
    private String price;
    private String qty;
}
