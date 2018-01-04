import controller.Controller2;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Cursa;
import model.Motociclist;
import model.User;
import repository.CursaDBRepository;
import repository.MotociclistDBRepository;
import service.CursaService;
import service.GeneralService;
import service.MotociclistService;
import service.UserService;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Created by Xoxii on 18-Mar-17.
 */
public class Controller
{
    private UserService userService;

    public Controller(UserService u) { userService = u; }

    @FXML
    public TextField textfieldUsername;
    @FXML
    public TextField textfieldPassword;
    @FXML
    public Button buttonLogIn;

    public void initialize()
    {
        textfieldUsername.setText("");
        textfieldPassword.setText("");
    }

    public void handleLogIn() throws IOException {
        String username = textfieldUsername.getText();
        String password = textfieldPassword.getText();
        ArrayList<User> users = userService.findAll();
        boolean ok = false;
        for (User i : users) {
            if (i.getId().equals(username))
                if (i.getPassword().equals(password))
                    ok = true;
        }

        if (ok == true){
            Properties serverProps = new Properties();
            serverProps.load(new FileReader("bd.config"));
            Properties serverProps2 = new Properties();
            serverProps2.load(new FileReader("bd.config"));
            CursaDBRepository repocursa = new CursaDBRepository(serverProps);
            MotociclistDBRepository repomoto = new MotociclistDBRepository(serverProps2);
            GeneralService service = new GeneralService(new CursaService(repocursa), new MotociclistService(repomoto));
            Controller2 ctr = new Controller2(service);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("view1.fxml"));

            AnchorPane root = (AnchorPane) loader.load();
            loader.setController(ctr);
            Scene scene = new Scene(root, 600, 450);
            Stage st = new Stage();
            st.setTitle("asd");
            st.setScene(scene);
            st.show();
        }
    }
}
