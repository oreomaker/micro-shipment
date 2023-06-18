package com.wgzmb.service;

import cn.hutool.db.sql.Order;
import com.wgzmb._common.vo.OrderShipmentVO;
import com.wgzmb.component.BaseResponse;
import com.wgzmb.dao.CargoRepository;
import com.wgzmb.dao.OrderRepository;
import com.wgzmb.dao.ShipmentRepository;
import com.wgzmb.model.Cargo;
import com.wgzmb.model.ShipOrder;
import com.wgzmb.model.Shipment;
import com.wgzmb._common.vo.CargoVO;
import com.wgzmb._common.vo.OrderReceiverVO;
import com.wgzmb._common.vo.OrderSenderVO;
import com.wgzmb.model.Cargo;
import com.wgzmb.model.ShipOrder;
import com.wgzmb.model.Shipment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class ShipOrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CargoRepository cargoRepository;

    public BaseResponse createOrder(Integer userId, OrderSenderVO orderSenderVO, OrderReceiverVO orderReceiverVO, CargoVO cargoVO) {
        Cargo cargo = new Cargo();
        cargo.setData(cargoVO.getName(), cargoVO.getLength(), cargoVO.getWidth(), cargoVO.getHeight(), cargoVO.getWeight());
        cargo = cargoRepository.save(cargo);
        // sender and receiver info
        ShipOrder order = new ShipOrder();
        order.setFromAddress(orderSenderVO.getAddress());
        order.setSenderName(orderSenderVO.getName());
        order.setSenderPhone(orderSenderVO.getPhone());
        order.setToAddress(orderReceiverVO.getAddress());
        order.setReceiverName(orderReceiverVO.getName());
        order.setReceiverPhone(orderReceiverVO.getPhone());
        // cargo info
        order.setCargo(cargo);
        order.setUserId(userId);

        // shipment info
        order.setOrderStatus(ShipOrder.Status.PENDING);
        order = orderRepository.save(order);
        List<Shipment> shipmentList = new ArrayList<>();
        Shipment shipment = new Shipment();
        shipment.setTime(LocalDateTime.now());
        shipment.setType(OrderShipmentVO.Type.ORDER);
        shipment.setDescription("订单创建");
        shipment.setShipOrder(order);

        shipmentList.add(shipment);
        order.setShipments(shipmentList);
        orderRepository.save(order);

        return BaseResponse.success("successfully create");
    }

    public BaseResponse updateOrderShipment(Integer userId, Integer orderId, String description, OrderShipmentVO.Type type) {
        ShipOrder order = orderRepository.findOrderByIdAndUserId(orderId, userId);
        if (order == null) {
            return BaseResponse.error("订单不存在");
        }

        // 完成运送的订单不允许再次修改
        if (order.getOrderStatus() == ShipOrder.Status.DELIVERED) {
            return BaseResponse.error("订单已完成，不允许修改");
        }

        if (type == OrderShipmentVO.Type.PROCESSING || type == OrderShipmentVO.Type.IN_TRANSIT) {
            order.setOrderStatus(ShipOrder.Status.DELIVERING);
        } else if (type == OrderShipmentVO.Type.DELIVERED) {
            order.setOrderStatus(ShipOrder.Status.DELIVERED);
        }
        Shipment shipment = new Shipment();
        shipment.setTime(LocalDateTime.now());
        shipment.setType(type);
        shipment.setDescription(description);
        shipment.setShipOrder(order);
        order.getShipments().add(shipment);
        orderRepository.save(order);

        return BaseResponse.success("successfully update");
    }

    public ShipOrder getOrder(Integer id, Integer userId) {
        return orderRepository.findByIdAndUserIdOrderByCreateTimeDesc(id, userId);
    }

    public List<ShipOrder> getOrders(Integer userId) {
        return orderRepository.findAllByUserIdOrderByCreateTimeDesc(userId);
    }

    public BaseResponse getOrderPrice(OrderSenderVO sender, OrderReceiverVO receiver, CargoVO cargo) {
        HashMap map = new HashMap<String, Object>();
        // 仅用作演示，实际应根据距离和货物重量计算价格
        map.put("price", 10.0f + 0.1*cargo.getWeight());
        map.put("basePrice", 10.0f);
        map.put("pricingType", "按重量计费");
        map.put("surcharge", 0.1*cargo.getWeight());
        return BaseResponse.success(map);
    }
}
