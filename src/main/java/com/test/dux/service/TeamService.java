package com.test.dux.service;

import com.test.dux.dto.TeamDTO;
import com.test.dux.exception.TeamNotFoundException;

import java.util.List;

public interface TeamService  {

    public List<TeamDTO> getAllTeams();
    List<TeamDTO> getTeamsByName(String name);
    TeamDTO getTeamById(Long id) throws TeamNotFoundException;
    TeamDTO updateTeam(Long id, TeamDTO updatedTeam)throws TeamNotFoundException;
    TeamDTO saveTeam(TeamDTO teamDTO);
    void deleteTeam(Long id) throws TeamNotFoundException;
}
