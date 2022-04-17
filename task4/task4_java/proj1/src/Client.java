import java.io.*;
import java.util.Random;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws IllegalArgumentException, FileNotFoundException, IOException {
        DataManipulation dm = new DataFactory().createDataManipulation(args[0]);//edit configuration设置为file(如果要用database就写database)
        dm.openDatasource();//打开数据库（？
//        先把前20000行加入
        Scanner in = new Scanner(System.in);
        System.out.print("Do you need to import the 20000 data first(0 is No,1 is Yes):");
        int data = in.nextInt();
        long start = System.currentTimeMillis();//读入初始的20000也要算
        if (data == 1) {
            BufferedReader br = new BufferedReader(new FileReader("cs3072_public_order1.csv"));
            String line;
            while ((line = br.readLine()) != null) {
                dm.insertOrder(line);
            }
        }
        System.out.print("What do you want to do next?(insert,delete,update,select)\n");
        String operation = in.next();
        //增,直接从第二个表中添加10000条
        if (operation.equals("insert")) {
            System.out.print("Please input the file of data you want to insert:\n");
            BufferedReader br2 = new BufferedReader(new FileReader(in.next()));
            String line2;
            while ((line2 = br2.readLine()) != null) {
                dm.insertOrder(line2);
            }
        }
        //删
        else if (operation.equals("delete")){
            System.out.print("please input how many data you want to delete:\n");
            int count=in.nextInt();
            for (int i = 1+5000000; i <= count+5000000; i++) {//删除的时候需要在这里加1W，10W，100W，500W，以错开index
                dm.deleteOrderByID(i);
            }
        }
        //改
        else if (operation.equals("update")) {
            System.out.print("please input how many data you want to update:\n");
            int count=in.nextInt();
            System.out.print("please input the file of the new data:\n");
            String dataUpdate=in.next();
            BufferedReader br2 = new BufferedReader(new FileReader(dataUpdate));
            String line2;
            for (int i = 1; i <= count; i++) {
                line2=br2.readLine();
                dm.updateOrderByID(i, line2);
            }
        }
        //查
        else if(operation.equals("select")){
            System.out.print("please input the scale of your select:\n");
            int lo=in.nextInt();
            int hi=in.nextInt();
            for (int i = lo; i <= hi; i++) {
                System.out.println(dm.selectOrderByID(i));
            }
        }
        long end = System.currentTimeMillis();
        long time = end - start;
        System.out.println(time);

//            dm.insertOrder("CSE0000000,Pinduoduo,Eastern China,China,Shanghai,Internet,T67P542,Tv Base,TvBaseR1,680,480,2018-02-02,2018-04-03,2018-03-13,Xu Zhuyu,Jiang Qizhen,12111414,Female,19,15648228450,");
//            System.out.println(dm.selectProductByID("T67P542"));
//            dm.deleteProductByID("T67P542");
//            dm.updateProductByID("T67P542","E6N2308,Electronic Dictionary");
        dm.closeDatasource();
    }

//    public static int[] createRan(int total, int num) {//生成total以内的不重复的5000个随机数
//        Random r = new Random();
//        int[] ran = new int[num];
//        for (int i = 0; i < num; i++) {
//            ran[i] = (total / num) * i + 1 + r.nextInt(total / num);
//        }
//        return ran;
//    }
}