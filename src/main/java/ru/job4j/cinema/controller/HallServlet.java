package ru.job4j.cinema.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.model.dao.TicketDAO;
import ru.job4j.cinema.model.dao.impl.PsqlTicketDAO;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.invoke.MethodHandles;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;

public class HallServlet extends HttpServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(
            MethodHandles.lookup().lookupClass());

    private static final Gson GSON = new GsonBuilder().create();

    private final TicketDAO ticketStorage = new PsqlTicketDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json; charset=utf-8");
        OutputStream output = resp.getOutputStream();
        try {
            String json = GSON.toJson(ticketStorage.getAllTickets());
            output.write(json.getBytes(StandardCharsets.UTF_8));
            output.flush();
            output.close();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
            resp.sendError(resp.SC_BAD_REQUEST);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Ticket ticket = GSON.fromJson(req.getReader(), Ticket.class);
        resp.setContentType("application/json; charset=utf-8");
        ticket.setAccountId(1);
        OutputStream output = resp.getOutputStream();
        try {
            String json = GSON.toJson(ticketStorage.createTicket(ticket));
            output.write(json.getBytes(StandardCharsets.UTF_8));
            output.flush();
            output.close();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
            resp.sendError(resp.SC_CONFLICT);
        }
    }
}
