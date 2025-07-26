package Trianglel.TriangelSpeletOnline;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Timer;
import java.util.TimerTask;

@SpringBootApplication(scanBasePackages = {
		"GameLogic",
		"WebSocketPackage",
		"Trianglel.TriangelSpeletOnline"
})
@EnableScheduling
public class TriangelSpeletOnlineApplication {

	public static void main(String[] args) {
		SpringApplication.run(TriangelSpeletOnlineApplication.class, args);
	}}
