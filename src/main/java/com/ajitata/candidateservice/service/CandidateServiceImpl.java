package com.ajitata.candidateservice.service;

import com.ajitata.candidateservice.model.Candidate;
import com.ajitata.candidateservice.repository.CandidateRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class CandidateServiceImpl {
    private final CandidateRepository candidateRepository;

    private final ModelMapper modelMapper;

    public CandidateServiceImpl (CandidateRepository candidateRepository, ModelMapper modelMapper) {
        this.candidateRepository = candidateRepository;
        this.modelMapper = modelMapper;
    }

    private final Logger logger = LoggerFactory.getLogger(CandidateServiceImpl.class);

    @RabbitListener(queues = "${spring.rabbitmq.queue}")
    public void registerCandidate(com.ajitata.candidateservice.dto.Candidate candidate) {
        logger.info("Candidate Details Received is.. " + candidate.getRegistrationNumber());

        candidateRepository.save(modelMapper.map(candidate, Candidate.class));

        logger.info("Candidate saved successfully.. " + candidate.getRegistrationNumber());
    }

}
