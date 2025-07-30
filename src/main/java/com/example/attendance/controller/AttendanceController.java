package com.example.attendance.controller;

import com.example.attendance.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    @GetMapping("/start-listening")
    public String startListening() {
        // Start listening to incoming Netcat data in a new thread
        new Thread(() -> attendanceService.listenForAttendanceData()).start();
        return "Started listening for Netcat data on port " + attendanceService.getNetcatServerPort();
    }
}
