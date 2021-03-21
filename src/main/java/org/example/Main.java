package org.example;

import org.example.entity.User;
import org.example.service.UserService;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.SortedMap;


public class Main {


    public static void main(String[] args) throws UnsupportedEncodingException, FileNotFoundException {



        String s = "???°?????»????";
        String sq = new String(s.getBytes("Windows-1251"), "UTF-8");
        String sq1 = new String(s.getBytes("UTF-8"), "UTF-8");

        System.out.println(sq);
















    }
}
