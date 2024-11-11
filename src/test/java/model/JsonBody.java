package model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.util.List;

public class JsonBody {

    private String last_name;
    private String first_name;
    private String patronymic;
    private LocalDate date_of_birth;
    @JsonFormat(pattern = "dd.MM.yyyy")

    public String getLast_name() {
        return last_name;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public LocalDate getDate_of_birth() {
        return date_of_birth;
    }

    public List<Group_number> getGroup_number() {
        return group_number;
    }

    private List<Group_number> group_number;



    public static class Group_number {
        public Integer getValue() {
            return value;
        }

        private Integer value;

    }
}
