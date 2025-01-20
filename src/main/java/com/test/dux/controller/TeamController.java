package com.test.dux.controller;

import com.test.dux.dto.TeamDTO;
import com.test.dux.exception.TeamNotFoundException;
import com.test.dux.service.TeamService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/equipos")
public class TeamController {

    private final TeamService teamService;
    private Logger log = LoggerFactory.getLogger(TeamController.class);
    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @GetMapping()
    public ResponseEntity<List<TeamDTO>> getAllTeams() {

        log.info("Se recibe peticion para obtener todos los equipos");
        List<TeamDTO> teamEntityList = teamService.getAllTeams();
        log.info("Equipos encontrados {}",teamEntityList);

        return ResponseEntity.ok(teamEntityList);

    }

    @GetMapping("/{id}")
    public ResponseEntity<TeamDTO> getTeamById(@PathVariable Long id) throws TeamNotFoundException {
        log.info("Se recibe peticion para obtener el equipo con id {}",id);
        return ResponseEntity.ok(teamService.getTeamById(id));

    }


    @GetMapping("/buscar")
    public ResponseEntity<List<TeamDTO>> getTeamById(@RequestParam(value = "nombre") String nombre) {
        log.info("Se recibe peticion para obtener los equipos que contengan:  {}",nombre);
        List<TeamDTO> dtoList = teamService.getTeamsByName(nombre);
        log.info("Equipos encontrados {}",dtoList);
        return ResponseEntity.ok().body(dtoList);

    }

    @PutMapping("/{id}")
    public ResponseEntity<TeamDTO> updateTea(@PathVariable Long id, @RequestBody @Valid TeamDTO teamDTO) throws TeamNotFoundException {
        teamDTO.setId(null);
        log.info("Se recibe peticion para actualizar el equipo con id: {} con estos valores {} ",id,teamDTO );
        TeamDTO updatedTeam = teamService.updateTeam(id, teamDTO);
        return ResponseEntity.ok().body(updatedTeam);
    }

    @PostMapping("")
    public ResponseEntity<TeamDTO> createTeam(@RequestBody @Valid TeamDTO teamDTO) {
        TeamDTO savedTeamDTO = teamService.saveTeam(teamDTO);
        return new ResponseEntity<>(savedTeamDTO, HttpStatus.CREATED);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteTeam(@PathVariable Long id) throws TeamNotFoundException {
        teamService.deleteTeam(id);
        return ResponseEntity.noContent().build();
    }
}