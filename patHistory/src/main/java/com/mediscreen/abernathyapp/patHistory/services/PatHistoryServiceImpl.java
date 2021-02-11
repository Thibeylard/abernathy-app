package com.mediscreen.abernathyapp.patHistory.services;

import com.mediscreen.abernathyapp.patHistory.models.PatHistory;
import com.mediscreen.abernathyapp.patHistory.repositories.PatHistoryRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

@Service
public class PatHistoryServiceImpl implements PatHistoryService {

    private final PatHistoryRepository patHistoryRepository;
    private final MongoTemplate mongoTemplate;
    private final Logger logger;

    @Autowired
    public PatHistoryServiceImpl(Logger logger, PatHistoryRepository patHistoryRepository, MongoTemplate mongoTemplate) {
        this.patHistoryRepository = patHistoryRepository;
        this.mongoTemplate = mongoTemplate;
        this.logger = logger;
    }

    @Override
    public long terminologySearch(String patientId, Set<String> terminology) {
        List<PatHistory> patHistoryList = this.patHistoryRepository.findByPatientId(patientId);
        StringBuilder patternBuilder = new StringBuilder();
        Iterator<String> it = terminology.iterator();

        logger.debug("Terminology set = {}", terminology.toString());

        // Build string pattern
        while (it.hasNext()) {
            patternBuilder.append("(").append(it.next()).append(")");
            if (it.hasNext()) {
                patternBuilder.append("|");
            }
        }
        String stringPattern = patternBuilder.toString();

        logger.debug("String pattern = {}", stringPattern);

        Pattern pattern = Pattern.compile(stringPattern, Pattern.CASE_INSENSITIVE);

        return patHistoryList.stream()
                .map(PatHistory::getContent)
                .mapToLong(content -> pattern.matcher(content).results().count()
                )
                .sum();
    }
}