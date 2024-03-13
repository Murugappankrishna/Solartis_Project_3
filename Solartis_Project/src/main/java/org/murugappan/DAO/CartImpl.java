package org.murugappan.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import org.murugappan.repo.JDBC;
import org.murugappan.service.createPDF;

@SuppressWarnings("ALL")
public class CartImpl implements CartDAO {
	  JDBC jdbc=new JDBC();
	  Connection con= jdbc.establishConnection();


	PreparedStatement preparedStatement;
	  PreparedStatement deleteStatement;
	@Override
	public void addToCart(Integer userid, Integer productid , Integer quantity ) {
		
		 try {
			 
	        	
	            preparedStatement = con.prepareStatement("INSERT INTO cart (user_id, product_id, quantity, total_amount) VALUES (?, ?, ?, (SELECT selling_price * ? FROM product_details WHERE product_id = ?))");
	            preparedStatement.setInt(1,userid);
	            preparedStatement.setInt(2,productid);
	            preparedStatement.setInt(3,quantity);
	            preparedStatement.setInt(4,quantity);
	            preparedStatement.setInt(5,productid);
	            int rowsInserted = preparedStatement.executeUpdate();
	        } catch (SQLException e) {
	           e.printStackTrace();
	        }
		
	}

public void showCart(Integer userid)  {
	
	try {
		preparedStatement = con.prepareStatement("SELECT" +
				"  u.username, " +
				"  pd.product_name," +
				"  c.quantity," +
				"  pd.selling_price AS unit_price," +
				"  pd.Tax_Percent AS tax_percent," +
				"  (pd.selling_price * c.quantity) AS total_price_before_tax," +
				"  (" +
				"    (pd.selling_price * c.quantity)* pd.Tax_Percent / 100" +
				"  ) AS tax_amount, " +
				"  (" +
				"    (pd.selling_price * c.quantity) + (" +
				"      pd.selling_price * c.quantity * pd.Tax_Percent / 100" +
				"    )" +
				"  ) AS price_inclusive_of_tax " +
				"FROM " +
				"  cart c " +
				"  JOIN users u ON c.user_id = u.user_id " +
				"  JOIN product_details pd ON c.product_id = pd.product_id " +
				"WHERE " +
				"  u.user_id = ?");

		preparedStatement.setInt(1, userid);
		ResultSet rs = preparedStatement.executeQuery();
		System.out.printf("%-15s%-15s%-10s%-15s%-15s%-25s%-15s%-10s\n",
				"Username", "Product Name", "Quantity", "unit_price", "Tax Percent",
				"Total Price Before Tax", "Tax Value", "Price Inclusive of Tax");

		while (rs.next()) {
			String username = rs.getString("username");
			String productName = rs.getString("product_name");
			int quantity = rs.getInt("quantity");
			double priceBeforeTax = rs.getDouble("unit_price");
			int taxPercent = rs.getInt("tax_percent");
			double totalPriceBeforeTax = rs.getDouble("total_price_before_tax");
			double taxAmount = rs.getDouble("tax_amount");
			double priceInclusiveOfTax = rs.getDouble("price_inclusive_of_tax");

			System.out.printf("%-15s%-15s%-10d%-20.2f%-10d%-25.2f%-15.2f%-10.2f\n",
					username, productName, quantity, priceBeforeTax, taxPercent,
					totalPriceBeforeTax, taxAmount, priceInclusiveOfTax);
		}





	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	}
	public ResultSet createPDF(int userid) {
        ResultSet rs = null;
        try {
			preparedStatement = con.prepareStatement("SELECT" +
					"  u.username, " +
					"  pd.product_name," +
					"  c.quantity," +
					"  pd.selling_price AS unit_price," +
					"  pd.Tax_Percent AS tax_percent," +
					"  (pd.selling_price * c.quantity) AS total_price_before_tax," +
					"  (" +
					"    (pd.selling_price * c.quantity)* pd.Tax_Percent / 100" +
					"  ) AS tax_amount, " +
					"  (" +
					"    (pd.selling_price * c.quantity) + (" +
					"      pd.selling_price * c.quantity * pd.Tax_Percent / 100" +
					"    )" +
					"  ) AS price_inclusive_of_tax " +
					"FROM " +
					"  cart c " +
					"  JOIN users u ON c.user_id = u.user_id " +
					"  JOIN product_details pd ON c.product_id = pd.product_id " +
					"WHERE " +
					"  u.user_id = ?");

			preparedStatement.setInt(1, userid);
            rs = preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

public void deleteCart(int userId) {
	try {
	 preparedStatement = con.prepareStatement("SELECT cart_id FROM cart WHERE user_id = ?");
     deleteStatement = con.prepareStatement("DELETE FROM cart WHERE cart_id = ?");
	  preparedStatement.setInt(1,userId);
	 ResultSet rs = preparedStatement.executeQuery();
	 while (rs.next()) {
		 int cartId = rs.getInt("cart_id");
		 deleteStatement.setInt(1, cartId);
		 int rowsAffected = deleteStatement.executeUpdate();
		 
	 }
	}
	catch(SQLException e){

		
	}
}

}
