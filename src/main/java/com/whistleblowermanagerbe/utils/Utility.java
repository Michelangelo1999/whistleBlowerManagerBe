package com.whistleblowermanagerbe.utils;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Utility {

    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public static String listToString(List<String> listItem){
        return listItem.stream().map(Objects::toString).collect(Collectors.joining(" | "));
    }

    public static List<String> stringToList(String item){
        return new ArrayList<>(Arrays.asList(item.split(" | ")));
    }
}
