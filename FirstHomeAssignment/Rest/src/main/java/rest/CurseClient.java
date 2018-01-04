package rest;

import curse.ServiceException;
import model.Cursa;

import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.concurrent.Callable;

/**
 * Created by Xoxii on 30-May-17.
 */
public class CurseClient {
    public static final String URL = "http://localhost:8080/curse/curses";

    private RestTemplate restTemplate = new RestTemplate();

    private <T> T execute(Callable<T> callable) {
        try {
            return callable.call();
        } catch (ResourceAccessException | HttpClientErrorException e) { // server down, resource exception
            throw new ServiceException(e);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    public ArrayList<Cursa> getAll() {
        return execute(() -> restTemplate.getForObject(URL, ArrayList.class));
    }

    public Cursa getById(String id) {
        return execute(() -> restTemplate.getForObject(String.format("%s/%s", URL, id), Cursa.class));
    }

    public Cursa create(Cursa user) {
        return execute(() -> restTemplate.postForObject(URL, user, Cursa.class));
    }

    public void update(Cursa user) {
        execute(() -> {
            restTemplate.put(String.format("%s/%s", URL, user.getIDCursa()), user);
            return null;
        });
    }

    public void delete(String id) {
        execute(() -> {
            restTemplate.delete(String.format("%s/%s", URL, id));
            return null;
        });
    }

}
