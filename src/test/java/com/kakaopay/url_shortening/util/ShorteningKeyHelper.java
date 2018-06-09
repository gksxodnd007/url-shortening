package com.kakaopay.url_shortening.util;

import org.apache.commons.validator.routines.UrlValidator;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;

public class ShorteningKeyHelper {

    private String testUrl = "https://www.kakaopay.com";

    private enum Protocol {
        HTTP(1, "http"),
        HTTPS(2, "https");

        private int protocolCode;
        private String protocolName;

        Protocol(int protocolCode, String protocolName) {
            this.protocolCode = protocolCode;
            this.protocolName = protocolName;
        }

        public int getProtocolCode() {
            return this.protocolCode;
        }

        public String getProtocolName() {
            return this.protocolName;
        }
    }

    //TODO URL을 1000개쯤 초기화해서 하나씩 돌려 보면서 shortening url이 얼마나 겹치나 count 해보자?
    private String generateShorteningUrl() {
        StringBuilder shorteningUrl = new StringBuilder();

        if (Protocol.HTTPS.protocolName.equals(parseProtocolFromUrl(testUrl))) {
            shorteningUrl.append(Protocol.HTTPS.protocolCode);
        }
        else if (Protocol.HTTP.protocolName.equals(parseProtocolFromUrl(testUrl))) {
            shorteningUrl.append(Protocol.HTTP.protocolCode);
        }

        shorteningUrl.append(extractCharacter(getStringAfterBase64UrlEncoding(removeSchemeFromUrl(testUrl)), 3));
        shorteningUrl.append(extractCharacter(getStringAfterBase64UrlEncoding(Long.toString(System.currentTimeMillis())), 3));

        return shorteningUrl.toString();
    }

    @Test
    public void generateShorteningUrlTest() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 1000; i ++) {
            list.add(generateShorteningUrl());
        }

        List<String> removeDuplicateElem = list.stream().distinct().collect(Collectors.toList());
        BiPredicate<List<String>, List<String>> testApi = (listA, listB) -> listA.size() - listB.size() < 5;
        Assert.assertTrue(testApi.test(list, removeDuplicateElem));
    }

    @Test
    public void parseProtocolFromUrlTest() {
        Assert.assertEquals(testUrl.split(":")[0], Protocol.HTTPS.protocolName);
    }

    @Test
    public void isValidUrlTest() {
        UrlValidator urlValidator = new UrlValidator();
        Assert.assertTrue(urlValidator.isValid(testUrl));
    }

    @Test
    public void removeSchemeFromUrl() {
        String url = "https://www.kakaopay.com";
        String[] resultSplitUrl = url.split(":");
        Assert.assertEquals(resultSplitUrl[1].substring(2, resultSplitUrl[1].length()), "www.kakaopay.com");
    }

    private String parseProtocolFromUrl(String url) {
        return url.split(":")[0];
    }

    private String extractCharacter(String str, int count) {
        StringBuilder strBuilder = new StringBuilder();
        char[] charArray = str.toCharArray();
        for (int index = 0; index < count; index++) {
            strBuilder.append(charArray[(int) (Math.random() * charArray.length)]);
        }
        return strBuilder.toString();
    }

    private String removeSchemeFromUrl(String url) {
        String[] resultSplitUrl = url.split(":");
        return resultSplitUrl[1].substring(2, resultSplitUrl[1].length());
    }

    private String getStringAfterBase64UrlEncoding(String str) {
        return new String(Base64.getUrlEncoder().encode(str.getBytes()));
    }

}
