package org.examplesolid.application.service;

import org.examplesolid.domain.port.service.IFunFact;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.examplesolid.domain.util.Constants.SEPARATOR_CONCAT_FUNFACT;

@Service
public class FunFactService implements IFunFact {
    @Override
    public List<String> stringToList(String funFactsInString) {
        if (funFactsInString == null) {
            return Collections.emptyList();
        }
        return Arrays.stream(funFactsInString.split(SEPARATOR_CONCAT_FUNFACT)).toList();
    }

    @Override
    public String listToString(List<String> funFactsInList) {
        if (funFactsInList.isEmpty()) return "";
        return funFactsInList.stream().map(funFact -> funFact.concat(SEPARATOR_CONCAT_FUNFACT)).collect(Collectors.joining());
    }
}
