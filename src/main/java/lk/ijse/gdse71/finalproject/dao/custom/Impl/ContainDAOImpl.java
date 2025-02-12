package lk.ijse.gdse71.finalproject.dao.custom.Impl;

import lk.ijse.gdse71.finalproject.dto.ContainDto;
import lk.ijse.gdse71.finalproject.util.CrudUtil;

import java.sql.SQLException;
import java.util.ArrayList;



public class ContainDAOImpl {

    private final ProductDAOImpl productModel = new ProductDAOImpl();

    public boolean saveContain(ArrayList<ContainDto> containDtos) throws SQLException {

        for (ContainDto containDto : containDtos) {

            boolean isOrderDetailsSaved = saveOrderDetail(containDto);
            if (!isOrderDetailsSaved) {
                return false;

            }

            System.out.println("oder ditails save");
            boolean isItemUpdated = productModel.reduceQty(containDto);
            if (!isItemUpdated) {
                return false;
            }

            System.out.println("item updated");
        }
        return true;

    }

    private boolean saveOrderDetail(ContainDto containDto) throws SQLException {
        return CrudUtil.execute("INSERT INTO contain VALUES (?,?)",
                containDto.getOrderId(),
                containDto.getProductId()

                );
    }


}
