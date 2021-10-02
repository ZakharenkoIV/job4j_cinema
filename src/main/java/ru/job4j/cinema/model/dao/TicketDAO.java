package ru.job4j.cinema.model.dao;

import ru.job4j.cinema.model.Ticket;

import java.sql.SQLException;
import java.util.Collection;

public interface TicketDAO {
    Collection<Ticket> getAllTickets() throws SQLException;

    Ticket createTicket(Ticket ticket) throws SQLException;

    Collection<Ticket> findAllTicketByAccountId(int accountId) throws SQLException;

}
