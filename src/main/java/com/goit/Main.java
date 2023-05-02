package com.goit;

import com.goit.conf.FlywayConfigurations;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
//        FlywayConfigurations flywayConfigurations = new FlywayConfigurations();
//        flywayConfigurations.setup();
//        flywayConfigurations.migrate();

        new FlywayConfigurations()
                .setup()
                .migrate();
    }
}