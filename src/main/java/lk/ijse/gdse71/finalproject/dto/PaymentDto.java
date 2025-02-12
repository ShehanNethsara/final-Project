package lk.ijse.gdse71.finalproject.dto;

import lombok.*;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PaymentDto {

    private String pid;
    private int discount ;
    private Date date ;
}
