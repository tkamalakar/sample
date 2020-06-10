package sree.saavarni.data;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import sree.saavarni.model.PositionLords;

public interface PositionLordsRepo extends CrudRepository<PositionLords, Long> {	
	
   // List<KPLords> findByName(String name);
   // List<Customer> findAll();

	PositionLords findByStartDegreeLessThanAndEndDegreeGreaterThanEqual(double forStartLongitude, double forEndLongitude);
}
