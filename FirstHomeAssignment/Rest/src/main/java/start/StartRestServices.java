package start;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Primary;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by Xoxii on 30-May-17.
 */
@ComponentScan("curse")
@SpringBootApplication
public class StartRestServices {

    @Bean
    @Primary
    public Properties jdbcProperties(){
        Properties serverProps=new Properties();
        try {
            serverProps.load(getClass().getResourceAsStream("/bd.config"));
            System.out.println("Properties set. ");
            serverProps.list(System.out);
        } catch (IOException e) {
            System.out.println("Cannot find bd.config "+e);

        }
        return serverProps;
    }

    public static void main(String[] args) {

        SpringApplication.run(StartRestServices.class, args);
    }
}

