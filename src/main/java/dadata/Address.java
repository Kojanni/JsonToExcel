package dadata;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.Getter;

@Getter
public class Address {
    //адрес одной строкой
    @SerializedName("value")
    private String value;

    //адрес одной строкой (полный, с индексом)
    @SerializedName("unrestricted_value")
    private String unrestrictedValue;

    //— гранулярный адрес
    @SerializedName("data")
    private DataAddress data;


@Data
    public class DataAddress {
        //— адрес одной строкой как в ЕГРЮЛ
        @SerializedName("source")
        private String source;

        //код проверки адреса
        //  0        — адрес распознан уверенно
        //  1 или 3  — требуется ручная проверка
        @SerializedName("qc")
        private String qc;

    }
}
