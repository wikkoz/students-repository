package tutor

import com.database.entity.Team
import com.database.entity.TeamState
import com.database.repository.TeamRepository
import com.gitlab.GitLabApi
import com.gitlab.project.ProjectDto
import com.services.tutor.TutorService
import spock.lang.Specification
import test.TestUtil

class TutorServiceTest extends Specification{

    TeamRepository teamRepository;
    TutorService service;
    GitLabApi gitLabApi;

    def setup() {
        teamRepository = Mock(TeamRepository);
        gitLabApi = Mock(GitLabApi)
        service = new TutorService();
        service.teamRepository = teamRepository;
        service.gitLabApi = gitLabApi;
    }

    def "should topic change state after accepting"() {
        Team team = TestUtil.initTeam()
        ProjectDto p =  new ProjectDto();
        p.setId(10);
        p.setPath("asd")

        when:
        service.acceptTeam(12L, "asd")

        then:
        teamRepository.findTeamWithStudents(_) >> team
        gitLabApi.createProject(*_) >> p;
        team.confirmed == TeamState.ACCEPTED;
        1 * gitLabApi.addUsersToProject([], *_)
    }
}
