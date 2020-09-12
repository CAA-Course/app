package ro.msg.learning.shop.dto;

import lombok.Data;
import ro.msg.learning.shop.entity.OrderDetail;

@Data
public class ProductAndQuantityDTO {

    private Integer productId;
    private Integer productQuantity;

    public static ProductAndQuantityDTO ofEntity(OrderDetail orderDetail){

        ProductAndQuantityDTO productAndQuantityDTO = new ProductAndQuantityDTO();

        productAndQuantityDTO.setProductId(orderDetail.getOrderDetailId().getProductId());
        productAndQuantityDTO.setProductQuantity(orderDetail.getQuantity());

        return productAndQuantityDTO;
    }
}
