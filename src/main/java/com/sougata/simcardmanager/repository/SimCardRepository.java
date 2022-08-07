package com.sougata.simcardmanager.repository;

import com.sougata.simcardmanager.model.SimCard;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface SimCardRepository extends CrudRepository<SimCard, String> {
    List<SimCard> findSimCardByExpiryDateBefore(Date limit);
}
