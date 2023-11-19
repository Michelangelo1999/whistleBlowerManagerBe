package com.whistleblowermanagerbe.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Utility {

    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    public static final DateTimeFormatter FORMATTER_ORA = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
    private static final BCryptPasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();

    public static String listToString(List<String> listItem){
        return listItem.stream().map(Objects::toString).collect(Collectors.joining(" | "));
    }

    public static List<String> stringToList(String item){
        return new ArrayList<>(Arrays.asList(item.split("\\|")));
    }

    public static String encryptPassword(String password) {
        return PASSWORD_ENCODER.encode(password);
    }

    // Verifica una password rispetto a una versione crittografata
    public static boolean verifyPassword(String password, String hashedPassword) {
        return PASSWORD_ENCODER.matches(password, hashedPassword);
    }

    public static String getDataFormattata(LocalDate date){
        try{
            return date.format(FORMATTER);
        }catch (Exception e){
            return null;
        }
    }
}
