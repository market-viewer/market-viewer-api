package jotalac.market_viewer.market_viewer_app.service.auth;

import jakarta.validation.constraints.NotNull;
import jotalac.market_viewer.market_viewer_app.entity.RecoveryCode;
import jotalac.market_viewer.market_viewer_app.entity.User;
import jotalac.market_viewer.market_viewer_app.repository.RecoveryCodeRepository;
import jotalac.market_viewer.market_viewer_app.util.RecoveryCodeGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static jotalac.market_viewer.market_viewer_app.config.Constants.RECOVERY_CODES_NUMBER;

@Service
@RequiredArgsConstructor
public class RecoveryCodeService {
    private final PasswordEncoder passwordEncoder;
    private final RecoveryCodeRepository recoveryCodeRepository;

    @Transactional
    public List<String> generateAndSaveCodes(User user) {
        List<String> rawCodes = new ArrayList<>();

        for (int i = 0; i < RECOVERY_CODES_NUMBER; i++) {
            String rawCode = RecoveryCodeGenerator.generateRecoveryCode();
            rawCodes.add(rawCode);

            //hash and save the code
            String hashedCode = passwordEncoder.encode(rawCode);
            RecoveryCode recoveryCode = new RecoveryCode(user, hashedCode, false);
            recoveryCodeRepository.save(recoveryCode);
        }

        return rawCodes;
    }

    @Transactional
    public Boolean validateCode(User user, String recoveryCode) {
        List<RecoveryCode> userCodes = recoveryCodeRepository.findByOwner(user);

        return userCodes.stream()
                .anyMatch(code -> passwordEncoder.matches(recoveryCode, code.getHashedCode()));
    }

}
