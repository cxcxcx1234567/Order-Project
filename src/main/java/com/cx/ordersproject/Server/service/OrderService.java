package com.cx.ordersproject.Server.service;

import com.cx.ordersproject.Pojo.DTO.OrderQueryDTO;
import com.cx.ordersproject.Pojo.DTO.OrderSubmitDTO;
import com.cx.ordersproject.Pojo.VO.OrderAcceptVO;
import com.cx.ordersproject.Pojo.VO.OrderQueryVO;
import com.cx.ordersproject.Pojo.VO.OrderSubmitVO;

import java.util.List;

public interface OrderService {



    OrderSubmitVO submitOrder(OrderSubmitDTO orderSubmitDTO);

    OrderAcceptVO acceptOrder(Long id);

    List<OrderQueryVO> selectPage(OrderQueryDTO orderQueryDTO);

}
