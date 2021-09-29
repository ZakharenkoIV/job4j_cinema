package ru.job4j.cinema.model.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.model.dao.TicketDAO;
import ru.job4j.cinema.storage.Psql;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PsqlTicketDAO implements TicketDAO {
    private static final Logger LOGGER = LoggerFactory.getLogger(
            MethodHandles.lookup().lookupClass());

    public Collection<Ticket> getAllTickets() {
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
        } catch (SQLException e) {
            LOGGER.error("Database getAllTickets error.", e);
        }
        return tickets;
    }

    public Ticket createTicket(Ticket ticket) {
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
        } catch (SQLException e) {
            LOGGER.error("Database createTicket error.", e);
        }
        return ticket;
    }

    public Collection<Ticket> findAllTicketByAccountId(int accountId) {
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
        } catch (SQLException e) {
            LOGGER.error("Database findAllTicketByAccountId error.", e);
        }
        return tickets;
    }
}
