package org.example.project2mvc.infrastructure;

import org.example.PathMatcherNew;
import org.example.project2mvc.annotations.*;
import org.example.project2mvc.refction.PackageScanner;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.PathMatcher;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DispatcherServlet extends HttpServlet {

    private final PackageScanner packageScanner = new PackageScanner();
    private final ApplicationContext context = new ApplicationContext();
    private final List<Class<?>> controllers;


    PathMatcherNew pathMatcher = new PathMatcherNew();
    int COUNT = 0;


    public DispatcherServlet() throws IllegalAccessException {
        this.controllers = packageScanner
                .findClassesWithAnnotation(
                        Controller.class,
                        "org.example");
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {

        String address = null;


        for (Class<?> controller : controllers) {

            for (Method m : controller.getDeclaredMethods()) {

                System.out.println("****************** №"+COUNT+" ***************************");

                if (request.getMethod().equalsIgnoreCase("GET")
                        && m.isAnnotationPresent(GetMapping.class)) {
                    System.out.println("Аннотация GetMapping");
                    address = m.getAnnotation(GetMapping.class).value();
                }

                if (request.getMethod().equalsIgnoreCase("POST")
                        && m.isAnnotationPresent(DeleteMapping.class)) {
                    System.out.println("Аннотация DeleteMapping");
                    address = m.getAnnotation(DeleteMapping.class).value();
                }

                if (request.getMethod().equalsIgnoreCase("POST")
                        && m.isAnnotationPresent(PostMapping.class)) {
                    System.out.println("Аннотация PostMapping");
                    address = m.getAnnotation(PostMapping.class).value();
                }

                if (request.getMethod().equalsIgnoreCase("GET")
                        && m.isAnnotationPresent(PutMapping.class)) {
                    System.out.println("Аннотация PutMapping");
                    address = m.getAnnotation(PutMapping.class).value();
                }



                if (address == null) continue;
                System.out.println("***********************************************");
                System.out.println("I. request.getContextPath() "+request.getContextPath());
                System.out.println("II. address "+address);
                String addr = request.getContextPath() + "/" + address;
                System.out.println("II. addr "+addr);
                System.out.println("***********************************************");
                COUNT++;
                if (extracted(request, resp, controller, m, addr)) return;
            }

        }
        resp.setStatus(404);
        resp.getWriter().write("NOT FOUND");

    }

    private boolean extracted(HttpServletRequest request, HttpServletResponse resp, Class<?> controller, Method m, String addr) {
        PathMatcherNew pathMatcherNew = new PathMatcherNew();



        System.out.println("addr ="+addr);
        System.out.println("request.getRequestURI(); ="+request.getRequestURI());
        System.out.println("pathMatcherNew.match(request.getContextPath(),addr) ="+pathMatcherNew.match(request.getRequestURI(),addr));


        if (pathMatcherNew.match(request.getRequestURI(),addr)) {

            String pathMatcherKey = pathMatcherNew.getKey();
            String pathMatcherValue = pathMatcherNew.getAdress().get(pathMatcherKey);

            if(pathMatcherKey!=null&&pathMatcherValue!=null){
                request.setAttribute(pathMatcherKey,pathMatcherValue);
            }


            Object instance = context.getBeanByType(controller);
            try {
                m.invoke(instance, request, resp);
                return true;
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
