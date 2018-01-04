import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import repository.UserDBRepository;
import service.UserService;

import java.io.FileReader;
import java.util.Properties;

/**
 * Created by Xoxii on 20-Mar-17.
 */
public class Main extends Application
{
    public void start(Stage primaryStage) throws Exception
    {
        Properties serverProps = new Properties();
        serverProps.load(new FileReader("bd.config"));

        UserDBRepository urep = new UserDBRepository(serverProps);
        UserService userService = new UserService(urep);
        Controller ctr = new Controller(userService);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
        System.out.println(loader.getLocation());
        loader.setController(ctr);
        AnchorPane root = (AnchorPane) loader.load();

        Scene scene = new Scene(root, 386, 232);
        Stage st = new Stage();
        st.setTitle("asd");
        st.setScene(scene);
        st.show();
    }

    public static void main(String[] args)
    {
        System.out.println("o pornit");
        launch(args);
    }
}
