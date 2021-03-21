package org.example.copyservlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.example.entity.Applications;
import org.example.entity.User;
import org.example.patterns.UberFactory;
import org.example.service.ApplicationsService;
import org.example.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;


public class BaseServletCOPY extends HttpServlet {


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











