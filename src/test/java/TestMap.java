import com.google.common.collect.RowSortedTable;
import com.google.common.collect.TreeBasedTable;

/**
 * Created by xuhaifeng on 2016/6/22.
 */
public class TestMap {
    public static void main(String[] args) {
        RowSortedTable<String, String, String> table = TreeBasedTable.create();
        table.put("G1", "C1", "U2");
        table.put("G1", "C2", "U2");
        table.put("G3", "C3", "U1");
        System.out.println(table.size());
        System.out.println(table.row("G1"));
        System.out.println(table.column("C2"));
        System.out.println(table.get("G1", "C2"));
        System.out.println(table.values());

    }
}
