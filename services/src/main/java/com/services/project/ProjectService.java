package com.services.project;

import com.database.entity.Course;
import com.database.entity.Project;
import com.database.entity.Team;
import com.database.repository.CourseRepository;
import com.database.repository.ProjectRepository;
import com.services.file.FileService;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProjectService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private FileService fileService;

    @Transactional
    public List<String> findAllCoursesForUser(String login) {
        return courseRepository.findAllCoursesWithLecturerLogin(login);
    }

    public void createProject(ProjectCreationRequest request) {
        Workbook file = fileService.getWorkbook(request.getFilename(),request.getFileData());

//        saveProject(request, teams);
    }

    @Transactional
    private void saveProject(ProjectCreationRequest request, List<Team> teams) {
        Project project = new Project();
        project.setStartDate(request.getStartDate());
        project.setCourse(findCourseWithName(request.getCourseName()));
        project.setTeams(teams);
        project.setMaxPoints(request.getPoints());
        project.setNextDate(request.getEndDate());
        projectRepository.save(project);
    }
    @Transactional
    private Course findCourseWithName(String name){
        return courseRepository.findCourseByAbbreviation(name);
    }

//    @Transactional
//    private Team saveTeam(){
//
//    }
}
