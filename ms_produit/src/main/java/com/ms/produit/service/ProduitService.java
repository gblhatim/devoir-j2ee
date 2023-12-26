package com.ms.produit.service;

import com.ms.produit.model.Produit;
import com.ms.produit.repository.ProduitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProduitService {

    @Autowired
    private ProduitRepository produitRepository;

    public List<Produit> getAllProduits() {
        return produitRepository.findAll();
    }

    public Optional<Produit> getProduitById(Long id) {
        return produitRepository.findById(id);
    }

    public Produit createProduit(Produit produit) {
        return produitRepository.save(produit);
    }

    public Optional<Produit> updateProduit(Long id, Produit updatedProduit) {
        Optional<Produit> existingProduit = produitRepository.findById(id);

        if (existingProduit.isPresent()) {
            updatedProduit.setId(id);
            return Optional.of(produitRepository.save(updatedProduit));
        }

        return Optional.empty();
    }

    public void deleteProduit(Long id) {
        produitRepository.deleteById(id);
    }
}