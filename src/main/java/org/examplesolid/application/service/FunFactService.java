package org.examplesolid.application.service;

import org.examplesolid.domain.port.service.IFunFact;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.examplesolid.domain.util.Constants.SEPARATOR_CONCAT_FUNFACT;

@Service
public class FunFactService implements IFunFact {
    @Override
    public List<String> stringToList(String funFactsInString) {
        if (funFactsInString == null || funFactsInString.isBlank() || funFactsInString.isEmpty()) return Collections.emptyList();
        return Arrays.stream(funFactsInString.split(SEPARATOR_CONCAT_FUNFACT)).toList();
    }

    @Override
    public String listToString(List<String> funFactsInList) {
        if (funFactsInList == null || funFactsInList.isEmpty()) return "";
        StringBuilder funFacts = new StringBuilder();
        int size = funFactsInList.size();
        for (int i = 0; i < size; i++) {
            funFacts.append(funFactsInList.get(i));
            if (i < size - 1) {
                funFacts.append(SEPARATOR_CONCAT_FUNFACT);
            }
        }
        return funFacts.toString();
    }
}
