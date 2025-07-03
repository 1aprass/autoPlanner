package ru.neoflex.autoplanner.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
//import ru.neoflex.autoplanner.dto.VehicleDto;
import ru.neoflex.autoplanner.entity.Vehicle;
//import ru.neoflex.autoplanner.mapper.VehicleMapper;
import ru.neoflex.autoplanner.repository.VehicleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class VehicleService {

    private static final Logger logger = LoggerFactory.getLogger(VehicleService.class);
    @Autowired
    private VehicleRepository vehicleRepository;

//    @Autowired
//    private VehicleMapper vehicleMapper;

//    public VehicleDto addVehicle(VehicleDto dto){
//        logger.info("бренд = {}, модель = {}", dto.getBrand(), dto.getModel());
//
//        //Vehicle vehicle = vehicleMapper.toEntity(dto);
//
//        Vehicle vehicle = new Vehicle();
//        vehicle.setBrand(dto.getBrand());
//        // vehicle.setModel(dto.getModel());
//
//        logger.info("Entity после маппинга: бренд = {}, модель = {}", vehicle.getBrand(), vehicle.getModel());
//        Vehicle saved = vehicleRepository.save(vehicle);
//        logger.info("Entity после сохранения, id = {}", saved.getId());
//        VehicleDto response = vehicleMapper.toDto(saved);
//        logger.info("DTO ответа после маппинга: бренд = {}, модель = {}", response.getBrand(), response.getModel());
//
//        return response;

//    }

}
