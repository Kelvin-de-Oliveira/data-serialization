package model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class HumanJsonSerializer {

    private Gson gson;

    public HumanJsonSerializer() {
        gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd")
                .setPrettyPrinting()
                .create();
    }

    public String toJson(Human human) {
        return gson.toJson(human);
    }

    public String toJson(List<Human> humans) {
        return gson.toJson(humans);
    }

    public Human fromJson(String json) {
        return gson.fromJson(json, Human.class);
    }

    public List<Human> listFromJson(String json) {
        Type listType = new TypeToken<List<Human>>(){}.getType();
        return gson.fromJson(json, listType);
    }
}