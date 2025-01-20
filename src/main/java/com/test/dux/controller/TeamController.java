package com.test.dux.controller;

import com.test.dux.dto.TeamDTO;
import com.test.dux.exception.TeamNotFoundException;
import com.test.dux.service.TeamService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(
            summary = "Obtener todos los equipos",
            description = "Obtiene un array de los equipos que estan almacenados"
    )
    @ApiResponses(value ={
            @ApiResponse(responseCode = "200", description = "Los equipos se obtuvieron con exito"),
            @ApiResponse(responseCode = "500", description = "Error interno en el servidor, revisar logs")
    })
    @GetMapping()
    public ResponseEntity<List<TeamDTO>> getAllTeams() {

        log.info("Se recibe peticion para obtener todos los equipos");
        List<TeamDTO> teamEntityList = teamService.getAllTeams();
        log.info("Equipos encontrados {}",teamEntityList);

        return ResponseEntity.ok(teamEntityList);

    }
    @Operation(
            summary = "Obtener un equipo por el id",
            description = "Obtiene un solo equipo correspondiente al id proporcionado"
    )
    @ApiResponses(value ={
            @ApiResponse(responseCode = "200", description = "Se encontro el equipo con exito"),
            @ApiResponse(responseCode = "404", description = "El equipo con el id no existe"),
            @ApiResponse(responseCode = "500", description = "Error interno en el servidor, revisar logs")
    })
    @GetMapping("/{id}")
    public ResponseEntity<TeamDTO> getTeamById(@PathVariable Long id) throws TeamNotFoundException {
        log.info("Se recibe peticion para obtener el equipo con id {}",id);
        return ResponseEntity.ok(teamService.getTeamById(id));

    }

    @Operation(
            summary = "Buscar los equipos que contengan el nombre proporcionado",
            description = "Obtiene una lista de equipos que contengan en el nombre el parametro asociado"
    )
    @ApiResponses(value ={
            @ApiResponse(responseCode = "200", description = "Se obtuvieron todas las coincidencias"),
            @ApiResponse(responseCode = "500", description = "Error interno en el servidor, revisar logs")
    })
    @GetMapping("/buscar")
    public ResponseEntity<List<TeamDTO>> getTeamById(@RequestParam(value = "nombre") String nombre) {
        log.info("Se recibe peticion para obtener los equipos que contengan:  {}",nombre);
        List<TeamDTO> dtoList = teamService.getTeamsByName(nombre);
        log.info("Equipos encontrados {}",dtoList);
        return ResponseEntity.ok().body(dtoList);

    }
    @Operation(
            summary = "Actualizar valores de un equipo",
            description = "Cambiara los valores de un equipo por el id en el path"
    )
    @ApiResponses(value ={
            @ApiResponse(responseCode = "200", description = "Se obtuvieron logro actualizar el equipo"),
            @ApiResponse(responseCode = "400", description = "No se pudo procesar la peticion, se necesita todos los  del equipo para actualizar"),
            @ApiResponse(responseCode = "404", description = "No existe ningun equipo con el id proporcionado"),
            @ApiResponse(responseCode = "500", description = "Error interno en el servidor, revisar logs")
    })
    @PutMapping("/{id}")
    public ResponseEntity<TeamDTO> updateTea(@PathVariable Long id, @RequestBody @Valid TeamDTO teamDTO) throws TeamNotFoundException {
        teamDTO.setId(null);
        log.info("Se recibe peticion para actualizar el equipo con id: {} con estos valores {} ",id,teamDTO );
        TeamDTO updatedTeam = teamService.updateTeam(id, teamDTO);
        return ResponseEntity.ok().body(updatedTeam);
    }
    @Operation(
            summary = "Crear un equipo",
            description = "Se creara en la base de datos un equipo nuevo"
    )
    @ApiResponses(value ={
            @ApiResponse(responseCode = "201", description = "Se crea exitosamente el equipo"),
            @ApiResponse(responseCode = "400", description = "No se pudo procesar la peticion, se necesita todos los  del equipo para crear"),
            @ApiResponse(responseCode = "500", description = "Error interno en el servidor, revisar logs")
    })
    @PostMapping("")
    public ResponseEntity<TeamDTO> createTeam(@RequestBody @Valid TeamDTO teamDTO) {
        TeamDTO savedTeamDTO = teamService.saveTeam(teamDTO);
        return new ResponseEntity<>(savedTeamDTO, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Eliminar un equipo",
            description = "Se eliminara el equipo con el id asociado"
    )
    @ApiResponses(value ={
            @ApiResponse(responseCode = "204", description = "Se elimino exitosamente el equipo"),
            @ApiResponse(responseCode = "404", description = "No se encontro el equipo correspondiente al id para la eliminacion"),
            @ApiResponse(responseCode = "500", description = "Error interno en el servidor, revisar logs")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteTeam(@PathVariable Long id) throws TeamNotFoundException {
        teamService.deleteTeam(id);
        return ResponseEntity.noContent().build();
    }
}