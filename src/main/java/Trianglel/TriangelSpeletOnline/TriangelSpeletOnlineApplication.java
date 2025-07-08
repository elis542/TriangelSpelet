package Trianglel.TriangelSpeletOnline;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
		"GameLogic",
		"WebSocketPackage",
		"Trianglel.TriangelSpeletOnline"
})
public class TriangelSpeletOnlineApplication {

	public static void main(String[] args) {
		SpringApplication.run(TriangelSpeletOnlineApplication.class, args);
	}

}
