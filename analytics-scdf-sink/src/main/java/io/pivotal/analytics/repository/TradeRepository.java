package io.pivotal.analytics.repository;

import io.pivotal.analytics.entity.Trade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface TradeRepository extends ElasticsearchRepository<Trade, String> {

    Page<Trade> findBySymbol(String symbol, Pageable pageable);

}