import java.sql.ResultSet;
import java.util.HashMap;

/**
 * Created by QuangNgo on 4/11/16.
 */
public class QueryClient {

    public static HashMap handleQueryAuthor(ResultSet rs) {
        HashMap result = new HashMap();
        if (rs != null) {
            System.out.println("Got result set");
            try {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    result.put(id, name);
                }
            }
            catch (Exception e) {
                System.out.println(e.toString());
            }
        }
        return result;
    }

    public static int [][] handleQueryMatrix(ResultSet rs, HashMap authorMap, HashMap reverseMap) {
        int[][] result = new int[AuthorList.MAX][AuthorList.MAX];
        if (rs != null) {
            try {
                while (rs.next()) {
                    int author1_id = rs.getInt("author1");
                    int author2_id = rs.getInt("author2");
                    int count = rs.getInt("count");
                    String author1_name = authorMap.get(author1_id).toString();
                    String author2_name = authorMap.get(author2_id).toString();
                    int index1 = (int)(reverseMap.get(author1_name));
                    int index2 = (int)(reverseMap.get(author2_name));
                    result[index1][index2] = count;
                    result[index2][index1] = count;
                }
            }
            catch (Exception e) {
                System.out.println(e.toString());
            }
        }
        return result;
    }
}
