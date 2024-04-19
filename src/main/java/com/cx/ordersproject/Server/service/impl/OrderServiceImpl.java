package com.cx.ordersproject.Server.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cx.ordersproject.Common.Constant;
import com.cx.ordersproject.Common.GlobalException;
import com.cx.ordersproject.Common.HttpClientUtil;
import com.cx.ordersproject.Pojo.DTO.OrderQueryDTO;
import com.cx.ordersproject.Pojo.DTO.OrderSubmitDTO;
import com.cx.ordersproject.Pojo.VO.OrderAcceptVO;
import com.cx.ordersproject.Pojo.VO.OrderQueryVO;
import com.cx.ordersproject.Pojo.VO.OrderSubmitVO;
import com.cx.ordersproject.Pojo.entity.Orders;
import com.cx.ordersproject.Server.Mapper.OrderMapper;
import com.cx.ordersproject.Server.service.OrderService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Resource
    public OrderMapper orderMapper;

    private static final String GOOGLE_MAPS_API_URL = "https://maps.googleapis.com/maps/api/geocode/json";
    private static final String GOOGLE_MAPS_DISTANCE_MATRIX_API_URL = "https://maps.googleapis.com/maps/api/distancematrix/json";
    private static final String API_KEY = "AIzaSyCswiZ21KiuQ9FwtzmBS_fIVly4brnsPMw";

    public String getLatLng(String address) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("address", address);
        parameters.put("key", API_KEY);

        String response = HttpClientUtil.doGet(GOOGLE_MAPS_API_URL, parameters);
        JSONObject jsonObject = JSON.parseObject(response);
        if(jsonObject != null){
            JSONArray results = jsonObject.getJSONArray("results");
            if (results != null && results.size() > 0) {
                JSONObject location = results.getJSONObject(0).getJSONObject("geometry").getJSONObject("location");
                double lat = location.getDoubleValue("lat");
                double lng = location.getDoubleValue("lng");
                return lat + "," + lng;
            }
        }
        throw new GlobalException(Constant.ADDRESS_ERROR);
    }

    public int calculateDistance(String origin, String destination) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("origins", origin);
        parameters.put("destinations", destination);
        parameters.put("key", API_KEY);

        String response = HttpClientUtil.doGet(GOOGLE_MAPS_DISTANCE_MATRIX_API_URL, parameters);
        JSONObject jsonObject = JSON.parseObject(response);
        JSONArray rows = jsonObject.getJSONArray("rows");
        if (rows == null || rows.size() == 0) {
            throw new GlobalException(Constant.ADDRESS_ERROR);

        }
        JSONArray elements = rows.getJSONObject(0).getJSONArray("elements");
        JSONObject element = elements.getJSONObject(0);
        JSONObject distanceObj = element.getJSONObject("distance");
        if(distanceObj == null){
            throw new GlobalException(Constant.ADDRESS_ERROR);
        }
        return distanceObj.getIntValue("value");

    }


    @Override
    @Transactional
    public OrderSubmitVO submitOrder(OrderSubmitDTO orderSubmitDTO) {

        String originLatLng = getLatLng(orderSubmitDTO.getOrigin());
        String destinationLatLng = getLatLng(orderSubmitDTO.getDestination());

        int distance = calculateDistance(originLatLng, destinationLatLng);

        Orders orders = new Orders();
        BeanUtils.copyProperties(orderSubmitDTO, orders);
        orders.setStatus("UNASSIGNED");
        orders.setDistance(distance);

        orderMapper.insert(orders);
        OrderSubmitVO orderSubmitVO = OrderSubmitVO.builder()
                .id(orders.getId())
                .distance(distance)
                .status(orders.getStatus())
                .build();
        return orderSubmitVO;
    }

    @Override
    public OrderAcceptVO acceptOrder(Long id) {
        long orderId = id;
        synchronized (this) {
            //check status
            Orders orders = orderMapper.getById(orderId);
            if(orders == null){
                throw new GlobalException(Constant.ORDER_ERROR);
            }
            if (orders.getStatus().equals("TAKEN")) {
                throw new GlobalException(Constant.ORDER_BEEN_TAKEN);
            }
            //update status

            orders.setStatus("TAKEN");
            orderMapper.updateOrder(orders);

            return new OrderAcceptVO("SUCCESS");
        }
    }

    @Override
    public List<OrderQueryVO> selectPage(OrderQueryDTO orderQueryDTO) {
        if(orderQueryDTO.getPage() <= 0 ||  orderQueryDTO.getLimit() <= 0){
            throw new GlobalException(Constant.QUERY_ERROR);
        }

        PageHelper.startPage(orderQueryDTO.getPage(), orderQueryDTO.getLimit());
        Page p = (Page) orderMapper.selectPage();
        return p;
    }
}