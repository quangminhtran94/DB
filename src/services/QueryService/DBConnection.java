import java.sql.*;
import java.util.HashMap;

/**
 * Created by QuangNgo on 3/11/16.
 */
public class DBConnection {

    private Connection c;
    boolean connected;

    public DBConnection() {
        c = null;
        connected = false;
    }

    public DBConnection(String host, int port, String dbname, String username, String password) {
        try {
            Class.forName("org.postgresql.Driver");
            String connectionString = "jdbc:postgresql://" + host + ":" + port + "/" + dbname;
            c = DriverManager.getConnection(connectionString, username, password);
            c.setAutoCommit(false);
            connected = true;
        }
        catch (Exception e) {
            connected = false;
            System.out.println(e.toString());
        }
    }

    public boolean isConnected() {
        return connected;
    }

    public HashMap queryAuthor(String query) {
        if (!connected) {
            return null;
        }
        HashMap result = null;
        try {
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            result = QueryClient.handleQueryAuthor(rs);
            rs.close();
            stmt.close();
        }
        catch (Exception e) {
            System.out.println(e.toString());
        }
        return result;
    }

    public int [][] queryMatrix(String query, HashMap authorMap, HashMap reverseMap) {
        int [][] result = new int [AuthorList.MAX][AuthorList.MAX];
        if (!connected) {
            return result;
        }
        try {
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            result = QueryClient.handleQueryMatrix(rs, authorMap, reverseMap);
            rs.close();
            stmt.close();
        }
        catch (Exception e) {
            System.out.println(e.toString());
        }
        return result;
    }

    public void close() {
        try {
            c.close();
        }
        catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}
