package com.goit.crud.repository;

import com.goit.crud.datasource.Datasource;
import com.goit.crud.entity.CustomerEntity;
import com.goit.crud.exception.DatasourceException;
import org.apache.log4j.Logger;
import org.intellij.lang.annotations.Language;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

//@Log4j
//DAO - DATA ACCESS OBJECT
public class CustomerRepositoryIml extends JDBCRepository<CustomerEntity> {

    private static final Logger LOG = Logger.getLogger(CustomerRepositoryIml.class);
    private static final String TABLE_NAME = "customers";

    @Language("SQL")
    private static final String INSERT_CUSTOMER = """
            INSERT INTO customers (customer_id, customer_name, contact_name, country) VALUES (DEFAULT, ?, ?, ?)
            """;

    @Language("SQL")
    private static final String UPDATE_CUSTOMER = """
            UPDATE customers SET customer_name=?, contact_name=?, country=? WHERE id=?;
            """;

    public CustomerRepositoryIml(Datasource datasource) {
        super(datasource);
        LOG.info("Created CustomerRepositoryIml");
    }

    @Override
    public List<CustomerEntity> findAll() {
        LOG.info("loading all customers");
        try {
            PreparedStatement preparedStatement = datasource.preparedStatement(getFindAllQuery(TABLE_NAME), true);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<CustomerEntity> customerEntities = new ArrayList<>();
            while (resultSet.next()) {
                CustomerEntity customerEntity = parseCustomerRow(resultSet);
                customerEntities.add(customerEntity);
            }
            datasource.close();
            LOG.info("all customers loaded");
            return customerEntities;
        } catch (Exception e) {
            LOG.error(e);
            throw new DatasourceException("findAll", e);
        }
    }

    @Override
    public CustomerEntity findById(Long id) {
        try {
            PreparedStatement preparedStatement = datasource.preparedStatement(getFindByIdQuery(TABLE_NAME), true);
            preparedStatement.setString(1, "customer_id");
            preparedStatement.setLong(2, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            boolean next = resultSet.next();
            if (!next) {
                return null;
            }
            CustomerEntity customerEntity = parseCustomerRow(resultSet);
            datasource.close();
            return customerEntity;
        } catch (Exception e) {
            LOG.error(e);
            throw new DatasourceException("findById", e);
        }
    }

    @Override
    public CustomerEntity save(CustomerEntity entity) {
        try {
            CustomerEntity byId = findById(entity.getCustomerId());
            if (Objects.isNull(byId)) {
                PreparedStatement preparedStatement = datasource.preparedStatement(INSERT_CUSTOMER, true);
                final int nameIndex = 1;
                final int contactNameIndex = 2;
                final int countryIndex = 3;

                preparedStatement.setString(nameIndex, entity.getCustomerName());
                preparedStatement.setString(contactNameIndex, entity.getContactName());
                preparedStatement.setString(countryIndex, entity.getCountry());
                preparedStatement.executeUpdate();
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                generatedKeys.first();
                long customerId = generatedKeys.getLong("customer_id");
                entity.setCustomerId(customerId);
                datasource.close();
                return entity;
            } else {
                return update(entity);
            }
        } catch (Exception e) {
            LOG.error(e);
            throw new DatasourceException("save", e);
        }
    }

    @Override
    public void delete(Long id) {
        try {
            PreparedStatement preparedStatement = datasource.preparedStatement(getDeleteByIdQuery(TABLE_NAME));
            final int customerIdIndex = 1;
            final int idIndex = 2;
            preparedStatement.setString(customerIdIndex, "customer_id");
            preparedStatement.setLong(idIndex, id);
            preparedStatement.executeUpdate();
            datasource.close();
        } catch (Exception e) {
            LOG.error(e);
            throw new DatasourceException("delete", e);
        }
    }

    private CustomerEntity update(CustomerEntity entity) {
        try {
            PreparedStatement preparedStatement = datasource.preparedStatement(UPDATE_CUSTOMER, true);
            final int nameIndex = 1;
            final int contactNameIndex = 2;
            final int countryIndex = 3;
            final int idIndex = 4;

            preparedStatement.setString(nameIndex, entity.getCustomerName());
            preparedStatement.setString(contactNameIndex, entity.getContactName());
            preparedStatement.setString(countryIndex, entity.getCountry());
            preparedStatement.setLong(idIndex, entity.getCustomerId());
            preparedStatement.executeUpdate();
            datasource.close();
            return entity;
        } catch (Exception e) {
            LOG.error(e);
            throw new DatasourceException("save", e);
        }
    }

    private CustomerEntity parseCustomerRow(ResultSet resultSet) throws SQLException {
        Long customerId = resultSet.getLong("customer_id");
        String customerName = resultSet.getString("customer_name");
        String contactName = resultSet.getString("contact_name");
        String country = resultSet.getString("country");
        return CustomerEntity.of(customerId, customerName, contactName, country);
    }
}
