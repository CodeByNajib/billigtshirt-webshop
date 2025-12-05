package dk.ss.backendtshirt.tshirt.service;

import dk.ss.backendtshirt.tshirt.model.*;
import dk.ss.backendtshirt.tshirt.repository.GiftConfigRepository;
import dk.ss.backendtshirt.tshirt.repository.OrderGiftRepository;
import dk.ss.backendtshirt.tshirt.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderGiftRepository orderGiftRepository;


    @Autowired
    private GiftService giftService;

    @Transactional
    public Order createOrderFromCart(Cart cart, Customer customer) {
        Order newOrder = new Order();
        newOrder.setCustomer(customer);
        newOrder.setOrderDate(LocalDateTime.now());
        newOrder.setTotalAmount(cart.getTotalAmount());
        newOrder.setOrderNumber(UUID.randomUUID().toString());

        // TASK A3.2: Gem historisk grænse
        // Vi bruger servicen til at hente tallet
        newOrder.setOriginalGiftThreshold(giftService.getCurrentThreshold());

        // Gem ordren
        newOrder = orderRepository.save(newOrder);

        // Tjek om der skal gave med
        checkAndApplyGift(newOrder, cart.getTotalAmount());

        return newOrder;
    }

    private void checkAndApplyGift(Order order, BigDecimal cartTotal) {
        // Vi bruger din fine metode fra GiftService
        boolean qualifies = giftService.checkQualification(cartTotal);

        if (qualifies) {
            OrderGift gift = new OrderGift();
            gift.setOrder(order);
            gift.setAvailable(1);

            // Bruger ID 1, som vi ved findes i databasen
            // Senere kan vi lave logik til at vælge den rigtige gave
            gift.setGiftProductId(1);

            orderGiftRepository.save(gift);
            System.out.println("Gave tilføjet til ordre: " + order.getOrderNumber());
        }
    }
}
