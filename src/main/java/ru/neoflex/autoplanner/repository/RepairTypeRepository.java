package ru.neoflex.autoplanner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.neoflex.autoplanner.entity.RepairType;

import java.util.List;
import java.util.Optional;

@Repository
public interface RepairTypeRepository extends JpaRepository<RepairType, Long> {

    Optional<RepairType> findById(Long Id);
}
