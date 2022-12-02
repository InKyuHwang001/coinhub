package com.hwang.coinhub.feign.response;

import lombok.Getter;

@Getter
public class BithumbRsponse<T> { //제네릭 T를 밖에서 정의하고 데이터의 타입이 결정된다.
    private String status;
    private T data;
}
