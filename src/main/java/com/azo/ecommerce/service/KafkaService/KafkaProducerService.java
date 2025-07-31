package com.azo.ecommerce.service.KafkaService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private static final Logger logger = LoggerFactory.getLogger(KafkaProducerService.class);

    public KafkaProducerService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendOrderEvent(String orderData) {
        sendKafkaMessage("order-events", orderData, "Order event");
    }

    public void sendInventoryUpdate(String inventoryData) {
        sendKafkaMessage("inventory-updates", inventoryData, "Inventory update");
    }

    public void sendCustomerNotification(String notificationData) {
        sendKafkaMessage("customer-notifications", notificationData, "Customer notification");
    }

    public void sendAuditLog(String auditData) {
        sendKafkaMessage("audit-logs", auditData, "Audit log");
    }

    private void sendKafkaMessage(String topic, String data, String messageType) {
        try {
            kafkaTemplate.send(topic, data)
                    .whenComplete((result, ex) -> {
                        if (ex == null) {
                            logger.info("{} sent successfully to topic '{}': {}",
                                    messageType, topic, data);
                        } else {
                            logger.error("Failed to send {} to topic '{}': {}",
                                    messageType, topic, ex.getMessage());
                        }
                    });
        } catch (Exception e) {
            logger.error("Exception while sending {} to topic '{}': {}",
                    messageType, topic, e.getMessage(), e);
        }
    }
}
