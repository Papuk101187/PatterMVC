package org.example;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PathMatcherTest {

    @Test
    public void test1() {

        PathMatcherNew pathMatcher = new PathMatcherNew();

        assertTrue(pathMatcher.match("/TomCats_war_exploded/users/1", "/TomCats_war_exploded/users/{id}"));



        System.out.println(pathMatcher.getAdress());





    }




}
