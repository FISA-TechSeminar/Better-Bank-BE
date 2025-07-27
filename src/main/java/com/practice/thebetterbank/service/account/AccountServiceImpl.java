package com.practice.thebetterbank.service.account;

import com.practice.thebetterbank.entity.Account;
import com.practice.thebetterbank.repository.account.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    @Override
    public Account save(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    @Override
    public Optional<Account> getAccountById(Long id) {
        return accountRepository.findById(id);
    }

    @Override
    public List<Account> getAccountsByMemberId(Long memberId) {
        return accountRepository.findByMemberId(memberId);
    }

}