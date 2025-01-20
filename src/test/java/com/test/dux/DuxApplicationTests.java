package com.test.dux;

import com.test.dux.dto.TeamDTO;
import com.test.dux.entity.TeamEntity;
import com.test.dux.exception.TeamNotFoundException;
import com.test.dux.repository.TeamRepository;
import com.test.dux.service.impl.TeamServiceInMemory;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Optional;

@SpringBootTest
class DuxApplicationTests {

	@Mock
	private TeamRepository teamRepository;

	@InjectMocks
	private TeamServiceInMemory teamService;

	@Test
	void testGetTeamById_Success() throws TeamNotFoundException {
		Long teamId = 1L;
		TeamEntity expectedTeam = new TeamEntity();
		expectedTeam.setId(1L);
		expectedTeam.setLiga("La liga");
		expectedTeam.setNombre("Real Madrid");
		expectedTeam.setPais("España");


		Mockito.when(teamRepository.findById(teamId)).thenReturn(Optional.of(expectedTeam));

		TeamDTO actualTeam = teamService.getTeamById(teamId);


		assertNotNull(actualTeam);
		assertEquals (expectedTeam.getId(), actualTeam.getId());
		assertEquals(expectedTeam.getNombre(), actualTeam.getNombre());
		assertEquals(expectedTeam.getLiga(), actualTeam.getLiga());
		assertEquals(expectedTeam.getPais(), actualTeam.getPais());


		Mockito.verify(teamRepository, Mockito.times(1)).findById(teamId);
	}

	@Test
	void testGetTeamById_NotFound() {
		Long teamId = 1L;


		Mockito.when(teamRepository.findById(teamId)).thenReturn(java.util.Optional.empty());

		assertThrows(TeamNotFoundException.class, () -> {
			teamService.getTeamById(teamId);
		});

		Mockito.verify(teamRepository, Mockito.times(1)).findById(teamId);
	}

}
