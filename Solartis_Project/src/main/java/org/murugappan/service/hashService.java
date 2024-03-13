package org.murugappan.service;
import org.murugappan.model.*;

public class hashService {
    UserCredentials uc;
    public hashService(UserCredentials uc) {
        this.uc = uc;
    }
    public void hashPassword(){
        uc.usercredentials.put("Password", Integer.toString((uc.usercredentials.get("Password")).hashCode()));
    }


}
