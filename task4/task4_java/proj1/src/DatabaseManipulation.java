
import java.sql.*;

/*
写一个sql语句，然后再执行
把preparedstatement与对应的sql连接好
statement的话是在运行时现编译，preparedstatement是在程序运行前（编译时）就开始解析  因此如果是同样一条语句执行多次的话建议使用preparedstatement，并不是多次执行就可以用statement
同时有时候为了防止sql注入，会使用preparedstatement，防止一些不规范的操作（比如drop database）
“只要有参数传递就要用preparedstatement，statement用于重复执行少的语句或者没有参数的语句”   查找、读操作是excutequery，写操作是excuteupdate
 */
public class DatabaseManipulation implements DataManipulation {
    private Connection con = null;
    private ResultSet resultSet;

    private String host = "localhost";
    private String dbname = "cs3072";//记得改名
    private String user = "checker";
    private String pwd = "123456";
    private String port = "5432";

//    //各个属性
//    String contract_number;
//    String client_enterprise;
//    String supply_center;
//    String country;
//    String city;
//    String industry;
//    String product_code;
//    String product_name;
//    String product_model;
//    int unit_price;
//    int quantity;
//    String contract_date;
//    String estimated_delivery_date;
//    String lodgement_date;
//    String director;
//    String salesman;
//    String salesman_number;
//    String gender;
//    int age;
//    String mobile_phone;

