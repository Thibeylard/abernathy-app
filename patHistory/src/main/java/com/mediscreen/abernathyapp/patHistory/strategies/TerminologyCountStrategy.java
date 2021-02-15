package com.mediscreen.abernathyapp.patHistory.strategies;

import java.util.List;
import java.util.Set;

public interface TerminologyCountStrategy {

    public long countTerms(List<String> elements, Set<String> terminology);
}
