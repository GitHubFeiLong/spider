package com.cfl.qq_42428264.thinks;

public class Test2 {
    public static void main(String[] args) {
        // 测试Integer 对象相等
        Integer i01 = 59;
        int i02 = 59;
        Integer i03 = Integer.valueOf(59);
        Integer i04 = new Integer(59);
        System.out.println("i01 == i02 " + (i01 == i02));
        System.out.println("i01 == i03 " + (i01 == i03));
        System.out.println("i01 == i04 " + (i01 == i04));
        System.out.println("i02 == i03 " + (i02 == i03));
        System.out.println("i02 == i04 " + (i02 == i04));
        System.out.println("i03 == i04 " + (i03 == i04));
    }
}
