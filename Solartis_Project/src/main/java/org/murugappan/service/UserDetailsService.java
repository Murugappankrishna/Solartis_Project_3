package org.murugappan.service;
import org.murugappan.DAO.*;
import org.murugappan.model.UserCredentials;



import java.util.Scanner;

public class UserDetailsService {
    UserCredentials uc=new UserCredentials();

    Scanner ip=new Scanner(System.in);



    public void  fetchRole()  {
    
        System.out.println("Enter Your Name");
        String name=ip.next();
        System.out.println("Enter Your Password");
        ip.next();
        String password=ip.next();
        System.out.println("Validating....");
        UserCredentialsImpl uci=new UserCredentialsImpl();
        uci.fetchRole(name,Integer.toString(password.hashCode()));


    }
    public void createUser()  {

        hashService hs=new hashService(uc);
        System.out.println("Enter Your Name");
        uc.usercredentials.put("Name",ip.next());

        System.out.println("Enter Your Password");
        uc.usercredentials.put("Password",ip.next());
        System.out.println("Enter Your Roll");
        uc.usercredentials.put("Roll",ip.next());
        hs.hashPassword();
        UserCredentialsImpl uci=new UserCredentialsImpl();
        uci.createUserCredentials(uc.usercredentials.get("Name"),uc.usercredentials.get("Password"),uc.usercredentials.get("Roll"));
    }

}
