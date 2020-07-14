package ipTVShopProject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;


@RestController
public class OrderController {

 @Autowired
 OrderRepository orderRepository;

 @RequestMapping(method=RequestMethod.PATCH, path="/orders/cancel")
 public void orderCancel(@RequestParam(value="orderId", required=false, defaultValue="0") String orderId) {

  Optional<Order> orders = orderRepository.findById(Long.valueOf(orderId));
  orders.get().setId(Long.valueOf(orderId));
  orders.get().setStatus("CANCELORDERED");
  orderRepository.save(orders.get());
 }


}