package org.murugappan.DAO;

import org.murugappan.repo.JDBC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ProductsImpl implements ProductsDAO {
    JDBC jdbc=new JDBC();
    Connection con= jdbc.establishConnection();
    PreparedStatement preparedStatement;

    @Override
    public void addProducts(String productname,String description,int costprice,int sellingprice,int stock,int tax) {

        try {
            preparedStatement = con.prepareStatement("INSERT INTO product_details (product_name, product_des, cost_price, selling_price, Stock,Tax_Percent) VALUES(?,?,?,?,?,?)"
       );
            preparedStatement.setString(1,productname);
            preparedStatement.setString(2,description);
            preparedStatement.setInt(3,costprice);
            preparedStatement.setInt(4,sellingprice);
            preparedStatement.setInt(5,stock);
            preparedStatement.setInt(6, tax);
            int rowsInserted = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {

            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();

            }

        }
    }

    @Override
    public void deleteProduct(int productid) {
        try {
            preparedStatement = con.prepareStatement("DELETE FROM product_details WHERE product_id = ?");
            preparedStatement.setInt(1,productid);
            int rowsAffected = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

	@Override
	public void showProducts() {
		try {
			 Statement statement = con.createStatement();
			 ResultSet rs = statement.executeQuery("select*from product_details");
			 System.out.printf("%-15s%-20s%-20s%-15s%-15s%-10s%-10s%n", 
					    "Product ID", "Product Name", "Product Description", 
					    "Cost Price", "Selling Price", "Stock", "Tax Percent");

		      System.out.println("--------------------------------------------------------------------------------------------------------");
		      System.out.println("--------------------------------------------------------------------------------------------------------");
			 while (rs.next()) {
	                int productId = rs.getInt("product_id");
	                String productName = rs.getString("product_name");
	                String productDes = rs.getString("product_des");
	                int costPrice = rs.getInt("cost_price");
	                int sellingPrice = rs.getInt("selling_price");
	                int stock = rs.getInt("Stock");
	                int tax=rs.getInt("Tax_Percent");
	                

	                System.out.printf("%-15d%-20s%-25s%-15d%-15d%-10d%-10d%n", 
	                        productId, productName, productDes, costPrice, sellingPrice, stock, tax);
	                
	            }
				 
			 
		}
		catch(SQLException e){
			e.printStackTrace();
		}

	}

	@Override
	public void editProduct(String columnToUpdate, int product_id,String newvalue) {

		 try {
			preparedStatement = con.prepareStatement("UPDATE product_details SET " + columnToUpdate + " = ? WHERE product_id = ?");
			preparedStatement.setString(1, newvalue);
            preparedStatement.setInt(2, product_id);
            int rowsAffected = preparedStatement.executeUpdate();
				       
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}
}
