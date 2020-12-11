package dadata;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;

@Getter
public class Management {
    //ФИО рководителя
    @SerializedName("name")
    private String name;

    //должность руководителя
    @SerializedName("post")
    private String post;
}
