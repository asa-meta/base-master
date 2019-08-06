package com.asa.meta.metaparty.model;

import com.asa.meta.helpers.language.LocalManageUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SizeModel {

    public List<LanguageBean> getSwitchData() {
        List<LanguageBean> languageBeans = new ArrayList<>();
        for (Map.Entry<Integer, String> entry : LocalManageUtil.languageHashMap.entrySet()) {
            languageBeans.add(new LanguageBean(entry.getKey(), entry.getValue()));
        }
        return languageBeans;
    }

    public static class LanguageBean {
        public int type;
        public String language;

        public LanguageBean(int type, String language) {
            this.type = type;
            this.language = language;
        }
    }
}
