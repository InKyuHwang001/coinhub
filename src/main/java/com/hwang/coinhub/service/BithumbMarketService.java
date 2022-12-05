package com.hwang.coinhub.service;

import com.hwang.coinhub.dto.CoinBuyDTO;
import com.hwang.coinhub.dto.CoinSellDTO;
import com.hwang.coinhub.feign.BithumbFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class BithumbMarketService implements MarketService {
    private final BithumbFeignClient bithumbFeignClient;
    public double getCoinCurrentPrice(String coin) {
        return Double.parseDouble(bithumbFeignClient.getCoinPrice(coin.toUpperCase()+"_KRW")
                .getData()
                .getClosing_price());

    }

    public List<String> getCoins(){
        //API를 이용하여 가져오기
        List<String> result = new ArrayList<>();
        bithumbFeignClient.getAssetStatus().getData().forEach((k,v) -> {
            if(v.getDeposit_status()==1&&v.getDeposit_status()==1){
                result.add(k.toUpperCase());
            }
        });
        return result;
    }

    public CoinBuyDTO calculateBuy(List<String> commonCoins, double amount){

        Map<String, Double> amounts = new HashMap<>();
        Map<String, Map<Double, Double>> orderBooks = new HashMap<>();


        //Feign 으로 orderbook 가져오기
        Map<String, Object> bithumbResponse = bithumbFeignClient.getOrderBook().getData();
        //orderbook for loop
        bithumbResponse.forEach((k,v)-> {
            if(!(k.equalsIgnoreCase("timestamp")||k.equalsIgnoreCase("payment_currency"))){
                double availableCurrency = amount;
                double availableCoin = 0;

                String coin = k;
                Map<Double,Double> eachOrderBook = new HashMap<>();
                List<Map<String, String>> wannaSell= (List<Map<String, String>>)((Map<String, Object>) v).get("asks");
                //해당 호가창의 총 가격보다 큰지 작은지 비교
                for(int i = 0; i<wannaSell.size(); i++ ){
                    Double price = Double.parseDouble(wannaSell.get(i).get("price"));
                    Double quantity = Double.parseDouble(wannaSell.get(i).get("quantity"));
                    Double eachTotalPrice = price*quantity;
                    Double buyableCoinAmount = availableCurrency/ price;
                    //amount<x: 현가 호가창에서 내가 살 수 있는만큼 추가하고 종료
                    if (availableCurrency <= eachTotalPrice) {
                        availableCoin += buyableCoinAmount;
                        //살수있는 호가창에 추가
                        eachOrderBook.put(price, buyableCoinAmount);
                        availableCurrency = 0;
                        break;
                    } else { //amount>x: 다음 스텝으로 진행
                        availableCoin += quantity;
                        eachOrderBook.put(price, quantity);
                        availableCurrency -= eachTotalPrice;
                    }
                }
                if (availableCurrency == 0) {
                    amounts.put(coin, availableCoin);
                    orderBooks.put(coin, eachOrderBook);
                }
            }
        });
        return new CoinBuyDTO(amounts, orderBooks);
    }
    public CoinSellDTO calculateSell(CoinBuyDTO buyDTO){
        return null;
    }

}
