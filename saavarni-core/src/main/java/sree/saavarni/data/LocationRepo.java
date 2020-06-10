package sree.saavarni.data;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import sree.saavarni.model.Location;

public interface LocationRepo extends CrudRepository<Location, Long> {	
	
    Location findById(long id);
    Location findByName(String name);
   // List<Customer> findAll();
}
