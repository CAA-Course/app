package ro.msg.learning.shop.dto;

import lombok.Data;
import ro.msg.learning.shop.entity.Order;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class OrderProductQuantityDTO {

    private Integer id;
    private Integer customerId;
    private LocalDateTime createdAt;
    private String addressCountry;
    private String addressCity;
    private String addressCounty;
    private String addressStreetAddress;
    private List<ProductAndQuantityDTO> products = new ArrayList<>();

    public static OrderProductQuantityDTO ofEntity(Order order,
                                                   List<ProductAndQuantityDTO> productAndQuantityDTOList) {

        OrderProductQuantityDTO orderProductQuantityDTO = new OrderProductQuantityDTO();
        orderProductQuantityDTO.setId(order.getId());
        orderProductQuantityDTO.setAddressCity(order.getAddressCity());
        orderProductQuantityDTO.setAddressCountry(order.getAddressCountry());
        orderProductQuantityDTO.setAddressStreetAddress(
                order.getAddressStreetAddress());
        orderProductQuantityDTO.setAddressCounty(order.getAddressCounty());
        orderProductQuantityDTO.setProducts(productAndQuantityDTOList);
        orderProductQuantityDTO.setCustomerId(order.getCustomer()
                                                   .getId());

        orderProductQuantityDTO.setCreatedAt(LocalDateTime.now());

        return orderProductQuantityDTO;
    }
}
