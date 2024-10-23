package config;

public class ApiConfig {
    public static String host="https://todo.ly";
    public static String user="api2024@2024.com";
    public static String pwd="12345";

    public static final String CREATE_PROJECT=host+"/api/projects.json";
    public static final String UPDATE_PROJECT=host+"/api/projects/{id}.json";
    public static final String READ_PROJECT=host+"/api/projects/{id}.json";
    public static final String DELETE_PROJECT=host+"/api/projects/{id}.json";

    public static final String CREATE_ITEM = host + "/api/items.json";
    public static final String DELETE_ITEM = host + "/api/items/{id}.json";

    public static final String CREATE_USER = host + "/api/user.json";
    public static final String UPDATE_USER = host + "/api/user/0.json";
    public static final String DELETE_USER = host + "/api/user/0.json";
    public static final String LOGIN_ENDPOINT = host + "/api/authentication/token.json";
    public static final String DELETE_TOKEN_ENDPOINT = host + "/api/authentication/token.json";
}
