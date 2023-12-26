package com.ms.commande.service;

import com.ms.commande.model.Commande;
import com.ms.commande.repository.CommandeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommandeService {

    @Autowired
    private CommandeRepository commandeRepository;

    public List<Commande> getAllCommandes() {
        return commandeRepository.findAll();
    }

    public Optional<Commande> getCommandeById(Long id) {
        return commandeRepository.findById(id);
    }

    public Commande createCommande(Commande commande) {
        return commandeRepository.save(commande);
    }

    public Optional<Commande> updateCommande(Long id, Commande updatedCommande) {
        Optional<Commande> existingCommande = commandeRepository.findById(id);

        if (existingCommande.isPresent()) {
            updatedCommande.setId(id);
            return Optional.of(commandeRepository.save(updatedCommande));
        }

        return Optional.empty();
    }

    public void deleteCommande(Long id) {
        commandeRepository.deleteById(id);
    }
}