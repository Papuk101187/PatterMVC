package org.example.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.example.service.UserService;
import org.example.entity.Applications;
import org.example.service.ApplicationsService;
import org.example.entity.User;
import org.example.patterns.UberFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class RootServlet extends HttpServlet {


    UberFactory uberFactory = new UberFactory();
    ApplicationsService applicationsService = UberFactory.getInstance().getApplicationsService();

    ObjectMapper objectMapper = new ObjectMapper();


    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        UserService userService = new UserService();

        request.setAttribute("users", userService.getAllUsers());

        getServletContext().getRequestDispatcher("/WEB-INF/views/root.jsp").forward(request, response);

    }


    @SneakyThrows
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {





    }




}











