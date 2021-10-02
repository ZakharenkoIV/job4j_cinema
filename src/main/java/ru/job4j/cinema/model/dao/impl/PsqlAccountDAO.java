package ru.job4j.cinema.model.dao.impl;

import ru.job4j.cinema.model.Account;
import ru.job4j.cinema.model.dao.AccountDAO;
import ru.job4j.cinema.storage.Psql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PsqlAccountDAO implements AccountDAO {

    public Collection<Account> getAllAccounts() throws SQLException {
        List<Account> accounts = new ArrayList<>();
        try (Connection cn = Psql.getConnection()) {
            PreparedStatement ps = cn.prepareStatement("SELECT * FROM account");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    accounts.add(new Account(rs.getInt("id"),
                            rs.getInt("username"),
                            rs.getInt("email"),
                            rs.getInt("phone")));
                }
            }
        }
        return accounts;
    }

    public Account createAccount(Account account) throws SQLException {
        try (Connection cn = Psql.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "INSERT INTO \"account\"(username, email, phone) VALUES ((?), (?), (?))")
        ) {
            ps.setInt(1, account.getUserName());
            ps.setInt(2, account.getEmail());
            ps.setInt(3, account.getPhone());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    account.setId(id.getInt("id"));
                }
            }
        }
        return account;
    }
}
