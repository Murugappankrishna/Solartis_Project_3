package org.murugappan.service;

import java.sql.ResultSet;
import java.util.Scanner;

import org.murugappan.model.Cart;
import org.murugappan.DAO.*;

public class CartService {
	Cart cart= new Cart();
	Scanner ip=new Scanner(System.in);
	CartDAO cdi = new CartImpl();
	createPDF cpdf=new createPDF();

	public void addToCart() {
		
		int flag;
		String isBilling;
		String modeofpayment;
		System.out.println("Enter The Products To Be Added TO The Cart");	
		System.out.println("Enter The User ID ");
		cart.usercart.put("UserID", ip.nextInt());
		do {
		System.out.println("Enter THe Product ID To Be Added In The Cart");
		cart.usercart.put("ProductID", ip.nextInt());
		System.out.println("Enter The Quantiy Needed");
		cart.usercart.put("Quantity", ip.nextInt());
		cdi.addToCart(cart.usercart.get("UserID"),cart.usercart.get("ProductID"),cart.usercart.get("Quantity"));
		System.out.print("Do Want More Products To Be added Enter 1 Else 2");
		flag=ip.nextInt();
		}while(flag==1);
		System.out.println("Yoru Products Has Been Added");
		cdi.showCart(cart.usercart.get("UserID"));
		System.out.println("DO Want To Procede To Billing Section Enter YES else NO");
		isBilling=ip.next().toUpperCase();
		if(isBilling.equals("YES")) {
			System.out.println("Say Your Choise of Payment Mode");
			modeofpayment=ip.next().toUpperCase();
			cpdf.createPDF(cart.usercart.get("UserID"));


			//function to edit the quantity in the products table!
			// function to move the cart data to Transcation DB
			cdi.deleteCart(cart.usercart.get("UserID"));
			
		
		}
		
		
	}




}
