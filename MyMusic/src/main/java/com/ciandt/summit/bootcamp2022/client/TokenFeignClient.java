package com.ciandt.summit.bootcamp2022.client;

import com.ciandt.summit.bootcamp2022.dto.UsernameDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "tokenFeignClient",url = "http://localhost:8080")
public interface TokenFeignClient {

    @PostMapping("/api/v1/token")
    String createToken(UsernameDto usernameDto);

    @PostMapping("/api/v1/token/authorizer")
    String authorizeToken(UsernameDto usernameDto);

}
