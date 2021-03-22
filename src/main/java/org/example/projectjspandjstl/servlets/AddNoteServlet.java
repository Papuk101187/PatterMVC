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
import java.util.*;


public class AddNoteServlet extends HttpServlet {


    UberFactory uberFactory = new UberFactory();
    ApplicationsService applicationsService = UberFactory.getInstance().getApplicationsService();
    ObjectMapper objectMapper = new ObjectMapper();
    UserService userService = new UserService();


    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        int start = request.getContextPath().length() + request.getServletPath().length();
        String id = request.getRequestURI().substring(start + 1);
        int index = Integer.parseInt(id) - 1;

        UserService userService = new UserService();

        User user = userService.getAllUsersId(id);
        ArrayList<Applications> applicat = applicationsService.getApplications(id);


        request.setAttribute("applicat", applicat);
        getServletContext().getRequestDispatcher("/WEB-INF/views/mjsp1.jsp").forward(request, response);


    }


    @SneakyThrows
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setCharacterEncoding("cp1251");
        response.setContentType("text/html;charset=cp1251");

        for (Map.Entry<String, String[]> stringEntry : request.getParameterMap().entrySet()) {

            System.out.println(stringEntry.getKey()+" "+stringEntry.getValue()[0]);
        }

        String nameuser = new String(request.getParameter("name").getBytes("ISO-8859-1"),"cp1251");
        String dateUser = new String(request.getParameter("date").getBytes("ISO-8859-1"),"cp1251");
        String passUser = new String(request.getParameter("password").getBytes("ISO-8859-1"),"cp1251");


        String name_application = new String(request.getParameter("name_application").getBytes("ISO-8859-1"),"cp1251");
        String description_application = new String(request.getParameter("description_application").getBytes("ISO-8859-1"),"cp1251");


        User user = new User()
                .setFio(nameuser)
                .setDate(dateUser)
                .setPassword(passUser);

        Applications applications = new Applications()
                .setName(name_application)
                .setDescription(description_application);


        userService.addUser(user);
        applicationsService.postApplications(applications,user);


        response.getWriter().print("В базу данных  добавлена  заявка "+user.getFio()+" "+applications);

        response.sendRedirect(request.getContextPath()+"/");


    }

}











