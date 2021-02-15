package com.mediscreen.abernathyapp.patHistory.strategies;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

@Component
public class TerminologyCountRegexStrategy implements TerminologyCountStrategy {


    private final Logger logger;

    @Autowired
    public TerminologyCountRegexStrategy(Logger logger) {
        this.logger = logger;
    }

    @Override
    public long countTerms(List<String> elements, Set<String> terminology) {

        String allNotes = String.join(" ", elements);

        return terminology.stream()
                .mapToLong(term -> {
                    boolean found = Pattern.compile(term, Pattern.CASE_INSENSITIVE).matcher(allNotes).find();
                    if (found) {
                        return 1;
                    } else {
                        return 0;
                    }
                })
                .sum();
    }
}
