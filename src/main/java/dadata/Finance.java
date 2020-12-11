package dadata;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;

@Getter
public class Finance {
    //доходы по бух. отчетности
    @SerializedName("income")
    private Long income;

    //расходы по бух. отчетности
    @SerializedName("expense")
    private Long expense;

    //недоимки по налогам
    @SerializedName("debt")
    private Long debt;

    //налоговые штрафы
    @SerializedName("penalty")
    private Long penalty;
}
