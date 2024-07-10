package demo.jdbc.application;

import demo.jdbc.model.entity.Department;
import demo.jdbc.model.entity.Seller;

import java.util.Date;

public class Main {
    public static void main(String[] args) {
        Department obj = new Department(1, "books");

        Seller seller = new Seller(1, "Leonardo", "leo@mail.com", new Date(), 2.000, obj);

        System.out.println(seller);
    }
}