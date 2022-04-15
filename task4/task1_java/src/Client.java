import java.io.*;
import java.util.Random;

public class Client {
    //Original
//    public static void main(String[] args) {
//        try {
//            DataManipulation dm = new DataFactory().createDataManipulation(args[0]);//edit configuration设置为file(如果要用database就写database)
//            dm.openDatasource();
////            dm.addOneMovie("流浪地球;cn;2019;127");
//            System.out.println(dm.allContinentNames());
//            System.out.println(dm.continentsWithCountryCount());
//            System.out.println(dm.FullInformationOfMoviesRuntime(65, 75));
////            System.out.println(dm.findMovieById(10));
//            dm.closeDatasource();
//        } catch (IllegalArgumentException e) {
//            System.err.println(e.getMessage());
//        }
//    }
    public static void main(String[] args) throws IllegalArgumentException, FileNotFoundException, IOException {
        DataManipulation dm = new DataFactory().createDataManipulation(args[0]);//edit configuration设置为file(如果要用database就写database)
        dm.openDatasource();//打开数据库（？
//        先把前20000行加入
//            BufferedReader br=new BufferedReader(new FileReader("cs3072_public_order1.csv"));
//            String line;
//            while((line=br.readLine())!=null){
//                dm.insertOrder(line);
//            }
        long start = System.currentTimeMillis();
        //增,直接从第二个表中添加10000条
//            int count=0;
//            BufferedReader br2=new BufferedReader(new FileReader("cs3072_public_order2.csv"));
//            String line2;
//            while(count<=10000&&(line2=br2.readLine())!=null){
//                dm.insertOrder(line2);
//                count++;
//            }
        //删
        int[] ran = createRan(20000, 5000);
        for (int i = 0; i < ran.length; i++) {
            dm.deleteOrderByID(ran[i]);
        }
            //改
//        int[] ran = createRan(20000, 5000);
//        int[] ran2 = createRan(30000, 5000);//30000以内的随机数
//        for (int i = 0; i < ran.length; i++) {
//            BufferedReader br2=new BufferedReader(new FileReader("cs3072_public_order2.csv"));
//            String line2;
//            while ((line2 = br2.readLine()) != null) {
//                String[] parts = line2.split(",");
//                int order_number = Integer.parseInt(parts[0]);
//                if (order_number == ran2[i] + 20000) {
//                    break;
//                }
//            }
//            dm.updateOrderByID(ran[i],line2);
//        }
            //查
//        int[]ran=createRan(20000,5000);
//        for (int i = 0; i < ran.length; i++) {
//            System.out.println(dm.selectOrderByID(ran[i]));
//        }
            long end = System.currentTimeMillis();
            long time = end - start;
            System.out.println(time);

//            dm.insertOrder("CSE0000000,Pinduoduo,Eastern China,China,Shanghai,Internet,T67P542,Tv Base,TvBaseR1,680,480,2018-02-02,2018-04-03,2018-03-13,Xu Zhuyu,Jiang Qizhen,12111414,Female,19,15648228450,");
//            System.out.println(dm.selectProductByID("T67P542"));
//            dm.deleteProductByID("T67P542");
//            dm.updateProductByID("T67P542","E6N2308,Electronic Dictionary");
            dm.closeDatasource();
        }
        public static int[] createRan ( int total, int num){//生成total以内的不重复的5000个随机数
            Random r = new Random();
            int[] ran = new int[num];
            for (int i = 0; i < num; i++) {
                ran[i] = (total / num) * i + 1 + r.nextInt(total / num);
            }
            return ran;
        }
    }