package com.test.dux.controller;

import com.test.dux.dto.ErrorResponse;
import com.test.dux.dto.TeamDTO;
import com.test.dux.exception.TeamNotFoundException;
import com.test.dux.service.TeamService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
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
@ApiResponses(value = {
        @ApiResponse(responseCode = "500",
                description = "Error interno en el servidor, revisar logs",
                content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = ErrorResponse.class),
                        examples = @ExampleObject(value = "{\"mensaje\": \"Ocurrio un error inesperado\", \"codigo\": \"500\"}")
                )
        ),

        @ApiResponse(responseCode = "401", description = "Debes autenticarte mediante un jwt",
                content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = ErrorResponse.class),
                        examples = @ExampleObject(value = "{\"mensaje\": \"Autenticacion no valida: Token JWT invalido o ausente\", \"codigo\": \"401\"}")
                )),

        @ApiResponse(responseCode = "403", description = "Estas autenticado pero no tienes permisos", content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponse.class),
                examples = @ExampleObject(value = "{\"mensaje\": \"Autorizacion no valida: debe presentar los permisos necesarios\", \"codigo\": \"403\"}")
        ))
})
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
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Los equipos se obtuvieron con exito"),
    })
    @GetMapping()
    public ResponseEntity<List<TeamDTO>> getAllTeams() {

        log.info("Se recibe peticion para obtener todos los equipos");
        List<TeamDTO> teamEntityList = teamService.getAllTeams();
        log.info("Equipos encontrados {}", teamEntityList);

        return ResponseEntity.ok(teamEntityList);

    }

    @Operation(
            summary = "Obtener un equipo por el id",
            description = "Obtiene un solo equipo correspondiente al id proporcionado"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se encontro el equipo con exito"),
            @ApiResponse(responseCode = "404", description = "El equipo con el id no existe", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class),
                    examples = @ExampleObject(value = "{\"mensaje\": \"Equipo no encontrado\", \"codigo\": \"404\"}")
            )),
    })
    @GetMapping("/{id}")
    public ResponseEntity<TeamDTO> getTeamById(@PathVariable Long id) throws TeamNotFoundException {
        log.info("Se recibe peticion para obtener el equipo con id {}", id);
        return ResponseEntity.ok(teamService.getTeamById(id));

    }

    @Operation(
            summary = "Buscar los equipos que contengan el nombre proporcionado",
            description = "Obtiene una lista de equipos que contengan en el nombre el parametro asociado"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se obtuvieron todas las coincidencias"),
    })
    @GetMapping("/buscar")
    public ResponseEntity<List<TeamDTO>> getTeamById(@RequestParam(value = "nombre") String nombre) {
        log.info("Se recibe peticion para obtener los equipos que contengan:  {}", nombre);
        List<TeamDTO> dtoList = teamService.getTeamsByName(nombre);
        log.info("Equipos encontrados {}", dtoList);
        return ResponseEntity.ok().body(dtoList);

    }

    @Operation(
            summary = "Actualizar valores de un equipo",
            description = "Cambiara los valores de un equipo"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se logro actualizar el equipo"),
            @ApiResponse(responseCode = "400", description = "No se pudo procesar la peticion, se necesita todos los datos del equipo para actualizar (id no es obligatorio)", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class),
                    examples = @ExampleObject(value = "{\"mensaje\": \"La solicitud es invalida\", \"codigo\": \"400\"}")
            )),
            @ApiResponse(responseCode = "404", description = "No existe ningun equipo con el id proporcionado", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class),
                    examples = @ExampleObject(value = "{\"mensaje\": \"Equipo no encontrado\", \"codigo\": \"404\"}")
            )),
    })
    @PutMapping("/{id}")
    public ResponseEntity<TeamDTO> updateTea(@PathVariable Long id, @RequestBody @Valid TeamDTO teamDTO) throws TeamNotFoundException {
        teamDTO.setId(null);
        log.info("Se recibe peticion para actualizar el equipo con id: {} con estos valores {} ", id, teamDTO);
        TeamDTO updatedTeam = teamService.updateTeam(id, teamDTO);
        return ResponseEntity.ok().body(updatedTeam);
    }

    @Operation(
            summary = "Crear un equipo",
            description = "Se creara en la base de datos un equipo nuevo"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Se crea exitosamente el equipo"),
            @ApiResponse(responseCode = "400", description = "No se pudo procesar la peticion, se necesita todos los datos del equipo para crear (id no es obligatorio) ",content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class),
                    examples = @ExampleObject(value = "{\"mensaje\": \"La solicitud es invalida\", \"codigo\": \"400\"}")
            )),
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
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Se elimino exitosamente el equipo"),
            @ApiResponse(responseCode = "404", description = "No se encontro el equipo correspondiente al id para la eliminacion", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class),
                    examples = @ExampleObject(value = "{\"mensaje\": \"Equipo no encontrado\", \"codigo\": \"404\"}")
            )),
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeam(@PathVariable Long id) throws TeamNotFoundException {
        teamService.deleteTeam(id);
        return ResponseEntity.noContent().build();
    }
}