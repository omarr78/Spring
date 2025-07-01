### application.properties

    mail-service.host = smtp.example.com
    mail-service.port = 587
    mail-service.from = no-reply@example.com

``` java

@RestController
public class MailConfigutationController {

    @Autowired
    private MailServiceConfiguration mailServiceConfiguration;

    @RequestMapping("/mail-configuration")
    public MailServiceConfiguration getMailServiceConfiguration() {
        return mailServiceConfiguration;
    }

}

@ConfigurationProperties(prefix = "mail-service")
@Component
public class MailServiceConfiguration {
    private String host;
    private int port;
    private String from;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }
}

```
```    
http://localhost:8080/mail-configuration

```

    

![image](https://github.com/user-attachments/assets/d0cbfc83-6ec5-45ea-bc11-defc6b479cb8)

--- 

### application-dev.properties

    mail-service.host = dev.example.com
    mail-service.port = 587
    mail-service.from = dev@example.com
    
### application-prod.properties

    mail-service.host = prod.example.com
    mail-service.port = 587
    mail-service.from = prod@example.com
    
### application.properties

```
spring.profiles.active= dev

```
![image](https://github.com/user-attachments/assets/e17f434c-85dc-4151-8a1a-e9613dd41129)

### application.properties

```
spring.profiles.active= prod

```
![image](https://github.com/user-attachments/assets/74c1fde4-d51e-44a9-bee7-65b7fd0df04e)
