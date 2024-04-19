package com.cx.ordersproject.Server.user;

import com.cx.ordersproject.Pojo.DTO.OrderQueryDTO;
import com.cx.ordersproject.Pojo.DTO.OrderSubmitDTO;
import com.cx.ordersproject.Pojo.VO.OrderAcceptVO;
import com.cx.ordersproject.Pojo.VO.OrderQueryVO;
import com.cx.ordersproject.Pojo.VO.OrderSubmitVO;
import com.cx.ordersproject.Server.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController("userOrderController")
@RequestMapping("/orders")
@Api(tags = "Api of Orders for clients")
public class OrderController {


    @Resource
    OrderService orderService;

    @PostMapping()
    @ApiOperation("order")
    public OrderSubmitVO submit(OrderSubmitDTO orderSubmitDTO){
        log.info("order from user: {}", orderSubmitDTO);
        OrderSubmitVO orderSubmitVO = orderService.submitOrder(orderSubmitDTO);

        return orderSubmitVO;
    }

    @PatchMapping("/{id}")
    public OrderAcceptVO accept(@PathVariable Long id){
        OrderAcceptVO orderAcceptVO = orderService.acceptOrder(id);
        return orderAcceptVO;
    }


    @GetMapping
    public List<OrderQueryVO> query(OrderQueryDTO orderQueryDTO){
        List<OrderQueryVO> list = orderService.selectPage(orderQueryDTO);
        return list;
    }
}