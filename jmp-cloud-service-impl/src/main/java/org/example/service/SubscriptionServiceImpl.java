package org.example.service;

import org.example.SubscriptionService;
import org.example.dao.SubscriptionRepository;
import org.example.dto.SubscriptionRequestDto;
import org.example.dto.SubscriptionResponseDto;
import org.example.entity.Subscription;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {

    @Autowired
    private SubscriptionRepository repository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public SubscriptionResponseDto createSubscription(SubscriptionRequestDto subscriptionRequestDto) {
        Subscription requestSubscription = mapper.map(subscriptionRequestDto, Subscription.class);
        requestSubscription.setStartDate(LocalDate.now());

        Subscription subscription = repository.save(requestSubscription);

        return mapper.map(subscription, SubscriptionResponseDto.class);
    }

    @Override
    public SubscriptionResponseDto updateSubscription(SubscriptionRequestDto subscriptionRequestDto) {
        Optional<Subscription> optionalSubscription = repository.findById(subscriptionRequestDto.getId());
        Subscription subscription = null;

        if (optionalSubscription.isPresent()) {
            Subscription requestSubscription = mapper.map(subscriptionRequestDto, Subscription.class);
            requestSubscription.setStartDate(LocalDate.now());
            subscription = repository.save(requestSubscription);
        }
        return mapper.map(subscription, SubscriptionResponseDto.class);
    }

    @Override
    public Long deleteSubscription(Long id) {
        Optional<Subscription> optionalSubscription = repository.findById(id);

        if (optionalSubscription.isEmpty()){
            return null;
        } else {
            repository.deleteById(id);
        }

        return id;
    }

    @Override
    public SubscriptionResponseDto getSubscription(Long id) {
        Optional<Subscription> optionalSubscription = repository.findById(id);

        return optionalSubscription.map(subscription -> mapper.map(optionalSubscription.get(), SubscriptionResponseDto.class))
                .orElse(null);
    }

    @Override
    public List<SubscriptionResponseDto> getAllSubscription() {
        Iterable<Subscription> subscriptions = repository.findAll();

        return StreamSupport.stream(subscriptions.spliterator(), false)
                .map(subscription -> mapper.map(subscription, SubscriptionResponseDto.class))
                .collect(Collectors.toList());
    }
}
