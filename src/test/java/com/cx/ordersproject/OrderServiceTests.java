package com.cx.ordersproject;

import com.cx.ordersproject.Pojo.VO.OrderAcceptVO;
import com.cx.ordersproject.Pojo.entity.Orders;
import com.cx.ordersproject.Server.Mapper.OrderMapper;
import com.cx.ordersproject.Server.service.impl.OrderServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;

public class OrderServiceTests {

    @Mock
    private OrderMapper orderMapper;

    @InjectMocks
    private OrderServiceImpl orderService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void testAcceptOrder() {
        long orderId = 1L;
        Orders orders = new Orders();
        orders.setId(orderId);
        orders.setStatus("UNASSIGNED");

        when(orderMapper.getById(orderId)).thenReturn(orders);
        doNothing().when(orderMapper).updateOrder(orders);
        OrderAcceptVO result = orderService.acceptOrder(orderId);
        verify(orderMapper, times(1)).getById(orderId);
        verify(orderMapper, times(1)).updateOrder(orders);
        Assertions.assertEquals("SUCCESS", result.getStatus());
    }
}