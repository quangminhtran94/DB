import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

public class QueryService {

    public static AuthorList init() {
        ArrayList<String> temp = new ArrayList<>();
        temp.add("Paul Fischer");
        temp.add("Sanjeev Saxena");
        temp.add("Hans Ulrich Simon");
        return new AuthorList(temp);
    }

    public static HashMap getAuthorMap(DBConnection dc, AuthorList authorList) {
        String query = "SELECT id, name FROM author WHERE name IN " + authorList + " ORDER BY id ASC";
        return dc.queryAuthor(query);
    }

    public static int [][] getAuthorMatrix(DBConnection dc, String idSet, HashMap authorMap, HashMap reverseMap) {
        String query = "SELECT o1.author_id AS author1, o2.author_id AS author2, COUNT(*) AS count " +
                "FROM owner o1, owner o2 " +
                "WHERE o1.pub_id = o2.pub_id AND o1.author_id IN " + idSet + " AND o2.author_id IN " + idSet + " " +
                "AND o1.author_id < o2.author_id " +
                "GROUP BY o1.author_id, o2.author_id " +
                "ORDER BY o1.author_id, o2.author_id";
        return dc.queryMatrix(query, authorMap, reverseMap);
    }

    public static DBConnection connectDatabase() {
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader("src/config.json"));
            JSONObject jsonObject = (JSONObject) obj;

            String dbname   = (String) jsonObject.get("dbname");
            String username = (String) jsonObject.get("username");
            String password = (String) jsonObject.get("password");
            String host     = (String) jsonObject.get("host");
            int   port     =  ((Long) (jsonObject.get("port"))).intValue();
            return new DBConnection(host, port, dbname, username, password);
        }
        catch (Exception e) {
            System.out.println(e.toString());
        }
        return null;
    }

    public static int [][] getCollaborationMatrix(ArrayList<String> list) {
        AuthorList authorList = new AuthorList(list);
        DBConnection dc = connectDatabase();
        int [][] result = new int[AuthorList.MAX][AuthorList.MAX];
        if (dc.isConnected()) {
            System.out.println("Connected to database");
            HashMap authorMap = getAuthorMap(dc, authorList);
            String idSet = authorMap.keySet().toString().replace("[", "(").replace("]", ")");
            result = getAuthorMatrix(dc, idSet, authorMap, authorList.getReverseMap());
            dc.close();
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println(getCollaborationMatrix(init())[0][2]);
    }
}

/*
SELECT o1.author_id AS author1, o2.author_id AS author2, COUNT(*) AS count
FROM owner o1, owner o2
WHERE o1.pub_id = o2.pub_id AND o1.author_id IN temp.id AND o2.author_id IN temp.id
GROUP BY o1.author_id, o2.author_id
ORDER BY o1.author_id, o2.author_id;

*/