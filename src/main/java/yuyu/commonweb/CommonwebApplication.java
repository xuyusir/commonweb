package yuyu.commonweb;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@MapperScan("yuyu.commonweb.*.dao")
@SpringBootApplication
public class CommonwebApplication {

    public static void main(String[] args) {
        SpringApplication.run(CommonwebApplication.class, args);
    }

}
