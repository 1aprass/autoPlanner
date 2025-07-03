package ru.neoflex.autoplanner.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
//import ru.neoflex.autoplanner.dto.VehicleDto;
import ru.neoflex.autoplanner.entity.Vehicle;
import ru.neoflex.autoplanner.repository.VehicleRepository;
import ru.neoflex.autoplanner.service.VehicleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/vehicle")
public class VehicleController {

    private static final Logger logger = LoggerFactory.getLogger(VehicleController.class);

//    @Autowired
//    private VehicleService vehicleService;
//
//    @Autowired
//    private VehicleRepository vehiclerepo;
//
//
//
//    @PostMapping
//    public ResponseEntity addVehicle(@RequestBody VehicleDto dto){
//        try{
//            logger.info("POST /vehicle с данными: {}", dto);
//            VehicleDto saved = vehicleService.addVehicle(dto);
//            return ResponseEntity.ok(saved);
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body("Ошибка");
//        }
//    }
//
//    @GetMapping
//    public ResponseEntity getVehicle(){
//        try{
//            return ResponseEntity.ok("Сервер работает");
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body("Ошибка");
//        }
//    }
}
