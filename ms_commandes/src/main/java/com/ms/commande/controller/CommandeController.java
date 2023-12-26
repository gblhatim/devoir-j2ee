package com.ms.commande.controller;

import com.ms.commande.model.Commande;
import com.ms.commande.service.CommandeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

@EnableHystrix
@EnableHystrixDashboard

@RestController
@RequestMapping("/commandes")
public class CommandeController implements HealthIndicator {

    @Autowired
    private CommandeService commandeService;

    @GetMapping("/myMessage")

    @HystrixCommand(fallbackMethod = "myHistrixbuildFallbackMessage",
    commandProperties = {
        @HystrixProperty(name =
        "execution.isolation.thread.timeoutInMilliseconds", value = "1000")
    },
    threadPoolKey = "messageThreadPool")

    public String getMessage() throws InterruptedException {
        System.out.println("Message from EmployeeController.getMessage(): Begin To sleep for 3 scondes "); 
        Thread.sleep(3000);
        return "Message from EmployeeController.getMessage(): End from sleep for 3 scondes "; 
    }

    private String myHistrixbuildFallbackMessage() {
      return "Message from myHistrixbuildFallbackMessage() : Hystrix Fallback message(after timeout: 1 second)"; 
    }
    

    @GetMapping
    public List<Commande> getAllCommandes() {
        return commandeService.getAllCommandes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Commande> getCommandeById(@PathVariable Long id) {
        Optional<Commande> commandeOptional = commandeService.getCommandeById(id);

        return commandeOptional.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Commande> createCommande(@RequestBody Commande commande) {
        Commande createdCommande = commandeService.createCommande(commande);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCommande);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Commande> updateCommande(@PathVariable Long id, @RequestBody Commande updatedCommande) {
        Optional<Commande> updated = commandeService.updateCommande(id, updatedCommande);

        return updated.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCommande(@PathVariable Long id) {
        commandeService.deleteCommande(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public Health health() {
        List<Commande> commandes = commandeService.getAllCommandes();
        if(commandes.isEmpty()){
            return Health.down().build();
        }
        return Health.up().build();
    }
}
