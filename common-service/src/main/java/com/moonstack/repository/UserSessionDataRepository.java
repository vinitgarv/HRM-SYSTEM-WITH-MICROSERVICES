package com.moonstack.repository;

import com.moonstack.entity.DeviceData;
import com.moonstack.entity.User;
import com.moonstack.entity.UserSessionData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserSessionDataRepository extends JpaRepository<UserSessionData,String>
{
    Optional<UserSessionData> findByUserAndDeviceData(User user, DeviceData deviceData);

    Optional<UserSessionData> findByDeviceData(DeviceData deviceData);
}
