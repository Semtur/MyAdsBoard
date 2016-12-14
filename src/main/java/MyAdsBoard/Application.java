package MyAdsBoard;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner demo(final MyAdsBoardService myAdsBoardService) {
        return new CommandLineRunner() {
            @Override
            public void run(String... strings) throws Exception {
                UserProfile userProfile = myAdsBoardService.getUser("admin@domain.com");
                if (userProfile == null)
                    myAdsBoardService.addUser(new UserProfile("admin@domain.com", "5baa61e4c9b93f3f0682250b6cf8331b7ee68fd8", "Admin", null, new Address("Київська область", "Київ", null), UserRole.ADMIN));
                userProfile = myAdsBoardService.getUser("user@domain.com");
                if (userProfile == null)
                    myAdsBoardService.addUser(new UserProfile("user@domain.com", "5baa61e4c9b93f3f0682250b6cf8331b7ee68fd8", "User", null, new Address("Київська область", "Київ", null), UserRole.USER));
                /*List<String> rubricList = new ArrayList<>();
                rubricList.add("Процесори");
                rubricList.add("Материнські плати");
                rubricList.add("Корпуси");
                rubricList.add("Відеокарти");
                rubricList.add("Пам'ять");
                rubricList.add("Жорсткі диски");
                rubricList.add("Блоки живлення");
                rubricList.add("Системи охолодження");
                rubricList.add("Оптичні приводи");
                Category category = new Category("Коплектуючі для ПК", rubricList);
                myAdsBoardService.addCategory(category);*/
            }
        };
    }
}