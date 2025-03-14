package org.admin.service.catalog.infrastructure;

import org.admin.service.catalog.application.UseCase;

public class Application {
    public static void main(String[] args) {
        System.out.println("Hello World!");
        System.out.println(new UseCase().execute());
    }
}