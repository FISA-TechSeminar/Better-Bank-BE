package com.practice.thebetterbank.controller;

import com.practice.thebetterbank.controller.dto.ResultDTO;
import com.practice.thebetterbank.controller.dto.MemberDTO;
import com.practice.thebetterbank.entity.Member;
import com.practice.thebetterbank.service.user.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/{id}")
    public ResultDTO<MemberDTO> getUser(@PathVariable("id") Long id, HttpSession session) {

        Member user = memberService.getUserById(id) // 존재하지 않을시 Exception
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다: " + id));

        session.setAttribute("userId", id);
        MemberDTO memberDTO = MemberDTO.builder()
            .id(user.getId())
            .memberName(user.getUsername())
            .build();

        return ResultDTO.res(HttpStatus.OK, "회원 정보를 불러왔습니다!", memberDTO);
    }

}
