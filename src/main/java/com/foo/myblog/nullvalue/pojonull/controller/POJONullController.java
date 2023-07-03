package com.foo.myblog.nullvalue.pojonull.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.foo.myblog.nullvalue.pojonull.entity.User;
import com.foo.myblog.nullvalue.pojonull.entity.UserDto;
import com.foo.myblog.nullvalue.pojonull.entity.UserEntity;
import com.foo.myblog.nullvalue.pojonull.repo.UserEntityRepository;
import com.foo.myblog.nullvalue.pojonull.repo.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("pojonull")
@RestController
public class POJONullController {

    private UserEntityRepository userEntityRepository;

    private UserRepository userRepository;

    public POJONullController(final UserEntityRepository userEntityRepository, final UserRepository userRepository) {
        this.userEntityRepository = userEntityRepository;
        this.userRepository = userRepository;
    }

    @GetMapping
    public void test() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new Jdk8Module());
        UserDto result = objectMapper.readValue("{\"id\":\"1\", \"age\":30, \"name\":null}", UserDto.class);
        log.info("field name with null value dto:{} name:{}", result, result.getName().orElse("N/A"));
        // field name with null value dto:UserDto(id=1, name=Optional.empty, age=Optional[30]) name:N/A
        log.info("missing field name dto:{}", objectMapper.readValue("{\"id\":\"1\", \"age\":30}", UserDto.class));
        // missing field name dto:UserDto(id=1, name=null, age=Optional[30])
    }

    @PostMapping("wrong")
    public User wrong(@RequestBody User user) {
        user.setNickname(String.format("guest%s", user.getName()));
        return userRepository.save(user);
    }

    @PostMapping("right")
    public UserEntity right(@RequestBody UserDto user) {
        if (user == null || user.getId() == null)
            throw new IllegalArgumentException("User Id should not be empty.");

        UserEntity userEntity = userEntityRepository.findById(user.getId())
                .orElseThrow(() -> new IllegalArgumentException("User not exist"));

        if (user.getName() != null) {
            userEntity.setName(user.getName().orElse(""));
        }
        userEntity.setNickname("guest" + userEntity.getName());
        if (user.getAge() != null) {
            userEntity.setAge(user.getAge().orElseThrow(() -> new IllegalArgumentException("age cant be empty")));
        }
        return userEntityRepository.save(userEntity);
    }
}