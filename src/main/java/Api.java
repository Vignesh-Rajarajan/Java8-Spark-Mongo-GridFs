/**
 * Created by vignesh on 1/2/2016.
 */

import com.sparak.domain.Post;
import com.sparak.domain.Users;
import com.spark.util.PostService;
import com.spark.util.UserService;
import org.mindrot.jbcrypt.BCrypt;

import javax.xml.bind.DatatypeConverter;

import static com.spark.util.JsonUtil.fromJson;
import static com.spark.util.JsonUtil.json;
import static spark.Spark.*;
public class Api {
    private static UserService userService=new UserService();
    private static PostService postService=new PostService();
    public static void main(String[] args){

        before("api/*",(req,res)->{
            String authorization =req.headers("Authorization");
           if(authorization==null || !authorization.startsWith("Basic")){
               halt("please login");
           }else{
               String headerValue=authorization.substring("Basic".length()).trim();
               byte[] decodedStr= DatatypeConverter.parseBase64Binary(headerValue);
               String[] decodedalue=new String(decodedStr).split(":");
               System.out.println(decodedalue[1]);
               String username=decodedalue[0];
               String password=decodedalue[1];
               boolean result=userService.authenticateusers(username,password);
               if(!result){
                   halt(401,"please use registerd username and password");
               }
           }

        });

        get("api/greet",(req,res)->{
            String name=req.queryParams("name");
            return "hello world"+name;
        });
        get("api/:username",(req,res)->{
            res.type("application/json");

            System.out.println(req.params("username"));
            String username=req.params(":username");
            Users user=userService.getUser(username);
            return user;
        },json());

        post("/new-user",(req,res)->{
           res.type("application/json");

            Users user=fromJson(req.body(),Users.class);
            System.out.println(user.getFirstName());
            String hashedPass=user.getPassword();
            hashedPass= BCrypt.hashpw(hashedPass,BCrypt.gensalt());
            user.setPassword(hashedPass);
            System.out.println(hashedPass);
            String message=userService.createUser(user);
            System.out.println(req.body());
              return message;
        }, json());

        post("/api/:username/new-post",(req,res)->{
            res.type("application/json");
            Users user=userService.getUser(req.params(":username"));
            Post post=fromJson(req.body(), Post.class);
           Users user1= postService.create(post,user);
            return user1;
        },json());

        get("api/:username/posts",(req,res)->{
            res.type("application/json");
            Users user=userService.getUser(req.params(":username"));
            return user.getPosts();
        },json());

        get("api/:username/:post",(req,res)->{
            res.type("application/json");
            Users user=userService.getUser(req.params(":username"));
            int id=Integer.parseInt(req.params(":post"));
           Post post= postService.getPost(id, user);
            if(post!=null) {
                return post;
            }
            else{
                return null;
            }
        },json());
    }
}
