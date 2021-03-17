package org.example.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.example.service.UserService;
import org.example.entity.Applications;
import org.example.service.ApplicationsService;
import org.example.entity.User;
import org.example.patterns.UberFactory;

import java.io.IOException;
import java.util.ArrayList;


public class MyServletDelete extends HttpServlet {


    UberFactory uberFactory = new UberFactory();

    UserService userService = UberFactory.getInstance().getUserService();
    ApplicationsService applicationsService = UberFactory.getInstance().getApplicationsService();

    ObjectMapper objectMapper = new ObjectMapper();


    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

    }


    @SneakyThrows
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        applicationsService.deleteApplications(request,response);



    }

}











