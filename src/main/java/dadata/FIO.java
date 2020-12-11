package dadata;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class FIO {
    @SerializedName("surname")
    private String surname;

    @SerializedName("name")
    private String name;

    @SerializedName("patronymic")
    private String patronymic;

    @SerializedName("gender")
    private String gender;

    @SerializedName("source")
    private String source;

    @SerializedName("qc")
    private String qc;
}
