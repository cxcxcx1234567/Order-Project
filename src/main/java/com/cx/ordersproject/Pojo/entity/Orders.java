package com.cx.ordersproject.Pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Orders implements Serializable {

    private Long id;

    private String status;

    private String origin;

    private String destination;

    private Integer distance;


}
