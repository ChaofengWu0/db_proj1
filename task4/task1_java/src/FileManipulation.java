//import java.io.*;
//import java.util.*;
//
//public class FileManipulation implements DataManipulation {
//    //由于txt不能进行删除和修改，因此需要先把数据放在内存（如数组）里面，增删改查操作完成后，把最终的数据写入txt(写入操作放在Client里)
//    static List[]product=new List[2];//似乎初始化不能直接在这里做（直接在class下面product[0]会报错）
//    //各个属性
////    String contract_number;
////    String client_enterprise;
////    String supply_center;
////    String country;
////    String city;
////    String industry;
////    String product_code;
////    String product_name;
////    String product_model;
////    int unit_price;
////    int quantity;
////    String contract_date;
////    String estimated_delivery_date;
////    String lodgement_date;
////    String director;
////    String salesman;
////    String salesman_number;
////    String gender;
////    int age;
////    String mobile_phone;
//    @Override
//    public void openDatasource() {
//
//    }
//
//    @Override
//    public void closeDatasource() {
//
//    }
//
//    @Override
//    public int addOneMovie(String str) {
//        try (FileWriter writer = new FileWriter("movies.txt", true)) {
//            writer.write(str);
//        } catch (IOException e) {
//            e.printStackTrace();
//            return 0;
//        }
//        return 1;
//    }
//
//    @Override
//    public String allContinentNames() {
//        String line;
//        int continentIndex = 2;
//        Set<String> continentNames = new HashSet<>();
//        StringBuilder sb = new StringBuilder();
//
//        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("countries.txt"))) {
//            bufferedReader.readLine();
//            while ((line = bufferedReader.readLine()) != null) {
//                line = line.split(";")[continentIndex];
//                if (!continentNames.contains(line)) {
//                    sb.append(line).append("\n");
//                    continentNames.add(line);
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return sb.toString();
//    }
//
//    @Override
//    public String continentsWithCountryCount() {
//        String line;
//        int continentIndex = 2;
//        Map<String, Integer> continentCount = new HashMap<>();
//        StringBuilder sb = new StringBuilder();
//
//        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("countries.txt"))) {
//            bufferedReader.readLine();
//            while ((line = bufferedReader.readLine()) != null) {
//                line = line.split(";")[continentIndex];
//                if (continentCount.containsKey(line)) {
//                    continentCount.put(line, continentCount.get(line) + 1);
//                } else {
//                    continentCount.put(line, 1);
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        for (Map.Entry<String, Integer> entry : continentCount.entrySet()) {
//            sb.append(entry.getKey())
//                    .append("\t")
//                    .append(entry.getValue())
//                    .append("\n");
//        }
//
//        return sb.toString();
//    }
//
//    private Map<String, String> getCountryMap() {
//        String line;
//        String[] splitArray;
//        int countryCodeIndex = 0, countryNameIndex = 1, continentIndex = 2;
//        Map<String, String> rst = new HashMap<>();
//
//        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("countries.txt"))) {
//            bufferedReader.readLine();
//            while ((line = bufferedReader.readLine()) != null) {
//                splitArray = line.split(";");
//                rst.put(splitArray[countryCodeIndex].trim(), String.format("%-18s", splitArray[countryNameIndex])
//                        + splitArray[continentIndex]);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return rst;
//    }
//
//    private List<FullInformation> getFullInformation(Map<String, String> countryMap, int min, int max) {
//        String line;
//        String[] splitArray;
//        List<FullInformation> list = new ArrayList<>();
//        int titleIndex = 1, countryIndex = 2, runTimeIndex = 4, runTime;
//
//        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("movies.txt"))) {
//            bufferedReader.readLine();
//            while ((line = bufferedReader.readLine()) != null) {
//                splitArray = line.split(";");
//
//                if (!"null".equals(splitArray[runTimeIndex])) {
//                    runTime = Integer.parseInt(splitArray[runTimeIndex]);
//                    if (runTime >= min && runTime <= max) {
//                        line = runTime + "\t" + countryMap.get(splitArray[countryIndex].trim()) + "\t"
//                                + splitArray[titleIndex] + "\n";
//                        list.add(new FullInformation(runTime, line));
//                    }
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return list;
//    }
//
//    @Override
//    public String FullInformationOfMoviesRuntime(int min, int max) {
//        Map<String, String> countryMap = getCountryMap();
//        List<FullInformation> list = getFullInformation(countryMap, min, max);
//        list.sort(Comparator.comparing(f -> f.runTime));
//
//        StringBuilder sb = new StringBuilder();
//        for (FullInformation f : list) {
//            sb.append(f.information);
//        }
//
//        return sb.toString();
//    }
//
//    @Override
//    public String findMovieById(int id) {
//        return null;
//    }
//    ///////////////////////////////////////////////////////////我是华丽的分割线////////////////////////////////////////////////////////////
//    @Override
//    public int insertOrder(String str) {//狭义增（目前是直接插入csv中的一排）
//
//        try (FileWriter writer = new FileWriter("tables/product", true)) {
//            String[] parts = str.split(",");
//            product[0]=new ArrayList();
//            product[1]=new ArrayList();
//            product[0].add(parts[6]);
//            product[1].add(parts[7]);
//        } catch (IOException e) {
//            e.printStackTrace();
//            return 0;
//        }
//        return 1;
//    }
//
//
//    @Override
//    public String selectOrderByID(String product_code) {
//        try {
//            BufferedReader br=new BufferedReader(new FileReader("tables/product"));
//            String line;
//            while((line = br.readLine()) != null){
//                String[] parts=line.split(",");
//                if(parts[0].equals(product_code)){
//                    return "product_code:"+parts[0]+","+"product_name:"+parts[1];
//                }
//            }
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return "Not Found";
//    }
//
//    @Override
//    public int deleteOrderByID(String product_code) {
//        return 0;
//    }
//
//    @Override
//    public int updateOrderByID(String product_code,String str) {
//        return 0;
//    }
//
//    class FullInformation {
//        int runTime;
//        String information;
//
//        FullInformation(int runTime, String information) {
//            this.runTime = runTime;
//            this.information = information;
//        }
//    }
//}
