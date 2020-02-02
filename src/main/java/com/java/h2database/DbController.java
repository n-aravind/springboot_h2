package com.java.h2database;

import com.java.h2database.model.UpdateRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
public class DbController {

    private static final Logger log = LoggerFactory.getLogger(DbController.class);

    @Autowired
    JdbcTemplate jdbcTemplate;

    @RequestMapping(method = RequestMethod.GET, path = "/data", params = "id")
    public List<Billionaires> getData(@RequestParam String id) {
        return jdbcTemplate.query("select * from billionaires where id = " + Integer.parseInt(id),
                (rs, rowNum) -> new Billionaires(rs.getLong("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("career")));
    }

    @RequestMapping(method = RequestMethod.POST, path = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String addBillionaire(@RequestBody UpdateRequest updateRequest) {

        try {
            jdbcTemplate.batchUpdate("insert into billionaires (first_name, last_name, career) VALUES (?,?,?)", Collections.singletonList(new Object[]{updateRequest.getFirstName(), updateRequest.getLastName(), updateRequest.getCareer()}));
        } catch (Exception e) {
            log.error(e.getMessage());
            return "Data not inserted into the table";
        }
        return "Successful";
    }
}
