package com.sougata.simcardmanager.controller;

import com.sougata.simcardmanager.model.SimCard;
import com.sougata.simcardmanager.service.SimCardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class WebController {

    private final SimCardService simCardService;

    public WebController(SimCardService simCardService) {
        this.simCardService = simCardService;
    }

    @GetMapping(path = "/")
    public ResponseEntity<Object> getRoot() {
        return ResponseEntity.ok().body(null);
    }

    @PostMapping(path = "/add")
    public ResponseEntity<Object> add(@RequestBody SimCard simCard) {
        SimCard saved = simCardService.createSimCard(simCard);
        if (saved != null)
            return ResponseEntity.ok().body(saved);
        else
            return ResponseEntity.internalServerError().body("Couldn't create resource.");
    }

    @GetMapping(path = "/listall")
    public ResponseEntity<Object> listall() {
        return ResponseEntity.ok().body(simCardService.fetchAll());
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<Object> update(@PathVariable("id") String entryId, @RequestBody SimCard simCard) {
        SimCard updated = simCardService.updateSimCard(entryId, simCard);
        return updated != null ?
                ResponseEntity.ok().body(updated) :
                ResponseEntity.internalServerError().body("Error occurred. Couldn't update resource.");
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") String entryId) {
        return simCardService.deleteSimCard(entryId) ?
                ResponseEntity.ok().body("SimCard deleted successfully.") :
                ResponseEntity.internalServerError().body("Error occurred. Couldn't delete resource.");
    }

    @GetMapping(path = "/to-renew")
    public ResponseEntity<Object> toRenew() {
        return ResponseEntity.ok().body(simCardService.fetchToRenew());
    }

    @PutMapping(path = "/renew/{id}")
    public ResponseEntity<Object> renew(@PathVariable("id") String entryId) {
        return simCardService.renew(entryId) ?
                ResponseEntity.ok().body("SimCard renewed successfully.") :
                ResponseEntity.internalServerError().body("Error occurred. Couldn't complete action.");
    }

    @GetMapping(path = "/verify")
    public ResponseEntity<Object> verify() {
        return ResponseEntity.ok().body(Boolean.TRUE);
    }
}
