import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;



public class Main {
public static Connection connection;
public static Statement stmt;
//


    public static void main(String[] args) {
        connect();
//        getInfo("names", "id", "1");
//        addInfo("names", "name, score", "'name3', 30");
//       createTable("students", "id", 2);
//deleteInfo("names", "id", "1");
        dropTable("names");

disconnect();
    }

    public static void createTable (String tableName, String id, int rows) {
        StringBuilder str = new StringBuilder();
        BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
        try {
        str.append("CREATE TABLE " + tableName + " ( " + id + " INTEGER PRIMARY KEY AUTOINCREMENT, ");
        for (int i = 1; i <= rows; i++) {
            System.out.print("Наименование столбца " + i + " : ");
            str.append(consoleReader.readLine() + " ");
            System.out.print("Тип значений : ");
            str.append(consoleReader.readLine() + " ");
            if (i<rows) str.append(", ");
        }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            str.append(");");
            try {
                stmt.executeUpdate(str.toString());
                consoleReader.close();
                disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

public static void addInfo (String tableName, String columns, String values) {
        StringBuilder str = new StringBuilder("INSERT INTO " + tableName + " ( ");
        str.append(columns + " ) ");
        str.append("VALUES ( " + values + " );");
    try {
        stmt.executeUpdate(str.toString());
    } catch (SQLException throwables) {
        throwables.printStackTrace();
    }
}


    public static void getInfo (String tableName, String id_column, String id) {
      try {
       ResultSet rs = stmt.executeQuery("SELECT * FROM " + tableName + " WHERE " + id_column + " = " + id);
       ResultSetMetaData rsmd = rs.getMetaData();
       for (int i = 1; i <=rsmd.getColumnCount(); i++)
          System.out.println(rsmd.getColumnName(i) + " " + rs.getString(i));

        } catch (Exception throwables) {
            throwables.printStackTrace();
        }
    }

    public static void deleteInfo (String tableName, String idColumn, String id) {
        StringBuilder str = new StringBuilder("DELETE FROM " + tableName + " WHERE " + idColumn + " = " + id);

        try {
            stmt.executeUpdate(str.toString());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public static void dropTable (String tableName) {
        StringBuilder str = new StringBuilder("DROP TABLE " + tableName);
        try {
            stmt.executeUpdate(str.toString());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void connect() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:lesson2.db");
            stmt = connection.createStatement();
            System.out.println("Connected");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void disconnect() {
        try {
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}
