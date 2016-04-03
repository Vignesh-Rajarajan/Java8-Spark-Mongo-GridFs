package com.spark.util;

import com.mongodb.MongoClient;
import com.sparak.domain.Post;
import com.sparak.domain.Users;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;

import java.util.List;

/**
 * Created by vignesh on 1/5/2016.
 */
public class PostService {
    MongoClient client= new MongoClient("localhost",27017);
    Datastore dstore=new Morphia().createDatastore(client,"usersDb");

    UpdateOperations<Users> updateops;

    public Users create(Post post, Users user){
        int id=user.getPosts().size()+1;
        post.setId(id);
        Query<Users> updatequery= dstore.createQuery(Users.class).field("_id").equal(user.getId());
        updateops=dstore.createUpdateOperations(Users.class).add("posts",post);
        dstore.update(updatequery,updateops);

        return user;
    }
public Post getPost(int id,Users user){

    List<Post> listOfPosts=user.getPosts();
    for(Post posts : listOfPosts){
         if(posts.getId()==id){
             return posts;
         }

    }
          return null;
}
}
