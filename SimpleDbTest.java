import java.sql.*;

import org.junit.jupiter.api.*;

import static java.sql.DriverManager.getConnection;

/**
 * Created by pwilkin on 27-Apr-20.
 */
public class SimpleDbTest {

    private static final String DBDESC = "jdbc:hsqldb:mem:test";

    @BeforeAll
    public static void prepareDatabase() {
        try (Connection c = getConnection(DBDESC, "SA", "")) {
            c.createStatement().execute("CREATE TABLE TESTING (ID INT PRIMARY KEY IDENTITY, TCOL VARCHAR(255), NUM DECIMAL(8, 2))");
            try (PreparedStatement ps = c.prepareStatement("INSERT INTO TESTING (TCOL, NUM) VALUES (?, ?)")) {
                ps.setString(1, "val1");
                ps.setDouble(2, 4.2);
                ps.execute();
                ps.setString(1, "val2");
                ps.setDouble(2, 5.0);
                ps.execute();
                ps.setString(1, "val3");
                ps.setDouble(2, -4.3);
                ps.execute();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @AfterAll
    public static void destroyDatabase() {
        try (Connection c = getConnection(DBDESC, "SA", "")) {
            c.createStatement().execute("DROP TABLE TESTING");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    @Test
    public void testConnection() {
        try (Connection c = getConnection(DBDESC, "SA", "")) {
            c.createStatement().executeQuery("SELECT * FROM TESTING");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testThreeEntries() {
        try (Connection c = getConnection(DBDESC, "SA", "")) {
            int cnt = 0;
            try (ResultSet rs = c.createStatement().executeQuery("SELECT * FROM TESTING")) {
                while (rs.next()) {
                    cnt++;
                }
            }
            Assertions.assertEquals(cnt, 3);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    //ZADANIE
    @Test     // sprawdzic, czy uda sie usunac jeden wpis z tabeli
    public void testDeleteEntry() { 
        try (Connection c = getConnection(DBDESC, "SA", "")) {
        	c.setAutoCommit(false);
        	c.createStatement().execute("DELETE FROM TESTING WHERE ID=0");
            int cnt = 0;
            try (ResultSet rs = c.createStatement().executeQuery("SELECT * FROM TESTING")) {
                while (rs.next()) {
                    cnt++;
                }
            }
            Assertions.assertEquals(cnt, 2);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    @Test     // sprawdzic, czy uda sie dodac jeden nowy wpis do tabeli
    public void testAddEntry() {
        try (Connection c = getConnection(DBDESC, "SA", "")) {
        	c.setAutoCommit(false);
        	try (PreparedStatement ps = c.prepareStatement("INSERT INTO TESTING (TCOL, NUM) VALUES (?, ?)")) {
                ps.setString(1, "val4");
                ps.setDouble(2, 5.1);
                ps.execute();
        	}
            int cnt = 0;
            try (ResultSet rs = c.createStatement().executeQuery("SELECT * FROM TESTING")) {
                while (rs.next()) {
                    cnt++;
                }
            }
            Assertions.assertEquals(cnt, 4);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    @Test    // sprawdzic, czy po dodaniu jednego wpisu i usunieciu jednego wpisu nadal w tabeli sa trzy wpisy
    public void testAddDeleteEntry() {
        try (Connection c = getConnection(DBDESC, "SA", "")) {
        	c.setAutoCommit(false);
        	try (PreparedStatement ps = c.prepareStatement("INSERT INTO TESTING (TCOL, NUM) VALUES (?, ?)")) {
                ps.setString(1, "val4");
                ps.setDouble(2, 9.0);
                ps.execute();
        	}
        	c.createStatement().execute("DELETE FROM TESTING WHERE ID=0");
            int cnt = 0;
            try (ResultSet rs = c.createStatement().executeQuery("SELECT * FROM TESTING")) {
                while (rs.next()) {
                    cnt++;
                }
            }
            Assertions.assertEquals(cnt, 3);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    
    @Test     // sprawdzic, czy po dodaniu wpisu z wartoscia 10.0 maksymalna wyciagnieta wartosc (podpowiedz: SELECT MAX(NUM) ...) wynosi 10.0)
    public void testAddTenEntry() {
    	final double epsilon = 0.00001;
        try (Connection c = getConnection(DBDESC, "SA", "")) {
        	c.setAutoCommit(false);
        	try (PreparedStatement ps = c.prepareStatement("INSERT INTO TESTING (TCOL, NUM) VALUES (?, ?)")) {
                ps.setString(1, "val4");
                ps.setDouble(2, 10.0);
                ps.execute();
        	}
        	boolean rd = false;
            try (ResultSet rs = c.createStatement().executeQuery("SELECT MAX(NUM) AS M FROM TESTING")) {    
            	rs.next();
            	double cnt = Math.abs(rs.getDouble("M") - 10.0);
                    if (cnt < epsilon) {
                    	rd = true;
                    }        	
            Assertions.assertTrue(rd);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
}
