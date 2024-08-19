package org.examplesolid.domain.port.service;

import java.util.List;

public interface IFunFact {
    List<String> stringToList(String funFactsInString);

    String listToString(List<String> funFactsInList);
}
