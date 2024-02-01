package com.nitconf.backend.request;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class profileReq {
    public String name;
    public String mail;
    public String contact;
    public String profession;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public java.util.Date doj;
}
