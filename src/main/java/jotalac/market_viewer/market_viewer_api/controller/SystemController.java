package jotalac.market_viewer.market_viewer_api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SystemController {

    @GetMapping("ping")
    public ResponseEntity<String> ping() {
        return ResponseEntity.ok("pong");
    }
}
