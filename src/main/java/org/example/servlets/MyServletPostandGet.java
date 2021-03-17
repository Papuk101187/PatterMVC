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


public class MyServletPostandGet extends HttpServlet {


    UberFactory uberFactory = new UberFactory();

    UserService userService = UberFactory.getInstance().getUserService();
    ApplicationsService applicationsService = UberFactory.getInstance().getApplicationsService();

    ObjectMapper objectMapper = new ObjectMapper();


    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setHeader("Content-Type","text/plain");

        ArrayList<Applications> applications = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder();
        uberFactory.buildParametrsGet(request); // создаём юзера и заявку внутри фабрики
        User user = uberFactory.getUser();
        applications= applicationsService.getpostApplications(user);
        response.getWriter().print(applications);


        for (Applications application : applications) {
            stringBuilder.append(application);
        }
        String responce = stringBuilder.toString();

        response.getWriter().print(applicationsService);

    }


    @SneakyThrows
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        uberFactory.buildParametrsPost(request); // создаём юзера и заявку внутри фабрики
        User user = uberFactory.getUser(); // получаем юзера из фабрики
        Applications applications = uberFactory.getApplications(); // получаем заявку из фабрики

        if (userService.checkUser(user) == false) {  // проверяем есть ли юзер в базе данных
            userService.addUser(user); // если -нет , добавляем его туда.
        }

        applicationsService.postApplications(applications, user); // добавляем заявку

    }

}











