package start;
import curse.ServiceException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import model.Cursa;
import rest.CurseClient;

import java.sql.Array;
import java.util.ArrayList;

/**
 * Created by Xoxii on 30-May-17.
 */

public class StartRestClient {
    private final static CurseClient usersClient=new CurseClient();
    public static void main(String[] args) {
        RestTemplate restTemplate=new RestTemplate();
        Cursa userT=new Cursa(123,133,123);
        try{
            //  User result= restTemplate.postForObject("http://localhost:8080/chat/users",userT, User.class);

            //  System.out.println("Result received "+result);
      /*  System.out.println("Updating  user ..."+userT);
        userT.setName("New name 2");
        restTemplate.put("http://localhost:8080/chat/users/test124", userT);

*/
            // System.out.println(restTemplate.postForObject("http://localhost:8080/chat/users",userT, User.class));
            //System.out.println( restTemplate.postForObject("http://localhost:8080/chat/users",userT, User.class));

            show(()-> System.out.println(usersClient.create(userT)));
            show(()->{
                ArrayList<Cursa> res=usersClient.getAll();
                for(Cursa u:res){
                    System.out.println(u.getIDCursa()+": "+u.getCapacitate_Generala());
                }
            });
        }catch(Exception ex){
            System.out.println("Exception ... "+ex.getMessage());
        }

    }



    private static void show(Runnable task) {
        try {
            task.run();
        } catch (ServiceException e) {
            //  LOG.error("Service exception", e);
            System.out.println("Service exception"+ e);
        }
    }
}
