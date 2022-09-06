package com.ciandt.summit.bootcamp2022.utils;

import com.ciandt.summit.bootcamp2022.client.TokenFeignClient;
import com.ciandt.summit.bootcamp2022.dto.UsernameDto;
import com.ciandt.summit.bootcamp2022.repositories.UserRepository;
import com.ciandt.summit.bootcamp2022.utils.exceptions.InvalidLogDataException;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TokenService {

    @Autowired
    private TokenFeignClient tokenFeignClient;

    @Autowired
    private UserRepository userRepository;

    public String createToken(UsernameDto usernameDto) throws InvalidLogDataException {

        if(!validateUserName(usernameDto.getData().getName())) {
            throw new InvalidLogDataException("Invalid Username!");
        }

        return tokenFeignClient.createToken(usernameDto);
    }

    public void validateToken(UsernameDto usernameDto) throws InvalidLogDataException {
        try {
            if(!validateUserName(usernameDto.getData().getName())) {
                throw new InvalidLogDataException("Invalid Username!");
            }
            if(!tokenFeignClient.authorizeToken(usernameDto).equals("ok")) {
                throw new InvalidLogDataException("Invalid Token!");
            }
        } catch (FeignException e) {
            throw new InvalidLogDataException("Invalid or Expired Token!");
        }

    }

    public boolean validateUserName(String username) {
        return userRepository.findByName(username).isPresent();
    }

}
