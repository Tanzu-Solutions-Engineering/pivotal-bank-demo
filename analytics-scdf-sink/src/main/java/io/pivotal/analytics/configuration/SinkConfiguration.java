package io.pivotal.analytics.configuration;

import io.pivotal.analytics.entity.Trade;
import io.pivotal.analytics.repository.TradeRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.util.LinkedCaseInsensitiveMap;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@EnableBinding(Sink.class)
public class SinkConfiguration {
    private static final Log logger = LogFactory.getLog(SinkConfiguration.class);

    @Autowired
    TradeRepository tradeRepository;

    @ServiceActivator(inputChannel=Sink.INPUT)
    public void loggerSink(Trade trade) {

        // sample input message {orderid=32772, accountid=1, completiondate=2018-06-25 18:37:00.0, currency=USD, orderfee=10.50, ordertype=0, price=24.80, quantity=15, symbol=PVTL, userid=nfoles, tag=null}

        logger.info("found order: " + trade.toString());

        // date format; 2018-06-25 18:37:00.0

        tradeRepository.save(trade);
    }
}
