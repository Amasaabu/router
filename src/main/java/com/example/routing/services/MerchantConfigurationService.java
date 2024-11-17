package com.example.routing.services;


import com.example.routing.models.MerchantConfiguration;
import com.example.routing.models.Transaction;
import com.example.routing.models.WeightEvaluationResult;
import com.example.routing.repositories.MerchantConfigRepo;
import com.example.routing.utils.Utils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class MerchantConfigurationService {

    MerchantConfigRepo merchantConfigRepo;
    Utils utils;
    //Retrive Merchant Configs
    public List<MerchantConfiguration> getAlLMerchantConfigs(String merchantId) {
        return merchantConfigRepo.findBymerchantId(merchantId);
    }

    public List<WeightEvaluationResult> routeTransaction (Transaction transaction, String merchantId){
        List<WeightEvaluationResult> providersAndWeight= new ArrayList<>();
        var configurations = getAlLMerchantConfigs(merchantId);
        for (int i = 0; i <configurations.size(); i++) {
            var currConfig = configurations.get(i);
            var res = new WeightEvaluationResult();
            res.setProvider(currConfig.getProvider());
           //check for compulsry conditions
           var satisfies= utils.compulsrySatisfaction(currConfig, transaction);
           if (!satisfies){
               res.setWeight(0);
               providersAndWeight.add(res);
               break;
           }
           var weight = utils.getWeight(currConfig, transaction);
           res.setWeight(weight);
           providersAndWeight.add(res);
        }

        return  providersAndWeight;
    }
}
