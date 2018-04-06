package io.pivotal.portfolio.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import io.pivotal.portfolio.domain.Order;
import io.pivotal.portfolio.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PortfolioRepositoryService {

    private Tracer tracer;
    private OrderRepository repository;

    @Autowired
    public PortfolioRepositoryService(Tracer tracer, OrderRepository repository) {
        this.tracer = tracer;
        this.repository = repository;
    }

    @HystrixCommand(
            commandKey = "portfolio-service.getOrderFromDB",
            groupKey = "portfolio-service.getOrderFromDB",
            threadPoolKey = "portfolio-service.getOrderFromDB")
    List<Order> getOrders(String userId) {

        Span newSpan = tracer.createSpan("retrieveUserId");
        List<Order> orders = repository.findByUserId(userId);
        try{
            return repository.findByUserId(userId);
        } finally {
            newSpan.logEvent(Span.CLIENT_RECV);
            tracer.close(newSpan);
        }
    }

}
