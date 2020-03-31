package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UpdateDatabase {
	public static void updateCustomer() { // otwieram po��czenie korzystaj�c tylko z URL
        Connection connection = null;
        String url = "jdbc:hsqldb:hsql://localhost/sampledb;ifexists=true";
         
        try {
        	connection = DriverManager.getConnection(url);
             
            // najpierw baza danych jest zmieniana, a potem wy�wietlana, �eby zobaczy� co si� zmieni�o
            String sqlupdate = "update CUSTOMER set CITY='Cracow', FIRSTNAME='Sammie' where ID between 11 and 13";
            String sqlquery = "select * from CUSTOMER";
            Statement statement = connection.createStatement();
            int rowsAffected    = statement.executeUpdate(sqlupdate);
            ResultSet ResultSet =  statement.executeQuery(sqlquery);
             
            // przegl�danie zbioru wynik�w i polecenie wy�wietlania
            while(ResultSet.next()) {
            	System.out.println("ID: " + ResultSet.getString("ID") + ", name: " + ResultSet.getString("LASTNAME") + " " + ResultSet.getString("FIRSTNAME")+ ", city: " + ResultSet.getString("CITY"));
            }

            ResultSet.close();
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
        updateCustomer();
    }
}
