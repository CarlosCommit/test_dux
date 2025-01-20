package com.test.dux.service.impl;

import com.test.dux.dto.TeamDTO;
import com.test.dux.dto.mapper.TeamMapper;
import com.test.dux.entity.TeamEntity;
import com.test.dux.exception.TeamNotFoundException;
import com.test.dux.repository.TeamRepository;
import com.test.dux.service.TeamService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamServiceInMemory implements TeamService {

    private final TeamRepository teamRepository;

    public TeamServiceInMemory(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    @Override
    public List<TeamDTO> getAllTeams() {
        List<TeamEntity> teamEntityList = teamRepository.findAll();
        return TeamMapper.INSTANCE.teamEntitiesToTeamDTOs(teamEntityList);
    }

    @Override
    public List<TeamDTO> getTeamsByName(String name) {
        List<TeamEntity> teamEntityList = teamRepository.findByNombreContainingIgnoreCase(name);
        return TeamMapper.INSTANCE.teamEntitiesToTeamDTOs(teamEntityList);
    }

    @Override
    public TeamDTO getTeamById(Long id) throws TeamNotFoundException {

        TeamEntity teamEntity = teamRepository.findById(id)
                .orElseThrow(TeamNotFoundException::new);

        return TeamMapper.INSTANCE.teamEntityToTeamDTO(teamEntity);
    }

    @Override
    public TeamDTO updateTeam(Long id, TeamDTO updatedTeam) throws TeamNotFoundException {

        if(!teamRepository.existsById(id))
            throw new TeamNotFoundException();

        TeamEntity teamEntity = TeamMapper.INSTANCE.teamDTOtoTeamEntity(updatedTeam);
        teamEntity.setId(id);

        teamRepository.save(teamEntity);

        return TeamMapper.INSTANCE.teamEntityToTeamDTO(teamEntity);
    }

    @Override
    public TeamDTO saveTeam(TeamDTO teamDTO) {
        teamDTO.setId(null);
        TeamEntity teamEntity = TeamMapper.INSTANCE.teamDTOtoTeamEntity(teamDTO);
        teamEntity = teamRepository.save(teamEntity);
        return TeamMapper.INSTANCE.teamEntityToTeamDTO(teamEntity);
    }

    @Override
    public void deleteTeam(Long id) throws TeamNotFoundException {

        if(!teamRepository.existsById(id))
            throw new TeamNotFoundException();

        teamRepository.deleteById(id);
    }
}