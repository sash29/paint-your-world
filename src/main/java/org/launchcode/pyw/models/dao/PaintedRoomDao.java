package org.launchcode.pyw.models.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.launchcode.pyw.models.PaintedRoom;
//import org.launchcode.pyw.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Transactional
@Repository
public interface PaintedRoomDao extends CrudRepository<PaintedRoom, Integer> {
    
    List<PaintedRoom> findByAuthor(int uid);
    
    // TODO - add method signatures as needed
	
}
