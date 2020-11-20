package com.company;

import java.sql.*;

public class Database {



    private final String TABLE_OBCE = "obce";
    private final String TABLE_CASTI_OBCE = "casti_obce";

    private final String COLUMN_NAZEV = "nazev";
    private final String COLUMN_KOD = "kod";
    private final String COLUMN_KOD_OBCE = "kod_obce";

    private String database_path;

    public Database(String database_path) {
        this.database_path = "jdbc:sqlite:" + database_path;
    }

    public void vytvor(){

        try{
            Connection connection = DriverManager.getConnection(database_path);
            Statement statement = connection.createStatement();

            statement.execute("DROP TABLE IF EXISTS " + TABLE_OBCE);
            statement.execute("DROP TABLE IF EXISTS " + TABLE_CASTI_OBCE);


            statement.execute("CREATE TABLE IF NOT EXISTS " + TABLE_OBCE +
                    " (" + COLUMN_NAZEV + " text," +
                           COLUMN_KOD +   " text" +
                    ")");

            statement.execute("CREATE TABLE IF NOT EXISTS " + TABLE_CASTI_OBCE +
                    " (" + COLUMN_NAZEV +    " text," +
                           COLUMN_KOD +      " text, " +
                           COLUMN_KOD_OBCE + " text" +
                    ")");

            connection.close();
            statement.close();

        } catch (SQLException throwables) {
            System.out.println("Chyba při vytváření databáze: " + throwables.getMessage());
            throwables.printStackTrace();
        }
    }

    public void vlozKolekci(KolekceDat kolekceDat){

        try{
            Connection connection = DriverManager.getConnection(database_path);
            PreparedStatement preparedStatement;
            //Statement statement = connection.createStatement();

            for (Obec obec : kolekceDat.getObce()){

                preparedStatement = connection.prepareStatement("INSERT INTO " + TABLE_OBCE +
                        " (" + COLUMN_NAZEV + ", " +
                        COLUMN_KOD +
                        " ) " +
                        "VALUES(?, ?)");
                preparedStatement.setString(1, obec.getNazev());
                preparedStatement.setString(2, obec.getKod());
                preparedStatement.executeUpdate();

        }
            for (CastObce castObce : kolekceDat.getCastiObce()){

                preparedStatement = connection.prepareStatement("INSERT INTO " + TABLE_CASTI_OBCE +
                        " (" + COLUMN_NAZEV + ", " +
                        COLUMN_KOD + ", " +
                        COLUMN_KOD_OBCE +
                        " ) " +
                        "VALUES(?, ?, ?)");
                preparedStatement.setString(1, castObce.getNazev());
                preparedStatement.setString(2, castObce.getKod());
                preparedStatement.setString(3, castObce.getKodObce());
                preparedStatement.executeUpdate();

            }

            connection.close();


    } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


    }
    }
