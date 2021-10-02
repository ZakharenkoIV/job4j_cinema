package ru.job4j.cinema.model.dao.impl;

import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.model.dao.TicketDAO;
import ru.job4j.cinema.storage.Psql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PsqlTicketDAO implements TicketDAO {

    public Collection<Ticket> getAllTickets() throws SQLException {
        List<Ticket> tickets = new ArrayList<>();
        try (Connection cn = Psql.getConnection()) {
            PreparedStatement ps = cn.prepareStatement("SELECT * FROM ticket");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    tickets.add(new Ticket(rs.getInt("id"),
                            rs.getInt("session_id"),
                            rs.getInt("row"),
                            rs.getInt("cell"),
                            rs.getInt("account_id")));
                }
            }
        }
        return tickets;
    }

    public Ticket createTicket(Ticket ticket) throws SQLException {
        try (Connection cn = Psql.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "INSERT INTO \"ticket\"(session_id, row, cell, account_id)"
                             + "VALUES ((?), (?), (?), (?))",
                     PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setInt(1, ticket.getSessionId());
            ps.setInt(2, ticket.getRow());
            ps.setInt(3, ticket.getCell());
            ps.setInt(4, ticket.getAccountId());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    ticket.setId(id.getInt("id"));
                }
            }
        }
        return ticket;
    }

    public Collection<Ticket> findAllTicketByAccountId(int accountId) throws SQLException {
        List<Ticket> tickets = new ArrayList<>();
        try (Connection cn = Psql.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "SELECT * FROM ticket WHERE account_id = (?)")
        ) {
            ps.setInt(1, accountId);
            ps.executeQuery();
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    tickets.add(new Ticket(rs.getInt("id"),
                            rs.getInt("session_id"),
                            rs.getInt("row"),
                            rs.getInt("cell"),
                            rs.getInt("account_id")));
                }
            }
        }
        return tickets;
    }
}
