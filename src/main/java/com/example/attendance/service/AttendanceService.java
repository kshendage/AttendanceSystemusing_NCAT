package com.example.attendance.service;

import com.example.attendance.model.Attendance;
import com.example.attendance.model.Device;
import com.example.attendance.repository.AttendanceRepository;
import com.example.attendance.repository.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

@Service
public class AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private DeviceRepository deviceRepository;

    @Value("${netcat.server.ip}")
    private String netcatServerIp;

    @Value("${netcat.server.port}")
    private int netcatServerPort;

    public int getNetcatServerPort() {
        return netcatServerPort;
    }

    @jakarta.annotation.PostConstruct
    public void init() {
        new Thread(this::listenForAttendanceData).start();
    }

    public void listenForAttendanceData() {
        try (ServerSocket serverSocket = new ServerSocket(netcatServerPort)) {
            System.out.println("Listening for incoming data on port " + netcatServerPort);

            while (true) {
                try (Socket clientSocket = serverSocket.accept();
                     BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {

                    String data = reader.readLine();
                    if (data != null) {
                        System.out.println("Received data: " + data);
                        processAttendanceData(data);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error while listening for data: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void processAttendanceData(String data) {
        // Format: "Employee: 13245, Timestamp: 2024-12-24T10:15:30, Action: check-in, Device: office_1_device_001"
        String[] parts = data.split(", ");
        Long employeeId = Long.parseLong(parts[0].split(": ")[1]);  // Changed to Long
        String timestamp = parts[1].split(": ")[1];
        String action = parts[2].split(": ")[1];
        String deviceId = parts[3].split(": ")[1];

        // Fetch or create the device from the database
        Device device = deviceRepository.findById(deviceId).orElseGet(() -> {
            Device newDevice = new Device();
            newDevice.setDeviceId(deviceId);
            newDevice.setDeviceName("Unknown Device");
            deviceRepository.save(newDevice);
            return newDevice;  // Return the newly created device
        });

        // Create and save the attendance record
        Attendance attendance = new Attendance();
        attendance.setEmployeeId(employeeId);
        attendance.setTimestamp(java.time.LocalDateTime.parse(timestamp));
        attendance.setAction(action);
        attendance.setDevice(device);  // Set the actual device object

        attendanceRepository.save(attendance);
        System.out.println("Attendance data saved: " + attendance);
    }
}
