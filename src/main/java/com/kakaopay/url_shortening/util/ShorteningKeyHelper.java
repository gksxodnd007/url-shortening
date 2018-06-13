package com.kakaopay.url_shortening.util;


import java.util.Base64;

public class ShorteningKeyHelper {

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

    private ShorteningKeyHelper() {

    }

    /**
     *
     * Generate shortening URL : within 8 charactors
     * Algorithm
     * 1 번째 문자 : Protocol종류
     * 2 ~ 4 번째 문자 : schemes를 제외한 url의 base64 인코딩후 결과 출력되는 문자들 중 랜덤 3글자
     * 5 ~ 7 번째 문자 : shortening URL을 요청받은 시점의 서버의 시스템 시간을 base64 인코딩한 결과 출력되는 문자들 중 랜덤 3글자
     *
     * **/
    public static String generateShortenedUrlKey(String url) {
        final String protocol = parseProtocolFromUrl(url);
        StringBuilder shorteningUrl = new StringBuilder();

        if (Protocol.HTTPS.protocolName.equals(protocol)) {
            shorteningUrl.append(Protocol.HTTPS.protocolCode);
        }
        else if (Protocol.HTTP.protocolName.equals(protocol)) {
            shorteningUrl.append(Protocol.HTTP.protocolCode);
        }

        shorteningUrl.append(extractCharacter(getStringAfterBase64UrlEncoding(removeSchemeFromUrl(url)), 3));
        shorteningUrl.append(extractCharacter(getStringAfterBase64UrlEncoding(Long.toString(System.currentTimeMillis())), 3));

        return shorteningUrl.toString();
    }

    public static String parseProtocolFromUrl(String url) {
        return url.split(":")[0];
    }

    private static String extractCharacter(String str, int count) {
        StringBuilder strBuilder = new StringBuilder();
        char[] charArray = str.toCharArray();
        for (int index = 0; index < count; index++) {
            strBuilder.append(charArray[(int) (Math.random() * charArray.length)]);
        }
        return strBuilder.toString();
    }

    private static String removeSchemeFromUrl(String url) {
        String[] resultSplitUrl = url.split(":");
        return resultSplitUrl[1].substring(2, resultSplitUrl[1].length());
    }

    private static String getStringAfterBase64UrlEncoding(String url) {
        return new String(Base64.getUrlEncoder().encode(url.getBytes()));
    }

}
