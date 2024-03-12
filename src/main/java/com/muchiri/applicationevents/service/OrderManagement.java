package com.muchiri.applicationevents.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class OrderManagement {
    @Autowired
    private ApplicationEventPublisher publisher;

    public void completeOrder(String name) {
        Order order = new Order(name);
        publisher.publishEvent(new OrderNotificationEvent(this, order.getOrderId(), false));
        System.out.println("persisting the order");
        publisher.publishEvent(new OrderNotificationEvent(this, order.getOrderId(), true));
    }

    class Order {
        private Integer oderId;
        private String name;

        public Order(String name) {
            this.oderId = 12345;
            this.name = name;
        }

        public Integer getOrderId() {
            return this.oderId;
        }

        public void setOrderId(Integer orderId) {
            this.oderId = orderId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

    }

    class OrderNotificationEvent extends ApplicationEvent {
        private Integer orderId;
        private boolean isProcessed;

        public OrderNotificationEvent(Object source, Integer orderId, boolean isProcessed) {
            super(source);
            this.orderId = orderId;
            this.isProcessed = isProcessed;
        }

        public Integer getOrderId() {
            return this.orderId;
        }

        public boolean isProcessed () {
            return this.isProcessed;
        }
    }

    // @Component
    // class OrderNotificationListener implements ApplicationListener<OrderNotificationEvent> {

    //     @Override
    //     public void onApplicationEvent(OrderNotificationEvent event) {
    //         System.out.println(
    //                 "We have received your order of id " + event.getOrderId()
    //                         + " and we are currently processing it.");
    //     }

    // }

    // @Async
    // @Component
    // class OrderNotificationListenerAsync implements ApplicationListener<OrderNotificationEvent> {
    //     @Override
    //     public void onApplicationEvent(OrderNotificationEvent event) {
    //         System.out.println(
    //                 "We have received your order of id " + event.getOrderId()
    //                         + " and we are currently processing it asynchronously.");
    //     }
    // }

    // We can decide to use annotation for listeners on methods intead of the above commented way.
    // They have to be in a bean class.
    @EventListener
    public void orderNotificationListener(OrderNotificationEvent event) {
        System.out.println(
                "We have received your order of id " + event.getOrderId()
                        + " and we are currently processing it.");
    }

    // This runs asyncronously in another thread hence not blocking the flow of code where
    // the event was published.
    @Async
    @EventListener
    public void orderNotificationListenerAsync(OrderNotificationEvent event) {
        System.out.println(
                "We have received your order of id " + event.getOrderId()
                        + " and we are currently processing it asynchronously.");
    }

    //This will execute only when the order isProcessed
    @EventListener(condition = "#event.isProcessed()")
    public void processedOrderNotificationListener(OrderNotificationEvent event) {
        System.out.println("Your order of id " +event.getOrderId( )+ " has been processed.");
    }

}
