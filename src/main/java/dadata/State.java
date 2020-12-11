package dadata;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;

@Getter
public class State {
    //Дата актуальности сведений
    @SerializedName("actuality_date")
    private long actualityDate;

    //Дата регистрации
    @SerializedName("registration_date")
    private long registrationDate;

    //Дата ликвидации
    @SerializedName("liquidation_date")
    private long liquidationDate;

    /**статус организации
     ACTIVE       — действующая
     LIQUIDATING  — ликвидируется
     LIQUIDATED   — ликвидирована
     BANKRUPT     — банкротство (февраль 2021)
     REORGANIZING — в процессе присоединения к другому юрлицу, с последующей ликвидацией
     */
    @SerializedName("status")
    private String status;

    //Детальный коде
    //https://github.com/hflabs/party-state/blob/master/party-state.csv
    @SerializedName("code")
    private int code;
}
