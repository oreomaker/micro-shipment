package com.wgzmb._common.vo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class OrderShipmentVO {
    public enum Type {
        ORDER, // 下单
        PROCESSING, // 揽收
        IN_TRANSIT, // 运输中
        DELIVERED // 已送达
    }
    @NotEmpty(message = "订单id不能为空")
    Integer orderId;
    @NotBlank(message = "描述不能为空")
    String description;
    @NotEmpty(message = "类型不能为空")
    Type type;
}
