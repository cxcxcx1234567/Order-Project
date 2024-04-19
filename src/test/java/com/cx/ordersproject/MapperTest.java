package com.cx.ordersproject;

import com.cx.ordersproject.Pojo.DTO.OrderAcceptDTO;
import com.cx.ordersproject.Pojo.entity.Orders;
import com.cx.ordersproject.Server.Mapper.OrderMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Assertions;

@SpringBootTest
public class MapperTest {


    @Autowired
    OrderMapper orderMapper;
    @Test
    void getById(){
        OrderAcceptDTO id = new OrderAcceptDTO();
        id.setId(20L);
        Orders orders = orderMapper.getById(id.getId());
        Assertions.assertNotNull(orders);
    }
}
