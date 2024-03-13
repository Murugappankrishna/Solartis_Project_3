package org.murugappan.DAO;

import java.sql.ResultSet;

public interface CartDAO {
 void addToCart(Integer userid, Integer productid, Integer quantity);

void showCart(Integer userid);
void deleteCart(int userid);
 public ResultSet createPDF(int userid);
}
