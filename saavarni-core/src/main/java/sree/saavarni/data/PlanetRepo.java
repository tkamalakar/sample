package sree.saavarni.data;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import sree.saavarni.model.Planet;

public interface PlanetRepo extends CrudRepository<Planet, Long> {	
	
    Planet findByName(String name);
    Planet findById(long id);
   // List<Customer> findAll();
}
