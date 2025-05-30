package flgfy.qrlogi.health;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {

    @GetMapping("/api/health")
    public String healthCheck() {
        return "정상 동작합니다.";
    }

    @GetMapping("/")
    public String home() {
        return "QRLogi!";
    }

}
