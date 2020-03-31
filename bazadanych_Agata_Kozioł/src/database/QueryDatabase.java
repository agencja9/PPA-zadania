package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class QueryDatabase {
	public static void queryCustomer() { // otwieram po³¹czenie korzystaj¹c tylko z URL
        Connection connection = null;
        String url = "jdbc:hsqldb:hsql://localhost/sampledb;ifexists=true";
         
        try {
        	connection = DriverManager.getConnection(url); 
            Statement statement = connection.createStatement(); //tworzê statement
            String sql = "select * from CUSTOMER where ID like '3%' order by LASTNAME"; //komenda sql do zapytania
            ResultSet ResultSet =  statement.executeQuery(sql); //zbiór wyników zapytania
             
            // przegl¹danie zbioru wyników i polecenie wyœwietlania
            while(ResultSet.next()) {
                System.out.println("ID: " + ResultSet.getString("ID") + ", name: " + ResultSet.getString("LASTNAME") + " " + ResultSet.getString("FIRSTNAME")+ ", city: " + ResultSet.getString("CITY"));
            }

            ResultSet.close(); //zamkniêcie bazy danych
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
