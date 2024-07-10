package demo.jdbc.application;

import demo.jdbc.model.entity.Department;

public class Main {
    public static void main(String[] args) {
        Department obj = new Department(1, "books");
        System.out.println(obj);
    }
}