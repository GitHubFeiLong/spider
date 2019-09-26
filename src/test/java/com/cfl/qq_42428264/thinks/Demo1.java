package com.cfl.qq_42428264.thinks;

import java.util.Arrays;
import java.util.Objects;

public class Demo1 implements Cloneable{
    private Integer id;
    private String name;
    private Integer[] arr = {1,2,3};
    private Address add;


    public Demo1(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public Demo1 clone() throws CloneNotSupportedException {
        Demo1 demo1 = null;
        demo1 = (Demo1)super.clone();
        demo1.add = add.clone();
        return demo1;
    }

    public Address getAdd() {
        return add;
    }

    public void setAdd(Address add) {
        this.add = add;
    }

    public Integer[] getArr() {
        return arr;
    }

    public void setArr(Integer[] arr) {
        this.arr = arr;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    @Override
    public String toString() {
        return "Demo1{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", arr=" + Arrays.toString(arr) +
                ", add=" + add +
                '}';
    }
}
