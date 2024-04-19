package com.cx.ordersproject;

import com.cx.ordersproject.Pojo.DTO.OrderQueryDTO;
import com.cx.ordersproject.Pojo.DTO.OrderSubmitDTO;
import com.cx.ordersproject.Pojo.VO.OrderAcceptVO;
import com.cx.ordersproject.Pojo.VO.OrderQueryVO;
import com.cx.ordersproject.Pojo.VO.OrderSubmitVO;
import com.cx.ordersproject.Server.service.OrderService;
import com.cx.ordersproject.Server.user.OrderController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class ControllerTest {
    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSubmitOrder() {
        OrderSubmitDTO orderSubmitDTO = new OrderSubmitDTO();
        OrderSubmitVO expectedResponse = new OrderSubmitVO();
        when(orderService.submitOrder(any(OrderSubmitDTO.class))).thenReturn(expectedResponse);

        OrderSubmitVO result = orderController.submit(orderSubmitDTO);

        Assert.notNull(result, "Result should not be null");
        assertEquals(expectedResponse.getId(), result.getId());
    }

    @Test
    public void testAcceptOrder() {
        Long orderId = 1L;

        OrderAcceptVO expectedResponse = new OrderAcceptVO();

        when(orderService.acceptOrder(orderId)).thenReturn(expectedResponse);

        OrderAcceptVO result = orderController.accept(orderId);

        Assert.notNull(result, "Result should not be null");
        assertEquals(expectedResponse.getStatus(), result.getStatus());
    }

    @Test
    public void testQueryOrders() {
        OrderQueryDTO orderQueryDTO = new OrderQueryDTO();
        List<OrderQueryVO> expectedResponse = Arrays.asList(new OrderQueryVO(), new OrderQueryVO());
        when(orderService.selectPage(any(OrderQueryDTO.class))).thenReturn(expectedResponse);
        List<OrderQueryVO> result = orderController.query(orderQueryDTO);
        Assert.notNull(result, "Result should not be null");
        assertEquals(expectedResponse.size(), result.size());
    }

}
