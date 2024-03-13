package org.murugappan.DAO;

public interface ProductsDAO {
    void addProducts(String productname,String description,int costprice,int sellingprice , int stock,int tax);
    void deleteProduct(int productid);
	void showProducts();
	void editProduct(String columnToUpdate, int product_id,String newvalue);

}
