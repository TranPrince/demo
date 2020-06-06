package com.prince.demo.contraller;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Prince
 * @date 2020/5/31 22:37
 */
public class Test {


    public static void main(String[] args){

//        ConcurrentHashMap<String,String> c = new ConcurrentHashMap<>();
//        for (int i = 0; i < 20; i++) {
//            Object s = c.put((char) (i + 65) + (char) (i + 66) + (char) (i + 67) + "", i + "1332");
//            System.out.println((char) (i + 65) + (char) (i + 66) + (char) (i + 67) + "");
//        }

//        String s0 = "good";


        String s1 = "abc";
        final String s2 = "a";
        final String s3 = "bc";
        String s4 = s2 + s3;
        System.out.println(s1 == s4);


//        String s1 = new StringBuilder("go").append("od").toString();
//        System.out.println(s1.intern() == s1);
//
//        String s2 = new StringBuilder("go").toString();
//        System.out.println(s2.intern() == s2);

//        System.out.println(System.identityHashCode(s1.intern()));
//        System.out.println(System.identityHashCode(s0));


//        String s2 = new StringBuilder("ja").append("va").toString();
//        System.out.println(s2.intern() == s2);
//        System.out.println(System.identityHashCode(s1));
//        System.out.println(System.identityHashCode(s1.intern()));

//        String s2 = "java";
//        System.out.println(s2.intern() == s2);
//        String s4 = new String("java");
//        System.out.println(s4.intern() == s4);
//        System.out.println(s2 == s4.intern());
//
//        System.out.println("---------------------------------");
//
//        String s7 = "good";
//        System.out.println(s7.intern() == s7);
//        String s8 = new String("good");
//        System.out.println(s8.intern() == s8);
//        System.out.println(s7 == s8.intern());



//        System.out.println(System.identityHashCode(s2));
//        System.out.println(System.identityHashCode(new String("java")));
//        String s3 = s2;
//        System.out.println(System.identityHashCode(s3));
//        System.out.println(System.identityHashCode(s2.intern()));





    }
}
