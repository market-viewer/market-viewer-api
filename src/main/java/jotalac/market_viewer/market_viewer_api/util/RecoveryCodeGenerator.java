package jotalac.market_viewer.market_viewer_api.util;

import java.security.SecureRandom;

public class RecoveryCodeGenerator {

    private static final String CHARS = "ABCDEFGHJKLMNPQRSTUVWXYZ23456789";
    private static final SecureRandom random = new SecureRandom();

    public static String generateRecoveryCode() {
        StringBuilder sb = new StringBuilder();

        sb.append(getRandomGroup(4));
        sb.append("-");
        sb.append(getRandomGroup(4));

        return sb.toString();
    }

    private static String getRandomGroup(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(CHARS.charAt(random.nextInt(CHARS.length())));
        }
        return sb.toString();
    }


}
