package summersbt.whereemp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import summersbt.whereemp.Objects.Employee;

import javax.servlet.MultipartConfigElement;
import java.util.Vector;

@SpringBootApplication
@ComponentScan
public class WhereempApplication {
    public static Vector<Employee> allEmployee;

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize("10240KB");
        factory.setMaxRequestSize("10240KB");
        return factory.createMultipartConfig();
    }

    public static void main(String[] args) {
        SpringApplication.run(WhereempApplication.class, args);
    }
}
