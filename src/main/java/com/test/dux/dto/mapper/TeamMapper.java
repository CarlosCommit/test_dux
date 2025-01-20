package com.test.dux.dto.mapper;

import com.test.dux.dto.TeamDTO;
import com.test.dux.entity.TeamEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface TeamMapper {

    TeamMapper INSTANCE = Mappers.getMapper(TeamMapper.class);

    TeamDTO teamEntityToTeamDTO(TeamEntity teamEntity);
    List<TeamDTO> teamEntitiesToTeamDTOs(List<TeamEntity> teamEntities);

    TeamEntity teamDTOtoTeamEntity(TeamDTO teamDTO);
}

