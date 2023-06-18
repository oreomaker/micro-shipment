package com.wgzmb.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.wgzmb._common.vo.OrderShipmentVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "shipment")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Shipment {
    @Id
    @GeneratedValue
    private Integer id;
    private LocalDateTime time;
    private String description;
    private OrderShipmentVO.Type type;
    @ManyToOne
    @JoinColumn(name = "shipOrder_id")
    @JsonBackReference
    private ShipOrder shipOrder;
}
