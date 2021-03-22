package org.example.projectjspandjstl.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.example.projectjspandjstl.entity.Applications;
import org.example.projectjspandjstl.entity.User;
import org.example.projectjspandjstl.patterns.UberFactory;
import org.example.projectjspandjstl.service.ApplicationsService;
import org.example.projectjspandjstl.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class DeleteNoteServlet extends HttpServlet {


    UberFactory uberFactory = new UberFactory();
    ApplicationsService applicationsService = UberFactory.getInstance().getApplicationsService();
    ObjectMapper objectMapper = new ObjectMapper();
    UserService userService = new UserService();


    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {




    }


    @SneakyThrows
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        UserService userService = new UserService();
        ApplicationsService applicationsService = new ApplicationsService();

        response.setCharacterEncoding("cp1251");
        response.setContentType("text/html;charset=cp1251");
        String id = request.getParameter("id");

        applicationsService.deleteApplicationsForId(id);
        userService.deieteUserForId(id);

        response.sendRedirect(request.getContextPath()+"/");

    }

}











