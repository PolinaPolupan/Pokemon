package ru.mirea.pkmn.polupanpolina.controller;

import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import ru.mirea.pkmn.polupanpolina.repository.PkmnRepository;
import ru.mirea.pkmn.polupanpolina.entity.CardEntity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/cards")
public class CardServlet extends HttpServlet {

    @Autowired
    PkmnRepository pkmnRepository;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String cardName = req.getParameter("name");
        CardEntity card = pkmnRepository.getCard(cardName);

        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();

        if (card != null) {
            out.write("{\"name\": \"" + card.getName() + "\", \"hp\": " + card.getHp() + "}");
        } else {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            out.write("{\"error\": \"Card not found\"}");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        BufferedReader reader = req.getReader();
        Gson gson = new Gson();
        CardEntity card = gson.fromJson(reader, CardEntity.class);

        pkmnRepository.saveCard(card);
        resp.setStatus(HttpServletResponse.SC_CREATED);
    }
}
