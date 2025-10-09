package com.moonstack.repository;

import com.moonstack.entity.DeviceData;
import com.moonstack.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DeviceDataRepository extends JpaRepository<DeviceData,String>
{
    Optional<DeviceData> findByDeviceIdAndUser(String deviceId, User user);

}
