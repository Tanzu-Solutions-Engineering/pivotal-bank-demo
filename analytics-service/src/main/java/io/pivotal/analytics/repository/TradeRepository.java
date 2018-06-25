package io.pivotal.analytics.repository;

import io.pivotal.analytics.entity.Trade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface TradeRepository extends ElasticsearchRepository<Trade, String> {

    Page<Trade> findBySymbolOrderByCompletiondateDesc(String symbol, Pageable pageable);

}