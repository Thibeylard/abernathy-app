package com.mediscreen.abernathyapp.patHistory.services;

import com.mediscreen.abernathyapp.patHistory.dtos.PatHistoryTermsCountDTO;
import com.mediscreen.abernathyapp.patHistory.models.PatHistory;
import com.mediscreen.abernathyapp.patHistory.repositories.PatHistoryRepository;
import com.mediscreen.abernathyapp.patHistory.strategies.TerminologyCountStrategy;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PatHistoryServiceImpl implements PatHistoryService {

    private final PatHistoryRepository patHistoryRepository;
    private final TerminologyCountStrategy countStrategy;
    private final Logger logger;

    @Autowired
    public PatHistoryServiceImpl(Logger logger, PatHistoryRepository patHistoryRepository, TerminologyCountStrategy countStrategy) {
        this.patHistoryRepository = patHistoryRepository;
        this.countStrategy = countStrategy;
        this.logger = logger;
    }

    @Override
    public PatHistoryTermsCountDTO terminologySearch(String patientId, Set<String> terminology) {
        List<PatHistory> patHistoryList = this.patHistoryRepository.findByPatientId(patientId);

        if (patHistoryList == null || patHistoryList.isEmpty()) {
            throw new NoSuchElementException("There are not PatHistory with this patientId");
        }

        List<String> contents = patHistoryList.stream()
                .map(PatHistory::getContent)
                .collect(Collectors.toList());

        long count = countStrategy.countTerms(contents, terminology);

        return new PatHistoryTermsCountDTO(patientId, terminology, (int) count);
    }
}
