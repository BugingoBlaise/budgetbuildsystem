package com.budgetbuildsystem.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @GetMapping("/demo")
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("Hello from safe url");
    }
    @GetMapping("/supplier-only")
    public ResponseEntity<String> hey() {
        return ResponseEntity.ok("Hello from supplier url");
    }
}
