package com.hwang.coinhub.view;

import com.hwang.coinhub.dto.TransferCalculateDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@Getter
@AllArgsConstructor//내부의 모든 필듬에 대하여 생성자를 만들라는 내용
public class TransferCalculateResponseView {
    private String coin;
    private double amount;
    private Map<Double, Double> buyOrderBook;
    private Map<Double, Double> sellOrderBook;

    //DTO -> View
    public static TransferCalculateResponseView of(TransferCalculateDTO dto){
        return new TransferCalculateResponseView(
                dto.getCoin(),
                dto.getAmount(),
                dto.getBuyOrderBook(),
                dto.getSellOrderBook()
        );
    }
}
