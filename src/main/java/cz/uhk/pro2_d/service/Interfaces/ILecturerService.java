package cz.uhk.pro2_d.service.Interfaces;

import cz.uhk.pro2_d.model.Lecturer;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ILecturerService {
    List<Lecturer> getAllLecturers();
    void saveLecturer(Lecturer lecturer);
    Lecturer getLecturer(long id);
    void deleteLecturer(long id);
}
