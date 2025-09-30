package com.example.HospitalManagmentSystemProject.utill;

import com.example.HospitalManagmentSystemProject.entity.Insurance;
import com.example.HospitalManagmentSystemProject.repository.InsuranceRepository;
import com.example.HospitalManagmentSystemProject.type.InsuranceStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PolicyExpiryScheduler {


    @Autowired
    private InsuranceRepository insuranceRepository;

    @Scheduled(cron = "0 0 0 * * ?")
    public void schedulePolicyExpiryTask() {

        List<Insurance> expiredList = insuranceRepository.findByStatusAndValidUntilBefore(InsuranceStatus.ACTIVE, LocalDate.now());
       if(!expiredList.isEmpty()){
           List<Long> ids = expiredList.stream().map(Insurance::getId).collect(Collectors.toList());
           insuranceRepository.updateInsuranceToExpired(ids);
           System.out.println("Scheduler: " + ids.size() + " policies marked as EXPIRED.");
       }else{
           System.out.println("Scheduler: No expired policies found.");
       }
    }
}
