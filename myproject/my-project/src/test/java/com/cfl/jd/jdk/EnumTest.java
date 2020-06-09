package com.cfl.jd.jdk;

/**
 * @ClassName EnumTest
 * @Author msi
 * @Date 2020/6/9 11:58
 * @Version 1.0
 */
public enum EnumTest {
    SPRING(100), SUMMER(101), AUTUMN(102), WINTER(103);
    private int seq;
    EnumTest(int seq) {
        this.seq = seq;
    }
    public int getSeq() {
        return seq;
    }
}
