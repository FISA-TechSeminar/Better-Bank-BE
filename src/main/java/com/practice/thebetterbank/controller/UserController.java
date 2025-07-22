package com.practice.thebetterbank.controller;

import com.practice.thebetterbank.controller.dto.ResultDTO;
import com.practice.thebetterbank.controller.dto.UserDTO;
import com.practice.thebetterbank.entity.User;
import com.practice.thebetterbank.service.user.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public ResultDTO<UserDTO> getUser(@PathVariable Long id, HttpSession session) {

        User user = userService.getUserById(id) // 존재하지 않을시 Exception
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다: " + id));

        session.setAttribute("userId", id);
        UserDTO userDTO = UserDTO.builder()
            .id(user.getId())
            .userName(user.getUsername())
            .sessionId(session.getAttribute("sessionId").toString())
            .build();

        return ResultDTO.res(HttpStatus.OK, "회원 정보를 불러왔습니다!", userDTO);
    }

}
