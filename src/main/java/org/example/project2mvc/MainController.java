package org.example.project2mvc;

import org.example.project2mvc.annotations.*;
import org.example.projectjspandjstl.entity.Applications;
import org.example.projectjspandjstl.entity.User;
import org.example.projectjspandjstl.service.ApplicationsService;
import org.example.projectjspandjstl.service.UserService;

import javax.servlet.ServletException;
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
        request.getServletContext().getRequestDispatcher("/WEB-INF/views/copu.jsp").forward(request, response);
    }

    @DeleteMapping("delete")
    public void usersDelete(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        UserService userService = new UserService();
        ApplicationsService applicationsService = new ApplicationsService();

        response.setCharacterEncoding("cp1251");
        response.setContentType("text/html;charset=cp1251");
        String id = request.getParameter("id");

        applicationsService.deleteApplicationsForId(id);
        userService.deieteUserForId(id);

        response.sendRedirect(request.getContextPath() + "/");

    }

    @PostMapping("add")
    public void usersPut(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, SQLException {

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

    @PutMapping("users/")
    public void usersInfo(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, SQLException {

        UserService userService = new UserService();
        int count = request.getRequestURI().length();
        String id = request.getRequestURI().substring(28);


        User user = userService.getAllUsersId(id);
        ArrayList<Applications> applicat = applicationsService.getApplications(id);


        request.setAttribute("applicat", applicat);
        request.getServletContext().getRequestDispatcher("/WEB-INF/views/mjsp1.jsp").forward(request, response);


    }


}
