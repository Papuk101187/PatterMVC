package org.example.project2mvc;

import org.example.projectjspandjstl.entity.User;
import org.example.projectjspandjstl.service.ApplicationsService;
import org.example.projectjspandjstl.service.UserService;
import org.example.project2mvc.annotations.Autowired;
import org.example.project2mvc.annotations.Controller;
import org.example.project2mvc.annotations.GetMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
public class MainController extends JsonController {


    @Autowired
    public UserService service;
    @Autowired
    public ApplicationsService applicationsService;


    @GetMapping("/")
    public void usersPage(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        List<User> users = service.getAllUsers();
        request.setAttribute("users", users);
        request.getServletContext().getRequestDispatcher("/WEB-INF/views/root.jsp").forward(request, response);
    }

}
