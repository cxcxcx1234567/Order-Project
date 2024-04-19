package com.cx.ordersproject.Pojo.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderSubmitDTO implements Serializable {

    private String origin;

    private String destination;

}
