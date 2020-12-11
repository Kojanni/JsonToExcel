package dadata;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;

@Getter
public class Authorities {
    //ИФНС регистрации
    @SerializedName("fts_registration")
    private Registration ftsRegistration;

    //ИФНС отчётности
    @SerializedName("fts_report")
    private Registration ftsReport;

    //Отделение Пенсионного фонда
    @SerializedName("pf")
    private Registration pf;

    //Отделение Фонда соц. страхования
    @SerializedName("sif")
    private Registration sif;

@Getter
    public class Registration {
        //код гос. органа
        @SerializedName("type")
        private String type;

        //код отделения
        @SerializedName("code")
        private Long code;

        //наименование отделения
        @SerializedName("name")
        private String name;

        //адрес отделения одной строкой
        @SerializedName("address")
        private String address;
    }
}
