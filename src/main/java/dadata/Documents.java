package dadata;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;

@Getter
public class Documents {
    //Свидетельство о регистрации в налоговой
    @SerializedName("fts_registration")
    private Registration ftsRegistration;

    //Свидетельство о регистрации в Пенсионном фонде
    @SerializedName("pf_registration")
    private Registration pfRegistration;

    //Свидетельство о регистрации в Фонде соц. страхования
    @SerializedName("sif_registration")
    private Registration sifRegistration;

    //Запись в реестре малого и среднего предпринимательства
    @SerializedName("smb")
    private Smb smb;

    @Getter
    public class Registration {
        //тип документа (= FTS_REGISTRATION)
        @SerializedName("type")
        private String type;

        //серия документа
        @SerializedName("series")
        private Long series;

        //номер документа
        @SerializedName("number")
        private Long number;

        //дата выдачи
        @SerializedName("issue_date")
        private Long issueDate;

        //код подразделения
        @SerializedName("issue_authority")
        private String issueAuthority;
    }

    @Getter
    public class Smb {
        //тип документа (= SMB)
        @SerializedName("type")
        private String type;

        //категория предприятия (MICRO, SMALL или MEDIUM)
        @SerializedName("category")
        private String category;

        //дата регистрации в реестре
        @SerializedName("issue_date")
        private Long issueDate;
    }
}
