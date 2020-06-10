package sree.saavarni.data;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import sree.saavarni.model.Star;

public interface StarRepo extends CrudRepository<Star, Long> {		
    List<Star> findByName(String name);
   // List<Customer> findAll();
}
