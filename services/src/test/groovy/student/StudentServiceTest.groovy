package student

import com.database.entity.Team
import com.database.entity.TeamState
import com.database.entity.Topic
import com.database.entity.User
import com.database.entity.UserTeam
import com.database.repository.TeamRepository
import com.database.repository.TopicRepository
import com.database.repository.UserRepository
import com.database.repository.UserTeamRepository
import com.google.common.collect.Lists
import com.services.student.StudentService
import spock.lang.Specification
import test.TestUtil

class StudentServiceTest extends Specification {

    TeamRepository teamRepository;
    StudentService service;
    TopicRepository topicRepository;
    UserRepository userRepository;
    UserTeamRepository userTeamRepository;

    def setup() {
        teamRepository = Mock(TeamRepository);
        topicRepository = Mock(TopicRepository)
        userRepository = Mock(UserRepository)
        userTeamRepository = Mock(UserTeamRepository)
        service = new StudentService();
        service.teamRepository = teamRepository;
        service.topicRepository = topicRepository
        service.userRepository = userRepository
        service.userTeamRepository = userTeamRepository
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

    def "Should add student to team not set its him to be leader and confirmed"() {
        given:
        Team team = TestUtil.initTeam()
        User user = new User();
        user.teamsAsStudent = Lists.asList(new UserTeam())

        when:
        service.addStudent(1,2)

        then:
        teamRepository.findTeamWithStudents(_) >> team
        userRepository.findUsersWithTeam(_) >> user

        !user.teamsAsStudent[0].leader
        !user.teamsAsStudent[0].confirmed
    }

    def "Should take team should set leader for user and change state to pending"() {
        given:
        Team team = TestUtil.initTeam()
        User user = new User();
        user.teamsAsStudent = Lists.asList(new UserTeam())
        user.setId(1L)

        when:
        service.takeTeam(1,"login")

        then:
        teamRepository.findTeamWithStudents(_) >> team
        teamRepository.findById(_) >> team
        userRepository.findUsersWithTeam(_) >> user
        userRepository.findUserByLogin(_) >> user
        userTeamRepository.findUserTeamByTeamAndStudent(_, _) >> user.teamsAsStudent[0]
        userTeamRepository.findAllUserTeamsForProject(_) >> Collections.emptyList()

        team.confirmed == TeamState.FORMING
        user.teamsAsStudent[0].leader
        user.teamsAsStudent[0].confirmed
    }

    def "Should accept request delete rest invitation" () {
        Team team = TestUtil.initTeam()
        Team team2 = new Team()
        team2.students = Lists.newArrayList()
        User user = new User();
        List<UserTeam> userTeams = Lists.newArrayList(new UserTeam(student: user, team: team),
                new UserTeam(student: user, team: team2));
        user.teamsAsStudent = userTeams
        user.setId(1L)

        when:

        service.acceptRequest(1, "asd")

        then:
        teamRepository.findById(_) >> team
        userRepository.findUserByLogin(_) >> user
        userTeamRepository.findUserTeamByTeamAndStudent(_, _) >> user.teamsAsStudent[0]
        userTeamRepository.findAllUserTeamsForProject(_) >> userTeams

        user.teamsAsStudent.size() == 1
        user.teamsAsStudent[0].confirmed
    }

    def "Should reject request delete user team for this student"() {
        Team team = TestUtil.initTeam()

        User user = new User();
        List<UserTeam> userTeams = Lists.newArrayList(new UserTeam(student: user, team: team));
        user.teamsAsStudent = userTeams
        team.students = userTeams

        when:
        service.rejectRequest(1, "asd")

        then:
        teamRepository.findById(_) >> team;
        userRepository.findUserByLogin(_) >> user;
        userTeamRepository.findUserTeamByTeamAndStudent(_, _) >> userTeams[0];

        1 * userTeamRepository.delete(userTeams[0])
    }

    def "Should delete leader make team Empty again" () {
        Team team = TestUtil.initTeam()

        User user = new User();
        List<UserTeam> userTeams = Lists.newArrayList(new UserTeam(student: user, team: team, leader: true));
        user.teamsAsStudent = userTeams
        team.students = userTeams
        user.login = "test"

        when:
        service.deleteStudent(1, 1, "test")

        then:
        teamRepository.findTeamWithStudents(_) >> team;
        userRepository.findUsersWithTeam(_) >> user;
        userTeamRepository.findUserTeamByTeamAndStudent(_, _) >> userTeams[0];

        team.confirmed == TeamState.EMPTY
    }
}

