package mx.tec.mna.adcs.team4.backend.health;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/health")
public class HealthController {

    @GetMapping
    public Map<String, String> checkHealth() {
        return Map.of("status", "UP", "message", "Application is running");
    }

}
