package com.payline.payment.splitit.bean.nesteed;
// todo attention au import inutiles (ctrl + alt + O) tu peux meme faire ca sur un package ;)
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

// todo: passe en private tout ce que tu peux, plus c'est encapsulé mieux c'est
public class QueryCriteria {
    @SerializedName("InstallmentPlanNumber")
    String installmentPlanNumber;

    // todo oubli pas le constructeur private pour ecraser le public, sinon on pourrais creer une instance vide sans passer par le Builder

    public static class QueryCriteriaBuilder {
        String installmentPlanNumber;

        public QueryCriteriaBuilder withInstallmentPlanNumber(String installmentPlanNumber) {
            this.installmentPlanNumber = installmentPlanNumber;
            return this;
        }

        public QueryCriteria build() {
            QueryCriteria queryCriteria = new QueryCriteria();
            queryCriteria.installmentPlanNumber = installmentPlanNumber;
            return queryCriteria;
        }
    }

    public String getInstallmentPlanNumber() {
        return installmentPlanNumber;
    }

}
