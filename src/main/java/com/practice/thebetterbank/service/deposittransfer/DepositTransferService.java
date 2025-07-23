package com.practice.thebetterbank.service.deposittransfer;

import com.practice.thebetterbank.controller.dto.DepositTransferDTO;

public interface DepositTransferService {
    DepositTransferDTO deposit(DepositTransferDTO dto);
    DepositTransferDTO transfer(DepositTransferDTO dto);
}
