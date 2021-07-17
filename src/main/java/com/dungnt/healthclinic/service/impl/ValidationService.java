package com.dungnt.healthclinic.service.impl;

import com.dungnt.healthclinic.dto.SignUpRequest;
import com.dungnt.healthclinic.model.Calendar;
import com.dungnt.healthclinic.model.ClinicService;
import com.dungnt.healthclinic.model.User;
import com.dungnt.healthclinic.repository.CalendarRepository;
import com.dungnt.healthclinic.repository.ClinicServiceRepository;
import com.dungnt.healthclinic.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Service
public class ValidationService {
    private UserRepository userRepository;
    private ClinicServiceRepository clinicServiceRepository;
    private CalendarRepository calendarRepository;

    @Autowired
    public ValidationService(UserRepository userRepository, ClinicServiceRepository clinicServiceRepository,
                             CalendarRepository calendarRepository) {
        this.userRepository = userRepository;
        this.clinicServiceRepository = clinicServiceRepository;
        this.calendarRepository = calendarRepository;
    }

    public void validateCredentials(SignUpRequest signUpRequest) throws Exception {
        if (signUpRequest == null) {
            throw new Exception("Khong co thong tin dang ky");
        }
        if (signUpRequest.getName() == null) {
            throw new Exception("Khong co thong tin ten");
        }
        if (signUpRequest.getUsername() == null) {
            throw new Exception("Khong co thong tin tai khoan");
        }
        if (signUpRequest.getPassword() == null) {
            throw new Exception("Khong co thong tin mat khau");
        }
        if (signUpRequest.getEmail() == null) {
            throw new Exception("Khong co thong tin email");
        }
        if (signUpRequest.getRole() == null) {
            throw new Exception("Khong co thong tin role");
        }
        String username = signUpRequest.getUsername();
        String password = signUpRequest.getPassword();
        if (!username.matches("[0-9]+") || username.length() < 10) {
            throw new Exception("Tai khoan phai la so dien thoai");
        }
        User user = userRepository.findByUsername(username);
        if (user != null) {
            throw new Exception("So dien thoai da ton tai");
        }
        if (password.length() < 8) {
            throw new Exception("Mat khau phai co toi thieu 8 ky tu");
        }
        boolean checkUppercase = false;
        boolean checkDigit = false;
        boolean checkLetter = false;
        for (int i = 0; i< password.length(); i++) {
            char ch = password.charAt(i);
            if (Character.isLetter(ch)) {
                checkLetter = true;
            }
            if (Character.isDigit(ch)) {
                checkDigit = true;
            }
            if (Character.isUpperCase(ch)) {
                checkUppercase = true;
            }
            if (checkLetter && checkDigit && checkUppercase) {
                break;
            }
        }
        if (!checkLetter || !checkDigit || !checkUppercase) {
            throw new Exception("Mat khau khong du manh");
        }
    }

    /**
     *
     * @param user
     * @throws Exception
     * @description Kiem tra tham sao cho api update user
     */
    public void checkNullParameter(User user) throws Exception {
        if (user.getName() == null) {
            throw new Exception("Khong co thong tin ten");
        }
        if (user.getDateOfBirth() == null) {
            throw new Exception("Khong co thong tin ten");
        }
        if (user.getAddress() == null) {
            throw new Exception("Khong co thong tin dia chi");
        }
        if (user.getEmail() == null) {
            throw new Exception("Khong co thong tin dia chi email");
        }
        if (user.getCountry() == null) {
            throw new Exception("Khong co thong tin quoc gia");
        }
        if (user.getGender() == null) {
            throw new Exception("Khong co thong tin gioi tinh");
        }

    }

    public void validateCalendar(Calendar calendar) throws Exception {
        if (calendar == null) {
            throw new Exception("Doi tuong calendar null");
        }
        if (calendar.getDate() == null) {
            throw new Exception("Gia tri date null");
        }
        if (calendar.getTimeStart() == null) {
            throw new Exception("Gia tri thoi gian bat dau null");
        }
        if (calendar.getTimeEnd() == null) {
            throw new Exception("Gia tri thoi gian ket thuc null");
        }
        if (calendar.getState() == null) {
            throw new Exception("Gia tri state null");
        }


        boolean check = true;
        List<Calendar> calendarList = calendarRepository.findAllByMedicalStaff(calendar.getMedicalStaff());
        LocalTime timeStart = calendar.getTimeStart();
        LocalTime timeEnd = calendar.getTimeEnd();

        if (timeEnd.compareTo(timeStart) <= 0) {
            throw new Exception("Thoi gian bat dau phai o truoc thoi gian ket thuc");
        }

        for (Calendar calendarTmp: calendarList) {
            if (calendarTmp.getDate().isEqual(calendar.getDate())) {
                LocalTime timeStartTmp = calendarTmp.getTimeStart();
                LocalTime timeEndTmp = calendarTmp.getTimeEnd();

                if (timeEnd.compareTo(timeStartTmp) > 0 && timeEnd.compareTo(timeEndTmp) <= 0  ) {
                    if (calendar.getId() == null || (calendar.getId() != null && calendar.getId() != calendarTmp.getId())) {
                        check = false;
                        break;
                    }
                }
                if (timeEnd.compareTo(timeEndTmp) > 0) {
                    if (timeStart.compareTo(timeEndTmp) < 0) {
                        if (calendar.getId() == null || (calendar.getId() != null && calendar.getId() != calendarTmp.getId())) {
                            check = false;
                            break;
                        }
                    }
                }
            }
        }
        if (!check) {
            throw new Exception("Thoi gian ban chon khong phu hop do trung voi lich kham da co");
        }
    }

}
