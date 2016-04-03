package com.spark.util;

import com.mongodb.MongoClient;
import com.sparak.domain.Users;
import org.mindrot.jbcrypt.BCrypt;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

/**
 * Created by vignesh on 1/3/2016.
 */
public class UserService {
    MongoClient client= new MongoClient("localhost",27017);
    Datastore dstore=new Morphia().createDatastore(client,"usersDb");

    public Users getUser(String userName){
        Users user=dstore.find( Users.class,"userName",userName).get();
        return user;
    }

    public String createUser(Users user){
        System.out.println(user.getFirstName());
        dstore.save(user);
        return "success";
    }
    public boolean authenticateusers(String username,String password){
        Users user=getUser(username);
        if(user!=null && BCrypt.checkpw(password,user.getPassword())){
            return true;
        }else{
            return false;
        }
    }
}
