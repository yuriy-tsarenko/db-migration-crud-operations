package com.goit.crud;

import com.goit.conf.FlywayConfigurations;
import com.goit.conf.LoggingConfiguration;
import com.goit.crud.datasource.Datasource;
import com.goit.crud.entity.CustomerEntity;
import com.goit.crud.repository.CustomerRepositoryIml;
import com.goit.crud.repository.JDBCRepository;

import java.io.IOException;
import java.util.List;

public class CrudApplication {
    public static void main(String[] args) throws IOException {
        new LoggingConfiguration();
        new FlywayConfigurations().setup().migrate();
        JDBCRepository<CustomerEntity> repository = new CustomerRepositoryIml(new Datasource());
        List<CustomerEntity> all = repository.findAll();
        System.out.println(all);
    }
}
