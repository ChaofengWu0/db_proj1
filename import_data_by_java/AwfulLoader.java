import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;
import java.util.Properties;
import java.sql.*;
import java.net.URL;

public class AwfulLoader {
    private static URL propertyURL = AwfulLoader.class
            .getResource("/loader.cnf");

    private static Connection con = null;
    private static boolean verbose = false;

    private static void openDB(String host, String dbname,
                               String user, String pwd) {
        try {
            //
            Class.forName("org.postgresql.Driver");
        } catch (Exception e) {
            System.err.println("Cannot find the Postgres driver. Check CLASSPATH.");
            System.exit(1);
        }
        String url = "jdbc:postgresql://" + host + "/" + dbname;
        Properties props = new Properties();
        props.setProperty("user", user);
        props.setProperty("password", pwd);
        try {
            con = DriverManager.getConnection(url, props);
            if (verbose) {
                System.out.println("Successfully connected to the database "
                        + dbname + " as " + user);
            }
            con.setAutoCommit(false);
        } catch (SQLException e) {
            System.err.println("Database connection failed");
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }

    private static void closeDB() {
        if (con != null) {
            try {
                con.close();
                con = null;
            } catch (Exception e) {
                // Forget about it
            }
        }
    }

    private static void loadData_for_client_enterprise(String client_enterprise, String supply_center, String country, String
            city, String industry
            , HashMap<String, Integer> map_for_table_cli)
            throws SQLException {
        Statement stmt0;
        if (con != null) {
            if (map_for_table_cli.get(client_enterprise) == null) {
                map_for_table_cli.put(client_enterprise, 1);
                stmt0 = con.createStatement();
                client_enterprise = polish(client_enterprise);
                city = polish(city);
                country = polish(country);
                stmt0.execute("insert into client_enterprise(client_enterprise,supply_center,country,city,industry) values('"
                        + client_enterprise + "','" + supply_center + "','" + country + "','" + city + "','" + industry + "')");
                con.commit();
            }
        }
    }


    private static void loadData_for_contract(String contract_number, String contract_date, String director, String
            client_enterprise, HashMap<String, Integer> map_for_table_con)
            throws SQLException {
        Statement stmt1;
        if (con != null) {
            if (map_for_table_con.get(contract_number) == null) {
                map_for_table_con.put(contract_number, 1);
                stmt1 = con.createStatement();
                client_enterprise = polish(client_enterprise);
                director = polish(director);
                stmt1.execute("insert into contract(contract_number,contract_date,director,client_enterprise) values('"
                        + contract_number + "','" + contract_date + "','" + director + "','" + client_enterprise + "')");
                con.commit();
            }
        }
    }


    private static void loadData_for_order(String product_code, String product_model, int quantity, String salesman_number,
                                           String estimated_date, String lodgement_date, String contract_number)
            throws SQLException {
        Statement stmt2;
        if (con != null) {
            stmt2 = con.createStatement();
            product_model = polish(product_model);
            stmt2.execute("insert into order_table(product_code,product_model,quantity,salesman_number" +
                    ",estimated_date,lodgement_date,contract_number) values ('" +
                    product_code + "','" + product_model + "','" + quantity + "','" + salesman_number + "','" +
                    estimated_date + "','" + lodgement_date + "','" + contract_number + "')");
            con.commit();
        }
    }


    private static void loadData_for_model(String product_model, int unit_price, String
            product_code, HashMap<String, Integer> map_for_table_model)
            throws SQLException {
        Statement stmt3;
        if (con != null) {
            if (map_for_table_model.get(product_model) == null) {
                map_for_table_model.put(product_model, 1);
                stmt3 = con.createStatement();
                product_model = polish(product_model);
                stmt3.execute("insert into model(product_model,unit_price,product_code) values('" +
                        product_model + "','" + unit_price + "','" + product_code + "')");
                con.commit();
            }
        }
    }

    private static void loadData_for_product(String product_code, String
            product_name, HashMap<String, Integer> map_for_table_pro)
            throws SQLException {
        Statement stmt4;
        if (con != null) {
            if (map_for_table_pro.get(product_code) == null) {
                map_for_table_pro.put(product_code, 1);
                stmt4 = con.createStatement();
                product_name = polish(product_name);
                stmt4.execute("insert into product(product_code,product_name) values('"
                        + product_code + "','" + product_name + "')");
                con.commit();
            }
        }
    }

    private static void loadData_for_supply_center(String supply_center, HashMap<String, Integer> map_for_supply_center)
            throws SQLException {
        Statement stmt5;
        if (con != null) {
            if (map_for_supply_center.get(supply_center) == null) {
                map_for_supply_center.put(supply_center, 1);
                stmt5 = con.createStatement();
                stmt5.execute("insert into supply_center(supply_center) values ('" + supply_center + "')");
                con.commit();
            }
        }
    }


    private static void loadData_for_salesman(String salesman_number, String salesman_name, String gender,
                                              int age, String mobile_phone, String supply_center, HashMap<String, Integer> map_for_table_sale)
            throws SQLException {
        Statement stmt6;
        if (con != null) {
            if (map_for_table_sale.get(salesman_number) == null) {
                map_for_table_sale.put(salesman_number, 1);
                stmt6 = con.createStatement();
                stmt6.execute("insert into salesman(salesman_number,salesman_name,gender" +
                        ",age,mobile_phone,supply_center) values('" + salesman_number + "','" +
                        salesman_name + "','" + gender + "','" + age + "','" + mobile_phone + "','"
                        + supply_center + "')");
                con.commit();
            }
        }
    }


    public static void main(String[] args) {
        String fileName = null;
        boolean verbose = false;
        long start;
        long end;

        switch (args.length) {
            case 1:
                fileName = args[0];
                break;
            case 2:
                switch (args[0]) {
                    case "-v":
                        verbose = true;
                        break;
                    default:
                        System.err.println("Usage: java [-v] AwfulLoader filename");
                        System.exit(1);
                }
                fileName = args[1];
                break;
            default:
                System.err.println("Usage: java [-v] AwfulLoader filename");
                System.exit(1);
        }

        if (propertyURL == null) {
            System.err.println("No configuration file (loader.cnf) found");
            System.exit(1);
        }
        Properties defprop = new Properties();
        defprop.put("host", "localhost");
        defprop.put("user", "u99999999");
        defprop.put("password", "99999999");
        defprop.put("database", "postgres");
        Properties prop = new Properties(defprop);
        try (BufferedReader conf
                     = new BufferedReader(new FileReader(propertyURL.getPath()))) {
            prop.load(conf);
        } catch (IOException e) {
            // Ignore
            System.err.println("No configuration file (loader.cnf) found");
        }
        try (BufferedReader infile
                     = new BufferedReader(new FileReader(fileName))) {
            String line;
            String[] parts;
            int cnt = 0;
            // Empty target table
            openDB(prop.getProperty("host"), prop.getProperty("database"),
                    prop.getProperty("user"), prop.getProperty("password"));
            Statement statement;
            if (con != null) {
                statement = con.createStatement();
                statement.execute("truncate table client_enterprise " +
                        ",contract,model,order_table,product,salesman,supply_center");
                con.commit();
                statement.close();
            }
            closeDB();
            //


            String contract_number;
            String
//                    []
                    client_enterprise
//                    = new String[2]
                    ;
            String supply_center;
            String country;
            String city;
            String industry;
            String product_code;
            String product_name;
            String product_model;
            int unit_price;
            int quantity;
            String contract_date;
            String estimated_delivery_date;
            String lodgement_date;
            String director;
            String salesman;
            String salesman_number;
            String gender;
            int age;
            String mobile_phone;


            // 这里除了第五个map之外，都是string，integer的hashmap，integer置为1的时候，说明在这个hashmap中，当前搜索的string已经出现过，就不能再进行添加操作，否则会违反
            // 主键unique的性质
            HashMap<String, Integer> map_for_table_con = new HashMap<>();
            HashMap<String, Integer> map_for_table_cli = new HashMap<>();
            HashMap<String, Integer> map_for_table_model = new HashMap<>();
            HashMap<String, Integer> map_for_table_pro = new HashMap<>();
            HashMap<String, Integer> map_for_supply = new HashMap<>();
            HashMap<String, Integer> map_for_table_sale = new HashMap<>();


            start = System.currentTimeMillis();
            String[] titles;
            titles = infile.readLine().split(",");
            while ((line = infile.readLine()) != null) {
                parts = line.split(",");
                if (parts.length > 1) {
                    contract_number = parts[0];
                    client_enterprise
//                            [0]
                            = parts[1];
                    supply_center = parts[2];
                    country = parts[3];
                    city = parts[4];
                    industry = parts[5];
                    product_code = parts[6];
                    product_name = parts[7];
                    product_model = parts[8];
                    unit_price = Integer.parseInt(parts[9]);
                    quantity = Integer.parseInt(parts[10]);
                    contract_date = parts[11];
                    estimated_delivery_date = parts[12];
                    lodgement_date = parts[13];
                    director = parts[14];
                    salesman = parts[15];
                    salesman_number = parts[16];
                    gender = parts[17];
                    age = Integer.parseInt(parts[18]);
                    mobile_phone = parts[19];

                    openDB(prop.getProperty("host"), prop.getProperty("database"),
                            prop.getProperty("user"), prop.getProperty("password"));

                    loadData_for_supply_center(supply_center, map_for_supply);
                    loadData_for_client_enterprise(client_enterprise, supply_center, country, city, industry, map_for_table_cli);
                    loadData_for_contract(contract_number, contract_date, director, client_enterprise, map_for_table_con);
                    loadData_for_salesman(salesman_number, salesman, gender, age, mobile_phone, supply_center, map_for_table_sale);
                    loadData_for_product(product_code, product_name, map_for_table_pro);
                    loadData_for_model(product_model, unit_price, product_code, map_for_table_model);
                    loadData_for_order(product_code, product_model, quantity, salesman_number
                            , estimated_delivery_date, lodgement_date, contract_number);

                    closeDB();
                    cnt++;
                }
            }
            end = System.currentTimeMillis();
            System.out.println(cnt + " records successfully loaded");
            System.out.println("Loading speed : "
                    + (cnt * 1000) / (end - start)
                    + " records/s");
        } catch (SQLException se) {
            System.err.println("SQL error: " + se.getMessage());
            closeDB();
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Fatal error: " + e.getMessage());
            closeDB();
            System.exit(1);
        }
        closeDB();
    }

    static String polish(String string) {
        if (string.contains("'")) {
            for (int i = 0; i < string.length(); i++) {
                char now_char = string.charAt(i);
                if (now_char == '\'') {
                    String front = string.substring(0, i);
                    String behind = string.substring(i + 1, string.length());
                    string = front + "''" + behind;
                    return string;
                }
            }
        }
        return string;
    }
}

