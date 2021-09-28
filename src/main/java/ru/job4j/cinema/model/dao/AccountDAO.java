package ru.job4j.cinema.model.dao;

import ru.job4j.cinema.model.Account;

import java.util.Collection;

public interface AccountDAO {
    Collection<Account> getAllAccounts();

    Account createAccount(Account account);
}
