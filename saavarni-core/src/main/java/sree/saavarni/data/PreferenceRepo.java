package sree.saavarni.data;

import org.springframework.data.repository.CrudRepository;
import sree.saavarni.model.Preference;

public interface PreferenceRepo extends CrudRepository<Preference, Long> {	
	
    Preference findById(long id);
}
