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
public class OrderQueryDTO implements Serializable {

    private Integer page;

    private Integer limit;
}
