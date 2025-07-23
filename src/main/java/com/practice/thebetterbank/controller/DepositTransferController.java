package com.practice.thebetterbank.controller;


import com.practice.thebetterbank.controller.dto.DepositTransferDTO;
import com.practice.thebetterbank.controller.dto.ResultDTO;
import com.practice.thebetterbank.service.deposittransfer.DepositTransferService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class DepositTransferController {

    private final DepositTransferService depositTransferService;

    @PostMapping("/deposit")
    public ResultDTO<DepositTransferDTO> deposit(@RequestBody DepositTransferDTO dto) {
        DepositTransferDTO result = depositTransferService.deposit(dto);
        return ResultDTO.res(HttpStatus.OK, "입금이 완료되었습니다.", result);
    }

    @PostMapping("/transfer")
    public ResultDTO<DepositTransferDTO> transfer(@RequestBody DepositTransferDTO dto) {
        DepositTransferDTO result = depositTransferService.transfer(dto);
        return ResultDTO.res(HttpStatus.OK, "송금이 완료되었습니다.", result);
    }
}