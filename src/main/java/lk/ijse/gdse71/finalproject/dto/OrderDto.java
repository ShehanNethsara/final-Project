package lk.ijse.gdse71.finalproject.dto;

import lombok.*;

import java.sql.Date;
import java.util.ArrayList;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class OrderDto {

    private String orderId;
    private Date orderDate;
    private String CustomerId;
    private String paymentId;

    private ArrayList<ContainDto> getContainDtos;





}
