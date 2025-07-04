package com.garments.inventory.application;

import com.garments.inventory.application.dto.RawMaterialDTO;
import com.garments.inventory.domain.entities.RawMaterial;
import com.garments.inventory.domain.repositories.RawMaterialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class RawMaterialService {

    @Autowired
    private RawMaterialRepository rawMaterialRepository;

    public RawMaterialDTO createRawMaterial(RawMaterialDTO rawMaterialDTO) {
        RawMaterial rawMaterial = new RawMaterial(rawMaterialDTO.getName(), rawMaterialDTO.getUnit(),
                rawMaterialDTO.getCurrentStock(), rawMaterialDTO.getReorderLevel(),
                rawMaterialDTO.getCategory());
        RawMaterial savedRawMaterial = rawMaterialRepository.save(rawMaterial);
        return mapToDTO(savedRawMaterial);
    }

    public Optional<RawMaterialDTO> getRawMaterialById(UUID id) {
        return rawMaterialRepository.findById(id).map(this::mapToDTO);
    }

    public List<RawMaterialDTO> getAllRawMaterials() {
        return rawMaterialRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<RawMaterialDTO> getRawMaterialsByCategory(String category) {
        return rawMaterialRepository.findByCategory(category).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<RawMaterialDTO> getLowStockItems() {
        return rawMaterialRepository.findLowStockItems().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public RawMaterialDTO updateStock(UUID id, int newStock) {
        Optional<RawMaterial> existingMaterial = rawMaterialRepository.findById(id);
        if (existingMaterial.isPresent()) {
            RawMaterial material = existingMaterial.get();
            material.setCurrentStock(newStock);
            RawMaterial savedMaterial = rawMaterialRepository.save(material);
            return mapToDTO(savedMaterial);
        }
        throw new RuntimeException("Raw material not found with id: " + id);
    }

    public void deleteRawMaterial(UUID id) {
        rawMaterialRepository.deleteById(id);
    }

    private RawMaterialDTO mapToDTO(RawMaterial rawMaterial) {
        return new RawMaterialDTO(rawMaterial.getId(), rawMaterial.getName(), rawMaterial.getUnit(),
                rawMaterial.getCurrentStock(), rawMaterial.getReorderLevel(),
                rawMaterial.getCategory(), rawMaterial.getCreatedAt());
    }
}