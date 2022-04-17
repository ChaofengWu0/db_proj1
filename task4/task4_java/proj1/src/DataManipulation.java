public interface DataManipulation {

    public void openDatasource();
    public void closeDatasource();
    public int addOneMovie(String str);
    public String allContinentNames();
    public String continentsWithCountryCount();
    public String FullInformationOfMoviesRuntime(int min, int max);
    public String findMovieById(int id);
    ///////////////////////////////////////////////////////////我是华丽的分割线//////////////////////////////////////////////////////////
    public int insertOrder(String str);
    public String selectOrderByID(int order_number);
    public int deleteOrderByID(int order_number);
    public int updateOrderByID(int order_number,String str);
}
