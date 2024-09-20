package com.example.routing.utils;

import com.example.routing.models.MerchantConfiguration;
import com.example.routing.models.Transaction;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Random;

@Component
public class Utils {
    public int satisfiesMinAmountConstrint(BigDecimal configminim, BigDecimal txnamnt){
        var resultOfComparison = configminim.compareTo(txnamnt);
        if (resultOfComparison==-1) {
            return 10;
        }
        return 0;
    }
    public int satisfiesMaxConstrint(BigDecimal configminim, BigDecimal txnamnt){
        var resultOfComparison = configminim.compareTo(txnamnt);
        if (resultOfComparison == 1) return 10;
        return 0;
    }
    public int getWeight (MerchantConfiguration config, Transaction txn){
        int w1 = satisfiesMinAmountConstrint(config.getMinAmount(), txn.getAmount());
        int w2 = satisfiesMaxConstrint(config.getMaxAmmount(), txn.getAmount());

        return w1+w2;
    }
    public int satisfiesCurrency(MerchantConfiguration config, Transaction txn){
        if (Objects.equals(config.getCurrencyConstrint(), txn.getCurrency())) return 10;
        return -1;
    }
    public int satisfiesCountry(MerchantConfiguration config, Transaction txn){
        if (Objects.equals(config.getOriginCountry().getCountry(), txn.getCountry())) return 10;
        return -1;
    }
    public boolean compulsrySatisfaction(MerchantConfiguration config, Transaction txn) {
        int compulsrySatisfaction = satisfiesCountry(config,  txn)+satisfiesCurrency(config,txn);
        return compulsrySatisfaction >= 20;
    }
    public String generateId(String idPrefix) {
        int length = 15;
        StringBuilder string = new StringBuilder();
        Random random = new Random();
        string.append(idPrefix.toString());

        for (int i = 0; i < length - idPrefix.toString().length(); i++) {
            int randomDigit = random.nextInt(10);
            string.append(randomDigit);
        }
        return string.toString();
    }

}
