package org.example.copyservlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.example.patterns.UberFactory;
import org.example.service.ApplicationsService;
import org.example.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class TestServletCOPY extends HttpServlet {


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











