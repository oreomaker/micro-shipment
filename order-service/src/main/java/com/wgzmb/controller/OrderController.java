package com.wgzmb.controller;

import com.wgzmb._common.vo.OrderShipmentVO;
import com.wgzmb._common.vo.OrderVO;
import com.wgzmb.component.BaseResponse;
import com.wgzmb.component.UserInfo;
import com.wgzmb.service.ShipOrderService;
import com.wgzmb.service.UserFeignInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private ShipOrderService orderService;
    @Resource
    private UserFeignInterface userFeignInterface;

    @GetMapping("{id}")
    public BaseResponse getOrder(@PathVariable Integer id) {
        return BaseResponse.success(orderService.getOrder(id, Integer.valueOf(UserInfo.get("id"))));
    }

    @GetMapping("")
    public BaseResponse getOrders() {
        return BaseResponse.success(orderService.getOrders(Integer.valueOf(UserInfo.get("id"))));
    }

    @PostMapping("")
    public BaseResponse createOrder(@RequestBody OrderVO orderVO) {
        return orderService.createOrder(Integer.valueOf(UserInfo.get("id")),
                orderVO.getSender(),
                orderVO.getReceiver(),
                orderVO.getCargo());
    }

    @PostMapping("/order-price")
    public BaseResponse getOrderPrice(@RequestBody OrderVO orderVO) {
        return orderService.getOrderPrice(orderVO.getSender(),
                orderVO.getReceiver(),
                orderVO.getCargo());
    }

    @PostMapping("/shipments")
    public BaseResponse updateOrderShipment(@RequestBody OrderShipmentVO orderShipmentVO) {

        return orderService.updateOrderShipment(Integer.valueOf(UserInfo.get("id")),
                orderShipmentVO.getOrderId(),
                orderShipmentVO.getDescription(),
                orderShipmentVO.getType());
    }

    @PostMapping("/user-info")
    public BaseResponse info(@RequestHeader("Authorization") String token) {
        return userFeignInterface.info(token);
    }
}