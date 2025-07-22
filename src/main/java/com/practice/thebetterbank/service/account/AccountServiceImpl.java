package com.practice.thebetterbank.service.account;

import com.practice.thebetterbank.entity.Account;
import com.practice.thebetterbank.repository.account.AccountRepository;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Account save(Account account) {
        return accountRepository.save(account);
    }
}
