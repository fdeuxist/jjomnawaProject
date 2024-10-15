package com.example.jjomnawaProject.service;

import com.example.jjomnawaProject.reposiroty.PriceLogRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PriceLogService {
    @Autowired
    private PriceLogRepository priceLogRepository;

    private static final Logger logger = LoggerFactory.getLogger(PriceLogService.class);


}
