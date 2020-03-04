package com.payline.payment.splitit.bean.nesteed;

import com.google.gson.annotations.SerializedName;

// todo: passe en private tout ce que tu peux, plus c'est encapsulé mieux c'est
public class EventsEndpoints {
    @SerializedName("CreateSucceeded")
    String createSucceeded;

    // todo oubli pas le constructeur private pour ecraser le public, sinon on pourrais creer une instance vide sans passer par le Builder
    // todo en vrai tu te fais ptet un peu chier pour rien, un Builder pour un seul champ tu pourrais juste faire un contructeur a un parametre (comme tu le sents)

    // todo: passe en private tout ce que tu peux, plus c'est encapsulé mieux c'est (meme dans le builder)
    public static class EventEndpointsBuilder {
        String createSucceeded;

        public EventEndpointsBuilder withCreateSucceeded(String createSucceeded) {
            this.createSucceeded = createSucceeded;
            return this;
        }
        public EventsEndpoints build() {
            EventsEndpoints eventsEndpoints = new EventsEndpoints();
            eventsEndpoints.createSucceeded = createSucceeded;
            return eventsEndpoints;
        }
    }

    public String getCreateSucceeded() {
        return createSucceeded;
    }

}
