package com.sougata.simcardmanager.service;

import com.sougata.simcardmanager.model.SimCard;
import com.sougata.simcardmanager.repository.SimCardRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class SimCardService {

    private final SimCardRepository simCardRepository;

    public SimCardService(SimCardRepository simCardRepository) {
        this.simCardRepository = simCardRepository;
    }

    public SimCard createSimCard(SimCard simCard) {
        Date expiryDate = Date.from(Instant.now().plus(30, ChronoUnit.DAYS));
        simCard.setExpiryDate(expiryDate);
        return simCardRepository.save(simCard);
    }

    public boolean deleteSimCard(String entryId) {
        try {
            simCardRepository.deleteById(entryId);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public SimCard updateSimCard(String entryId, SimCard simCard) {
        Optional<SimCard> existing = simCardRepository.findById(entryId);
        if (!existing.isPresent())
            return null;

        SimCard existingOne = existing.get();
        existingOne.setSimCardNo(simCard.getSimCardNo());
        existingOne.setMobileNo(simCard.getMobileNo());
        existingOne.setStatus(simCard.getStatus());
        existingOne.setStateOfRegistration(simCard.getStateOfRegistration());
        existingOne.setKyc(simCard.getKyc());
        existingOne.setTelecomProvider(simCard.getTelecomProvider());
        existingOne.setFullName(simCard.getFullName());
        return simCardRepository.save(existingOne);
    }

    public List<SimCard> fetchAll() {
        return (List<SimCard>) simCardRepository.findAll();
    }

    public List<SimCard> fetchToRenew() {
        return simCardRepository.findSimCardByExpiryDateBefore(
                Date.from(Instant.now().plus(30, ChronoUnit.DAYS))
        );
    }

    public boolean renew(String entryId) {
        Optional<SimCard> existing = simCardRepository.findById(entryId);
        if (!existing.isPresent())
            return false;
        if (!verify())
            return false;
        SimCard existingOne = existing.get();
        existingOne.setExpiryDate(Date.from(Instant.now().plus(30, ChronoUnit.DAYS)));
        simCardRepository.save(existingOne);
        return true;
    }

    public boolean verify() {
        String uri = "http://localhost:8080/verify";
        RestTemplate restTemplate = new RestTemplate();
        return Boolean.TRUE.equals(restTemplate.getForObject(uri, Boolean.class));
    }
}
