package com.practice.thebetterbank.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class MemberDTO {
    Long id;
    String memberName;
    String sessionId;
}
