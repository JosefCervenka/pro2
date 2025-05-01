package cz.uhk.pro2_d.service.Interfaces;

import cz.uhk.pro2_d.model.Course;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ICourseService {
    List<Course> getAllCourses();
    void saveCourse(Course course);
    Course getCourse(long id);
    void deleteCourse(long id);
}
