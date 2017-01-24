package student

import com.database.entity.Team
import com.database.entity.Topic
import com.database.entity.UserTeam
import com.database.repository.TeamRepository
import com.database.repository.TopicRepository
import com.google.common.collect.Lists
import com.services.student.StudentService
import spock.lang.Specification
import test.TestUtil

class StudentServiceTest extends Specification {

    TeamRepository teamRepository;
    StudentService service;
    TopicRepository topicRepository;

    def setup() {
        teamRepository = Mock(TeamRepository);
        topicRepository = Mock(TopicRepository)
        service = new StudentService();
        service.teamRepository = teamRepository;
        service.topicRepository = topicRepository
    }

    def "setting topic should change topic and save in database"() {
        given:
        Team team = TestUtil.initTeam();
        when:
        service.saveTopic(1L, "temat")
        then:
        teamRepository.findById(_) >> team
        team.topic == "temat";
        1 * teamRepository.save(_);
    }

    def "setting description should change topic and save in database"() {
        given:
        Team team = TestUtil.initTeam();
        when:
        service.saveDescription(1L, "description")
        then:
        teamRepository.findById(_) >> team
        team.description == "description";
        1 * teamRepository.save(_);
    }

    def "Should team be autoacceptable when topic is predefined and number of student between min and max"() {
        given:
        Team team = TestUtil.initTeam()
        team.setTopic("test");
        team.setDescription("test")
        team.setStudents(Lists.asList(new UserTeam(), new UserTeam(), new UserTeam()))
        Topic topic = new Topic();
        topic.setChosen(true);
        topic.setDescription("test");
        topic.setTopic("test")
        when:
        boolean autoacceptable = service.isTeamAutoAcceptable(team);
        then:
        topicRepository.findTopicsByCourseAbbreviationAndUser(*_) >> [topic]
        autoacceptable;
    }

    def "Should not team be autoacceptable when topic is not predefined "() {
        given:
        Team team = TestUtil.initTeam()
        team.setTopic("test");
        team.setDescription("test")
        team.setStudents(Lists.asList(new UserTeam(), new UserTeam(), new UserTeam()))
        when:
        boolean autoacceptable = service.isTeamAutoAcceptable(team);
        then:
        topicRepository.findTopicsByCourseAbbreviationAndUser(*_) >> [new Topic()]
        !autoacceptable;
    }

    def "Should not team be autoacceptable when number od students is not between min and max "() {
        given:
        Team team = TestUtil.initTeam()
        team.setTopic("test");
        team.setDescription("test")
        team.setStudents(Lists.asList(new UserTeam(), new UserTeam()))
        Topic topic = new Topic();
        topic.setChosen(true);
        topic.setDescription("test");
        topic.setTopic("test")
        when:
        boolean autoacceptable = service.isTeamAutoAcceptable(team);
        then:
        topicRepository.findTopicsByCourseAbbreviationAndUser(*_) >> [topic]
        !autoacceptable;
    }

    def "Should not team be autoacceptable when topic exist but is not chosen "() {
        given:
        Team team = TestUtil.initTeam()
        team.setTopic("test");
        team.setDescription("test")
        team.setStudents(Lists.asList(new UserTeam(), new UserTeam(), new UserTeam()))
        Topic topic = new Topic();
        topic.setChosen(false);
        topic.setDescription("test");
        topic.setTopic("test")
        when:
        boolean autoacceptable = service.isTeamAutoAcceptable(team);
        then:
        topicRepository.findTopicsByCourseAbbreviationAndUser(*_) >> [topic]
        !autoacceptable;
    }
}

