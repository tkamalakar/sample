package sree.saavarni.data;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import sree.saavarni.model.Sign;

public interface SignRepo extends CrudRepository<Sign, Long> {	
	
    List<Sign> findByName(String name);
   // List<Customer> findAll();
}
