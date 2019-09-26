package com.cfl.qq_42428264.thinks;

import com.cfl.qq_42428264.lang.exceptions.Demo;

import java.util.HashSet;

public class Test1 {
    public static void main(String[] args) throws CloneNotSupportedException {
//        HashSet<Demo1> set = new HashSet<Demo1>();
//        Demo1 d1 = new Demo1(1,"张三");
//        Demo1 d2 = new Demo1(1,"张三");
//        set.add(d1);
//        set.add(d2);
//        System.out.println("set = " + set);
        Address address = new Address("万州");
        Demo1 d1 = new Demo1(1,"张三");
        d1.setAdd(address);
        address.setAddress("上海");
        Demo1 clone = (Demo1)d1.clone();
        System.out.println("d1 = " + d1);
        System.out.println("clone = " + clone);
    }
}