    @Override
    public void openDatasource() {
        try {
            Class.forName("org.postgresql.Driver");

        } catch (Exception e) {
            System.err.println("Cannot find the PostgreSQL driver. Check CLASSPATH.");
            System.exit(1);
        }

        try {
            String url = "jdbc:postgresql://" + host + ":" + port + "/" + dbname;
            con = DriverManager.getConnection(url, user, pwd);

        } catch (SQLException e) {
            System.err.println("Database connection failed");
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }

    @Override
    public void closeDatasource() {
        if (con != null) {
            try {
                con.close();
                con = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public int addOneMovie(String str) {
        int result = 0;
        String sql = "insert into movies (title, country,year_released,runtime) " +
                "values (?,?,?,?)";
        String[] movieInfo = str.split(";");
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, movieInfo[0]);
            preparedStatement.setString(2, movieInfo[1]);
            preparedStatement.setInt(3, Integer.parseInt(movieInfo[2]));
            preparedStatement.setInt(4, Integer.parseInt(movieInfo[3]));
            System.out.println(preparedStatement.toString());

            result = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public String allContinentNames() {
        StringBuilder sb = new StringBuilder();
        String sql = "select continent from countries group by continent";
        try {
            Statement statement = con.createStatement();
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                sb.append(resultSet.getString("continent")).append("\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return sb.toString();
    }

    @Override
    public String continentsWithCountryCount() {
        StringBuilder sb = new StringBuilder();
        String sql = "select continent, count(*) countryNumber from countries group by continent;";
        try {
            Statement statement = con.createStatement();
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                sb.append(resultSet.getString("continent")).append("\t");
                sb.append(resultSet.getString("countryNumber"));
                sb.append(System.lineSeparator());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return sb.toString();
    }

    @Override
    public String FullInformationOfMoviesRuntime(int min, int max) {
        StringBuilder sb = new StringBuilder();
        String sql = "select m.title,c.country_name country,c.continent ,m.runtime " +
                "from movies m " +
                "join countries c on m.country=c.country_code " +
                "where m.runtime between ? and ? order by runtime;";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, min);
            preparedStatement.setInt(2, max);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                sb.append(resultSet.getString("runtime")).append("\t");
                sb.append(String.format("%-18s", resultSet.getString("country")));
                sb.append(resultSet.getString("continent")).append("\t");
                sb.append(resultSet.getString("title")).append("\t");
                sb.append(System.lineSeparator());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    @Override
    public String findMovieById(int id) {
        StringBuilder sb = new StringBuilder();
        String sql = "select m.title, c.country_name, m.year_released, m.runtime from movies m\n" +
                "  join countries c             \n" +
                "      on m.country = c.country_code \n" +
                "where m.movieid = ?;";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, id);//传参
            resultSet = preparedStatement.executeQuery();//执行
            while (resultSet.next()) {
                sb.append("runtime:" + resultSet.getString("runtime")).append("\n");//\t表示水平制表（跳到下一个tab位置）
                sb.append("country:" + String.format("%-18s", resultSet.getString("country_name"))).append("\n");
                sb.append("year_released:" + resultSet.getString("year_released")).append("\n");
                sb.append("title:" + resultSet.getString("title")).append("\n");
                sb.append(System.lineSeparator());
//            while (resultSet.next()) {
//                sb.append(resultSet.getString("runtime")).append("\t");
//                sb.append(String.format("%-18s", resultSet.getString("country")));
//                sb.append(resultSet.getString("continent")).append("\t");
//                sb.append(resultSet.getString("title")).append("\t");
//                sb.append(System.lineSeparator());
//            }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    ///////////////////////////////////////////////////////////我是华丽的分割线////////////////////////////////////////////////////////////
    @Override
    public int insertOrder(String str) {//狭义增（目前是直接插入csv中的一排）
        int result = 0;
        String sql = "insert into orders (order_number,product_code,product_model,quantity,contract_number,salesman_number,estimated_date,lodgement_date) " +
                "values (?,?,?,?,?,?,?,?)";
        String[] parts = str.split(",");
            int order_number=Integer.parseInt(parts[0]);
            String product_code=parts[1];
            String product_model=parts[2];
            int quantity=Integer.parseInt(parts[3]);
            String contract_number=parts[4];
            String salesman_number=parts[5];
            String estimated_date=parts[6];
            String lodgement_date=parts[7];
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, order_number);
            preparedStatement.setString(2, product_code);
            preparedStatement.setString(3, product_model);
            preparedStatement.setInt(4, quantity);
            preparedStatement.setString(5, contract_number);
            preparedStatement.setString(6, salesman_number);
            preparedStatement.setString(7, estimated_date);
            preparedStatement.setString(8, lodgement_date);
//            System.out.println(preparedStatement.toString());

            result = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public String selectOrderByID(int order_number) {//狭义查
        StringBuilder sb = new StringBuilder();
        String sql = "select * from orders\n" +
                "where order_number = ?;";//?代表传参，不可以打很多个
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, order_number);//传参
            resultSet = preparedStatement.executeQuery();//执行
            while (resultSet.next()) {
//                sb.append("product_code:" + resultSet.getString("product_code")).append("\n");//\t表示水平制表（跳到下一个tab位置）
//                sb.append("product_name:" +  resultSet.getString("product_name")).append("\n");
                sb.append("order_number:"+resultSet.getInt("order_number") +" product_code:"+ resultSet.getString("product_code")+
                        " product_model:"+resultSet.getString("product_model")+" quantity:"+resultSet.getInt("quantity")+
                        " contract_number:"+resultSet.getString("contract_number")+" salesman_number:"+resultSet.getString("salesman_number")+
                        " estimated_date:"+resultSet.getString("estimated_date")+" lodgement_date:"+resultSet.getString("lodgement_date")+"\n");
                sb.append(System.lineSeparator());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    @Override
    public int deleteOrderByID(int order_number) {//狭义删
        int result = 0;
        String sql = "delete from orders  " +
                "where order_number=?";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, order_number);
//            System.out.println(preparedStatement.toString());
            result = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int updateOrderByID(int order_number,String str) {//狭义改
        int result = 0;
        String sql = "update orders  " +
                "set order_number=?,"+
                "product_code=?,"+
                "product_model=?,"+
                "quantity=?,"+
                "contract_number=?,"+
                "salesman_number=?,"+
                "estimated_date=?,"+
                "lodgement_date=?"+
                "where order_number=?"
                ;
        String[] parts = str.split(",");
        int order_number2=Integer.parseInt(parts[0]);
        String product_code=parts[1];
        String product_model=parts[2];
        int quantity=Integer.parseInt(parts[3]);
        String contract_number=parts[4];
        String salesman_number=parts[5];
        String estimated_date=parts[6];
        String lodgement_date=parts[7];
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, order_number2);
            preparedStatement.setString(2, product_code);
            preparedStatement.setString(3, product_model);
            preparedStatement.setInt(4, quantity);
            preparedStatement.setString(5, contract_number);
            preparedStatement.setString(6, salesman_number);
            preparedStatement.setString(7, estimated_date);
            preparedStatement.setString(8, lodgement_date);
            preparedStatement.setInt(9, order_number);
//            System.out.println(preparedStatement.toString());

            result = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
