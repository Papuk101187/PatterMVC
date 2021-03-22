package org.example.project2mvc.infrastructure;

import org.example.project2mvc.MainController;
import org.example.project2mvc.annotations.Controller;
import org.example.project2mvc.refction.PackageScanner;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class DispatcherServlet extends HttpServlet {


    private final PackageScanner packageScanner = new PackageScanner();
    private final ApplicationContext context = new ApplicationContext();

    public DispatcherServlet() throws IllegalAccessException {
    }


    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Class<?>> controllersClasses =

                packageScanner.findClassesWithAnnotation(Controller.class, "org.example");

        List<Object> controllers = controllersClasses.stream()
                .map(c -> context.getBeanByType(c))
                .collect(Collectors.toList());

        controllers.forEach(c -> System.out.println(((MainController)c).service));

    }


}
