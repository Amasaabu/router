package com.example.routing.controllers;


import com.example.routing.models.Provider;
import com.example.routing.models.Transaction;
import com.example.routing.repositories.TransactionRepo;
import com.example.routing.services.MerchantConfigurationService;
import com.example.routing.utils.Utils;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;

@RestController
@RequestMapping("/api/route")
@AllArgsConstructor
public class RouteController {
    MerchantConfigurationService merchantConfigurationService;
    Utils utils;
    TransactionRepo transactionRepo;
    @PostMapping(path = "/route")
    public ResponseEntity<Transaction> route (HttpServletRequest req, @Valid @RequestBody Transaction request) throws JsonProcessingException {
        var resultOfRouting = merchantConfigurationService.routeTransaction(request, request.getMid());
        int initialMaxWeight = 0;
        Provider result = null;
        for (com.example.routing.models.WeightEvaluationResult currProviderWeightBeingEvaluated : resultOfRouting) {
            if (currProviderWeightBeingEvaluated.getWeight() > initialMaxWeight) {
                initialMaxWeight = currProviderWeightBeingEvaluated.getWeight();
                result = currProviderWeightBeingEvaluated.getProvider();
            }
        }
        request.setRouteId(utils.generateId("RT-"));
        request.setProvider(result);
        var updatedTransaction = transactionRepo.save(request);
        return ResponseEntity.status(HttpStatus.OK).body(updatedTransaction);
    }
    @GetMapping(path = "/test")
    public ResponseEntity<HashMap<Object, Object>> test (HttpServletRequest req, @Valid @RequestBody Transaction request) throws JsonProcessingException {
        var resp = new HashMap<>();
        resp.put("status", "200");
        resp.put("message", "working");
        resp.put("time", LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.OK).body(resp);
    }


}
