package org.example.project2mvc;

import org.example.project2mvc.annotations.*;
import org.example.projectjspandjstl.entity.Applications;
import org.example.projectjspandjstl.entity.User;
import org.example.projectjspandjstl.service.ApplicationsService;
import org.example.projectjspandjstl.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class MainController extends JsonController {


    @Autowired
    public UserService service;
    @Autowired
    public ApplicationsService applicationsService;

    @GetMapping("")
    public void usersPage(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        List<User> users = service.getAllUsers();
        request.setAttribute("users", users);
        request.getServletContext().getRequestDispatcher("/WEB-INF/views/root.jsp").forward(request, response);
    }

    @DeleteMapping("delete/{id}")
    public void usersDelete(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        String id = (String) request.getAttribute("id");

        UserService userService = new UserService();
        ApplicationsService applicationsService = new ApplicationsService();

        response.setCharacterEncoding("cp1251");
        response.setContentType("text/html;charset=cp1251");

        applicationsService.deleteApplicationsForId(id);
        userService.deieteUserForId(id);

        response.sendRedirect(request.getContextPath() + "/");

    }

    @PostMapping("add")
    public void usersPut(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, SQLException {

        System.out.println("Попали в метод add");
        UserService userService = new UserService();

        response.setCharacterEncoding("cp1251");
        response.setContentType("text/html;charset=cp1251");

        for (Map.Entry<String, String[]> stringEntry : request.getParameterMap().entrySet()) {

            System.out.println(stringEntry.getKey() + " " + stringEntry.getValue()[0]);
        }

        String nameuser = new String(request.getParameter("name").getBytes("ISO-8859-1"), "cp1251");
        String dateUser = new String(request.getParameter("date").getBytes("ISO-8859-1"), "cp1251");
        String passUser = new String(request.getParameter("password").getBytes("ISO-8859-1"), "cp1251");


        String name_application = new String(request.getParameter("name_application").getBytes("ISO-8859-1"), "cp1251");
        String description_application = new String(request.getParameter("description_application").getBytes("ISO-8859-1"), "cp1251");


        User user = new User()
                .setFio(nameuser)
                .setDate(dateUser)
                .setPassword(passUser);

        Applications applications = new Applications()
                .setName(name_application)
                .setDescription(description_application);


        userService.addUser(user);
        applicationsService.postApplications(applications, user);


        response.getWriter().print("В базу данных  добавлена  заявка " + user.getFio() + " " + applications);

        response.sendRedirect(request.getContextPath() + "/");

    }

    @PutMapping("users/{id}")
    public void usersInfo(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, SQLException {

        String id = (String) request.getAttribute("id");
        System.out.println("id ===== "+id);

        UserService userService = new UserService();
        User user = userService.getAllUsersId(id);
        System.out.println("user "+user);
        ArrayList<Applications> applicat = applicationsService.getApplications(id);
        request.setAttribute("applicat", applicat);
        System.out.println("applicat "+applicat);
        request.getServletContext().getRequestDispatcher("/WEB-INF/views/page1.jsp").forward(request, response);
    }



}
