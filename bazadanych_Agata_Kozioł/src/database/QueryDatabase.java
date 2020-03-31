package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class QueryDatabase {
	public static void queryCustomer() { // otwieram po��czenie korzystaj�c tylko z URL
        Connection connection = null;
        String url = "jdbc:hsqldb:hsql://localhost/sampledb;ifexists=true";
         
        try {
        	connection = DriverManager.getConnection(url); 
            Statement statement = connection.createStatement(); //tworz� statement
            String sql = "select * from CUSTOMER where ID like '3%' order by LASTNAME"; //komenda sql do zapytania
            ResultSet ResultSet =  statement.executeQuery(sql); //zbi�r wynik�w zapytania
             
            // przegl�danie zbioru wynik�w i polecenie wy�wietlania
            while(ResultSet.next()) {
                System.out.println("ID: " + ResultSet.getString("ID") + ", name: " + ResultSet.getString("LASTNAME") + " " + ResultSet.getString("FIRSTNAME")+ ", city: " + ResultSet.getString("CITY"));
            }

            ResultSet.close(); //zamkni�cie bazy danych
            statement.close();
        }
        catch (SQLException exp) {
            System.err.println(exp.getMessage());
        }
        finally {
            try {
                if (connection != null) 
                	connection.close();
            }
            catch (SQLException exp) {
                System.err.println(exp.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        queryCustomer();
    }
}
