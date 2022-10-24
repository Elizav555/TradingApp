package com.elizav.tradingapp.data.model.promo;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "GetCCPromo")
public class GetCCPromo{
    String lang;
    @XmlElement(name="lang")
    public void setLang(String lang) {
        this.lang = lang;
    }
}
