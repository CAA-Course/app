package ro.msg.learning.shop.dto;

import lombok.Data;
import ro.msg.learning.shop.entity.Order;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Data
public class OrderProductQuantityDTO {

    private Integer id;
    private Integer customerId;
    private String createdAt;
    private String addressCountry;
    private String addressCity;
    private String addressCounty;
    private String addressStreetAddress;
    private List<ProductAndQuantityDTO> productAndQuantityDTOList = new ArrayList<>();

    public static OrderProductQuantityDTO ofEntity(Order order, List<ProductAndQuantityDTO> productAndQuantityDTOList){

        OrderProductQuantityDTO orderProductQuantityDTO = new OrderProductQuantityDTO();
        orderProductQuantityDTO.setId(order.getId());
        orderProductQuantityDTO.setAddressCity(order.getAddressCity());
        orderProductQuantityDTO.setAddressCountry(order.getAddressCountry());
        orderProductQuantityDTO.setAddressStreetAddress(order.getAddressStreetAddress());
        orderProductQuantityDTO.setAddressCounty(order.getAddressCounty());
        orderProductQuantityDTO.setProductAndQuantityDTOList(productAndQuantityDTOList);
        orderProductQuantityDTO.setCustomerId(order.getCustomer().getId());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        orderProductQuantityDTO.setCreatedAt(order.getCreatedAt().format(formatter));

        return orderProductQuantityDTO;
    }
}
