package org.youcode.hunters_leagues.web.api.hunt;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.youcode.hunters_leagues.domain.Hunt;
import org.youcode.hunters_leagues.service.dto.HuntDto;
import org.youcode.hunters_leagues.service.implementations.HuntServiceImpl;

import org.youcode.hunters_leagues.web.vm.HuntResponseVm;
import org.youcode.hunters_leagues.web.vm.mapper.HuntVmMapper;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/hunts")
public class HuntController {

    private final HuntServiceImpl huntServiceImp;
    private final HuntVmMapper huntVmMapper;

    public HuntController(HuntServiceImpl huntServiceImp, HuntVmMapper huntVmMapper) {
        this.huntServiceImp = huntServiceImp;
        this.huntVmMapper = huntVmMapper;
    }

    @PostMapping("/save")
    public ResponseEntity<Map<String, Object>> addHunt(@RequestBody @Valid HuntDto huntDto){
        Hunt CreatedHunt = huntServiceImp.save(huntDto);
        HuntResponseVm huntResponseVm = huntVmMapper.toVM(CreatedHunt);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Hunt created successfully");
        response.put("hunt", huntResponseVm);
        return new  ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
