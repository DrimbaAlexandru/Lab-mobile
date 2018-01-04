package curse;

/**
 * Created by Xoxii on 28-May-17.
 */


import model.Cursa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
@CrossOrigin
@RestController
@RequestMapping("/curse/curses")
public class CurseController {

    private static final String template = "Hello, %s!";

    @Autowired
    private CurseDBRepository userRepository;

    @RequestMapping("/greeting")
    public String greeting(@RequestParam(value="name", defaultValue="World") String name) {
        return String.format(template, name);
    }

    @RequestMapping( method=RequestMethod.GET)
    public ArrayList<Cursa> getAll(){
        return userRepository.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getById(@PathVariable String id){

        Cursa user=userRepository.findOne(Integer.parseInt(id));
        if (user==null)
            return new ResponseEntity<String>("User not found",HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<Cursa>(user, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public Cursa create(@RequestBody Cursa user){
        userRepository.save(user);
        return user;

    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Cursa update(@RequestBody Cursa user) {
        System.out.println("Updating user ...");
        userRepository.update(user.getIDCursa(),user);
        return user;

    }

    @RequestMapping(value="/{id}", method= RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable String id){
        System.out.println("Deleting user ... "+id);
        try {
            userRepository.delete(Integer.parseInt(id));
            return new ResponseEntity<Cursa>(HttpStatus.OK);
        }catch (Exception ex){
            System.out.println("Ctrl Delete user exception");
            return new ResponseEntity<String>(ex.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }


    /*@RequestMapping("/{user}/name")
    public String name(@PathVariable String user){
        Cursa result=userRepository.findOne(Integer.parseInt(user));
        System.out.println("Result ..."+result);

        return result.getCapacitate_Generala().toString();
    }
*/

/*
    @ExceptionHandler(RepositoryException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String userError(RepositoryException e) {
        return e.getMessage();
    }*/
}