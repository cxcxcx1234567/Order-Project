package com.cx.ordersproject.Server.Mapper;


import com.cx.ordersproject.Pojo.VO.OrderQueryVO;
import com.cx.ordersproject.Pojo.entity.Orders;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface OrderMapper {

    void insert(Orders orders);

    @Select("select * from orders where id = #{id}")
    Orders getById(Long id);

    @Update("update orders set status = #{status} where id = #{id}")
    void updateOrder(Orders orders);

    @Select("select id, distance, status from orders")
    List<OrderQueryVO> selectPage();
}