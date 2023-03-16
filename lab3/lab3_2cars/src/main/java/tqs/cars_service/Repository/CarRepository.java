package tqs.cars_service.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tqs.cars_service.Model.Car;

@Repository
public interface CarRepository extends JpaRepository<Car, Long>{
    public Optional<Car> findByCarId(Long carId);
    public List<Car> findAll();
}
