package org.murugappan.service;
import org.murugappan.DAO.ProductsDAO;
import org.murugappan.DAO.ProductsImpl;
import org.murugappan.model.ProductDetails;

import java.util.Scanner;

public class ProductsService {
    ProductDetails pd=new ProductDetails();
    ProductsDAO pdi=new ProductsImpl();
    
    Scanner ip = new Scanner(System.in);
    public void addProduct(){
    	


        System.out.println("Enter The Product Name");
        pd.productdetails.put("ProductName",ip.next());
        System.out.println("Enter The Cost  Price");
        pd.productdetails.put("CostPrice",ip.next());
        System.out.println("Enter The Selling Price");
        pd.productdetails.put("SellingPrice", ip.next());
        System.out.println("Enter The StockNo");
        pd.productdetails.put("Stock", ip.next());
        System.out.println("Enter The Product Decription");
        pd.productdetails.put("Description", ip.next());
        System.out.println("Enter The Tax Percent");
        pd.productdetails.put("Tax",ip.next());
        pdi.addProducts(pd.productdetails.get("ProductName"),pd.productdetails.get("Description"),Integer.parseInt(pd.productdetails.get("CostPrice")),Integer.parseInt(pd.productdetails.get("SellingPrice")),Integer.parseInt(pd.productdetails.get("Stock")),Integer.parseInt(pd.productdetails.get("Tax")));

    }
    public void deleteProduct(){
        System.out.println("Enter The ProductId To Be Deleted");
        int produuctid= ip.nextInt();
        pdi.deleteProduct(produuctid);
    }
	public void showProducts() {
		System.out.println("Here The List of Availabe Products In the Inventory!");
		System.out.println();
		pdi.showProducts();
	}
	public void editProducts() {
		System.out.println("Enter the Detail To Be Edited \n1.Edit Product Name\n2.Edit Product Selling Price \n3.Edit Cost Price \n4.Edit Product Description \n5.Edit The Total Stock \n6. Edit The Tax Percent ");		
		String choice=(ip.nextLine()).toUpperCase();
		String columnToUpdate=null;
		System.out.println("Enter the product ID");
		int product_id=ip.nextInt();
		System.out.println("Enter The New Value Which To Be Updated");
		String newvalue=ip.next();
		
;		if(choice.equals("PRODUCT NAME"))
			columnToUpdate="product_name";
		else if(choice.equals("SELLING PRICE"))
			columnToUpdate="selling_price";
		else if(choice.equals("COST PRICE"))
			columnToUpdate="cost_price";
		else if(choice.equals("STOCK"))
			columnToUpdate="Stock";
		else if(choice.equals("PRODUCT DESCRIPTION"))
			columnToUpdate="product_des";
		else if(choice.equals("TAX PERCENT"))
			columnToUpdate="Tax_Percent";
		else
			System.out.println("Enetr A Valid Choise");
		pdi.editProduct(columnToUpdate,product_id,newvalue);
	
	}
	
	
}
