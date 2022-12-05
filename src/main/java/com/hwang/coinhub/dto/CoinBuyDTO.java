package com.hwang.coinhub.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@Getter
@AllArgsConstructor
public class CoinBuyDTO {
    private Map<String, Double> amount;
    private Map<String, Map<Double, Double>> orderBooks;
}