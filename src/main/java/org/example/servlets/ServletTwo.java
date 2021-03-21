package org.example.servlets;

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
import java.util.List;
import java.util.Map;


public class ServletTwo extends HttpServlet {


    UberFactory uberFactory = new UberFactory();
    ApplicationsService applicationsService = UberFactory.getInstance().getApplicationsService();
    ObjectMapper objectMapper = new ObjectMapper();
    UserService userService = new UserService();


    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        UserService userService = new UserService();
        List<User> users = userService.getAllUsers();

        int start = request.getContextPath().length() + request.getServletPath().length();
        String id = request.getRequestURI().substring(start + 1);
        int index = Integer.parseInt(id) - 1;

        User user = users.get(index);
        ArrayList<Applications> applicat = applicationsService.getpostApplications(user);

        request.setAttribute("applicat", applicat);
        getServletContext().getRequestDispatcher("/WEB-INF/views/mjsp1.jsp").forward(request, response);


    }


    @SneakyThrows
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        String a = request.getParameter("name");
        response.getWriter().print(a);





    }

}











