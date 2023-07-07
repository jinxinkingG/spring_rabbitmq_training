package com.example.demo;

import java.util.*;

public class MainTest {
    public static void main(String[] args) {
        Set<String> test = new HashSet<>();
        StringBuilder testB = new StringBuilder();
        testB.append("dsaf");
        System.out.print(testB);
        String testStr = test.toString();
        testStr=testStr.substring(1,testStr.length()-1);
        String[] str = testStr.split(",");
        List<String> list = new ArrayList<>(Arrays.asList(str));
        System.out.print(list);
    }
}
