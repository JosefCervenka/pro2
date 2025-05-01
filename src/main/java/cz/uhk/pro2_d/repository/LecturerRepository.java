package cz.uhk.pro2_d.repository;

import cz.uhk.pro2_d.model.Lecturer;
import cz.uhk.pro2_d.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LecturerRepository extends JpaRepository<Lecturer, Long> {

}
