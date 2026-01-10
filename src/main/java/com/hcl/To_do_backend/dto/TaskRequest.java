package com.hcl.To_do_backend.dto;

import com.hcl.To_do_backend.entity.Priority;
import com.hcl.To_do_backend.entity.Status;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class TaskRequest {
    private String description;
    private Priority priority;
    private Status status;
    private LocalDate deadline;
}


//package com.hcl.To_do_backend.dto;
//
//import com.hcl.To_do_backend.entity.Priority;
//import com.hcl.To_do_backend.entity.Status;
//import com.fasterxml.jackson.annotation.JsonFormat;
//import lombok.Getter;
//import lombok.Setter;
//
//import java.time.LocalDate;
//
//@Getter
//@Setter
//public class TaskRequest {
//
//    private String description;
//    private Priority priority;
//    private Status status;
//
//    @JsonFormat(pattern = "yyyy-MM-dd") // ✅ FIX
//    private LocalDate deadline;
//}
