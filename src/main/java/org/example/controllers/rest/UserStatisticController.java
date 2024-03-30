package org.example.controllers.rest;

import lombok.AllArgsConstructor;
import org.example.controllers.dtos.UserStatisticDTO;
import org.example.model.statistic.user.UserStatistic;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@AllArgsConstructor
@RequestMapping("/api/stats")
public class UserStatisticController {
    //    @Autowired
    private final UserStatistic userStatistic;
//    @Autowired
//    private final ModelMapper modelMapper;

    @GetMapping
    public UserStatisticDTO getStats(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
                                     @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        return new UserStatisticDTO(userStatistic, from, to);
    }
}
