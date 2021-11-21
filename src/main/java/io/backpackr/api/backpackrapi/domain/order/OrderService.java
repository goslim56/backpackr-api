package io.backpackr.api.backpackrapi.domain.order;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    public Long save(Order order) {
        orderRepository.save(order);
        return order.getId();
    }

    public Order findById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }
}
