package org.oefa.com.ssigner.infra.output.adapter;

import org.oefa.com.ssigner.configuration.ApplicationConfiguration;
import org.oefa.com.ssigner.domain.util.Log;
import org.oefa.com.ssigner.infra.output.port.DatabasePort;
import org.oefa.com.ssigner.util.LogUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;

public class H2DatabaseAdapter implements DatabasePort {

    private final String DB_HOST = ApplicationConfiguration.getKey("DB_HOST");
    private final String DB_USER = ApplicationConfiguration.getKey("DB_USER");
    private final String DB_PASS = ApplicationConfiguration.getKey("DB_PASS");


    @Override
    public void setupDatabase() throws Exception{
        this.createTables();
    }

    private void createTables() throws Exception{
        String query = "RUNSCRIPT FROM 'classpath:setupDB.sql'";
        this.executeAlterQuery(query);
    }

    private void executeAlterQuery(String query) throws Exception{
        try {
            Connection connection = this.getConnection();
            Statement statement = connection.createStatement();
            statement.execute(query);
            statement.close();
            connection.close();

        }catch (Exception e){
            LogUtil.setError(
                    new Log(
                            "Error creando tablas",
                            this.getClass().getName(),
                            e
                    )
            );
            throw e;
        }

    }

    private ArrayList<?> executeSelectQuery(String query) throws Exception{

        return new ArrayList<>();
    }

    private Connection getConnection() throws Exception{
        try {
            return DriverManager.getConnection(DB_HOST, DB_USER, DB_PASS);

        }catch (Exception e){
            LogUtil.setError(
                    new Log(
                            "Error conectando a la base de datos",
                            this.getClass().getName(),
                            e
                    )
            );
            throw e;
        }
    }

}
