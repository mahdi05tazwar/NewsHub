package com.mahditaz.newshub.util;

public class Utils {
    public static final String API_KEY = APIKeyHolder.API_KEY; // APIKeyHolder.API_KEY will not be on GitHub; developers will have to add their own key here.
    public static final String BASE_URL = "https://newsapi.org/v2/";
    public static final int pageSize = 10;
    public static final String[] articleLanguages = {"en"};
    public static final String sortBy = "publishedAt";
    public static final int numPages = 10;
    public static String currentCategory = "Entertainment";
    public static String currentQuery = "";
}
