package com.mediscreen.abernathyapp.patHistory.services;

import com.mediscreen.abernathyapp.patHistory.models.PatHistory;
import com.mediscreen.abernathyapp.patHistory.repositories.PatHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class PatHistoryServiceImpl implements PatHistoryService {

    private final PatHistoryRepository patHistoryRepository;
    private final MongoTemplate mongoTemplate;

    @Autowired
    public PatHistoryServiceImpl(PatHistoryRepository patHistoryRepository, MongoTemplate mongoTemplate) {
        this.patHistoryRepository = patHistoryRepository;
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public int terminologySearch(String patientId, Set<String> terminology) {
        List<PatHistory> patHistoryList = this.patHistoryRepository.findByPatientId(patientId);

        return patHistoryList.stream()
                .map(PatHistory::getContent)
                .mapToInt(content -> terminology.stream()
                        .mapToInt(word -> {
                            int searchIndex = 0;
                            int wordCounts = 0;
                            while (searchIndex != -1) {
                                searchIndex = content.indexOf(word, searchIndex + 1);
                                if (searchIndex != -1) {
                                    wordCounts += 1;
                                }
                            }
                            return wordCounts; })
                        .sum())
                .sum();
    }
}
