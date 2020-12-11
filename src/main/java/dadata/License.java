package dadata;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;

import java.util.List;

@Getter
public class License {
        //серия документа
        @SerializedName("series")
        private Long series;

        //номер документа
        @SerializedName("number")
        private String number;

        //дата выдачи
        @SerializedName("issue_date")
        private Long issueDate;

        //название выдавшего органа
        @SerializedName("issue_authority")
        private String issueAuthority;

        //дата приостановки
        @SerializedName("suspend_date")
        private Long suspendDate;

        //название приостановившего органа
        @SerializedName("suspend_authority")
        private String suspendAuthority;

        //дата начала действия
        @SerializedName("valid_from")
        private Long validFrom;

        //дата окончания действия
        @SerializedName("valid_to")
        private Long validTo;

        //перечень лицензируемых видов деятельности
        @SerializedName("activities")
        private List<String> activities;

        //перечень адресов, по которым действует лицензия
        @SerializedName("addresses")
        private List<String> addresses;

}
