package ru.job4j.cinema.model.dao;

import ru.job4j.cinema.model.Account;

import java.sql.SQLException;
import java.util.Collection;

public interface AccountDAO {
    Collection<Account> getAllAccounts() throws SQLException;

    Account createAccount(Account account) throws SQLException;
}
