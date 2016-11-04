import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by QuangNgo on 3/11/16.
 */
public class AuthorList {

    private ArrayList<String> listAuthor;
    private int noOfAuthor;

    public AuthorList() {
        this.listAuthor = new ArrayList<String>();
        this.noOfAuthor = 0;
    }

    public AuthorList(ArrayList<String> listAuthor) {
        this.listAuthor = listAuthor;
        this.noOfAuthor = listAuthor.size();
    }

    public String toString() {
        String rs = "";
        for (int i = 0; i < noOfAuthor - 1; i++) {
            rs += "'" + listAuthor.get(i) + "', ";
        }
        rs += "'" + listAuthor.get(noOfAuthor - 1) + "'";
        rs = "(" + rs + ")";
        return rs;
    }

    public HashMap getReverseMap() {
        HashMap result = new HashMap();
        for (int i = 0; i < noOfAuthor; i++) {
            result.put(listAuthor.get(i), i);
        }
        return result;
    }

    public static final int MAX = 8;
}
