package lk.ijse.gdse71.finalproject.dto.tm;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class OrderTm {
    private String ProductId;
    private String ProductName;
    private int Quantity;
    private double Price;
    private String CustomerId;

}
